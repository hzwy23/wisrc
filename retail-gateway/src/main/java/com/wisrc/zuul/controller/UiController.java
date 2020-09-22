package com.wisrc.zuul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {

    @GetMapping(value = {
            "/",
            "/ui/**"
    })
    public String index(){
        return "ui/index";
    }
}
