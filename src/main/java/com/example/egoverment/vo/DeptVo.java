package com.example.egoverment.vo;

import com.example.egoverment.entity.User;

public class DeptVo {

    private int id;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门负责人
     */
    private  User uId;

    /**
     * 更新人
     */
    private User dId;

    /**
     * 成立时间
     */
    private String deptStartTime;

    /**
     * 更新时间
     */
    private String deptUpdateTime;

    public DeptVo() {
    }

    public DeptVo(int id, String deptName, User uId, User dId, String deptStartTime, String deptUpdateTime) {
        this.id = id;
        this.deptName = deptName;
        this.uId = uId;
        this.dId = dId;
        this.deptStartTime = deptStartTime;
        this.deptUpdateTime = deptUpdateTime;
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

    public User getdId() {
        return dId;
    }

    public void setdId(User dId) {
        this.dId = dId;
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

    @Override
    public String toString() {
        return "DeptVo{" +
                "id=" + id +
                ", deptName='" + deptName + '\'' +
                ", uId=" + uId +
                ", dId=" + dId +
                ", deptStartTime='" + deptStartTime + '\'' +
                ", deptUpdateTime='" + deptUpdateTime + '\'' +
                '}';
    }
}
