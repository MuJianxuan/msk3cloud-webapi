package com.msk3cloud.kingdee.response;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 结果
 * @author Rao
 * @Date 2022/01/07
 **/
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -6340402961063811929L;

    @SerializedName("ResponseStatus")
    @JSONField(name = "ResponseStatus")
    private Responsestatus responsestatus;

    @SerializedName("ID")
    @JSONField(name = "ID")
    private String id;

    @SerializedName("Number")
    @JSONField(name = "Number")
    private String number;

    /**
     * 不知道是什么牛马
     */
    @SerializedName("NeedReturnData")
    @JSONField(name = "NeedReturnData")
    private List<T> needReturnData;

    /**
     * view 返回的结果 很诡异，先不拿对象封装
     */
    @SerializedName("Result")
    @JSONField(name = "Result")
    private JSONObject result;


}
