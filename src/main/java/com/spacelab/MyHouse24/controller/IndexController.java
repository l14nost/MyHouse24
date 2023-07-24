package com.spacelab.MyHouse24.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String indexPage(){
        return "admin/index/index";
    }

    @GetMapping("/login-index")
    public String loginIndexPage(){
        return "admin/index/login-index";
    }
}
