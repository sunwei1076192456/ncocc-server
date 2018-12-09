package com.tz_tech.module.common.config;

import com.tz_tech.module.common.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 注册拦截器，否则不起作用
 */

@Component
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    LoginInterceptor loginInterceptor;

    /**
     * 不需要登录拦截的url:登录
     */
    final String[] notLoginInterceptPaths = {"/login.do"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/ncocc/**").excludePathPatterns(notLoginInterceptPaths);
        registry.addInterceptor(loginInterceptor);
        super.addInterceptors(registry);
    }
}
