package com.wisrc.zuul.filter;


import com.google.gson.Gson;
import com.wisrc.zuul.constant.RestCodeEnum;
import com.wisrc.zuul.vo.ResultBody;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


/**
 * 当客户端使用 POST /login 发起登陆请求时，才会进入这个过滤器
 * UsernamePasswordLoginFilter 主要完成了用户名与密码的校验工作
 */
@Slf4j
@Configuration
public class UsernamePasswordLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Value("${token.secret:https://github.com/hzwy23}")
    private String AUTHORIZATION_SECRET;

    @Value("${token.key:Authorization}")
    private String AUTHORIZATION_HEADER_KEY;

    @Value("${token.salt:Bearer}")
    private String AUTHORIZATION_SALT_KEY;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UsernamePasswordLoginFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @PostConstruct
    public void init() {
        setAuthenticationManager(authenticationManager);
    }


    // 接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        log.debug("登陆请求，用户名是：{}, {}", username, password);
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    // 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        Gson gson = new Gson();

        String token = Jwts.builder()
                .setSubject(auth.getName())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, AUTHORIZATION_SECRET)
                .setId(auth.getName())
                .claim("X-AUTH-ID",auth.getPrincipal())
                .compact();

        String saltToken = AUTHORIZATION_SALT_KEY + " " + token;
        ResultBody body = ResultBody.success(RestCodeEnum.SUCCESS, saltToken);

        res.setContentType("application/json;charset=utf-8");
        Cookie cookie = new Cookie(AUTHORIZATION_HEADER_KEY, token);
        res.addCookie(cookie);
        res.addHeader(AUTHORIZATION_HEADER_KEY, saltToken);
        PrintWriter printWriter = res.getWriter();
        printWriter.print(gson.toJson(body));
        printWriter.close();

    }


    /**
     * 用户登陆失败之后，将会调用这个方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        Gson gson = new Gson();
        ResultBody body = ResultBody.success(RestCodeEnum.LOGIN_FAILED, "请检查账号和密码，重新登陆");

        response.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(gson.toJson(body));
        printWriter.close();
    }

}
