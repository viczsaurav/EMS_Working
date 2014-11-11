/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.entities;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author mani
 */
@Entity
@SessionScoped
@NamedQueries({
    @NamedQuery(name = "ExamSession.findAll", query = "SELECT s FROM ExamSession s"),
    @NamedQuery(name = "ExamSession.findById", query = "SELECT s FROM ExamSession s WHERE s.id = :id"),
    @NamedQuery(name = "ExamSession.findCurrent", query = "SELECT s FROM ExamSession s WHERE s.examPaper = :ePaper AND s.student = :student")})
public class ExamSession extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private ExamPaper examPaper;

    @ManyToOne
    private Student student;

    private Boolean isActiveSession = false;

    public Boolean getIsActiveSession() {
        return isActiveSession;
    }

    public void setIsActiveSession(Boolean isActiveSession) {
        this.isActiveSession = isActiveSession;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ExamPaper getExamPaper() {
        return examPaper;
    }

    public void setExamPaper(ExamPaper examPaper) {
        this.examPaper = examPaper;
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
        if (!(object instanceof ExamSession)) {
            return false;
        }
        ExamSession other = (ExamSession) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.EMS.entities.ExamSession[ id=" + id + " ]";
    }

}
