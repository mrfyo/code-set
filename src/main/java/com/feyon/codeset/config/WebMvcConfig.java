package com.feyon.codeset.config;

import com.feyon.codeset.controller.interceptor.AuthInterceptor;
import com.feyon.codeset.controller.interceptor.TimeCastApiInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Feng Yong
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TimeCastApiInterceptor())
                .addPathPatterns("/api/**");

        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/api/**");
    }
}
