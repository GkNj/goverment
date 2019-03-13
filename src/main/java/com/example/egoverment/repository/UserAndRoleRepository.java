package com.example.egoverment.repository;

import com.example.egoverment.entity.UserAndRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAndRoleRepository extends JpaRepository<UserAndRole, Long> {


    /**
     * 通过userid查找
     *
     * @param id
     * @return
     */
    UserAndRole findByUserId(Long id);
}
