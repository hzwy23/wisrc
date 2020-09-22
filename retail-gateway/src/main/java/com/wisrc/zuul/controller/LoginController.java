package com.wisrc.zuul.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class LoginController {


    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

}
