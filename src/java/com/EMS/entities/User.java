    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.entities;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author mani
 */
@Entity
@SessionScoped

@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByUserLoginID", query = "SELECT u FROM User u WHERE u.loginId = :username")})

public class User extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    private String loginId;
    @Basic(optional = false)
    private String password;
    @Basic(optional = false)
    private String name;
    @Basic(optional = false)
    private String isFirstLogin;
    @Basic(optional = false)
    private String role;

    @OneToOne(mappedBy = "user")
    private Student student;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setIsFirstLogin(String isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

//    @OneToOne(mappedBy="user") private Lecturer lecturer;
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.EMS.entities.User[ id=" + id + ",password=" + password + " ]";
    }

    public User cloneUser() {
        User newUser = new User();
        newUser.setId(this.getId());
        newUser.setIsFirstLogin(this.getIsFirstLogin());
        newUser.setLoginId(this.getLoginId());
        newUser.setName(this.getName());
        newUser.setPassword(this.getPassword());
        newUser.setRole(this.getRole());
        newUser.setStudent(this.getStudent());
        return newUser;
    }

}
