package com.example.egoverment.controller;

import com.example.egoverment.entity.Dept;
import com.example.egoverment.service.serviceImpl.DeptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptServiceImpl deptService;

    @RequestMapping("/findAllDept")
    @ResponseBody
    public List<Dept> findAllDept() {
        List<Dept> list = deptService.queryAllDept();
        System.out.println(list.toString());
        return list;
    }
}
