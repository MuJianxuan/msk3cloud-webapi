package com.msk3cloud.kingdee;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.kingdee.bos.webapi.sdk.QueryParam;
import com.kingdee.bos.webapi.sdk.RepoRet;
import com.kingdee.bos.webapi.sdk.SaveParam;
import com.kingdee.bos.webapi.sdk.SuccessEntity;
import com.msk3cloud.client.K3CloudApiClient;
import com.msk3cloud.core.params.K3CloudBatchSaveParam;
import com.msk3cloud.kingdee.entity.common.EntityResult;
import com.msk3cloud.kingdee.request.OperateParam;
import com.msk3cloud.kingdee.response.Errors;
import com.msk3cloud.kingdee.response.ResponseResult;
import com.msk3cloud.kingdee.response.Result;
import com.msk3cloud.util.FieldKeyAnoUtils;
import com.msk3cloud.core.Intercept.LoginInvalidation;
import com.msk3cloud.core.params.K3CloudSaveParam;
import com.msk3cloud.kingdee.request.FormParams;
import com.msk3cloud.kingdee.response.Responsestatus;
import com.msk3cloud.util.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Rao
 * @Date 2022/01/06
 **/
@Slf4j
@AllArgsConstructor
@LoginInvalidation
public class K3cloudRemoteServiceImpl implements K3cloudRemoteService {

    protected final K3CloudApiClient k3CloudApiClient;

    @Override
    public <T> Result<T> view(String formId , FormParams formParams) throws Exception {
        String jsonData = this.k3CloudApiClient.view( formId, JSON.toJSONString( formParams));
        return JSON.parseObject(jsonData, new TypeReference< ResponseResult<T>>() {
        }).getResult();
    }

    @Override
    public <T> Result<T> submit(String formId, FormParams formParams) throws Exception {
        Gson gson = new Gson();
        String jsonData = this.k3CloudApiClient.submit( formId, gson.toJson(formParams));
        ResponseResult<T> responseResult = gson.fromJson( jsonData, new TypeToken< ResponseResult<T> >(){}.getType() );
        return responseResult.getResult();
    }

    @Override
    public <P> EntityResult saveOrUpdate(String formId, SaveParam<P> saveParam) throws Exception {
        return this.k3CloudApiClient.saveOrUpdate(formId, saveParam);
    }

    @Override
    public <P> P saveOrUpdateWithReturnObj(String formId, SaveParam<P> saveParam,Class<P> cls) throws Exception {
        String responseJson = this.k3CloudApiClient.saveOrUpdateWithReturnObj(formId, saveParam);
        return parseClassObjWithFastJson(cls, responseJson);
    }

    /**
     * 解析类对象 (有坑  日期字符串转string失败...)
     * @param cls
     * @param responseJson
     * @param <P>
     * @return
     */
    @Deprecated
    public  <P> P parseClassObjWithGson(Class<P> cls, String responseJson) {
        Gson gson = new Gson();
        ResponseResult<P> responseResult = gson.fromJson( responseJson, new TypeToken<ResponseResult<P>>(){}.getType() );
        Boolean handleSuccess = Optional.ofNullable(responseResult.getResult()).map(Result::getResponsestatus).map(Responsestatus::getIssuccess).orElse(false);
        if( ! handleSuccess){
            // 抛异常
            String errorMessage = Optional.ofNullable(responseResult.getResult())
                    .map(Result::getResponsestatus)
                    .map(Responsestatus::getErrors)
                    .map(errors -> errors.stream().map(Errors::getMessage).collect(Collectors.joining(",")))
                    .orElse("获取响应的结果信息失败，请获取请求参数手动尝试!");
            throw new RuntimeException( errorMessage);
        }
        List<P> objList = Optional.ofNullable(responseResult.getResult()).map(Result::getNeedReturnData).orElse(new ArrayList<>());
        if(CollectionUtils.isEmpty(objList )){
            log.warn("获取响应结果的值是空的，有可能序列化的字段问题，也有可能返回结果的问题，请开启debug查看金蝶返回结果的详情！");
            return null;
        }

        Object obj = objList.get(0);
        if( obj instanceof LinkedTreeMap){
            return gson.fromJson(((LinkedTreeMap) obj).toString(), cls);
        }
        if( obj instanceof JSONObject){
            return ((JSONObject) obj).toJavaObject( cls );
        }
        log.error("获取响应结果的值是有的，但我写的序列化强转不了,焯，请开启debug查看查看金蝶返回结果的详情！");
        return null;
    }

    /**
     * 解析类对象
     * @param cls
     * @param responseJson
     * @param <P>
     * @return
     */
    public  <P> P parseClassObjWithFastJson(Class<P> cls, String responseJson) {
        List<P> pList = this.parseClassObjListWithFastJson(cls, responseJson);
        if( pList.size() > 0){
            return pList.get(0);
        }
        log.error("parseClassObjWithFastJson  错误 cls:{},responseJson:{}",cls,responseJson);
        throw new RuntimeException("解析返回的数据对象失败！");
    }

    /**
     * 解析类对象集合
     * @param cls
     * @param responseJson
     * @param <P>
     * @return
     */
    public <P> List<P> parseClassObjListWithFastJson(Class<P> cls, String responseJson) {
        ResponseResult<P> responseResult = JSON.parseObject( responseJson,new TypeReference< ResponseResult<P>>(){} );
        Boolean handleSuccess = Optional.ofNullable(responseResult.getResult()).map(Result::getResponsestatus).map(Responsestatus::getIssuccess).orElse(false);
        if( ! handleSuccess){
            // 抛异常
            String errorMessage = Optional.ofNullable(responseResult.getResult())
                    .map(Result::getResponsestatus)
                    .map(Responsestatus::getErrors)
                    .map(errors -> errors.stream().map(Errors::getMessage).collect(Collectors.joining(",")))
                    .orElse("获取响应的结果信息失败，请获取请求参数手动尝试!");
            throw new RuntimeException( errorMessage);
        }
        List<P> objList = Optional.ofNullable(responseResult.getResult()).map(Result::getNeedReturnData).orElse(new ArrayList<>());
        if(CollectionUtils.isEmpty(objList )){
            log.warn("获取响应结果的值是空的，有可能序列化的字段问题，也有可能返回结果的问题，请开启debug打印查看返回结果的详情！");
            return new ArrayList<>();
        }
        return objList.stream().map(obj -> ((JSONObject) obj).toJavaObject(cls)).collect(Collectors.toList());
    }

