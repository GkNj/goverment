package com.example.egoverment.service.serviceImpl;

import com.example.egoverment.entity.Role;
import com.example.egoverment.entity.User;
import com.example.egoverment.entity.UserAndRole;
import com.example.egoverment.repository.UserAndRoleRepository;
import com.example.egoverment.repository.UserRepository;
import com.example.egoverment.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 高凯
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAndRoleRepository userAndRoleRepository;

    /**
     * 加载用户密码判断数据库密码和输入密码是否正确
     *
     * @param username 登录用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user==null){
            throw new InsufficientAuthenticationException("用户名或密码错误");
        }
        System.out.println("登陆的信息" + user.toString());
        return user;
    }


    @Transactional
    @Override
    public User saveUser(User user, Role role) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(user.getPassword().trim());
        user.setPassword(encode);
        User save = userRepository.save(user);
        UserAndRole userAndRole = new UserAndRole();
        userAndRole.setRoleId(role.getId());
        userAndRole.setUserId(Long.valueOf(save.getId()));
        userAndRoleRepository.save(userAndRole);
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

    @Transactional
    @Override
    public int deleteUser(int id) {
        int i = userRepository.deleteUserById(id);
        return i;
    }

    @Override
    public String checkUsername(String username) {
        String result = null;
        List<User> user = userRepository.findUsersByUsername(username);
        if (user.size() == 0) {
            result = "200";
        } else {
            result = "201";
        }
        System.out.println(result);
        return result;
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findUsersByUsername(String username) {
        return userRepository.findUsersByUsername(username);
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findUserById(id);
    }

    @Override
    public List findGroupByDept() {
        return userRepository.findGroupByDept();
    }
}
