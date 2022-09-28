package com.msk3cloud.kingdee.entity.login;

import lombok.Data;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Rao
 * @Date 2022/01/06
 **/
@Data
public class Currentorganizationinfo {
    @SerializedName("ID")
    private Integer id;
    @SerializedName("AcctOrgType")
    private String acctorgtype;
    @SerializedName("Name")
    private String name;
    @SerializedName("FunctionIds")
    private List<Integer> functionids;

}