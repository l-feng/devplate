package com.example.core.list;

import android.content.Context;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.core.R;
import com.example.core.listener.OnLoadMoreListener;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class RefreshableGridView extends RefreshableView implements
        View.OnClickListener, AbsListView.OnScrollListener {

    private View contentView;
    private View loadingView;
    private TextView emptyView;
    private LinearLayout mLayoutEmpty;
    private EmptyView netErrorView;
    private CommonGridView gridView;
    private ListAdapter oriListAdapter;
    private OnLoadMoreListener loadMoreListener;
    private boolean isNoMoreData;

    public RefreshableGridView(Context context) {
        super(context);
        init(context);
    }

    public RefreshableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        contentView = LayoutInflater.from(getContext())
                .cloneInContext(getContext())
                .inflate(R.layout.refreshable_gridview, this);
        setOrientation(VERTICAL);
        netErrorView = (EmptyView) contentView.findViewById(R.id.neterror_view);
        netErrorView.showNetSetting(false);
        netErrorView.setOnClickListener(this);
        emptyView = (TextView) contentView.findViewById(R.id.empty_text);
        mLayoutEmpty = (LinearLayout) contentView.findViewById(R.id.ll_empty);
        gridView = (CommonGridView) contentView.findViewById(R.id.original_list);
        setRefreshEnabled(true);
        emptyView.setClickable(true);
        emptyView.setOnClickListener(this);
        netErrorView.setClickable(true);
        gridView.setOnScrollListener(this);
        updateUI(false);
    }

    public void setListAdapter(ListAdapter adapter) {
        oriListAdapter = adapter;
        addLoadingView(gridView);
        gridView.setAdapter(adapter);
        removeLoadingView(gridView);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.neterror_view) {
            refresh();
        } else if (v.getId() == R.id.empty_text) {
        }

    }

    @Override
    public void refresh() {
        super.refresh();
        // gridView.setVisibility(View.VISIBLE);
        hideEmptyView();
    }

    public void finishRefresh(boolean isNetError) {
        super.finishRefresh();
        updateUI(isNetError);
    }

    public void finishRefreshWithHeader(boolean isNetError) {
        super.finishRefresh();
        if (oriListAdapter == null || oriListAdapter.isEmpty()) {
            if (isNetError) {
                gridView.setVisibility(GONE);
                netErrorView.setVisibility(VISIBLE);
            } else {
                gridView.setVisibility(VISIBLE);
                hideEmptyView();
                // AppLog.getInstance().debug(getClass(), "emptyView");
                // emptyView.setVisibility(View.VISIBLE);
            }
        } else {
            gridView.setVisibility(VISIBLE);
            hideEmptyView();
            removeLoadingView(gridView);
        }
    }

    public void addHeaderView(View view) {
        if (view != null) {
            gridView.addHeaderView(view);
        }
    }

    private void updateUI(boolean isNetError) {

        if (oriListAdapter == null || oriListAdapter.isEmpty()) {
            gridView.setVisibility(GONE);
            if (isNetError) {
                netErrorView.setVisibility(VISIBLE);
            } else {
                mLayoutEmpty.setVisibility(VISIBLE);
            }
        } else {
            gridView.setVisibility(VISIBLE);
            hideEmptyView();
            removeLoadingView(gridView);
        }

    }

    private void addLoadingView(CommonGridView gridView) {
        if (loadingView == null) {
            loadingView = LayoutInflater.from(getContext())
                    .cloneInContext(getContext())
                    .inflate(R.layout.loading_item, gridView, false);
            loadingView.setDrawingCacheEnabled(false);
            gridView.addFooterView(loadingView);
        }
    }

    private void removeLoadingView(CommonGridView gridView) {
        if (loadingView != null && gridView.getFooterViewCount() > 0) {
            gridView.removeFooterView(loadingView);
            loadingView = null;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (gridView.getChildCount() > 0) {
            // AppLog.getInstance().debug(
            // getClass(),
            // "gridView.getLastVisiblePosition():"
            // + gridView.getLastVisiblePosition()
            // + " oriListAdapter.getCount() :" + (oriListAdapter.getCount()));
            if (oriListAdapter.getCount() >= 5// 避免在数目为0时出现加载View
                    && (gridView.getLastVisiblePosition() >= oriListAdapter.getCount() - 1)
                    && !isNoMoreData) {
                addLoadingView(gridView);
                // AppLog.getInstance().debug(getClass(),
                // "gridView onScroll add loading View");
            }
        }

        if (isLoadingViewShown(gridView) && loadMoreListener != null) {
            loadMoreListener.onLoad();
        }
    }

    public OnLoadMoreListener getLoadMoreListener() {
        return loadMoreListener;
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    private boolean isLoadingViewShown(CommonGridView listView) {
        int count = listView.getChildCount();
        for (int i = 0; i < count; i++) {
            if (listView.getChildAt(i).getId() == R.id.loading_item || listView.isInFooter(listView.getChildAt(i), R.id.loading_item)) {
                return true;
            }
        }
        return false;
    }

    public GridView getGridView() {
        return gridView;
    }

    @Override
    protected boolean canScroll() {
        if (gridView.getChildCount() > 0) {
            View view = gridView.getChildAt(0);
            if (view.getTop() == gridView.getListPaddingTop()
                    && gridView.getFirstVisiblePosition() == 0) {
                // the first child is on the top
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void setEmptyHint(String hint) {
        emptyView.setText(hint);
    }

    public void setEmptyHint(Spanned hint) {
        emptyView.setText(hint);
    }

    public void setNetErrorViewClickListener(View.OnClickListener l) {
        netErrorView.setOnClickListener(l);
    }

    public void hideEmptyView() {
        mLayoutEmpty.setVisibility(GONE);
        netErrorView.setVisibility(GONE);
    }

    public boolean isNoMoreData() {
        return isNoMoreData;
    }

    public void setNoMoreData(boolean isNoMoreData) {
        this.isNoMoreData = isNoMoreData;
    }

    public void setAdapter(ListAdapter adapter) {
        this.gridView.setAdapter(adapter);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.gridView.setOnItemClickListener(onItemClickListener);
    }

    public void setOnGridScroll2TopListener(CommonGridView.OnGridScroll2TopListener listener) {
        this.gridView.setOnGridScroll2TopListener(listener);
    }

    public void setOnScrollListener(AbsListView.OnScrollListener listener) {
        this.gridView.setOnScrollListener(listener);
    }

    public void setSelection(int selection) {
        this.gridView.setSelection(selection);
    }
}

