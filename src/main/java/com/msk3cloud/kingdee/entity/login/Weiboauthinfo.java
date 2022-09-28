package com.msk3cloud.kingdee.entity.login;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Rao
 * @Date 2022/01/06
 **/
@Data
public class Weiboauthinfo {
    @SerializedName("Account")
    private String account;
    @SerializedName("AppKey")
    private String appkey;
    @SerializedName("AppSecret")
    private String appsecret;
    @SerializedName("TokenKey")
    private String tokenkey;
    @SerializedName("TokenSecret")
    private String tokensecret;
    @SerializedName("UserId")
    private String userid;
    @SerializedName("Charset")
    private Charset charset;
}
