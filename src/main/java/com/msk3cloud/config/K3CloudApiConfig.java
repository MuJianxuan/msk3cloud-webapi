package com.msk3cloud.config;

import lombok.Data;

/**
 * {@link com.kingdee.bos.webapi.sdk.AppCfg}
 * @author Rao
 * @Date 2022/01/06
 **/
@Data
public class K3CloudApiConfig {

    /**
     *  也叫 DBID/DCID
     */
    private String acctId;

    /**
     * appId
     */
    private String appId;

    /**
     * app 密钥
     */
    private String appSecret;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 国际化ID
     */
    private Integer lcId = 2052;

    /**
     * 服务地址
     */
    private String serverUrl;

    /**
     * 组织num
     */
    private String orgNum;

    /**
     * ？
     */
    private String secSmartKet;

    /**
     * 连接超时
     */
    private Integer connectTimeout = 120;

    /**
     * 请求超时
     */
    private Integer requestTimeout = 120;

    /**
     * socket超时时间
     */
    private Integer stockTimeout = 120;

    /**
     * 代理地址  ip:端口
     */
    private String proxy;

}
