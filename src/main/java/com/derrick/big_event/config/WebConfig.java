package com.derrick.big_event.config;

import com.derrick.big_event.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Register LoginInterceptor
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/api/v1/user/login", "/api/v1/user/register");
    }
}
