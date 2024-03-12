package com.jobs.im.core.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;

/**
 * @program: im
 * @ClassName: JwtUtil
 * @description:
 * @author: Author
 * @create: 2024-02-23 10:42
 * @Version 1.0
 **/
public class JwtUtil {

    /**
     * 密钥
     */
    private static final String SECRET = "wustim@8899";

    /**
     * Description: 创建token
     *
     * @param username
     * @param password
     * @return String
     * @throws
     * @author Author
     * @date 2024/2/23 10:51
     **/
    public static String createToken(String username, String password) throws UnsupportedEncodingException {
        // 设置token时间 三小时
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.DATE, 1);
        Date date = nowTime.getTime();
        // 密文生成
        return JWT.create().withClaim("username", username).withClaim("password", password).withExpiresAt(date)
            .withIssuedAt(new Date()).sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * Description: 验证token的有效性
     *
     * @param token
     * @return boolean
     * @throws
     * @author Author
     * @date 2024/2/23 10:51
     **/
    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    /**
     * Description: 获取token过期时间点
     *
     * @param token
     * @return Date
     * @throws
     * @author Author
     * @date 2024/2/23 10:55
     **/
    public static Date getExpiresAt(String token) {
        try {
            return JWT.decode(token).getExpiresAt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * Description: 获取token用户名
     *
     * @param token
     * @return String
     * @throws
     * @author Author
     * @date 2024/2/23 10:51
     **/
    public static String getUserName(String token) {
        try {
            return JWT.decode(token).getClaim("username").asString();
        } catch (Exception e) {
            return null;
        }
    }

}