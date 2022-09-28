package com.msk3cloud.util;

import com.kingdee.bos.webapi.sdk.AppCfg;
import com.msk3cloud.config.K3CloudApiConfig;
import org.springframework.util.Assert;

/**
 * desc:
 *
 * @author Rao
 * @Date 2022/06/07
 **/
public class AppCfgUtils {

    /**
     * 配置
     * @param appCfg
     * @param k3CloudApiConfig
     */
    public static void config(AppCfg appCfg, K3CloudApiConfig k3CloudApiConfig) {

        Assert.notNull( appCfg,"appCfg is null, of  CfgUtil.getAppDefaultCfg()." );

        appCfg.setOrgNum( k3CloudApiConfig.getOrgNum() );
        appCfg.setlCID( k3CloudApiConfig.getLcId() );
        appCfg.setdCID( k3CloudApiConfig.getAcctId() );
        appCfg.setAppId( k3CloudApiConfig.getAppId() );
        appCfg.setAppSecret( k3CloudApiConfig.getAppSecret());
        appCfg.setUserName( k3CloudApiConfig.getUserName());
        appCfg.setPwd( k3CloudApiConfig.getPassword() );
        appCfg.setServerUrl( k3CloudApiConfig.getServerUrl());

        appCfg.setProxy( k3CloudApiConfig.getProxy() );

        appCfg.setConnectTimeout( k3CloudApiConfig.getConnectTimeout() );
        appCfg.setRequestTimeout( k3CloudApiConfig.getRequestTimeout() );
        appCfg.setStockTimeout( k3CloudApiConfig.getStockTimeout() );

        appCfg.setSecSmartKet( k3CloudApiConfig.getSecSmartKet());


    }
}
