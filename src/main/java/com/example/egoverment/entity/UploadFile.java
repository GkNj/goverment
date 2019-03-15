package com.example.egoverment.entity;


import javax.persistence.*;

@Entity
@Table(name = "file")
public class UploadFile {

    /**
     * 文件id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件上传人
     */
    private int userId;
    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件后缀
     */
    private String extension;

    public UploadFile() {
    }

    public UploadFile(String fileName, String fileType, int userId, String path, String extension) {
        this.fileName = fileName;
        this.fileType = fileType;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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
        return "UploadFile{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", userId=" + userId +
                ", path='" + path + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
