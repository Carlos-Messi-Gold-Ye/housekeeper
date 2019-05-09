package com.housekeeper.page.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Page查询配置信息
 *
 * @author yezy
 * @since 2019/2/28
 */
@ConfigurationProperties(prefix = "page-configuration-list")
public class PageProperties {

    private List<Page> pages;

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public static class Page {

        private String pageId;
        private String sqlId;
        private String paramHandler;
        private String resultHandler;

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

        public String getParamHandler() {
            return paramHandler;
        }

        public void setParamHandler(String paramHandler) {
            this.paramHandler = paramHandler;
        }

        public String getResultHandler() {
            return resultHandler;
        }

        public void setResultHandler(String resultHandler) {
            this.resultHandler = resultHandler;
        }

    }
}
