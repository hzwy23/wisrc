package com.wisrc.zuul.config;

import com.wisrc.zuul.filter.JwtTokenSecurityFilter;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
@Slf4j
//@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
public class FeignConfiguration implements RequestInterceptor {

    @Autowired
    private JwtTokenSecurityFilter jwtTokenSecurityFilter;

    private String HEADER_LABEL = "Authorization";

    // 获取登陆信息
    @Override
    public void apply(RequestTemplate requestTemplate) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =attributes.getRequest();
        String token = jwtTokenSecurityFilter.getToken(request);
        requestTemplate.header(HEADER_LABEL, token);
    }

}
