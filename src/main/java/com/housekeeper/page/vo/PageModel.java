package com.housekeeper.page.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.housekeeper.page.IPagResponse;
import com.housekeeper.page.IPageRequest;


/**
 * @author yezy
 * @since 2019/2/27
 * page查询接口入参Model基类
 */
public class PageModel<P extends IPageRequest, R extends IPagResponse> {
    @Valid
    @NotNull(message = "{pageInfo.notNull}")
    private PageInfo<R> pageInfo;
    private P pageParam;

    public PageInfo<R> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<R> pageInfo) {
        this.pageInfo = pageInfo;
    }

    public P getPageParam() {
        return pageParam;
    }

    public void setPageParam(P pageParam) {
        this.pageParam = pageParam;
    }
}
