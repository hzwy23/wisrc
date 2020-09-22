package com.wisrc.zuul.services;

import com.wisrc.zuul.dao.LoginIdentifyDao;
import com.wisrc.zuul.entity.UserLoginEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.LinkedList;


@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginIdentifyDao loginIdentifyDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserLoginEntity element = loginIdentifyDao.findByUserId(s);
        if (element == null) {
            log.info("用户不存在，{}", s);
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new User(element.getUsername(), element.getPassword(), new LinkedList<>());
    }
}
