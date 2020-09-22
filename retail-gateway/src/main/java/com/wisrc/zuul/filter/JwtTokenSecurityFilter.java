package com.wisrc.zuul.filter;

import com.wisrc.zuul.services.JwtTokenAuthentication;
import com.wisrc.zuul.utils.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 对用户进行进行判断，
 * 1. 白名单中地址，直接放过
 * 2. Jwt token校验
 * 3. 不做处理，放入登陆校验
 * 4。Oauth2 校验
 */
@Configuration
@Slf4j
public class JwtTokenSecurityFilter extends OncePerRequestFilter {

    // 匿名授权标识符
    private final String ANONYMOUS_USER = "anonymousUser";

    @Value("${token.key:Authorization}")
    private String AUTHORIZATION_HEADER_KEY = "Authorization";

    @Value("${token.salt:Bearer}")
    private String SALT_KEY = "Bearer";

    @Autowired
    private JwtTokenAuthentication jwtTokenAuthentication;



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        log.debug("Token 拦截器 -> 请求方式是：{}, 请求路由是：{}", httpServletRequest.getMethod(), httpServletRequest.getRequestURL().toString());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomHttpServletRequest  request = new CustomHttpServletRequest(httpServletRequest);

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal == null || ANONYMOUS_USER.equals(principal.toString())) {
                log.debug("从请求中获取到了Token信息，根据Token校验用户身份, authentication is: {}", authentication.getPrincipal());
                String token = getToken(httpServletRequest);

                Jws<Claims> claims = jwtTokenAuthentication.identify(token);
                if (claims != null && claims.getBody() != null && claims.getBody().get("X-AUTH-ID") != null) {
                    Object userId = claims.getBody().get("X-AUTH-ID");
                    log.debug("X-AUTH-ID is : {}", request.getHeader("X-AUTH-ID"));
                    if (userId != null && request.getHeader("X-AUTH-ID") == null) {
                        request.addHeader("X-AUTH-ID", userId.toString());
                    }
                }

                JwtAuthenticationToken customToken = new JwtAuthenticationToken(AUTHORIZATION_HEADER_KEY, token);
                SecurityContextHolder.getContext().setAuthentication(customToken);
            }
        }

        if (request.getHeader("X-AUTH-ID") == null) {
            request.addHeader("X-AUTH-ID", ANONYMOUS_USER);
        }

        filterChain.doFilter(request, httpServletResponse);
    }

    public String getToken(HttpServletRequest request) {

            String token = request.getHeader(AUTHORIZATION_HEADER_KEY);

            if (null == token || token.trim().isEmpty()) {
                log.debug("http header中Token为空，改从Cookies中获取，");
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie item : cookies) {
                        if (AUTHORIZATION_HEADER_KEY.equals(item.getName()) && !item.getValue().startsWith(SALT_KEY)) {
                            // 获取到token
                            log.info("add salt origin is : {}", item.getValue());
                            return SALT_KEY + " " + item.getValue();
                        }
                    }
                }
                // 从url中获取token
                log.debug("http cookies中token为空，改从URL中获取，");
                token = request.getParameter(AUTHORIZATION_HEADER_KEY);
                if (null == token || token.trim().isEmpty()) {
                    return null;
                }
            }
            return token;

    }

    static class CustomHttpServletRequest extends HttpServletRequestWrapper {

        private Map<String,String> headers = new HashMap<>();

        public CustomHttpServletRequest(HttpServletRequest request){
            super(request);
        }

        public void addHeader(String name,String value){
            headers.put(name, value);
        }

        @Override
        public String getHeader(String name) {
            String value=super.getHeader(name);

            if (headers.containsKey(name)){
                value=headers.get(name);
            }

            return value;
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            List<String> names=Collections.list(super.getHeaderNames());
            names.addAll(headers.keySet());

            return Collections.enumeration(names);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            List<String> list= Collections.list(super.getHeaders(name));

            if (headers.containsKey(name)){
                list.add(headers.get(name));
            }

            return Collections.enumeration(list);
        }
    }

}
