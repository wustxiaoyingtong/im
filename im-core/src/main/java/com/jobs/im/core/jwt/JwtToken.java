package com.jobs.im.core.jwt;

import org.apache.shiro.authc.AuthenticationToken;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @program: im
 * @ClassName: JwtToken
 * @description:
 * @author: Author
 * @create: 2024-02-23 10:56
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken implements AuthenticationToken {
    private String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
