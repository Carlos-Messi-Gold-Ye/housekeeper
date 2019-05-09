package com.housekeeper.page.configuration;


import com.housekeeper.page.IPageParamHandler;
import com.housekeeper.page.IPageResultHandler;

/**
 * Page查询配置信息转化对象
 *
 * @author yezy
 * @since 2019/2/19
 */
public class PageConfiguration {
    private String pageId;
    private String sqlId;
    private IPageParamHandler paramHandler;
    private IPageResultHandler resultHandler;

    public PageConfiguration(String pageId, String sqlId, IPageParamHandler paramHandler, IPageResultHandler resultHandler) {
        this.pageId = pageId;
        this.sqlId = sqlId;
        this.paramHandler = paramHandler;
        this.resultHandler = resultHandler;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public IPageParamHandler getParamHandler() {
        return paramHandler;
    }

    public void setParamHandler(IPageParamHandler paramHandler) {
        this.paramHandler = paramHandler;
    }

    public IPageResultHandler getResultHandler() {
        return resultHandler;
    }

    public void setResultHandler(IPageResultHandler resultHandler) {
        this.resultHandler = resultHandler;
    }
}
