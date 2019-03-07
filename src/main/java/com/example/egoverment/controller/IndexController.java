package com.example.egoverment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    /**
     * 登录视图转跳
     *
     * @return 登录视图
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 403页面
     *
     * @Return 403页面
     */
    @RequestMapping("/403")
    public String _403() {
        return "403";
    }
}
