package com.wisrc.zuul.services;


import com.wisrc.zuul.utils.WhiteListToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


/**
 * 自定义身份认证
 */
@Configuration
@Slf4j
public class WhiteListAuthentication implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return new UsernamePasswordAuthenticationToken("whiteList", "whiteList");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(WhiteListToken.class);
    }

}
