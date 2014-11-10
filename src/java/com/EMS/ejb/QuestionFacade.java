/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.ejb;

import com.EMS.entities.Question;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 *
 * @author mani
 */
@Stateless
public class QuestionFacade extends AbstractFacade<Question> {
    @PersistenceContext(unitName = "EMSPU",type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionFacade() {
        super(Question.class);
    }
    
    public void flushQuestions()
    {
        em.flush();
    }
    
}
