package com.example.egoverment.service.serviceImpl;

import com.example.egoverment.entity.Dept;
import com.example.egoverment.repository.DeptRepository;
import com.example.egoverment.repository.UserRepository;
import com.example.egoverment.service.DeptService;
import com.example.egoverment.vo.DeptVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
            deptVo.setdId(userRepository.getOne(i.getdId()));
            deptVo.setuId(userRepository.getOne(i.getuId()));
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
    public List<Dept> queryAllDept() {
        List<Dept> depts = deptRepository.findAll();
        List list = connectQuery(depts);
        return list;
    }
}
