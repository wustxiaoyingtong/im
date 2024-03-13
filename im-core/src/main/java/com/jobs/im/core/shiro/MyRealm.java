package com.jobs.im.core.shiro;

import java.util.Objects;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.jobs.im.core.config.FeignBeanAgent;
import com.jobs.im.core.jwt.JwtToken;
import com.jobs.im.feign.client.RbacApiClient;
import com.jobs.im.feign.dto.ReqAuthenticationInfo;
import com.jobs.im.feign.dto.RspAuthenticationInfo;

/**
 * @program: im
 * @ClassName: MyRealm
 * @description:
 * @author: Author
 * @create: 2024-02-23 10:58
 * @Version 1.0
 **/
@ConditionalOnBean({CredentialsMatcher.class, FeignBeanAgent.class})
@Component("myRealm")
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private RbacApiClient rbacApiClient;

    public MyRealm(@Qualifier("credentialsMatcher") CredentialsMatcher credentialsMatcher) {
        super();
        this.setCredentialsMatcher(credentialsMatcher);
        this.setCachingEnabled(true);
        // 只缓存 登陆的token
        this.setAuthenticationCachingEnabled(true);
        // 不缓存 权限信息 换成在service 层控制缓存
        this.setAuthorizationCachingEnabled(false);
        this.setAuthenticationCacheName("authenticationCache");
        this.setAuthorizationCacheName("authorizationCache");
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * Description: 只有当检测用户需要权限或者需要判定角色的时候才会走
     *
     * @param principalCollection
     * @return AuthorizationInfo
     * @throws
     * @author Author
     * @date 2024/2/23 11:03
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
    }

    /**
     * Description: 默认使用此方法进行用户名正确与否验证, 如果没有权限注解的话就不会去走上面的方法只会走这个方法，其实就是过滤器传过来的token， 然后进行
     * 验证authenticationToken.toString()获取的就是你的token字符串,然后你在里面做逻辑验证就好了,没通过的话直接抛出异常就可以了
     *
     * @param authenticationToken
     * @return AuthenticationInfo
     * @throws
     * @author Author
     * @date 2024/2/23 11:04
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
        throws AuthenticationException {
        String accessToken = (String)authenticationToken.getCredentials();
        RspAuthenticationInfo rspAuthenticationInfo =
            rbacApiClient.getAuthenticationInfo(ReqAuthenticationInfo.builder().accessToken(accessToken).build());
        if (Objects.isNull(rspAuthenticationInfo)) {
            throw new AuthenticationException("认证失败");
        }
        if (!rspAuthenticationInfo.getSuccess()) {
            throw new AuthenticationException(rspAuthenticationInfo.getMessage());
        }
        // 构造认证信息, 放入shiro.调用CredentialsMatcher检验密码
        return new SimpleAuthenticationInfo(accessToken, accessToken, getName());
    }
}
