package com.example.egoverment.service.serviceImpl;

import com.example.egoverment.entity.Dept;
import com.example.egoverment.repository.DeptRepository;
import com.example.egoverment.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptRepository deptRepository;

    @Override
    public List<Dept> findAllDept() {
        List<Dept> list = deptRepository.findAll();
        return list;
    }
}
