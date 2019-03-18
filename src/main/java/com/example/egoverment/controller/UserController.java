package com.example.egoverment.controller;


import com.example.egoverment.entity.Dept;
import com.example.egoverment.entity.Role;
import com.example.egoverment.entity.User;
import com.example.egoverment.repository.DeptRepository;
import com.example.egoverment.repository.RoleRepository;

import com.example.egoverment.repository.UserRepository;
import com.example.egoverment.service.serviceImpl.DeptServiceImpl;
import com.example.egoverment.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
     * 查询当前登录用户
     *
     * @return
     */
    @RequestMapping("/findLoginUser")
    public String findLoginUser(ModelMap map) {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String position = user.getPosition();
        Role role = roleRepository.findRoleByName(position);
        String introduction = role.getIntroduction();
        user.setPosition(introduction);
        map.addAttribute("user", user);
        return "file/personFile";
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
        String position = user.getPosition();
        Role role = roleRepository.findRoleByName(position);
        String introduction = role.getIntroduction();
        user.setPosition(introduction);
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
     * 补充用户信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/editorUser")
    public String editorUser(HttpServletRequest request) {
        String id = request.getParameter("id");
        String phone = request.getParameter("phone");
        String nativePlace = request.getParameter("nativePlace");
        String address = request.getParameter("address");
        String graduate = request.getParameter("graduate");
        String education = request.getParameter("education");
        String political = request.getParameter("political");
        String email = request.getParameter("email");
        String major = request.getParameter("major");
        String birthday = request.getParameter("birthday");
        User user = userService.findUserById(Integer.parseInt(id));
        user.setId(Integer.parseInt(id));
        user.setPhone(phone);
        user.setNativePlace(nativePlace);
        user.setAddress(address);
        user.setGraduate(graduate);
        user.setEducation(education);
        user.setEmail(email);
        user.setPolitical(political);
        user.setMajor(major);
        user.setBirthday(birthday);
        userService.updateUser(user);
        return "redirect:/user/findLoginUser";
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
    public String findUserBySalary(ModelMap modelMap) {
        List<User> users = userService.findAllUsers();
        modelMap.addAttribute("list", users);
        System.out.println(users.toString());
        return "administrative/employee_salary_list";
    }

    @RequestMapping("/saveSalary")
    public String saveSalary(HttpServletRequest request) {
        String salary = request.getParameter("salary");
        String id = request.getParameter("id");
        User user = userService.findUser(Integer.parseInt(id));
        user.setSalary(Double.valueOf(salary));
        userRepository.save(user);
        return "redirect:/user/findUserBySalary";
    }

    /**
     * 处理上班打卡
     *
     * @return
     */
    @RequestMapping("/clockIn")
    public String clockIn(HttpServletRequest request) {
        String latetime = request.getParameter("latetime");
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String username = user.getUsername();
        List<User> user1 = userService.findUserByUsername(username);
        String punchNum = user1.get(0).getPunchNum();
        if (punchNum == null) {
            punchNum = "1";
            user1.get(0).setPunchNum(punchNum);
        } else {
            int punch = Integer.parseInt(punchNum);
            punch++;
            user1.get(0).setPunchNum(String.valueOf(punch));
            System.out.println(punch);
        }
        userService.updateUser(user1.get(0));
        if (Integer.parseInt(latetime) > 0) {
            String lateNum = user1.get(0).getLateNum();
            if (lateNum == null) {
                lateNum = "1";
                user1.get(0).setLateNum(lateNum);
            } else {
                int num = Integer.parseInt(lateNum);
                num++;
                System.out.println(num);
                user1.get(0).setLateNum(String.valueOf(num));
            }
            userService.updateUser(user1.get(0));
        }
        return "forward:/administrative/punch_lock.html";
    }

    /**
     * 处理下班打卡
     *
     * @return
     */
    @RequestMapping("/clockOut")
    public String clockOut(HttpServletRequest request) {
        String outlocktime = request.getParameter("outlocktime");
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String username = user.getUsername();
        List<User> user1 = userService.findUserByUsername(username);
        if (Integer.parseInt(outlocktime) > 0) {
            String earlyNum = user1.get(0).getEarlyNum();
            if (earlyNum == null) {
                earlyNum = "1";
                user1.get(0).setEarlyNum(earlyNum);
            } else {
                int num = Integer.parseInt(earlyNum);
                num++;
                user1.get(0).setEarlyNum(String.valueOf(num));
            }
            userService.updateUser(user1.get(0));
        }
        return "forward:/administrative/punch_lock.html";
    }
}
