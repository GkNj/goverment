package com.example.egoverment.service.serviceImpl;

import com.example.egoverment.entity.Role;
import com.example.egoverment.entity.User;
import com.example.egoverment.repository.UserRepository;
import com.example.egoverment.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 高凯
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    /**
     * 加载用户密码判断数据库密码和输入密码是否正确
     *
     * @param username 登录用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).get(0);
        System.out.println(user.toString());
        return user;
    }


    @Override
    public User saveUser(User user, Role role) {
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> list = userRepository.findAll();
        return list;
    }

    @Override
    public User findUser(int id) {
        User user = userRepository.findUserById(id);
        return user;
    }

    @Override
    public int deleteUser(User user) {
        return 0;
    }

    @Override
    public String checkUsername(String username) {
        return null;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }
}
