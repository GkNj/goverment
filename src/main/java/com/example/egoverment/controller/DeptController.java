package com.example.egoverment.controller;

import com.example.egoverment.entity.Dept;
import com.example.egoverment.entity.Role;
import com.example.egoverment.entity.User;
import com.example.egoverment.entity.UserAndRole;
import com.example.egoverment.repository.RoleRepository;
import com.example.egoverment.repository.UserAndRoleRepository;
import com.example.egoverment.service.serviceImpl.DeptServiceImpl;
import com.example.egoverment.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static javafx.scene.input.KeyCode.L;

@Controller
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptServiceImpl deptService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserAndRoleRepository userAndRoleRepository;

    /**
     * ajax查找所有部门
     *
     * @return
     */
    @RequestMapping("/findAllDept")
    @ResponseBody
    public List<Dept> findAllDept() {
        List<Dept> list = deptService.queryAllDept();
        System.out.println(list.toString());
        return list;
    }

    @RequestMapping("/addDept")
    public String addDept(HttpServletRequest request) {
        String dept_name = request.getParameter("dept_name");
        String uId = request.getParameter("uId");
        String dept_start_time = request.getParameter("dept_start_time");
        Dept dept = new Dept();
        dept.setDeptName(dept_name);
        dept.setuId(Integer.parseInt(uId));
        dept.setDeptStartTime(dept_start_time);
        deptService.saveDept(dept);
        List<Role> roles = roleRepository.findByIntroductionLike(dept_name);
        User user = userService.findUser(Integer.parseInt(uId));
        user.setPosition(roles.get(0).getName());
        userService.updateUser(user);
        UserAndRole userAndRole = userAndRoleRepository.findByUserId(Long.valueOf(uId));
        userAndRole.setRoleId(Long.valueOf(roles.get(0).getId()));
        userAndRoleRepository.save(userAndRole);
        return "redirect:/dept/findDept";
    }

    /**
     * 查询部门列表
     *
     * @param map
     * @return
     */
    @RequestMapping("/findDept")
    public String findDept(ModelMap map) {
        List<Dept> list = deptService.queryAllDept();
        map.addAttribute("list", list);
        System.out.println("查询到的部门是" + list.toString());
        return "administrative/dept_list";
    }

    /**
     * 查找单个部门信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/findOneDept")
    @ResponseBody
    public List<Dept> findOneDept(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<Dept> list = deptService.findDeptByID(Integer.parseInt(id));
        return list;
    }

    /**
     * 更新部门信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/updateDept")
    public String updateDept(HttpServletRequest request) {
        String id = request.getParameter("id");
        String deptName = request.getParameter("dept_name");
        String deptStartTime = request.getParameter("deptStartTime");
        String deptUpdateTime = request.getParameter("deptUpdateTime");
        String uId = request.getParameter("uId");
        String username = request.getParameter("dId");
        List<User> list = userService.findUserByUsername(username);
        int did = list.get(0).getId();
        Dept dept = new Dept();
        dept.setId(Integer.parseInt(id));
        dept.setDeptName(deptName);
        dept.setDeptStartTime(deptStartTime);
        dept.setDeptUpdateTime(deptUpdateTime);
        dept.setuId(Integer.parseInt(uId));
        dept.setdId(did);
        deptService.saveDept(dept);
        List<Role> roles = roleRepository.findByIntroductionLike(deptName);
        User user = userService.findUser(Integer.parseInt(uId));
        user.setPosition(roles.get(0).getName());
        userService.updateUser(user);
        UserAndRole userAndRole = userAndRoleRepository.findByUserId(Long.valueOf(uId));
        userAndRole.setRoleId(Long.valueOf(roles.get(0).getId()));
        userAndRoleRepository.save(userAndRole);
        System.out.println("角色是" + roles.toString());
        return "redirect:/dept/findDept";
    }

    @RequestMapping("/deleteDept")
    public String deleteDept(HttpServletRequest request){
        String id = request.getParameter("id");

        deptService.deleteDept(Integer.parseInt(id));
        return "redirect:/dept/findDept";
    }
}
