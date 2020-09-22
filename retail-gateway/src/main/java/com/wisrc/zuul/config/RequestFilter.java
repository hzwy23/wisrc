package com.wisrc.zuul.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: 主要用于拦截http请求，添加对跨域请求的处理逻辑
 * 设置了3个响应头信息，分别是：Access-Control-Allow-Origin，Access-Control-Allow-Headers，Access-Control-Allow-Methods
 */
//@Configuration
//@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
//public class RequestFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("开发环境--设置返回头信息");
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Headers", "*");
//        response.setHeader("Access-Control-Allow-Methods", "*");
//        filterChain.doFilter(servletRequest, response);
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
