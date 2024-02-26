package com.jobs.im.core.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.stereotype.Component;

/**
 * @program: im
 * @ClassName: CredentialsMatcher
 * @description: 配置自定义的密码比较器
 * @author: Author
 * @create: 2024-02-23 11:46
 * @Version 1.0
 **/
@Component("credentialsMatcher")
public class CredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        return super.doCredentialsMatch(authenticationToken, authenticationInfo);
    }
}
