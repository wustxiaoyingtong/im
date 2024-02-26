package com.jobs.im.core.advice;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.jobs.im.core.enu.ApiCodeEnum;
import com.jobs.im.core.exception.ServerException;
import com.jobs.im.core.model.ApiResult;
import com.jobs.im.core.utils.StackUtil;
import com.jobs.im.core.utils.WebUtil;

/**
 * @program: im
 * @ClassName: GlobalExceptionHandler
 * @description:
 * @author: Author
 * @create: 2024-02-23 15:32
 * @Version 1.0
 **/
@RestController
@ControllerAdvice
public class GlobalExceptionHandler implements ErrorController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getErrorPath() {
        return null;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult handlerException(Exception ex) {
        String stackTrace = StackUtil.getStackTrace(ex);
        log.error(stackTrace);
        return ApiResult.fail(ex);
    }

    /**
     * Description: 处理全局自定义异常
     *
     * @param ex
     * @param response
     * @return ApiResult
     * @throws
     * @author Author
     * @date 2024/2/23 15:45
     **/
    @ExceptionHandler(ServerException.class)
    public ApiResult handlerServerException(ServerException ex, HttpServletResponse response) {
        String stackTrace = StackUtil.getStackTrace(ex);
        log.error(stackTrace);
        return ApiResult.fail(ex.getApiCodeEnum());
    }

    /**
     * Description: 处理未登录的异常401
     *
     * @param ex
     * @return ApiResult
     * @throws
     * @author Author
     * @date 2024/2/23 15:49
     **/
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ApiResult handUnauthorizedException(AuthenticationException ex) {
        String stackTrace = StackUtil.getStackTrace(ex);
        log.error(stackTrace);
        return ApiResult.fail(ApiCodeEnum.UNAUTHORIZED, ex.getMessage());
    }

    /**
     * Description: 处理权限不足异常403
     *
     * @param ex
     * @return ApiResult
     * @throws
     * @author Author
     * @date 2024/2/23 15:50
     **/
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ApiResult handIllegalException(AuthorizationException ex) {
        String stackTrace = StackUtil.getStackTrace(ex);
        log.error(stackTrace);
        return ApiResult.fail(ApiCodeEnum.FORBIDDEN, ex.getMessage());
    }

    /**
     * Description: 处理请求方法不对异常405
     *
     * @param ex
     * @param response
     * @return
     * @throws
     * @author Author
     * @date 2024/2/23 15:58
     **/
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public void handHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex,
        HttpServletResponse response) {
        String stackTrace = StackUtil.getStackTrace(ex);
        log.error(stackTrace);
        WebUtil.renderJson(response, ApiResult.fail(ApiCodeEnum.METHOD_NOT_ALLOWED));
    }

    /**
     * Description: 处理参数错误异常
     *
     * @param ex
     * @return ApiResult
     * @throws
     * @author Author
     * @date 2024/2/23 16:04
     **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult handMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String stackTrace = StackUtil.getStackTrace(ex);
        log.error(stackTrace);
        return ApiResult.fail(ApiCodeEnum.ARGUMENT_ERROR, StackUtil.getMessage(ex));
    }

    /**
     * Description: 处理数据库访问异常
     *
     * @param ex
     * @return ApiResult
     * @throws
     * @author Author
     * @date 2024/2/23 16:06
     **/
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult handDataAccessException(DataAccessException ex) {
        String stackTrace = StackUtil.getStackTrace(ex);
        log.error(stackTrace);
        return ApiResult.fail(ApiCodeEnum.DATABASE_ERROR, ex.getMessage());
    }

    /**
     * Description: 处理找不到异常404
     *
     * @param response
     * @return
     * @throws
     * @author Author
     * @date 2024/2/23 15:56
     **/
    @RequestMapping(value = "/error")
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handleError(HttpServletResponse response) {
        WebUtil.renderJson(response, ApiResult.fail(ApiCodeEnum.NOT_FOUND));
    }

    @RequestMapping("/401")
    public void handle401(ServletRequest request) {
        Object error = request.getAttribute("error");
        if (error != null) {
            throw (RuntimeException)error;
        } else {
            throw new AuthenticationException("认证失败");
        }
    }
}
