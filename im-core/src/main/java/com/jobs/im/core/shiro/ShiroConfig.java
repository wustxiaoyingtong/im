package com.jobs.im.core.shiro;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jobs.im.core.jwt.JWTFiler;

/**
 * @program: im
 * @ClassName: ShiroConfig
 * @description:
 * @author: Author
 * @create: 2024-02-23 11:12
 * @Version 1.0
 **/
@ConditionalOnBean(MyRealm.class)
@Configuration
public class ShiroConfig {
    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("myRealm") MyRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义 realm.
        securityManager.setRealm(myRealm);
        // 关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean
        shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // 添加自己的过滤器 并且取名为filter
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        // 设置自定义的JWT过滤器
        filterMap.put("jwt", new JWTFiler());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);

        // 设置无权限跳转的url 权限验证如果没权限跳转
        factoryBean.setUnauthorizedUrl("/noRole");
        Map<String, String> filterRuleMap = new HashMap<>();

        // 设置需要通过过滤器的请求 /**意思是 所有请求接口都通过自定义的jwt过滤器
        // anon的意思是 不需要走这一套逻辑 自己配置就好了
        filterRuleMap.put("/**", "jwt");
        // swagger
        filterRuleMap.put("/favicon.ico", "anon");
        filterRuleMap.put("/doc.html", "anon");
        filterRuleMap.put("/webjars/**", "anon");
        filterRuleMap.put("/v2/**", "anon");
        filterRuleMap.put("/swagger-resources/**", "anon");
        // 登录
        filterRuleMap.put("/sys/login", "anon");
        // feign
        filterRuleMap.put("/feign/**", "anon");
        // health
        filterRuleMap.put("/health/**", "anon");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);

        // 设置登录的url
        factoryBean.setLoginUrl("/sys/login");
        return factoryBean;
    }

    /**
     * 添加注解支持
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor
        authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
