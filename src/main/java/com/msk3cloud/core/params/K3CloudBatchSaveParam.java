package com.msk3cloud.core.params;

import com.google.gson.annotations.SerializedName;
import com.kingdee.bos.webapi.sdk.BatchSave;

import java.util.List;

/**
 * desc: 批量保存参数
 *
 * @author Rao
 * @Date 2022/04/27
 **/
public class K3CloudBatchSaveParam<T> extends BatchSave<T> {

    /**
     * 自动提交和审核
     */
    @SerializedName("IsAutoSubmitAndAudit")
    private Boolean isAutoSubmitAndAudit;

    public K3CloudBatchSaveParam(List<T> data) {
        super(data);
    }

    public Boolean getAutoSubmitAndAudit() {
        return isAutoSubmitAndAudit;
    }

    public void setAutoSubmitAndAudit(Boolean autoSubmitAndAudit) {
        isAutoSubmitAndAudit = autoSubmitAndAudit;
    }
}
