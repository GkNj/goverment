package com.example.egoverment.service.serviceImpl;

import com.example.egoverment.entity.Dept;
import com.example.egoverment.entity.User;
import com.example.egoverment.repository.DeptRepository;
import com.example.egoverment.repository.UserRepository;
import com.example.egoverment.service.DeptService;
import com.example.egoverment.vo.DeptVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private UserRepository userRepository;

    private List connectQuery(List<Dept> depts) {
        Stream<DeptVo> deptVoStream = depts.stream().map(i -> {
            DeptVo deptVo = new DeptVo();
            BeanUtils.copyProperties(i, deptVo);
            if (i.getdId() > 0) {
                deptVo.setdId(userRepository.getOne(i.getdId()));
            }
            if (i.getuId() > 0) {
                deptVo.setuId(userRepository.getOne(i.getuId()));
            }
            return deptVo;
        });
        List<DeptVo> list = deptVoStream.collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Dept> findAllDept() {
        List<Dept> list = deptRepository.findAll();
        return list;
    }

    @Override
    public List<Dept> findDeptByID(int id) {
        List<Dept> depts = deptRepository.findDeptById(id);
        List list = connectQuery(depts);
        return list;
    }

    @Override
    public List<Dept> queryAllDept() {
        List<Dept> depts = deptRepository.findAll();
        List list = connectQuery(depts);
        return list;
    }

    @Override
    public Dept saveDept(Dept dept) {
        return deptRepository.save(dept);
    }

    @Transactional
    @Override
    public int deleteDept(int id) {
        int i = deptRepository.deleteDeptById(id);
        return i;
    }

    @Override
    public Dept findDeptByName(String dept_name) {
        return deptRepository.findDeptByDeptName(dept_name);
    }
}
