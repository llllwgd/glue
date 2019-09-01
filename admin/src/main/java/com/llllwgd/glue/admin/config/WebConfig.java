package com.llllwgd.glue.admin.config;

import com.llllwgd.glue.admin.controller.interceptor.CookieInterceptor;
import com.llllwgd.glue.admin.controller.interceptor.PermissionInterceptor;
import com.llllwgd.glue.admin.controller.resolver.WebExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * @Author: llllwgd
 * Description:
 * Date: 2019-08-31
 * Time: 15:00
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("/favicon.ico");
        registry.addResourceHandler("/static/**").addResourceLocations("/static/**");
        registry.addResourceHandler("/**/*.html").addResourceLocations("/templates/**");
    }

    @Bean
    public FreeMarkerViewResolver getFreeMarkerViewResolver() {
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setContentType("text/html;charset=UTF-8");
        freeMarkerViewResolver.setViewClass(FreeMarkerView.class);
        freeMarkerViewResolver.setPrefix("");
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setExposeSpringMacroHelpers(true);
        freeMarkerViewResolver.setExposeRequestAttributes(true);
        freeMarkerViewResolver.setExposeSessionAttributes(true);
        freeMarkerViewResolver.setRequestContextAttribute("request");
        freeMarkerViewResolver.setCache(true);
        freeMarkerViewResolver.setOrder(0);
        return freeMarkerViewResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PermissionInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new CookieInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Bean
    public WebExceptionResolver getWebExceptionResolver() {
        return new WebExceptionResolver();
    }

}
