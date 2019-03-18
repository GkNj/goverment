package com.example.egoverment.entity;

import javax.persistence.*;

@Entity
@Table(name = "document")
public class Document {
    /**
     * 公文id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 公文名称
     */
    private String documentName;


    /**
     * 公文上传人
     */
    private int userId;
    /**
     * 公文路径
     */
    private String path;

    /**
     * 公文后缀
     */
    private String extension;

    public Document() {
    }

    public Document(String documentName, int userId, String path, String extension) {
        this.documentName = documentName;
        this.userId = userId;
        this.path = path;
        this.extension = extension;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", documentName='" + documentName + '\'' +
                ", userId=" + userId +
                ", path='" + path + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
