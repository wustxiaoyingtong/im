package com.jobs.im.core.jwt;

import java.util.Objects;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jobs.im.core.utils.WebUtil;

/**
 * @program: im
 * @ClassName: JWTFiler
 * @description:
 * @author: Author
 * @create: 2024-02-23 11:07
 * @Version 1.0
 **/
public class JWTFiler extends BasicHttpAuthenticationFilter {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        res.setHeader("Access-control-Allow-Origin", req.getHeader("Origin"));
        res.setHeader("Access-control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        res.setHeader("Access-control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            res.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * Description: preHandle执行完之后会执行这个方法 再这个方法中 我们根据条件判断去去执行isLoginAttempt和executeLogin方法。
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问。例如我们提供一个地址 GET /article 登入用户和游客看到的内容是不同的 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     *
     * @param request
     * @param response
     * @return boolean
     * @throws
     * @author Author
     * @date 2024/2/23 11:09
     **/
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        /**
         * 先去调用 isLoginAttempt方法 字面意思就是是否尝试登陆 如果为true 执行executeLogin方法
         */
        if (isLoginAttempt(request, response)) {
            if (executeLogin(request, response)) {
                return true;
            }
            response401(request, response);
            return false;
        }
        request.setAttribute("error", new AuthenticationException("凭证缺失"));
        response401(request, response);
        return false;
    }

    /**
     * Description: 这里我们只是简单去做一个判断请求头中的token信息是否为空 如果没有我们想要的请求头信息则直接返回false
     *
     * @param request
     * @param response
     * @return boolean
     * @throws
     * @author Author
     * @date 2024/2/23 11:11
     **/
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        return !Objects.isNull(WebUtil.getAccessToken());
    }

    /**
     * Description: 执行登陆，因为已经判断token不为空了,所以直接执行登陆逻辑 将token放入JwtToken类中去 然后getSubject方法是调用到了MyRealm的执行方法
     * 因为上面我是抛错的所有最后做个异常捕获就好了
     *
     * @param request
     * @param response
     * @return boolean
     * @throws
     * @author Author
     * @date 2024/2/23 11:12
     **/
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        JwtToken token = new JwtToken(WebUtil.getAccessToken());
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        try {
            getSubject(request, response).login(token);
        } catch (Exception e) {
            request.setAttribute("error", e);
            return false;
        }
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * Description: 父类的这个方法会再次调用认证接口，导致出现两次调用认证
     *
     * @param request
     * @param response
     * @return boolean
     * @throws
     * @author Author
     * @date 2024/2/23 18:01
     **/
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

    private void response401(ServletRequest req, ServletResponse resp) {
        try {
            req.getRequestDispatcher("/401").forward(req, resp);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}