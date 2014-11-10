/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.entities;

import com.EMS.enums.SectionTypes;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author mani
 */
@Entity
public class Section extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sectionName;

    private Long sectionTotalMarks;

    @ManyToOne
    private ExamPaper examPaper;
    
    @Enumerated(EnumType.STRING)
    private SectionTypes sectionType;

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Long getSectionTotalMarks() {
        return sectionTotalMarks;
    }

    public void setSectionTotalMarks(Long sectionTotalMarks) {
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
