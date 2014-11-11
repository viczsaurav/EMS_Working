/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.ejb;

import com.EMS.entities.AppUser;
import com.EMS.entities.CourseModule;
import com.EMS.entities.ExamPaper;
import com.EMS.entities.Student;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Saurav
 */
@Stateless
public class ExamPaperFacade extends AbstractFacade<ExamPaper> {
    @PersistenceContext(unitName = "EMSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExamPaperFacade() {
        super(ExamPaper.class);
    }
    
     public ExamPaper fetchExamPaper(CourseModule mod) {
        TypedQuery<ExamPaper> query = em.createNamedQuery("ExamPaper.findByModule", ExamPaper.class);
        query.setParameter("module", mod);
        List<ExamPaper> mods = query.getResultList();
        if (mods.size() > 0) {
            return mods.get(0);
        } else {
            return null;
        }
    }
    
}
