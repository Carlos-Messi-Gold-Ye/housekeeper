package com.housekeeper.page;

/**
 * Page查询参数前置的接口，允许在查询前对前台传入的查询参数进行修改
 *
 * @author yezy
 * @since 2019/2/27
 */
public interface IPageParamHandler {

    /**
     * 对查询条件参数进行操作，例如根据后台逻辑增加一些查询条件
     *
     * @param iPageParam 现有查询条件
     * @return
     */
    <P extends IPageRequest> P handler(P iPageParam);
}
