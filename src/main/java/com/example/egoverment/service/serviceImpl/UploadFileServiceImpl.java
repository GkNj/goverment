package com.example.egoverment.service.serviceImpl;


import com.example.egoverment.entity.UploadFile;
import com.example.egoverment.repository.FileRepository;
import com.example.egoverment.repository.UserRepository;
import com.example.egoverment.service.UploadFileService;
import com.example.egoverment.vo.UploadFileVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;

    private List connectQuery(List<UploadFile> uploadFiles) {
        Stream<UploadFileVo> uploadFileVoStream = uploadFiles.stream().map(i -> {
            UploadFileVo uploadFileVo = new UploadFileVo();
            BeanUtils.copyProperties(i, uploadFileVo);
            uploadFileVo.setUserId(userRepository.getOne(i.getUserId()));
            return uploadFileVo;
        });
        List<UploadFileVo> list = uploadFileVoStream.collect(Collectors.toList());
        return list;
    }

    @Override
    public UploadFile saveFile(UploadFile uploadFile) {
        return fileRepository.save(uploadFile);
    }

    @Transactional
    @Override
    public int deleteFile(int id) {
        return fileRepository.deleteUploadFileById(id);
    }

    @Override
    public List<UploadFile> findFileByFileType(String fileType) {
        List<UploadFile> uploadFiles = fileRepository.findUploadFileByFileType(fileType);
        List list = connectQuery(uploadFiles);
        return list;
    }

    @Override
    public List<UploadFile> findFileById(int id) {
        List<UploadFile> uploadFiles = fileRepository.findUploadFileById(id);
        List list = connectQuery(uploadFiles);
        return list;
    }

    @Override
    public List findFileGroupByType() {
        return fileRepository.findFileGroupByType();
    }


}
