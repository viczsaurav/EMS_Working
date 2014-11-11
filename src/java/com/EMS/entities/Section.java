/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.entities;

import com.EMS.enums.SectionTypes;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
/**
 *
 * @author mani
 */
@Entity
@ViewScoped
public class Section extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sectionName;
    @OneToMany
    private List<Question> questions;

    private int sectionTotalMarks;

    @ManyToOne
    private ExamPaper examPaper;
    
    @Enumerated(EnumType.STRING)
    private SectionTypes sectionType;
    
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getSectionTotalMarks() {
        return sectionTotalMarks;
    }

    public void setSectionTotalMarks(int sectionTotalMarks) {
        this.sectionTotalMarks = sectionTotalMarks;
    }

    public SectionTypes getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionTypes sectionType) {
        this.sectionType = sectionType;
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
        if (!(object instanceof Section)) {
            return false;
        }
        Section other = (Section) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.EMS.entities.Section[ id=" + id + " ]";
    }

}