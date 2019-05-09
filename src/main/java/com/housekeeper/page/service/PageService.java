package com.housekeeper.page.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.housekeeper.core.exception.ApiErrorException;
import com.housekeeper.database.access.MybatisAccess;
import com.housekeeper.page.IPagResponse;
import com.housekeeper.page.IPageParamHandler;
import com.housekeeper.page.IPageRequest;
import com.housekeeper.page.IPageResultHandler;
import com.housekeeper.page.configuration.PageConfiguration;
import com.housekeeper.page.vo.PageInfo;
import com.housekeeper.page.vo.PageModel;


/**
 * Page数据构建类
 *
 * @author yezy
 * @since 2019/2/19
 */
public class PageService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MybatisAccess mybatisAccess;

    private final Map<String, PageConfiguration> pageConfigurationMap;

    public PageService(MybatisAccess mybatisAccess, Map<String, PageConfiguration> pageConfigurationMap) {
        this.mybatisAccess = mybatisAccess;
        this.pageConfigurationMap = pageConfigurationMap;
    }

    /**
     * 分页查询
     * @param <R> 查询结果泛型
     * @param <P> 查询参数泛型
     * @param pageId 要查询的pageId 对应配置文件page-configuration.yml中
     * @return 查询分页结果信息
     */
    public <R extends IPagResponse, P extends IPageRequest> PageInfo<R> loadPage(String pageId, PageModel<P, R> pageModel) {
        PageInfo info = pageModel.getPageInfo();
        P param = pageModel.getPageParam();
        PageConfiguration pageConfiguration = getPageConfiguration(pageId);
        int size = info.getPageSize();
        int offset = info.getPageNum(); //客户端规定从1开始记。
        P iPageParam = getPageParam(pageConfiguration.getParamHandler(), param);
        int total = mybatisAccess.queryForTotal(pageConfiguration.getSqlId(), iPageParam);
        PageInfo pageInfo = new PageInfo(offset, size);
        pageInfo.setTotalRowNum(total);
        if (total > 0) {
            List<R> iPageResults = mybatisAccess.queryForList(pageConfiguration.getSqlId(), iPageParam, (offset - 1) * size, size);
            iPageResults = getPageResult(pageConfiguration.getResultHandler(), iPageResults, iPageParam);
            pageInfo.setPageDataList(iPageResults);
        }
        return pageInfo;
    }

    private PageConfiguration getPageConfiguration(String pageId) {
        PageConfiguration pageConfiguration = pageConfigurationMap.get(pageId);
        if (pageConfiguration == null) {
            logger.error("没有找到列表配置，pageConfigurationMap not found，pageId：{}", pageId);
            throw new ApiErrorException("Page configuration not found.");
        }
        return pageConfiguration;
    }

    private <P extends IPageRequest> P getPageParam(IPageParamHandler iPageParamHandler, P param) {
        return iPageParamHandler != null ? iPageParamHandler.handler(param) : param;
    }

    private <R extends IPagResponse, P extends IPageRequest> List<R> getPageResult(IPageResultHandler iPageResultHandler, List<R> iPageResults, P param) {
        return iPageResultHandler != null ? iPageResultHandler.handler(iPageResults, param) : iPageResults;
    }

}
