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

    /**
     * 通过id查找部门
     *
     * @param id
     * @return
     */
    List<Dept> findDeptByID(int id);

    /**
     * 查找所有部门(DeptVo用到的方法)
     *
     * @return
     */
    List<Dept> queryAllDept();

    /**
     * 修改dept的Did
     *
     * @return
     */
    Dept saveDept(Dept dept);

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    int deleteDept(int id);
}