    @Override
    public <P> P saveOrUpdateWithReturnObj(String formId, K3CloudSaveParam<P> saveParams, Class<P> cls) throws Exception {
        String responseJson = this.k3CloudApiClient.saveOrUpdateWithReturnObj(formId, saveParams.toJson());
        return this.parseClassObjWithFastJson( cls,responseJson );
    }

    @Override
    public <P> List<P> batchSaveOrUpdateWithReturnObj(String formId, K3CloudBatchSaveParam<P> batchSaveParam , Class<P> cls) throws Exception {
        String responseJson = this.k3CloudApiClient.batchSaveOrUpdateWithReturnObj(formId, batchSaveParam.toJson());
        return this.parseClassObjListWithFastJson( cls,responseJson );
    }

    @Override
    public List<JSONObject> documentQuery(QueryParam queryParam) throws Exception {

        // 返回字段值
        List<String> fieldList = Arrays.asList( queryParam.getFieldKeys().split(","));
        return this.documentQuery(queryParam, fieldList);
    }

    @Override
    public JSONObject documentQueryOne(QueryParam queryParam) throws Exception {
        List<JSONObject> jsonObjectList = this.documentQuery(queryParam);
        int size = jsonObjectList.size();
        if( size != 1 ){
            throw new RuntimeException( String.format( "want query one,but found %d !", size));
        }
        return jsonObjectList.get(0);
    }
    @Override
    public List<JSONObject> documentQuery(QueryParam queryParam, Map<String,String> fieldMap) throws Exception {
        List<List<Object>> resultList = this.k3CloudApiClient.documentQuery(queryParam);
        String[] qpParam = queryParam.getFieldKeys().split(",");
        int qpSize = queryParam.getFieldKeys().split(",").length;
        return resultList.stream().map(paramsList -> {
            JSONObject jsonObject = new JSONObject( fieldMap.size());
            for (int i = 0; i < qpSize; i++) {
                String qpField = qpParam[i];
                String field = fieldMap.get(qpField);
                if(!StringUtils.isEmpty(field)){
                    jsonObject.put(field, paramsList.get(i));
                }
            }
            return jsonObject;
        }).collect(Collectors.toList());
    }
    /**
     * documentQuery 的 底层方法
     */
    public List<JSONObject> documentQuery(QueryParam queryParam,List<String> fieldList) throws Exception {
        List<List<Object>> resultList = this.k3CloudApiClient.documentQuery(queryParam);

        final int size = fieldList.size();
        return resultList.stream().map(paramsList -> {

            JSONObject jsonObject = new JSONObject( size );

            for (int i = 0; i < size; i++) {
                String field = fieldList.get(i);
//                if( field.endsWith("Id")){
//                    String value = "{\"" + field + "\":" + JSON.toJSONString(paramsList.get(i)) + "}";
//                    jsonObject.put( field,value);
//                }else{
//
//                }
                jsonObject.put(field, paramsList.get(i));
            }
            return jsonObject;
        }).collect(Collectors.toList());

    }


    @Override
    public <T> List<T> documentQueryWithClass(QueryParam queryParam,Class<T> cls) throws Exception {

        //修复实体类字段跟查询顺序可能对不上的bug
        Map<String,String> classFieldList = FieldKeyAnoUtils.getClassFieldMap(cls);
        List<JSONObject> jsonObjectList = this.documentQuery(queryParam, classFieldList);

        return jsonObjectList.stream().map(jsonObject -> jsonObject.toJavaObject(cls) ).collect(Collectors.toList());
    }

    @Override
    public <T> T documentQueryOne(QueryParam queryParam,Class<T> cls) throws Exception {
        List<T> list = this.documentQueryWithClass( queryParam, cls);
        int size = list.size();
        if( size != 1 ){
            throw new RuntimeException( String.format( "want query one,but found %d !", size));
        }
        return list.get(0);
    }


    @Override
    public <P> P draftWithReturnObj(String formId, K3CloudSaveParam<P> saveParams, Class<P> cls) throws Exception {
        String responseJson = this.k3CloudApiClient.draftWithReturnObj(formId, saveParams);
        return this.parseClassObjWithFastJson( cls,responseJson );
    }

    @Override
    public List<SuccessEntity> delete(String formId, OperateParam operateParam) throws Exception {
        RepoRet repoRet = this.k3CloudApiClient.delete(formId, operateParam);
        return handleRepoRet(repoRet);
    }

    @Override
    public List<SuccessEntity> unAudit(String formId, OperateParam operateParam) throws Exception {
        RepoRet repoRet = this.k3CloudApiClient.unAudit(formId, operateParam);
        return handleRepoRet(repoRet);
    }

    private List<SuccessEntity> handleRepoRet(RepoRet repoRet) {
        Assert.notNull(repoRet, "调用结果repoRet is null");
        if (!repoRet.isSuccessfully()) {
            throw new RuntimeException(ResultUtils.parseResultErrorMessage(repoRet));
        }
        return ResultUtils.parseResultSuccessEntity(repoRet);
    }

}