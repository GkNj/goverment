package com.example.egoverment.controller;

import com.example.egoverment.entity.User;
import com.example.egoverment.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 查询所有职员
     *
     * @return 职员列表
     */
    @RequestMapping("/findAllUser")
    public String findAllUsers(HttpServletRequest request, ModelMap modelMap) {

        List<User> users = userService.findAllUsers();
        modelMap.addAttribute("list", users);
        System.out.println(users.toString());
        return "administrative/employee_list";
    }

    @RequestMapping("/findUser")
    @ResponseBody
    public User findUser(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userService.findUser(id);
        System.out.println(user.toString()+ "11111");
        return  user;
    }
}
