package com.example.egoverment.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController implements ErrorController {

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

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == 401) {
            return "/401";
        } else if (statusCode == 404) {
            return "/404";
        } else if (statusCode == 403) {
            return "/403";
        } else {
            return "/500";
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
