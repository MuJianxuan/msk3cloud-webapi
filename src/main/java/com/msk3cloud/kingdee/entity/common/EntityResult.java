package com.msk3cloud.kingdee.entity.common;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Rao
 * @Date 2022/01/11
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EntityResult implements Serializable {
    private static final long serialVersionUID = 878350242883896042L;

    /**
     * 实体主键ID
     */
    @JSONField(name = "Id")
    private String id;

    /**
     * 实体编码
     */
    @JSONField(name = "Number")
    private String number;


}
