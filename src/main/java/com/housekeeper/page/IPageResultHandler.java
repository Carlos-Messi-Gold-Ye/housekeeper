package com.housekeeper.page;

import java.util.List;

/**
 * Page查询结果后置的接口，允许在查询后对查询结果进行修改
 *
 * @author yezy
 * @since 2019/2/27
 */
public interface IPageResultHandler {

    /**
     * 对查询结果进行操作，例如合并字段
     * 
     * @param iPageResults 查询的现有结果
     * @param iPageParam 现有的查询条件
     * @return 更新后的查询结果集
     */
    <R extends IPagResponse, P extends IPageRequest> List<R> handler(List<R> iPageResults, P iPageParam);
}
