package com.example.egoverment.repository;

import com.example.egoverment.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 通过登录用户名查找用户
     *
     * @param username 登录用户名
     * @return
     */
    List<User> findUserByUsername(String username);

    /**
     * 通过id查找用户
     *
     * @param id
     * @return
     */

    User findUserById(int id);
//    @Query(value = "select * from user",nativeQuery = true)
//    List<User> findUserByPosition(String position);
}