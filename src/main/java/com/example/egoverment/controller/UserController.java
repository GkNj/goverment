package com.example.egoverment.controller;


import com.example.egoverment.entity.Dept;
import com.example.egoverment.entity.Role;
import com.example.egoverment.entity.User;
import com.example.egoverment.repository.DeptRepository;
import com.example.egoverment.repository.RoleRepository;

import com.example.egoverment.repository.UserRepository;
import com.example.egoverment.service.serviceImpl.DeptServiceImpl;
import com.example.egoverment.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeptServiceImpl deptService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DeptRepository deptRepository;

    /**
     * 查询所有职员
     *
     * @return 职员列表
     */
    @RequestMapping("/findAllUser")
    public String findAllUsers(ModelMap modelMap) {
        List<User> users = userService.findAllUsers();
        modelMap.addAttribute("list", users);
        System.out.println(users.toString());
        return "administrative/employee_list";
    }

    /**
     * 查询单个职员
     *
     * @param request
     * @return
     */
    @RequestMapping("/findUser")
    @ResponseBody
    public User findUser(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userService.findUser(id);
        return user;
    }

    /**
     * 添加新员工
     *
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/addUser")
    public String addUser(User user, HttpServletRequest request) {
        String position = request.getParameter("position");
        System.out.println(position);
        Role role1 = roleRepository.findRoleByName(position);
        System.out.println(role1.toString());
        String did = request.getParameter("did");
        Role role = new Role();
        role.setId(Long.valueOf(role1.getId()));
        user.setPosition(position);
        Dept dept = new Dept();
        dept.setId(Integer.parseInt(did));
        user.setDept(dept);
        userService.saveUser(user, role);
        return "redirect:/user/findAllUser";
    }

    /**
     * 更新员工
     *
     * @param request
     * @return
     */
    @RequestMapping("/updateUser")
    public String updateUser(HttpServletRequest request) {
        String id = request.getParameter("id");
        String dId = request.getParameter("dId");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        User user = userRepository.findUserById(Integer.parseInt(id));
        user.setName(name);
        user.setPhone(phone);
        Dept dept = new Dept();
        dept.setId(Integer.parseInt(dId));
        user.setDept(dept);
        userService.updateUser(user);
        return "redirect:/user/findAllUser";
    }

    /**
     * 删除员工
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteUser")
    public String deleteUser(HttpServletRequest request) {
        String id = request.getParameter("id");
        //拿到用户id去部门表里判断一下是否是负责人或者更新人，如果是将部门表里对应的id改为null
        List<Dept> list = deptRepository.findDeptByDId(Integer.parseInt(id));
        List<Dept> list1 = deptRepository.findDeptByUId(Integer.parseInt(id));
        if (list.size() != 0) {
            System.out.println(list.toString());
            for (Dept dept : list) {
                dept.setdId(-1);
                deptService.saveDept(dept);
            }
        }
        if (list1.size() != 0) {
            for (Dept dept : list1) {
                dept.setuId(-1);
                deptService.saveDept(dept);
            }
        }
        System.out.println("id" + id);
        int i = userService.deleteUser(Integer.parseInt(id));
        return "redirect:/user/findAllUser";
    }

    /**
     * 检查用户名是否重名
     *
     * @param username
     * @return
     */
    @RequestMapping("/checkUsername")
    @ResponseBody
    public String checkUsername(String username) {
        String s = userService.checkUsername(username);
        return s;
    }

    /**
     * 查找所有角色
     *
     * @return
     */
    @RequestMapping("/findAllRole")
    @ResponseBody
    public List<Role> findAllRole() {
        List<Role> list = roleRepository.findAll();
        return list;
    }

    /**
     * 查询不是经理的user
     *
     * @return
     */
    @RequestMapping("/findByPosition")
    @ResponseBody
    public List<User> findByPosition() {
        List<String> listString = new ArrayList<>();
        listString.add("MOI");
        listString.add("MFS");
        listString.add("MUC");
        listString.add("MCS");
        listString.add("LS");
        List<User> list = userRepository.findUserByPositionIn(listString);
        return list;
    }

    @RequestMapping("/findUserBySalary")
    public String findUserBySalary(ModelMap modelMap){
        List<User> users = userService.findAllUsers();
        modelMap.addAttribute("list", users);
        System.out.println(users.toString());
        return "administrative/employee_salary_list";
    }

    @RequestMapping("/saveSalary")
    public String saveSalary(HttpServletRequest request){
        String salary = request.getParameter("salary");
        System.out.println(salary);
        String id = request.getParameter("id");
        System.out.println("啊啊啊啊啊"+id);
        User user = userService.findUser(Integer.parseInt(id));
        user.setSalary(Double.valueOf(salary));
        userRepository.save(user);
        return "redirect:/user/findUserBySalary";
    }

    @RequestMapping("/clockIn")
    public String clockIn(){
        return "forward:/administrative/punch_lock.html";
    }
}
