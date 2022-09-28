package com.msk3cloud.kingdee.entity.common;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 编码实体
 * @author Rao
 * @Date 2022/01/11
 **/
public class Number implements Serializable {
    private static final long serialVersionUID = 6306735496810802579L;

    /**
     * 编码
     */
    @JSONField(name = "FNumber")
    private String fNumber;

}
