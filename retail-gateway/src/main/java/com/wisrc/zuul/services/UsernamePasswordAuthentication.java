package com.wisrc.zuul.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义身份认证
 */
@Configuration
@Slf4j
public class UsernamePasswordAuthentication implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        Object username = authentication.getPrincipal();

        Object password = authentication.getCredentials();

        if (username == null || password == null) {
            log.info("用户名或密码为空，用户名是：{}", username);
            throw new BadCredentialsException("用户名或密码为空值");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username.toString());

        if (userDetails == null) {
            log.info("用户不存在，用户名是：{}", username);
            throw new BadCredentialsException("用户不存在");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("USER");
        authorities.add(simpleGrantedAuthority);
//        String saltPassword = passwordEncoder.encode(password.toString());
//        log.info("{}", saltPassword);
        if (passwordEncoder.matches(password.toString(), userDetails.getPassword())) {
            log.info("用户名密码校验成功.用户名是：{}, {}", userDetails.getUsername(), password.toString());
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), password, authorities);
        } else {
//            log.info("用户名或密码错误，用户名是：{}, {}, {}", userDetails.getUsername(), userDetails.getPassword(), password);
            throw new BadCredentialsException("用户名或密码错误");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
