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
@RequestMapping()
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



    /**
     * 添加部门
     *
     * @param request
     * @return
     */
    @RequestMapping("/addDept")
    public String addDept(HttpServletRequest request) {
        String dept_name = request.getParameter("dept_name");
        String uId = request.getParameter("uId");
        String dept_start_time = request.getParameter("dept_start_time");
        String charge_introduction = request.getParameter("charge_introduction");
        String charge_role_name = request.getParameter("charge_role_name");
        String introduction = request.getParameter("introduction");
        String role_name = request.getParameter("role_name");
        Role role = new Role();
        role.setIntroduction(introduction);
        role.setName(role_name);
        Role role1 = new Role();
        role1.setName(charge_role_name);
        role1.setIntroduction(charge_introduction);
        roleRepository.save(role);
        roleRepository.save(role1);
        Dept dept = new Dept();
        dept.setDeptName(dept_name);
        dept.setuId(Integer.parseInt(uId));
        dept.setDeptStartTime(dept_start_time);
        deptService.saveDept(dept);
        Dept dept1 = deptService.findDeptByName(dept_name);
        Dept dept2 = new Dept();
        int id = dept1.getId();
        dept2.setId(id);
        List<Role> roles = roleRepository.findByIntroductionLike(dept_name);
        User user = userService.findUser(Integer.parseInt(uId));
        user.setPosition(roles.get(0).getName());
        user.setDept(dept2);
        userService.updateUser(user);
        UserAndRole userAndRole = userAndRoleRepository.findByUserId(Long.valueOf(uId));
        userAndRole.setRoleId(Long.valueOf(roles.get(0).getId()));
        userAndRoleRepository.save(userAndRole);
        return "redirect:/findDept";
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
        List<User> list = userService.findUsersByUsername(username);
        int did = list.get(0).getId();
        Dept dept = new Dept();
        dept.setId(Integer.parseInt(id));
        dept.setDeptName(deptName);
        dept.setDeptStartTime(deptStartTime);
        dept.setDeptUpdateTime(deptUpdateTime);
        dept.setuId(Integer.parseInt(uId));
        dept.setdId(did);
        deptService.saveDept(dept);
        List<Role> roles = roleRepository.findByIntroductionLike(deptName + "经理");
        System.out.println(deptName + "经理");
        User user = userService.findUser(Integer.parseInt(uId));
        //将修改之前的部门经理改为该部门的普通员工
        user.setPosition(roles.get(0).getName());
        user.setDept(dept);
        userService.updateUser(user);
        UserAndRole userAndRole = userAndRoleRepository.findByUserId(Long.valueOf(uId));
        userAndRole.setRoleId(Long.valueOf(roles.get(0).getId()));
        userAndRoleRepository.save(userAndRole);
//        System.out.println("角色是" + roles.toString());
        return "redirect:/findDept";
    }

    @RequestMapping("/deleteDept")
    public String deleteDept(HttpServletRequest request) {
        String id = request.getParameter("id");

        deptService.deleteDept(Integer.parseInt(id));
        return "redirect:/findDept";
    }
}
