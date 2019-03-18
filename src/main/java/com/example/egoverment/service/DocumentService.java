package com.example.egoverment.service;

import com.example.egoverment.entity.Document;

import java.util.List;

public interface DocumentService {

    /**
     * 保存公文
     *
     * @param document
     * @return
     */
    Document saveDocument(Document document);

    /**
     * 查找所有公文
     *
     * @return
     */
    List<Document> findAllDocument();
}
