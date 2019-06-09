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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
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
     * 查看考勤情况
     *
     * @return
     */
    @GetMapping("/findAllClock")
    public String findAllClock(ModelMap modelMap) {
        List<User> users = userService.findAllUsers();
        modelMap.addAttribute("list", users);
        return "administrative/employee_clock";
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
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(position);
        if (!(m.find())) {
            Role role = roleRepository.findRoleByName(position);
            String introduction = role.getIntroduction();
            user.setPosition(introduction);
        }
        map.addAttribute("user", user);
        return "/file/personFile";
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
        return "redirect:/findAllUser";
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
        User user1 = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        user1.setMajor(major);
        user1.setEmail(email);
        user1.setPolitical(political);
        user1.setGraduate(graduate);
        user1.setAddress(address);
        user1.setNativePlace(nativePlace);
        user1.setBirthday(birthday);
        user1.setEducation(education);
        userService.updateUser(user);
        return "redirect:/findLoginUser";
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
        List<Dept> dept1 = deptRepository.findDeptById(Integer.parseInt(dId));
        //查看更改人是否为部门经理
        Dept dept2 = deptRepository.findDeptsByUId(Integer.parseInt(id));
        if (!(dept2 == null)) {
            dept2.setuId(-1);
            deptService.saveDept(dept2);
        }
        String deptName = dept1.get(0).getDeptName();
        List<Role> roles = roleRepository.findByIntroductionLike(deptName + "职员");
        user.setName(name);
        user.setPhone(phone);
        Dept dept = new Dept();
        dept.setId(Integer.parseInt(dId));
        user.setDept(dept);
        user.setPosition(roles.get(0).getName());
        userService.updateUser(user);
        return "redirect:/findAllUser";
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
        return "redirect:/findAllUser";
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

    @RequestMapping("/findRoleByManager")
    @ResponseBody
    public List<Role> findRoleByManager(@RequestParam("name") String name) {
        System.out.println(name);
        List<Role> list = roleRepository.findByIntroductionLike(name+"职");
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
        listString.add("MES");
        listString.add("SMH");
        listString.add("SDT");
        listString.add("java");
        listString.add("AS");
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

    /**
     * 保存工资
     *
     * @param request
     * @return
     */
    @RequestMapping("/saveSalary")
    public String saveSalary(HttpServletRequest request) {
        String salary = request.getParameter("salary");
        String id = request.getParameter("id");
        User user = userService.findUser(Integer.parseInt(id));
        user.setSalary(salary);
        userRepository.save(user);
        return "redirect:/findUserBySalary";
    }

    /**
     * 处理上班打卡
     *
     * @return
     */
    @RequestMapping("/clockIn")
    public String clockIn(HttpServletRequest request) {
        String latetime = request.getParameter("latetime");
        System.out.println(latetime);
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String username = user.getUsername();
        List<User> user1 = userService.findUsersByUsername(username);
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
        return "forward:administrative/punch_lock";
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
        List<User> user1 = userService.findUsersByUsername(username);
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
        return "forward:administrative/punch_lock.html";
    }


    /**
     * 跳转修改密码的页面
     *
     * @return
     */
    @RequestMapping("/skip")
    public String skip() {
        return "administrative/updatePassword";
    }

    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping("/updatePassword")
    public String edit(HttpServletRequest request, ModelMap map) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = request.getParameter("password");
        String id = request.getParameter("id");
        User user = userService.findUserById(Integer.parseInt(id));
        String encode = encoder.encode(password.trim());
        user.setPassword(encode);
        userService.updateUser(user);
        System.out.println(user.toString());
        return "redirect:/index";
    }

    @RequestMapping("/findGroupByDept")
    @ResponseBody
    public List findGroupByDept() {

        /**
         * x: 财政,财政部,财政部,财政部,财政部
         * y：1,23,3,4,5,6,8,9
         */

        //1,财务部,2人事部
        List list = userService.findGroupByDept();

        //财政,财政部,财政部,财政部,财政部
        List list1 = new ArrayList();
        //1,23,3,4,5,6,8,9
        List list2 = new ArrayList();
        List list3 = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Object[] o = (Object[]) list.get(i);
            list1.add(o[0]);
            list2.add(o[1]);
        }
        list3.add(list1);
        list3.add(list2);
        return list3;
    }
}
