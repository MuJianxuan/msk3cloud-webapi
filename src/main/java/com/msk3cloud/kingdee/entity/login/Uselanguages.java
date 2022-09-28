package com.msk3cloud.kingdee.entity.login;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Rao
 * @Date 2022/01/06
 **/
@Data
public class Uselanguages {
    @SerializedName("LocaleId")
    private Integer localeid;
    @SerializedName("LocaleName")
    private String localename;
    @SerializedName("Alias")
    private String alias;

}