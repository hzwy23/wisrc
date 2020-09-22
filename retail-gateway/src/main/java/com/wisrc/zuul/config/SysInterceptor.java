package com.wisrc.zuul.config;

import io.micrometer.core.lang.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器，仅提供示例参考
 */
//@Configuration
//@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
//public class SysInterceptor extends HandlerInterceptorAdapter {
//
//    private final Logger logger = LoggerFactory.getLogger(SysInterceptor.class);
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        logger.info("自定义拦截器--- preHandle。{}", request.getRequestURI());
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
//        logger.info("自定义拦截器--- postHandle。{}", request.getRequestURI());
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
//        logger.info("自定义拦截器--- afterCompletion。{}", request.getRequestURI());
//    }
//
//    @Override
//    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        logger.info("自定义拦截器--- afterConcurrentHandlingStarted。{}", request.getRequestURI());
//    }
//}
