package com.example.egoverment.entity;




import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;


@Entity
@Table(name = "dept")
@JsonIgnoreProperties
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

    private  Integer uId;

    /**
     * 更新人
     */

    private int dId;
    /**
     * 成立时间
     */
    private String deptStartTime;

    /**
     * 更新时间
     */
    private String deptUpdateTime;


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

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getDeptStartTime() {
        return deptStartTime;
    }

    public void setDeptStartTime(String deptStartTime) {
        this.deptStartTime = deptStartTime;
    }

    public String getDeptUpdateTime() {
        return deptUpdateTime;
    }

    public void setDeptUpdateTime(String deptUpdateTime) {
        this.deptUpdateTime = deptUpdateTime;
    }

    public int getdId() {
        return dId;
    }

    public void setdId(int dId) {
        this.dId = dId;
    }

    public Dept(String deptName, int uId, String deptStartTime, String deptUpdateTime, int dId) {
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
                ", uId=" + uId +
                ", deptStartTime=" + deptStartTime +
                ", deptUpdateTime=" + deptUpdateTime +
                ", dId=" + dId +
                '}';
    }
}
