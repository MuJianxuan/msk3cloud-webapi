package com.msk3cloud.client;

import com.kingdee.bos.webapi.sdk.QueryParam;
import com.kingdee.bos.webapi.sdk.RepoRet;
import com.kingdee.bos.webapi.sdk.SaveParam;
import com.msk3cloud.kingdee.entity.common.EntityResult;
import com.msk3cloud.kingdee.request.OperateParam;

import java.util.List;

/**
 * @author Rao
 * @Date 2022/01/07
 **/
public interface ApiClient {

    /**
     * 提交
     * @param formId
     * @param data
     * @return
     * @throws Exception
     */
    @Deprecated
    String submit(String formId, String data) throws Exception;

    /**
     * view
     * @param formId
     * @param data
     * @return
     * @throws Exception
     */
    @Deprecated
    String view(String formId, String data) throws Exception;

    /**
     * 单据查询
     * @param queryParam
     * @return
     */
    List<List<Object>> documentQuery(QueryParam queryParam) throws Exception;

    /**
     * 保存或更新
     * @param <P>
     * @param formId
     * @param saveParam
     * @return
     */
    <P> EntityResult saveOrUpdate(String formId, SaveParam<P> saveParam) throws Exception;


    /**
     * 带返回对象的 保存or更新
     *   k3cloud 实际调用 is save
     * @return
     */
    <P> String saveOrUpdateWithReturnObj(String formId, SaveParam<P> saveParam) throws Exception;

    /**
     * 带返回对象的 保存or更新
     *   k3cloud 实际调用 is save
     * @return
     */
    <P> String saveOrUpdateWithReturnObj(String formId, String params) throws Exception;

    /**
     * 暂存
     *   k3cloud 实际调用 is save
     * @return
     */
    <P> String draftWithReturnObj(String formId, SaveParam<P> saveParam) throws Exception;

    /**
     * 单据反审
     * @param formId
     * @param operateParam
     * @return
     */
    RepoRet unAudit(String formId, OperateParam operateParam) throws Exception;

    /**
     * 删除单据
     * @param formId
     * @param operateParam
     * @return
     */
    RepoRet delete(String formId, OperateParam operateParam) throws Exception;

}