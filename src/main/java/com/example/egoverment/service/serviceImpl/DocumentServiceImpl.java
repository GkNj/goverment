package com.example.egoverment.service.serviceImpl;

import com.example.egoverment.entity.Document;
import com.example.egoverment.entity.UploadFile;
import com.example.egoverment.repository.DocumentRepository;
import com.example.egoverment.repository.UserRepository;
import com.example.egoverment.service.DocumentService;
import com.example.egoverment.vo.DocumentVo;
import com.example.egoverment.vo.UploadFileVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private UserRepository userRepository;


    private List connectQuery(List<Document> DocumentVos) {
        Stream<DocumentVo> DocumentVoVoStream = DocumentVos.stream().map(i -> {
            DocumentVo documentVo = new DocumentVo();
            BeanUtils.copyProperties(i, documentVo);
            documentVo.setUserId(userRepository.getOne(i.getUserId()));
            return documentVo;
        });
        List<DocumentVo> list = DocumentVoVoStream.collect(Collectors.toList());
        return list;
    }

    @Override
    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public List<Document> findAllDocumentByType(String type) {
        List<Document> list = documentRepository.findAllByType(type);
        List list1 = connectQuery(list);
        return list1;
    }
}
