package com.example.egoverment.service;

import com.example.egoverment.entity.Dept;

import java.util.List;

public interface DeptService {

    /**
     * 查询所有部门
     *
     * @return 所有部门
     */
    List<Dept> findAllDept();

    List<Dept> queryAllDept();
}
