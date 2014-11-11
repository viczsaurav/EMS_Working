/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author mani
 */
@Entity

@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
    @NamedQuery(name = "Student.findById", query = "SELECT s FROM Student s WHERE s.id = :id"),
    @NamedQuery(name = "Student.findByUser", query = "SELECT s FROM Student s WHERE s.user = :appUser")})

public class Student extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    @OneToOne
    private AppUser user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "STUDENT_COURSEMODULES", joinColumns
            = @JoinColumn(name = "STUDENT_ID"), inverseJoinColumns
            = @JoinColumn(name = "COURSE_ID"))
    private List<CourseModule> enrolledModules;

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourseModule> getEnrolledModules() {
        return enrolledModules;
    }

    public void setEnrolledModules(List<CourseModule> enrolledModules) {
        this.enrolledModules = enrolledModules;
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
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.EMS.entities.Student[ id=" + id + " ]";
    }

}
