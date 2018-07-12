package com.example.core.list;

import android.content.Context;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.core.R;
import com.example.core.listener.OnLoadMoreListener;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class RefreshableListView extends RefreshableView implements
        View.OnClickListener, AbsListView.OnScrollListener {

    private View contentView;
    private View loadingView;
    private TextView emptyView;
    private LinearLayout mLayoutEmpty;
    private EmptyView netErrorView;
    private ListView oriList;
    private ListAdapter oriListAdapter;
    private OnLoadMoreListener loadMoreListener;
    private boolean isNoMoreData;
    private boolean hasHeadView;
    private Button applyButton;

    private AbsListView.OnScrollListener scrollListener;

    public RefreshableListView(Context context) {
        super(context);
        init(context);
    }

    public RefreshableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        contentView = LayoutInflater.from(getContext())
                .cloneInContext(getContext())
                .inflate(R.layout.refreshable_listview, this);
        setOrientation(VERTICAL);
        netErrorView = (EmptyView) contentView.findViewById(R.id.neterror_view);
        netErrorView.showNetSetting(false);
        netErrorView.setOnClickListener(this);
        emptyView = (TextView) contentView.findViewById(R.id.empty_text);
        mLayoutEmpty = (LinearLayout) contentView.findViewById(R.id.ll_empty);
        oriList = (ListView) contentView.findViewById(R.id.original_list);
        applyButton= (Button) contentView.findViewById(R.id.refresh_nothing_button);
        applyButton.setOnClickListener(this);
        setRefreshEnabled(true);
        emptyView.setClickable(true);
        emptyView.setOnClickListener(this);
        netErrorView.setClickable(true);
        oriList.setOnScrollListener(this);
        updateUI(false);
    }

    public void setListAdapter(ListAdapter adapter) {
        oriListAdapter = adapter;
        addLoadingView(oriList);
        oriList.setAdapter(adapter);
        removeLoadingView(oriList);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.neterror_view) {
            refresh();
        } else if (v.getId() == R.id.head_bar_empty_text) {
        }

    }

    @Override
    public void refresh() {
        super.refresh();
        hideEmptyView();
        oriList.setSelectionAfterHeaderView();
    }

    public void finishRefresh(boolean isNetError) {
        super.finishRefresh();
        updateUI(isNetError);
    }

    public void finishRefreshWithHeader(boolean isNetError) {
        super.finishRefresh();
        if (oriListAdapter == null || oriListAdapter.isEmpty()) {
            if (isNetError) {
                oriList.setVisibility(GONE);
                netErrorView.setVisibility(VISIBLE);
            } else {
                oriList.setVisibility(VISIBLE);
                hideEmptyView();
                // AppLog.getInstance().debug(getClass(), "emptyView");
                // emptyView.setVisibility(View.VISIBLE);
            }
        } else {
            oriList.setVisibility(VISIBLE);
            hideEmptyView();
            removeLoadingView(oriList);
        }
    }

    public void addHeaderView(View view) {
        if (view != null) {
            oriList.addHeaderView(view);
            hasHeadView = true;
        }
    }

    private void updateUI(boolean isNetError) {
        if (getLayoutParams() != null && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            setLayoutParams(getLayoutParams());
        }
        if (oriListAdapter == null || oriListAdapter.isEmpty()) {
//            oriList.setVisibility(GONE);
            if (isNetError) {
                oriList.setVisibility(GONE);
                netErrorView.setVisibility(VISIBLE);
            } else {
                if (!hasHeadView) {
                    oriList.setVisibility(GONE);
                } else {
                    oriList.setVisibility(VISIBLE);
                    if (getLayoutParams() != null) {
                        getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        setLayoutParams(getLayoutParams());
                    }
                }
                mLayoutEmpty.setVisibility(VISIBLE);
            }
        } else {
            oriList.setVisibility(VISIBLE);
            hideEmptyView();
            removeLoadingView(oriList);
        }

    }

    private void addLoadingView(ListView listView) {
        if (loadingView == null) {
            loadingView = LayoutInflater.from(getContext())
                    .cloneInContext(getContext())
                    .inflate(R.layout.loading_item, listView, false);
            loadingView.setDrawingCacheEnabled(false);
            listView.addFooterView(loadingView);
        }
    }

    private void removeLoadingView(ListView listView) {
        if (loadingView != null && listView.getFooterViewsCount() > 0) {
            listView.removeFooterView(loadingView);
            loadingView = null;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollListener != null) {
            scrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (scrollListener != null) {
            scrollListener.onScroll(view, firstVisibleItem, visibleItemCount,totalItemCount);
        }
        if (oriList.getChildCount() > 0) {
            // AppLog.getInstance().debug(
            // getClass(),
            // "oriList.getLastVisiblePosition():"
            // + oriList.getLastVisiblePosition()
            // + " oriListAdapter.getCount() :" + (oriListAdapter.getCount()));
            if (oriListAdapter.getCount() >= 5// 避免在数目为0时出现加载View
                    && (oriList.getLastVisiblePosition() >= oriListAdapter.getCount() - 1)
                    && !isNoMoreData) {
                addLoadingView(oriList);
                // AppLog.getInstance().debug(getClass(),
                // "oriList onScroll add loading View");
            }
        }

        if (isLoadingViewShown(oriList) && loadMoreListener != null) {
            loadMoreListener.onLoad();
        }
    }

    public OnLoadMoreListener getLoadMoreListener() {
        return loadMoreListener;
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    private boolean isLoadingViewShown(AbsListView listView) {
        int count = listView.getChildCount();
        for (int i = 0; i < count; i++) {
            if (listView.getChildAt(i).getId() == R.id.loading_item) {
                return true;
            }
        }
        return false;
    }

    public ListView getOriList() {
        return oriList;
    }

    @Override
    protected boolean canScroll() {
        if (oriList.getChildCount() > 0) {
            View view = oriList.getChildAt(0);
            if (view.getTop() == oriList.getListPaddingTop()
                    && oriList.getFirstVisiblePosition() == 0) {
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
        oriList.removeFooterView(emptyView);
        mLayoutEmpty.setVisibility(GONE);
        netErrorView.setVisibility(GONE);
    }

    public boolean isNoMoreData() {
        return isNoMoreData;
    }

    public void setNoMoreData(boolean isNoMoreData) {
        this.isNoMoreData = isNoMoreData;
    }

    public void setScrollListener(AbsListView.OnScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public int getListY() {
        View c = oriList.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = oriList.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight() ;
    }



}

