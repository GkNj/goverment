package com.example.egoverment.vo;

import com.example.egoverment.entity.User;

public class DocumentVo {
    /**
     * id
     */
    private int id;

    /**
     * 公文名称
     */
    private String documentName;


    /**
     * 公文上传人
     */
    private User userId;
    /**
     * 公文路径
     */
    private String path;

    /**
     * 公文后缀
     */
    private String extension;

    public DocumentVo() {
    }

    public DocumentVo(int id, String documentName, User userId, String path, String extension) {
        this.id = id;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
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
        return "DocumentVo{" +
                "id=" + id +
                ", documentName='" + documentName + '\'' +
                ", userId=" + userId +
                ", path='" + path + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
