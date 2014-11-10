/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.entities;

import com.EMS.enums.QuestionTypes;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
/**
 *
 * @author mani
 */
@Entity
@DiscriminatorColumn(name = "QuestionType")
@DiscriminatorValue("MULTI_ANSWER")
@PrimaryKeyJoinColumn(name = "id")
public class QuestionMultiAnswer extends Question implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ElementCollection
    private List<String> choices;

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
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
        if (!(object instanceof QuestionMultiAnswer)) {
            return false;
        }
        QuestionMultiAnswer other = (QuestionMultiAnswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.EMS.entities.QuestionMultiAnswer[ id=" + id + " ]";
    }
    
}
