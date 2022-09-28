package com.msk3cloud.kingdee.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author Rao
 * @Date 2022/09/08
 **/
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperateParam {

    /**
     * 创建者组织内码（非必录）
     */
    @JSONField(name = "CreateOrgId")
    private Integer createOrgId;
    /**
     * 单据编码集合，数组类型，格式：[No1,No2,...]（使用编码时必录）  No1 是字符串类型
     */
    @JSONField(name = "Numbers")
    private List<String> numbers;
    /**
     * 单据内码集合，字符串类型，格式："Id1,Id2,..."（使用内码时必录）
     */
    @JSONField(name = "Ids")
    private String ids;
    /**
     * 交互标志集合，字符串类型，分号分隔，格式："flag1;flag2;..."（非必录） 例如（允许负库存标识：STK_InvCheckResult）
     */
    @JSONField(name = "InterationFlags")
    private String interationFlags;
    /**
     * 是否允许忽略交互，布尔类型，默认true（非必录）
     */
    @JSONField(name = "IgnoreInterationFlag")
    private String ignoreInterationFlag;
    /**
     * 是否启用网控，布尔类型，默认false（非必录）
     */
    @JSONField(name = "NetworkCtrl")
    private String networkCtrl;
    /**
     * 是否检验单据关联运行中的工作流实例，布尔类型，默认false（非必录）
     */
    @JSONField(name = "IsVerifyProcInst")
    private String isVerifyProcInst;


    /**
     * 转json
     * @return
     */
    public String toJson(){
        return JSON.toJSONString(this);
    }

}
