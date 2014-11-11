/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mani
 */
@NamedQueries(
        {@NamedQuery(name = "CourseModule.findByModuleId", query = "SELECT c FROM CourseModule c WHERE c.name = :name")})

@ViewScoped
@Entity
@XmlRootElement
public class CourseModule extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private Date dateOfExam;

    @Basic(optional = false)
    private Time startTime;

    @Basic(optional = false)
    private Integer duration;

    @Basic(optional = false)
    private String location;
    
    @ManyToMany
    private List<Student> students;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "coursemodule")
    private List<Question> questions;
    
    @Transient private long timeToStart=1;

    public long getTimeToStart() {
        return timeToStart;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setTimeToStart(long timeToStart) {
        this.timeToStart = timeToStart;
    }

    public Date getDateOfExam() {
        return dateOfExam;
    }

    public void setDateOfExam(Date dateOfExam) {
        this.dateOfExam = dateOfExam;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    @OneToMany(cascade=ALL, mappedBy="coursemodule")
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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
        if (!(object instanceof CourseModule)) {
            return false;
        }
        CourseModule other = (CourseModule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.EMS.entities.Module[ id=" + id + " ]";
    }

    public String timeToString() {
        long timeInMilliSeconds = (this.startTime.getTime() + this.dateOfExam.getTime() - System.currentTimeMillis());
        long seconds = timeInMilliSeconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        timeToStart = seconds;
        String time = days + " days:" + hours % 24 + " hours:" + minutes % 60 + " mins:" + seconds % 60 + " secs";
        return time;
        
    }
}
