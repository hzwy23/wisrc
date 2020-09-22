package com.wisrc.zuul.services;


import com.wisrc.zuul.utils.JwtAuthenticationToken;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * 自定义身份认证
 */
@Configuration
@Slf4j
public class JwtTokenAuthentication implements AuthenticationProvider {

    @Value("${token.secret:https://github.com/hzwy23}")
    private String SECRET = "https://github.com/hzwy23";

    @Value("${token.salt:Bearer}")
    private String AUTHORIZATION_SALT_KEY = "Bearer";


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Object token = authentication.getCredentials();

        Jws<Claims> jwt = identify(token);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("USER");
        authorities.add(simpleGrantedAuthority);

        if (jwt != null) {
            log.debug("Token解析成功");
            return new JwtAuthenticationToken(jwt.getBody().getId(), token, authorities);
        } else {
            log.warn("Token解析错误");
            return new AnonymousAuthenticationToken(UUID.randomUUID().toString(), "anonymousUser", authorities);
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(JwtAuthenticationToken.class);
    }

    public Jws<Claims> identify(Object token) {
        if (token != null) {
            try {
                return Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.toString().substring(AUTHORIZATION_SALT_KEY.length() + 1));
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
                log.warn(e.getMessage());
                return null;
            }
        }
        return null;
    }
}
