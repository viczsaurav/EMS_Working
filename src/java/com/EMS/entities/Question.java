/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.entities;

import com.EMS.enums.QuestionTypes;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mani
 */
@SessionScoped
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "QuestionType")
@DiscriminatorValue("ESSAY")
@XmlRootElement
public class Question extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    private String text;
    
    @ManyToOne
    private Section section;
    
    @ManyToOne
    private CourseModule coursemodule;
    
    @Basic(optional = false)
    private Integer marks;
        
    @ManyToMany
    @JoinTable(name = "QUESTION_SUBJECT", joinColumns = {
    @JoinColumn(name = "QUESTION_ID")}, inverseJoinColumns = {
    @JoinColumn(name = "SUBJECT_ID")})
    private List<Subject> subjects;
    
    @Basic(optional = true)
    @Enumerated(EnumType.STRING)
    private QuestionTypes typeOfQuestion;

    public QuestionTypes getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(QuestionTypes typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }
    
    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CourseModule getCoursemodule() {
        return coursemodule;
    }

    public void setCoursemodule(CourseModule coursemodule) {
        this.coursemodule = coursemodule;
    }


    @XmlTransient
    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
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
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.EMS.entities.Question[ id=" + id + " ]";
    }
    
}
