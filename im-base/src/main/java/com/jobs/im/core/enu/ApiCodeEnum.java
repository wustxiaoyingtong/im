package com.jobs.im.core.enu;

import lombok.AllArgsConstructor;

/**
 * @program: im
 * @ClassName: ApiCode
 * @description:
 * @author: Author
 * @create: 2024-02-22 15:14
 * @Version 1.0
 **/
@AllArgsConstructor
public enum ApiCodeEnum {
    SUCCESS(200, "success"), FAIL(500, "fail"), UNAUTHORIZED(401, "unauthorized"), FORBIDDEN(403, "forbidden"),
    NOT_FOUND(404, "not_found"), METHOD_NOT_ALLOWED(405, "method_not_allowed"),
    ARGUMENT_ERROR(100001, "argument_error"), DATABASE_ERROR(100002, "database_error"),
    ACCOUNT_NOT_EXISTS(110001, "account_not_exists"), ACCOUNT_PWD_ERROR(110002, "account_pwd_error"),
    ACCOUNT_FORBIDDEN(110003, "account_forbidden"), TOKEN_EXPIRE(110004, "token_expire"),
    USERNAME_DUPLICATED(110005, "username_duplicated");

    public int code;

    public String desc;
}
