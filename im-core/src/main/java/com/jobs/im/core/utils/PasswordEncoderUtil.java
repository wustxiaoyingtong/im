package com.jobs.im.core.utils;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

/**
 * @program: im
 * @ClassName: PasswordEncoderUtil
 * @description:
 * @author: Author
 * @create: 2024-02-23 15:21
 * @Version 1.0
 **/
public final class PasswordEncoderUtil {
    /**
     * Description: 加密
     *
     * @param rawPassword
     * @return String
     * @throws
     * @author Author
     * @date 2024/2/23 15:22
     **/
    public static String encode(String rawPassword) {
        /*加密过程
        1. 使用MD5算法
        2. 使用随机的盐值
        3. 循环5次
        4. 盐的处理方式为：盐 + 原密码 + 盐 + 原密码 + 盐
        注意：因为使用了随机盐，盐值必须被记录下来，本次的返回结果使用$分隔盐与密文*/
        String salt = UUID.randomUUID().toString().replace("-", "");
        String encodedPassword = rawPassword;
        for (int i = 0; i < 5; i++) {
            encodedPassword =
                DigestUtils.md5DigestAsHex((salt + encodedPassword + salt + encodedPassword + salt).getBytes());
        }
        return salt + encodedPassword;
    }

    /**
     * Description: 匹配密码
     *
     * @param rawPassword
     * @param encodedPassword
     * @return boolean
     * @throws
     * @author Author
     * @date 2024/2/23 15:22
     **/
    public static boolean matches(String rawPassword, String encodedPassword) {
        if (StringUtils.isBlank(rawPassword) || StringUtils.isBlank(encodedPassword)) {
            return false;
        }
        String salt = encodedPassword.substring(0, 32);
        String newPassword = rawPassword;
        for (int i = 0; i < 5; i++) {
            newPassword = DigestUtils.md5DigestAsHex((salt + newPassword + salt + newPassword + salt).getBytes());
        }
        newPassword = salt + newPassword;
        return newPassword.equals(encodedPassword);
    }

    public static void main(String[] args) {
        String a = "1234";
        String b = PasswordEncoderUtil.encode(a);
        System.out.println(b);
        String a1 = "aaaaaa1";
        System.out.println(PasswordEncoderUtil.matches(a, b));
    }
}
