package com.example.egoverment.repository;

import com.example.egoverment.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    /**
     * 根据档案类型查找
     * @return
     */
    @Query(value = "select file_type as 文档类型 ,count(*) as 文档数 from file group by file_type",nativeQuery = true)
    List findFileGroupByType();
}
