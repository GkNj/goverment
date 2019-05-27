package com.example.egoverment.repository;

import com.example.egoverment.entity.Dept;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DeptRepository extends JpaRepository<Dept, Integer> {


    /**
     * 通过id查询dept
     *
     * @param id
     * @return
     */
    List<Dept> findDeptById(int id);

    Dept findDeptsByUId(int id);

    /**
     * 通过更改人id查询部门
     *
     * @param id
     * @return
     */
    List<Dept> findDeptByDId(int id);

    /**
     * 通过负责人id查询部门
     *
     * @param id
     * @return
     */
    List<Dept> findDeptByUId(int id);

    /**
     * 通过id删除部门
     *
     * @param id
     * @return
     */
    int deleteDeptById(int id);

    /**
     * 通过名称获得部门信息
     *
     * @param dept_name
     * @return
     */
    Dept findDeptByDeptName(String dept_name);
}
