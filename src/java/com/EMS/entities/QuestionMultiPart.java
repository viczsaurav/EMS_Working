package com.EMS.entities;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.EMS.entities;
//
//import java.io.Serializable;
//import java.util.Collection;
//import javax.persistence.CascadeType;
//import javax.persistence.DiscriminatorColumn;
//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.PrimaryKeyJoinColumn;
//
///**
// *
// * @author mani
// */
//@Entity
//@DiscriminatorColumn(name = "QuestionType")
//@DiscriminatorValue("MULTI_PART")
//@PrimaryKeyJoinColumn(name = "id")
//public class QuestionMultiPart extends Question implements Serializable {
//    private static final long serialVersionUID = 1L;
//    
//    @ManyToOne (cascade = CascadeType.ALL)
//    private QuestionMultiPart parentQuestion;
//    
//    @OneToMany(cascade=CascadeType.ALL,mappedBy = "parentQuestion")
//    private Collection<Question> childQuestions;
//
//    public QuestionMultiPart getParentQuestion() {
//        return parentQuestion;
//    }
//
//    public void setParentQuestion(QuestionMultiPart parentQuestion) {
//        this.parentQuestion = parentQuestion;
//    }
//
//    public Collection<Question> getChildQuestions() {
//        return childQuestions;
//    }
//
//    public void setChildQuestions(Collection<Question> childQuestions) {
//        this.childQuestions = childQuestions;
//    }
//    
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (id != null ? id.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof QuestionMultiPart)) {
//            return false;
//        }
//        QuestionMultiPart other = (QuestionMultiPart) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.EMS.entities.QuestionMultiPart[ id=" + id + " ]";
//    }
//    
//}
