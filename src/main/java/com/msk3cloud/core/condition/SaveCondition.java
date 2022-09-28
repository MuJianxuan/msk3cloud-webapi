package com.msk3cloud.core.condition;

import com.kingdee.bos.webapi.sdk.SaveParam;
import com.msk3cloud.core.params.K3CloudBatchSaveParam;
import com.msk3cloud.core.params.K3CloudSaveParam;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rao
 * @Date 2022/01/13
 **/
public class SaveCondition<T> {

    /**
     * 保存 or update的实体对象
     *   存在主键Id则 update
     */
    private T entity;

    /**
     * 批量保存的对象
     */
    private List<T> entityList;

    /**
     * 需要返回的字段
     */
    private final List<String> needReturnFieldList = new ArrayList<String>();

    /**
     * 需要更新的字段
     */
    private final List<String> needUpdateFieldList = new ArrayList<String>();

    /**
     * 是否自动同步并提交
     */
    private Boolean isAutoSubmitAndAudit;

    private SaveCondition(T entity){
        this.entity = entity;
        this.isAutoSubmitAndAudit = false;
    }

    private SaveCondition(List<T> entityList){
        this.entityList = entityList;
        this.isAutoSubmitAndAudit = false;
    }

    public static <P> SaveCondition<P> build(P entity){
        return new SaveCondition<P>( entity );
    }

    public static <P> SaveCondition<P> build(List<P> entityList){
        return new SaveCondition<P>( entityList );
    }


    /**
     * 需要返回的字段
     * @param key
     * @return
     */
    public SaveCondition<T> needReturnField(String key){
        needReturnFieldList.add(key);
        return this;
    }

    /**
     * 需要返回的字段
     * @param fieldList
     * @return
     */
    public SaveCondition<T> needReturnField(List<String> fieldList){
        needReturnFieldList.addAll( fieldList );
        return this;
    }

    /**
     * 需要更新的字段
     * @param key
     * @return
     */
    public SaveCondition<T> needUpdateField(String key){
        needUpdateFieldList.add(key);
        return this;
    }

    /**
     * 需要更新的字段
     * @param fieldList
     * @return
     */
    public SaveCondition<T> needUpdateField(List<String> fieldList){
        needUpdateFieldList.addAll( fieldList );
        return this;
    }

    /**
     * 设置自动提交和审核
     * @param autoSubmitAndAudit
     * @return
     */
    public SaveCondition<T> setAutoSubmitAndAudit(Boolean autoSubmitAndAudit) {
        isAutoSubmitAndAudit = autoSubmitAndAudit;
        return this;
    }

    /**
     * 保存参数
     * @return
     */
    public SaveParam<T> saveParam(){
        SaveParam<T> saveParam = new SaveParam<>(this.entity);
        saveParam.setNeedReturnFields((ArrayList<String>) this.needReturnFieldList);
        saveParam.setNeedUpDateFields((ArrayList<String>) this.needUpdateFieldList);
        return saveParam;
    }


    /**
     * 保存参数
     * @return
     */
    public K3CloudSaveParam<T> k3CloudSaveParam(){
        K3CloudSaveParam<T> k3CloudSaveParam = new K3CloudSaveParam<>(this.entity);
        k3CloudSaveParam.setNeedReturnFields((ArrayList<String>) this.needReturnFieldList);
        k3CloudSaveParam.setNeedUpDateFields((ArrayList<String>) this.needUpdateFieldList);
        k3CloudSaveParam.setAutoSubmitAndAudit( this.isAutoSubmitAndAudit );
        return k3CloudSaveParam;
    }

    /**
     * 批量保存参数
     * @return
     */
    public K3CloudBatchSaveParam<T> k3CloudBatchSaveParam(){
        Assert.isTrue( !CollectionUtils.isEmpty( this.entityList ),"批量保存对象未设置，不允许创建批量调用参数！");
        K3CloudBatchSaveParam<T> k3CloudBatchSaveParam = new K3CloudBatchSaveParam<>(this.entityList);
        k3CloudBatchSaveParam.setNeedReturnFields((ArrayList<String>) this.needReturnFieldList);
        k3CloudBatchSaveParam.setNeedUpDateFields((ArrayList<String>) this.needUpdateFieldList);
        k3CloudBatchSaveParam.setAutoSubmitAndAudit( this.isAutoSubmitAndAudit );
        return k3CloudBatchSaveParam;
    }




}
