package com.example.egoverment.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "dept")
@JsonIgnoreProperties(value = {"uId","dId"})
public class Dept {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门负责人
     */


    @OneToOne
    @JoinColumn(name = "u_id", referencedColumnName = "id")
    private User uId;

    /**
     * 更新人
     */

    @OneToOne
    @JoinColumn(name = "dd_id", referencedColumnName = "id")
    private User dId;
    /**
     * 成立时间
     */
    private Date deptStartTime;

    /**
     * 更新时间
     */
    private Date deptUpdateTime;


    public Dept() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public User getuId() {
        return uId;
    }

    public void setuId(User uId) {
        this.uId = uId;
    }

    public Date getDeptStartTime() {
        return deptStartTime;
    }

    public void setDeptStartTime(Date deptStartTime) {
        this.deptStartTime = deptStartTime;
    }

    public Date getDeptUpdateTime() {
        return deptUpdateTime;
    }

    public void setDeptUpdateTime(Date deptUpdateTime) {
        this.deptUpdateTime = deptUpdateTime;
    }

    public User getdId() {
        return dId;
    }

    public void setdId(User dId) {
        this.dId = dId;
    }

    public Dept(String deptName, User uId, Date deptStartTime, Date deptUpdateTime, User dId) {
        this.deptName = deptName;
        this.uId = uId;
        this.deptStartTime = deptStartTime;
        this.deptUpdateTime = deptUpdateTime;
        this.dId = dId;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", deptName='" + deptName + '\'' +
//                ", uId=" + uId +
                ", deptStartTime=" + deptStartTime +
                ", deptUpdateTime=" + deptUpdateTime +
//                ", dId=" + dId +
                '}';
    }
}
