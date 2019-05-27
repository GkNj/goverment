package com.example.egoverment.repository;

import com.example.egoverment.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    List<User> findUsersByUsername(String username);

    /**
     * 通过登录名查找用户
     *
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 查早不是部门经理的user
     *
     * @param positions
     * @return
     */
    List<User> findUserByPositionIn(List<String> positions);

    /**
     * 通过id查找用户
     *
     * @param id
     * @return
     */
    User findUserById(int id);

    int deleteUserById(int id);

    /**
     * 查询每个部门的员工人数
     *
     * @return
     */
    @Query(value = "select  d.dept_name as 部门名称 , count(*) as 人数 from user u ,dept d where  d.id =u.d_id group by u.d_id", nativeQuery = true)
    List findGroupByDept();
}
