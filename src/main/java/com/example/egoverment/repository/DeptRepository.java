package com.example.egoverment.repository;

import com.example.egoverment.entity.Dept;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface DeptRepository extends JpaRepository<Dept,Integer> {


}
