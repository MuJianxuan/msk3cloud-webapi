package com.msk3cloud.kingdee.entity.login;

import lombok.Data;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author Rao
 * @Date 2022/01/06
 **/
@Data
public class Context implements Serializable {
    private static final long serialVersionUID = -5970800946989664543L;
    @SerializedName("UserLocale")
    private String userlocale;
    @SerializedName("LogLocale")
    private String loglocale;
    @SerializedName("DBid")
    private String dbid;
    @SerializedName("DatabaseType")
    private Integer databasetype;
    @SerializedName("SessionId")
    private String sessionid;
    @SerializedName("UseLanguages")
    private List<Uselanguages> uselanguages;
    @SerializedName("UserId")
    private Integer userid;
    @SerializedName("UserName")
    private String username;
    @SerializedName("CustomName")
    private String customname;
    @SerializedName("DisplayVersion")
    private String displayversion;
    @SerializedName("DataCenterName")
    private String datacentername;
    @SerializedName("UserToken")
    private String usertoken;
    @SerializedName("CurrentOrganizationInfo")
    private Currentorganizationinfo currentorganizationinfo;
    @SerializedName("IsCH_ZH_AutoTrans")
    private Boolean ischZhAutotrans;
    @SerializedName("ClientType")
    private Integer clienttype;
    @SerializedName("WeiboAuthInfo")
    private Weiboauthinfo weiboauthinfo;
    @SerializedName("GDCID")
    private String gdcid;
    @SerializedName("TRLevel")
    private Integer trlevel;
    @SerializedName("ProductEdition")
    private Integer productedition;

}