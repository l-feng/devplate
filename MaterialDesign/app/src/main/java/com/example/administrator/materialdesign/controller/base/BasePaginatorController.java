package com.example.administrator.materialdesign.controller.base;


import com.example.administrator.materialdesign.controller.page.Paginator;
import com.example.core.http.model.CommonResult;
import com.example.core.utils.StringUtils;

public abstract class BasePaginatorController extends BaseController {
    protected int page = 1;
    protected int pageSize = Integer.MAX_VALUE;
    protected boolean isLoadingMore = false;

    public String getPaginatorParameters(boolean loadMore) {
        String parameter = "";
        if (loadMore) {
            page++;
        } else {
            page = 1;
        }
        if (page > 1 && page > pageSize) {
            return null;
        }
        if (StringUtils.isNotBlank(parameter)) {
            parameter += "&";
        }
        parameter += "page=" + page;
        return parameter;
    }

    @Override
    public void complete(CommonResult result, String action) {
        isLoadingMore = false;
        super.complete(result, action);
    }

    public void setPaginator(Paginator paginator) {
        if (paginator != null) {
            this.page = paginator.getPage();
            this.pageSize = paginator.getTotalPages();
        }
    }

    public boolean hasMoreData() {
        return this.pageSize > this.page;
    }

}
