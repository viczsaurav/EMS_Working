/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author mani
 */
@SessionScoped
@Entity
@Named
public class ExamPaper extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date createdOn;

    @OneToOne
    private CourseModule module;
    
      
    @OneToMany (mappedBy = "examPaper")
    private Collection<Section> section;

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public CourseModule getModule() {
        return module;
    }

    public void setModule(CourseModule module) {
        this.module = module;
    }

    public Collection<Section> getSection() {
        return section;
    }

    public void setSection(Collection<Section> section) {
        this.section = section;
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
        if (!(object instanceof ExamPaper)) {
            return false;
        }
        ExamPaper other = (ExamPaper) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.EMS.entities.ExamPaper[ id=" + id + " ]";
    }

}
