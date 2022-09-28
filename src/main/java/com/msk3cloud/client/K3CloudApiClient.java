package com.msk3cloud.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.internal.LinkedTreeMap;
import com.kingdee.bos.webapi.sdk.*;
import com.msk3cloud.constant.K3CloudApiConstants;
import com.msk3cloud.config.K3CloudApiConfig;
import com.msk3cloud.kingdee.entity.common.EntityResult;
import com.msk3cloud.kingdee.entity.login.ValidateUser;
import com.msk3cloud.kingdee.request.OperateParam;
import com.msk3cloud.util.AppCfgUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;


/**
 * @author Rao
 * @Date 2022/01/06
 **/
@Slf4j
public class K3CloudApiClient implements ApiClient {

    private final K3CloudApi k3CloudApi;
    private final K3CloudApiConfig k3CloudApiConfig;

    /**
     * 认证状态
     */
    private volatile boolean authStatus = false;
    /**
     * 认证用户信息
     */
    private volatile ValidateUser validateUser;

    public K3CloudApiClient( K3CloudApiConfig k3CloudApiConfig ) {

        // info
        Objects.requireNonNull( k3CloudApiConfig,"k3CloudApiConfig not null!");
        this.k3CloudApiConfig = k3CloudApiConfig;

        // init
        AppCfg appCfg = CfgUtil.getAppDefaultCfg();
        AppCfgUtils.config( appCfg, k3CloudApiConfig );
        this.k3CloudApi = new K3CloudApi( appCfg.getServerUrl(),appCfg.getRequestTimeout() );

        // auth
        if (! this.authApiClient() ){
            throw new RuntimeException("[K3CloudApiClient] authApiClient auth fail at start !");
        }

    }

    /**
     * 校验
     * @return
     */
    public K3CloudApi apiInstance(){
        Objects.requireNonNull( this.k3CloudApi,"[严重] 获取k3cloud api 失败，处于 K3CloudApiClient#instance 严重！" );
        return this.k3CloudApi;
    }

    /**
     * 获取
     * @return
     */
    public ValidateUser getValidateUser() {
        return validateUser;
    }

    /**
     * 获取
     * @return
     */
    public boolean isAuthStatus() {
        return authStatus;
    }

    /**
     * 更新认证状态
     * @param authStatus
     */
    public void updateAuthStatus(boolean authStatus) {
        this.authStatus = authStatus;
    }

    /**
     * 认证 apiClient
     * @return
     */
    public Boolean authApiClient( ){
        synchronized (this){
            if(! authStatus ){
                try {
                    Object[] params = Arrays.asList( k3CloudApiConfig.getAcctId(), k3CloudApiConfig.getUserName(), k3CloudApiConfig.getPassword(), k3CloudApiConfig.getLcId()).toArray();
                    this.validateUser = this.apiInstance().execute( K3CloudApiConstants.VALIDATE_USER_API, params, ValidateUser.class);
                    if( validateUser == null || validateUser.getLoginresulttype() == null || validateUser.getLoginresulttype() != 1){
                        this.authStatus = false;
                        throw new RuntimeException("[K3CloudApiClient] authApiClient auth fail,username or password error!");
                    }
                    this.authStatus = true;
                    return true;
                } catch (Exception ex){
                    this.authStatus = false;
                    log.error("[K3CloudApiClient] authApiClient auth fail !",ex);
                    return false;
                }
            }
            return true;
        }

    }


    @Override
    public String submit(String formId, String data) throws Exception {
        String result = this.apiInstance().submit(formId, data);
        return this.postProcess(formId, data, result);
    }

    @Override
    public String view(String formId, String data) throws Exception {
        String result = this.apiInstance().view(formId, data);
        return this.postProcess(formId, data, result);
    }

    @Override
    public List<List<Object>> documentQuery(QueryParam queryParam) throws Exception {
        List<List<Object>> resultList = this.apiInstance().executeBillQuery(queryParam.toJson());

        // 打印日志
        this.postProcess( queryParam.getFormId(),queryParam.toJson(),JSON.toJSONString( resultList) );

        if(CollectionUtils.isEmpty( resultList )){
            return new ArrayList<>();
        }

        // 判断返回的是正常数据
        Object responseResultObj = resultList.get(0).get(0);
        if( responseResultObj instanceof LinkedTreeMap ){
            // 说明报警了
            log.error( "client call remote api result throws exception, request fromId:{} , responseResult:{}", queryParam.getFormId(), JSON.toJSONString( responseResultObj ) );
            SaveResult saveResult = JSON.parseObject(JSON.toJSONString( responseResultObj), new TypeReference<SaveResult>(){});
            String message = this.parseResultErrorMessage(saveResult);
            throw new RuntimeException( message);
        }

        return resultList;
    }

