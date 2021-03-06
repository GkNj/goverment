package com.example.egoverment.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**
     * 登录名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 入职时间
     */
    private String startDate;

    /**
     * 所属部门
     */
    @ManyToOne
    @JoinColumn(name = "d_id", referencedColumnName = "id")
    private Dept dept;


    /**
     * 联系方式
     */
    private String phone;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 家庭地址
     */
    private String address;

    /**
     * 毕业院校
     */
    private String graduate;

    /**
     * 学历
     */
    private String education;

    /**
     * 职位
     */
    private String position;

    /**
     * 政治面貌
     */
    private String political;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 专业
     */
    private String major;

    /**
     * 出生年月
     */
    private String birthday;

    /**
     * 工资
     */
    private String salary;

    /**
     * 打卡次数
     */
    private String punchNum;

    /**
     * 迟到次数
     */
    private String lateNum;

    /**
     * 早退次数
     */
    private String earlyNum;

    /**
     * 用户头像
     */
    private String image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {
                    @JoinColumn(name = "ur_user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ur_role_id")
            }
    )
    private List<Role> roles;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGraduate() {
        return graduate;
    }

    public void setGraduate(String graduate) {
        this.graduate = graduate;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPunchNum() {
        return punchNum;
    }

    public void setPunchNum(String punchNum) {
        this.punchNum = punchNum;
    }

    public String getLateNum() {
        return lateNum;
    }

    public void setLateNum(String lateNum) {
        this.lateNum = lateNum;
    }

    public String getEarlyNum() {
        return earlyNum;
    }

    public void setEarlyNum(String earlyNum) {
        this.earlyNum = earlyNum;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 包含当前用户的权限信息
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = getRoles();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        authorities.forEach(System.out::println);
        return authorities;
    }

    public User(String username, String password, String name, String sex, String startDate, Dept dept, String phone, String nativePlace, String address, String graduate, String education, String position, String political, String email, String major, String birthday, String salary, String punchNum, String lateNum, String earlyNum, String image, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.startDate = startDate;
        this.dept = dept;
        this.phone = phone;
        this.nativePlace = nativePlace;
        this.address = address;
        this.graduate = graduate;
        this.education = education;
        this.position = position;
        this.political = political;
        this.email = email;
        this.major = major;
        this.birthday = birthday;
        this.salary = salary;
        this.punchNum = punchNum;
        this.lateNum = lateNum;
        this.earlyNum = earlyNum;
        this.image = image;
        this.roles = roles;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", startDate='" + startDate + '\'' +
                ", dept=" + dept +
                ", phone='" + phone + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", address='" + address + '\'' +
                ", graduate='" + graduate + '\'' +
                ", education='" + education + '\'' +
                ", position='" + position + '\'' +
                ", political='" + political + '\'' +
                ", email='" + email + '\'' +
                ", major='" + major + '\'' +
                ", birthday='" + birthday + '\'' +
                ", salary=" + salary +
                ", punchNum='" + punchNum + '\'' +
                ", lateNum='" + lateNum + '\'' +
                ", earlyNum='" + earlyNum + '\'' +
                ", image='" + image + '\'' +
                ", roles=" + roles +
                '}';
    }
}
