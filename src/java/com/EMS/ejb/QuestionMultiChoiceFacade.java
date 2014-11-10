/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.ejb;

import com.EMS.entities.QuestionMultiChoice;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mani
 */
@Stateless
public class QuestionMultiChoiceFacade extends AbstractFacade<QuestionMultiChoice> {
    @PersistenceContext(unitName = "EMSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionMultiChoiceFacade() {
        super(QuestionMultiChoice.class);
    }
    
}
