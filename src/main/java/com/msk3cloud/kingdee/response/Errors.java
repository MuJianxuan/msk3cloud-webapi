package com.msk3cloud.kingdee.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Rao
 * @Date 2022/01/07
 **/
@Data
public class Errors implements Serializable {
    private static final long serialVersionUID = 977222852093123548L;
    @SerializedName("FieldName")
    private String fieldname;
    @SerializedName("Message")
    private String message;
    @SerializedName("DIndex")
    private int dindex;
}
