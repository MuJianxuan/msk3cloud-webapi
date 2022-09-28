package com.msk3cloud.kingdee.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * 最外层
 * @author Rao
 * @Date 2022/01/07
 **/
@Data
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = -4024429338507197011L;

    /**
     * 结果
     */
    @SerializedName("Result")
    @JSONField(name = "Result")
    private Result<T> result;

}
