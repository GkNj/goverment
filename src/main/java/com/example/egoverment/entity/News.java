package com.example.egoverment.entity;

import javax.persistence.*;

@Entity
@Table(name = "news")
public class News {
    /**
     * 新闻编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 新闻内容
     */
    private String content;

    /**
     * 发布人
     */
    private int addUser;

    /**
     * 发布时间
     */
    private String pubDate;

    public News() {
    }

    public News(String title, String content, int addUser, String pubDate) {
        this.title = title;
        this.content = content;
        this.addUser = addUser;
        this.pubDate = pubDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAddUser() {
        return addUser;
    }

    public void setAddUser(int addUser) {
        this.addUser = addUser;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", addUser=" + addUser +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }
}
