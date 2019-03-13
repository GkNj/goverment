package com.example.egoverment.repository;

import com.example.egoverment.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * 通过职位名称查找职位
     *
     * @param name
     * @return
     */
    Role findRoleByName(String name);

    /**
     * 通过部门模糊查询
     *
     * @param name
     * @return
     */
    @Query(value = "select * from Role where introduction like  CONCAT('%',?1,'%')",nativeQuery = true)
    List<Role> findByIntroductionLike(String name);

}
