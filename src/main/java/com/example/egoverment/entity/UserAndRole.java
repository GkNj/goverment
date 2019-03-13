package com.example.egoverment.entity;


import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class UserAndRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ur_user_id")
    private Long userId;

    @Column(name = "ur_role_id")
    private Long roleId;

    public UserAndRole() {

    }

    @Override
    public String toString() {
        return "UserAndRole{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public UserAndRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
