package com.msk3cloud.kingdee.response;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Rao
 * @Date 2022/01/07
 **/
@Data
public class Responsestatus implements Serializable {
    private static final long serialVersionUID = -4685603590108310869L;

    @SerializedName("ErrorCode")
    @JSONField(name = "ErrorCode")
    private Integer errorcode;
    @SerializedName("IsSuccess")
    @JSONField(name = "IsSuccess")
    private Boolean issuccess;
    @SerializedName("Errors")
    @JSONField(name = "Errors")
    private List<Errors> errors;
    @SerializedName("SuccessEntitys")
    @JSONField(name = "SuccessEntitys")
    private List<JSONObject> successentitys;
    @SerializedName("SuccessMessages")
    @JSONField(name = "SuccessMessages")
    private List<JSONObject> successmessages;
    @SerializedName("MsgCode")
    @JSONField(name = "MsgCode")
    private Integer msgcode;

}
