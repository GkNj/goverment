package com.example.egoverment.service;

import com.example.egoverment.entity.UploadFile;

import java.util.List;


public interface UploadFileService {
    /**
     * 保存档案
     *
     * @param uploadFile
     * @return
     */
    UploadFile saveFile(UploadFile uploadFile);

    /**
     * 删除文件
     *
     * @param id
     * @return
     */
    int deleteFile(int id);

    /**
     * 通过文件类型查找档案
     *
     * @param fileType
     * @return
     */
    List<UploadFile> findFileByFileType(String fileType);

    /**
     * 通过id查找档案
     *
     * @param id
     * @return
     */
    List<UploadFile> findFileById(int id);
}
