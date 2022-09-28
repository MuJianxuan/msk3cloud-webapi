package com.msk3cloud.core.params;

import com.google.gson.annotations.SerializedName;
import com.kingdee.bos.webapi.sdk.SaveParam;

/**
 * desc: 扩展保存参数
 *
 * @author Rao
 * @Date 2022/02/17
 **/
public class K3CloudSaveParam<T> extends SaveParam<T> {

    /**
     * 自动提交和审核
     */
    @SerializedName("IsAutoSubmitAndAudit")
    private Boolean isAutoSubmitAndAudit;

    public K3CloudSaveParam(T data) {
        super(data);
    }

    public void setAutoSubmitAndAudit(Boolean autoSubmitAndAudit) {
        isAutoSubmitAndAudit = autoSubmitAndAudit;
    }

    public Boolean getAutoSubmitAndAudit() {
        return isAutoSubmitAndAudit;
    }
}