    /**
     * 解析结果错误信息
     * @param saveResult
     * @return
     */
    private String parseResultErrorMessage(SaveResult saveResult) {
        return Optional.ofNullable(saveResult.getResult().getResponseStatus())
                .map(RepoStatus::getErrors)
                .map( list -> list.get(0) )
                .map( RepoError::getMessage )
                .orElse("请求接口信息获取数据异常！");
    }

    /**
     * 返回主键ID + 编码
     * @param <P>
     * @param formId
     * @param saveParam
     * @throws Exception
     * @return
     */
    @Override
    public <P> EntityResult saveOrUpdate(String formId, SaveParam<P> saveParam) throws Exception {
        SaveResult saveResult = this.apiInstance().save(formId, saveParam);
        // 打印日志
        this.postProcess( formId,saveParam.toJson(),JSON.toJSONString( saveResult) );

        if (! saveResult.isSuccessfully() ){
            String message = this.parseResultErrorMessage( saveResult);
            throw new RuntimeException(  message );
        }

        SuccessEntity successEntity = Optional.ofNullable( saveResult.getResult().getResponseStatus().getSuccessEntitys()).map(list -> list.get(0)).orElseThrow(() -> new RuntimeException("未获取到返回的实体信息"));
        return new EntityResult( successEntity.getId(),successEntity.getNumber() );

    }

    @Override
    public <P> String saveOrUpdateWithReturnObj(String formId, SaveParam<P> saveParam) throws Exception {
        String params = saveParam.toJson();
        String responseJson = this.apiInstance().save(formId, params);

        return this.postProcess(formId, params, responseJson);
    }

    @Override
    public <P> String saveOrUpdateWithReturnObj(String formId, String params) throws Exception {
        String responseJson = this.apiInstance().save(formId, params);

        return this.postProcess(formId, params, responseJson);
    }

    @Override
    public <P> String draftWithReturnObj(String formId, SaveParam<P> saveParam) throws Exception {
        String params = saveParam.toJson();
        String responseJson = this.apiInstance().draft(formId, params);

        return this.postProcess(formId, params, responseJson);
    }

    @Override
    public RepoRet unAudit(String formId, com.msk3cloud.kingdee.request.OperateParam operateParam) throws Exception {
        String params = operateParam.toJson();
        String responseJson = this.apiInstance().unAudit(formId, params);
        this.postProcess(formId, params, responseJson);

        return JSON.parseObject(responseJson, RepoRet.class);
    }

    @Override
    public RepoRet delete(String formId, OperateParam operateParam) throws Exception {
        String params = operateParam.toJson();
        String responseJson = this.apiInstance().delete(formId, params);
        this.postProcess(formId, params, responseJson);
        return JSON.parseObject(responseJson, RepoRet.class);
    }


    /**
     * 批量保存or更新
     * @param formId
     * @param params
     * @return
     */
    public String batchSaveOrUpdateWithReturnObj(String formId, String params) throws Exception {
        String responseJson = this.apiInstance().batchSave(formId, params);
        return this.postProcess(formId, params, responseJson);
    }

    /**
     * 后置处理
     * 校验返回数据
     * 1、日志打印方法
     * @return
     */
    private String postProcess(String formId, String params, String resultJson ){
        this.postLog(formId, params, resultJson);
        if( resultJson == null){
            log.error( "client call remote api result is null, request fromId: "+ formId );
            throw new RuntimeException("result is null!");
        }
        return resultJson;
    }

    /**
     * 日志
     * @param formId
     * @param params
     * @param resultJson
     */
    private void postLog(String formId, String params, String resultJson) {
        if (log.isInfoEnabled()) {
            log.info("[K3CloudApiClient] request fromId: {},params: {} ; response data >> {} ",formId,params,resultJson );
        }
    }


}
