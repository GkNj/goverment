package com.example.egoverment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 高凯
 * 角色实体类
 *
 */
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String introduction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Role(String name, String introduction) {
        this.name = name;
        this.introduction = introduction;
    }

    public Role() {
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
