package com.msk3cloud.util;

import com.kingdee.bos.webapi.sdk.RepoError;
import com.kingdee.bos.webapi.sdk.RepoRet;
import com.kingdee.bos.webapi.sdk.RepoStatus;
import com.kingdee.bos.webapi.sdk.SuccessEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author Rao
 * @Date 2022/09/08
 **/
public class ResultUtils {

    /**
     * 解析结果错误信息
     * @param repoRet
     * @return
     */
    public static String parseResultErrorMessage(RepoRet repoRet) {
        return Optional.ofNullable(repoRet.getResult().getResponseStatus())
                .map(RepoStatus::getErrors)
                .map( list -> list.get(0) )
                .map( RepoError::getMessage )
                .orElse("请求接口信息获取错误信息数据异常！");
    }

    /**
     * 解析成功信息
     * @param repoRet
     * @return
     */
    public static List<SuccessEntity> parseResultSuccessEntity(RepoRet repoRet) {
        return Optional.ofNullable(repoRet.getResult().getResponseStatus())
                .map(RepoStatus::getSuccessEntitys).orElseThrow( () -> new RuntimeException("请求接口信息获取成功信息数据异常！"));
    }
}
