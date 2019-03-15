package com.example.egoverment.repository;

import com.example.egoverment.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<UploadFile, Integer> {

    /**
     * 通过id删除文档
     *
     * @param id
     * @return
     */
    int deleteUploadFileById(int id);

    /**
     * 通过id查找文档
     * @param id
     * @return
     */
    List<UploadFile> findUploadFileById(int id);

    /**
     * 通过文件类型查询文档
     *
     * @param fileType
     * @return
     */
    List<UploadFile> findUploadFileByFileType(String fileType);
}
