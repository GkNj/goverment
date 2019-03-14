package com.example.egoverment.service;

import com.example.egoverment.entity.Role;
import com.example.egoverment.entity.User;


import java.util.List;


public interface UserService {

    /**
     * 添加用户
     *
     * @param user 用户
     * @param role 角色
     * @return
     */
    User saveUser(User user, Role role);

    /**
     * 获取所有用户
     *
     * @return 所有用户
     */
    List<User> findAllUsers();

    /**
     * 获取单个用户
     *
     * @return 用户
     */
    User findUser(int id);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return
     */
    int deleteUser(int id);

    /**
     * 检查是否重名
     *
     * @param username 用户名
     * @return
     */
    String checkUsername(String username);

    /**
     * 更新用户
     *
     * @param user 用户
     * @return
     */
    User updateUser(User user);

    /**
     * 通过用户名查找用户
     *
     * @param username
     * @return
     */
    List<User> findUserByUsername(String username);

    /**
     * 通过id查询User
     *
     * @param id
     * @return
     */
    User findUserById(int id);
}
