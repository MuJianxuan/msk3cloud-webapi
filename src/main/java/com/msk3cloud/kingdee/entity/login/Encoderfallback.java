package com.msk3cloud.kingdee.entity.login;

import lombok.Data;
import com.google.gson.annotations.SerializedName;

/**
 * @author Rao
 * @Date 2022/01/06
 **/
@Data
public class Encoderfallback {
    @SerializedName("DefaultString")
    private String defaultstring;
    @SerializedName("MaxCharCount")
    private Integer maxcharcount;

}
