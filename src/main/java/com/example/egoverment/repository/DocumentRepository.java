package com.example.egoverment.repository;

import com.example.egoverment.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    /**
     * 根据类型查找公文
     *
     * @return
     */
    List<Document> findAllByType(String type);
}
