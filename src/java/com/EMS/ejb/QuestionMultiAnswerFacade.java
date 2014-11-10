/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.ejb;

import com.EMS.entities.QuestionMultiAnswer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mani
 */
@Stateless
public class QuestionMultiAnswerFacade extends AbstractFacade<QuestionMultiAnswer> {
    @PersistenceContext(unitName = "EMSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionMultiAnswerFacade() {
        super(QuestionMultiAnswer.class);
    }
    
}
