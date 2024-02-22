package com.jobs.im.core.model;

import java.io.Serializable;

import com.jobs.im.core.enu.ApiCodeEnum;
import com.jobs.im.core.utils.PropertiesUtil;

import lombok.Data;

/**
 * @program: im
 * @ClassName: ApiResult
 * @description:
 * @author: Author
 * @create: 2024-02-22 11:39
 * @Version 1.0
 **/
@Data
public class ApiResult<T> implements Serializable {
    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;

    private static ApiResult of() {
        return new ApiResult();
    }

    public static ApiResult of(boolean success, Integer code, String msg) {
        ApiResult apiResult = ApiResult.of();
        apiResult.setSuccess(success);
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public static ApiResult success() {
        return of(true, ApiCodeEnum.SUCCESS.code, null);
    }

    public static ApiResult success(ApiCode apiCode) {
        return of(true, apiCode.code, apiCode.msg);
    }

    public static ApiResult success(ApiCode apiCode, String msg) {
        return of(true, apiCode.code, msg);
    }

    public static ApiResult success(ApiCodeEnum enu) {
        return success(PropertiesUtil.get(enu));
    }

    public static ApiResult success(ApiCodeEnum enu, String msg) {
        return success(PropertiesUtil.get(enu), msg);
    }

    public static ApiResult success(String msg) {
        return of(true, ApiCodeEnum.SUCCESS.code, msg);
    }

    public static <T> ApiResult success(T data) {
        ApiCode apiCode = PropertiesUtil.get(ApiCodeEnum.SUCCESS);
        ApiResult apiResult = of(true, ApiCodeEnum.SUCCESS.code, apiCode.msg);
        apiResult.setData(data);
        return apiResult;
    }

    public static ApiResult fail() {
        return of(false, ApiCodeEnum.FAIL.code, null);
    }

    public static ApiResult fail(ApiCode apiCode) {
        return of(false, apiCode.code, apiCode.msg);
    }

    public static ApiResult fail(ApiCode apiCode, String msg) {
        return of(false, apiCode.code, msg);
    }

    public static ApiResult fail(ApiCodeEnum enu) {
        return fail(PropertiesUtil.get(enu));
    }

    public static ApiResult fail(ApiCodeEnum enu, String msg) {
        return fail(PropertiesUtil.get(enu), msg);
    }

    public static ApiResult fail(String msg) {
        return of(false, ApiCodeEnum.FAIL.code, msg);
    }

    public static ApiResult fail(Exception e) {
        return of(false, ApiCodeEnum.FAIL.code, e.getMessage());
    }

    public static <T> ApiResult fail(T data) {
        ApiCode apiCode = PropertiesUtil.get(ApiCodeEnum.FAIL);
        ApiResult apiResult = of(false, ApiCodeEnum.FAIL.code, apiCode.msg);
        apiResult.setData(data);
        return apiResult;
    }
}
