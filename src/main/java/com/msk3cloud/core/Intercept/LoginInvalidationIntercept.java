package com.msk3cloud.core.Intercept;

import com.msk3cloud.client.K3CloudApiClient;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;


/**
 * 功能点：
 * 1、登录失效拦截
 * 2、恢复登录并重试接口返回信息
 * 3、重试登录失败处理
 *
 * 4、日志打印
 *
 * @author Rao
 * @Date 2022/01/07
 **/
@Order(1)
@Aspect
@Slf4j
public class LoginInvalidationIntercept {

    private final K3CloudApiClient k3CloudApiClient;

    public LoginInvalidationIntercept(K3CloudApiClient k3CloudApiClient) {
        this.k3CloudApiClient = k3CloudApiClient;
    }

    /**
     * 登录失效重试机制
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(value = "@within(com.msk3cloud.core.Intercept.LoginInvalidation)")
    public Object process(ProceedingJoinPoint pjp) throws Throwable {
        return this.process( pjp,0);
    }

    /**
     * 会有问题吗？
     * @param pjp
     * @return
     * @throws Throwable
     */
    public Object process(ProceedingJoinPoint pjp,int count) throws Throwable {
        Object result;
        try {
            if (! this.k3CloudApiClient.isAuthStatus() ) {
                this.k3CloudApiClient.authApiClient();
            }
            result = pjp.proceed();
        } catch (Exception ex){
            if( ex.getMessage().contains("会话信息已丢失") && count == 0){
                k3CloudApiClient.updateAuthStatus(false);
                log.warn("[k3CloudApiClient] 认证过期，已重置认证状态为失败并重试业务执行！");
                return this.process( pjp,1 );
            }

            if( ex.getMessage().contains("Session information is lost") && count == 0){
                k3CloudApiClient.updateAuthStatus(false);
                log.warn("[k3CloudApiClient] 认证过期，已重置认证状态为失败并重试业务执行！");
                return this.process( pjp,1 );
            }

            throw ex;
        }

        return result;
    }

}
