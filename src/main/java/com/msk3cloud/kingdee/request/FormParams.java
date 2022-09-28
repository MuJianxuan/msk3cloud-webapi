package com.msk3cloud.kingdee.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 表单参数
 * @author Rao
 * @Date 2022/01/07
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormParams implements Serializable {

    private static final long serialVersionUID = -1029945250402345609L;
    /**
     * 创建者组织内码，字符串类型（非必录）
     */
    @JSONField(name = "CreateOrgId")
    private String CreateOrgId;

    /**
     * 单据编码，字符串类型（使用编码时必录）
     */
    @JSONField(name = "Number")
    private String Number;

    /**
     * 单据编码集合，数组类型，格式：[No1,No2,...]（使用编码时必录）
     */
    @JSONField(name = "Id")
    private Integer[] Numbers;

    /**
     * 表单内码（使用内码时必录）
     */
    @JSONField(name = "Id")
    private String Id;



    /**
     * 单据内码集合，字符串类型，格式："Id1,Id2,..."（使用内码时必录）
     */
    @JSONField(name = "Ids")
    private String Ids;

    /**
     * 工作流发起员工岗位内码，整型（非必录） 注（员工身兼多岗时不传参默认取第一个岗位）
     */
    @JSONField(name = "SelectedPostId")
    private Integer SelectedPostId;

    /**
     * 是否启用网控，布尔类型，默认false（非必录）
     */
    private Boolean NetworkCtrl;


    public FormParams(String createOrgId, String number, String id) {
        CreateOrgId = createOrgId;
        Number = number;
        Id = id;
    }
}
