package com.msk3cloud.kingdee;

import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.webapi.sdk.QueryParam;
import com.kingdee.bos.webapi.sdk.SaveParam;
import com.kingdee.bos.webapi.sdk.SuccessEntity;
import com.msk3cloud.core.params.K3CloudBatchSaveParam;
import com.msk3cloud.kingdee.entity.common.EntityResult;
import com.msk3cloud.core.params.K3CloudSaveParam;
import com.msk3cloud.kingdee.request.FormParams;
import com.msk3cloud.kingdee.request.OperateParam;
import com.msk3cloud.kingdee.response.Result;

import java.util.List;
import java.util.Map;

/**
 * 聚合接口
 * @author Rao
 * @Date 2022/01/07
 **/
public interface K3cloudRemoteService {

    /**
     * 查看
     * @return
     */
    @Deprecated
    <T> Result<T> view(String formId , FormParams formParams) throws Exception;

    /**
     * 提交
     * @param formId
     * @param formParams
     * @param <T>
     * @return
     * @throws Exception
     */
    @Deprecated
    <T> Result<T> submit(String formId,FormParams formParams) throws Exception;

    /**
     * 保存or更新
     *   k3cloud 实际调用 is save
     * @return
     */
    <P> EntityResult saveOrUpdate(String formId, SaveParam<P> saveParam) throws Exception;

    /**
     * 带返回对象的 保存or更新
     *   k3cloud 实际调用 is save
     * @return
     */
    <P> P saveOrUpdateWithReturnObj(String formId, SaveParam<P> saveParam,Class<P> cls) throws Exception;

    /**
     * 带返回对象的保存or更新
     * @param formId
     * @param saveParams
     * @param cls
     * @param <P>
     * @return
     * @throws Exception
     */
    <P> P saveOrUpdateWithReturnObj(String formId, K3CloudSaveParam<P> saveParams , Class<P> cls) throws Exception;

    /**
     * 批量保存Or更新
     * @param formId
     * @param batchSaveParam
     * @param cls
     * @param <P>
     * @return
     * @throws Exception
     */
    <P> List<P> batchSaveOrUpdateWithReturnObj(String formId, K3CloudBatchSaveParam<P> batchSaveParam , Class<P> cls) throws Exception;

    /**
     * 单据查询
     */
    List<JSONObject> documentQuery(QueryParam queryParam) throws Exception;

    /**
     * 字段以map形式
     * @param queryParam
     * @param fieldMap
     * @return
     * @throws Exception
     */
    List<JSONObject> documentQuery(QueryParam queryParam, Map<String,String> fieldMap) throws Exception;
    /**
     * 单据查询 one
     */
    JSONObject documentQueryOne(QueryParam queryParam) throws Exception;

    /**
     * 单据查询
     * @param queryParam
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> List<T> documentQueryWithClass(QueryParam queryParam,Class<T> cls) throws Exception;

    /**
     * 单据查询 one
     */
    <T> T documentQueryOne(QueryParam queryParam,Class<T> cls) throws Exception;

    /**
     * 带返回对象的保存or更新
     * @param formId
     * @param saveParams
     * @param cls
     * @param <P>
     * @return
     * @throws Exception
     */
    <P> P draftWithReturnObj(String formId, K3CloudSaveParam<P> saveParams , Class<P> cls) throws Exception;

    /**
     * 删除单据
     * @param formId
     * @return
     */
    List<SuccessEntity> delete(String formId, OperateParam operateParam) throws Exception;

    /**
     * 反审单据
     *   -- 多个单据编号提交，若是其中一个单据不存在，则会返回执行成功的。若是单据都不存在，则返回错误。
     * {
     *     "Numbers": ["SCGSL004744","SCGSL004743"],
     *     "Ids": "",
     *     "InterationFlags": "",
     *     "IgnoreInterationFlag": "",
     *     "NetworkCtrl": "",
     *     "IsVerifyProcInst": ""
     * }
     *  {"Result":{"ResponseStatus":{"IsSuccess":true,"Errors":[],"SuccessEntitys":[{"Id":105410,"Number":"SCGSL004743","DIndex":0},{"Id":105411,"Number":"SCGSL004744","DIndex":1}],"SuccessMessages":[],"MsgCode":0}}}
     * @param formId
     * @return
     */
    List<SuccessEntity> unAudit(String formId, OperateParam operateParam) throws Exception;

}

