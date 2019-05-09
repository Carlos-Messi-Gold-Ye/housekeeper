package com.housekeeper.page.vo;

import java.util.List;

import javax.validation.constraints.Min;

import com.housekeeper.page.IPagResponse;


/**
 * @author yezy
 * @since 2019/2/19
 * page分页信息vo
 */
public class PageInfo<R extends IPagResponse> {

    @Min(value = 1, message = "{pageInfo.pageNum.min.one}")
    private int pageNum;
    @Min(value = 1, message = "{pageInfo.pageSize.min.one}")
    private int pageSize;
    private int totalRowNum;
    private List<R> pageDataList;

    public PageInfo() {
    }

    public PageInfo(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRowNum() {
        return totalRowNum;
    }

    public void setTotalRowNum(int totalRowNum) {
        this.totalRowNum = totalRowNum;
    }

    public List<R> getPageDataList() {
        return pageDataList;
    }

    public void setPageDataList(List<R> pageDataList) {
        this.pageDataList = pageDataList;
    }
}
