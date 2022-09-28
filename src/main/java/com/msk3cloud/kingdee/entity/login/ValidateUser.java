package com.msk3cloud.kingdee.entity.login;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Rao
 * @Date 2022/01/06
 **/
@Data
public class ValidateUser implements Serializable {
    private static final long serialVersionUID = 5979627404059630605L;
    @SerializedName("Message")
    private String message;
    @SerializedName("MessageCode")
    private String messagecode;
    @SerializedName("LoginResultType")
    private Integer loginresulttype;
    @SerializedName("Context")
    private Context context;
    @SerializedName("KDSVCSessionId")
    private String kdsvcsessionid;
    @SerializedName("FormId")
    private String formid;
    @SerializedName("RedirectFormParam")
    private String redirectformparam;
    @SerializedName("FormInputObject")
    private String forminputobject;
    @SerializedName("ErrorStackTrace")
    private String errorstacktrace;
    @SerializedName("Lcid")
    private Integer lcid;
    @SerializedName("AccessToken")
    private String accesstoken;
    @SerializedName("KdAccessResult")
    private String kdaccessresult;
    @SerializedName("IsSuccessByAPI")
    private Boolean issuccessbyapi;

}