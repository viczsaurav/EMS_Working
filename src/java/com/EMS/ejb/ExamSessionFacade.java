/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.ejb;

import com.EMS.entities.CourseModule;
import com.EMS.entities.ExamPaper;
import com.EMS.entities.ExamSession;
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
public class ExamSessionFacade extends AbstractFacade<ExamSession> {
    @PersistenceContext(unitName = "EMSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExamSessionFacade() {
        super(ExamSession.class);
    }
    
     public ExamSession fetchExamPaper(ExamPaper ePaper,Student student) {
        TypedQuery<ExamSession> query = em.createNamedQuery("ExamSession.findCurrent", ExamSession.class);
        query.setParameter("ePaper", ePaper);
         query.setParameter("student", student);
        List<ExamSession> sessions = query.getResultList();
        if (sessions.size() > 0) {
            return sessions.get(0);
        } else {
            return null;
        }
    }
    
}
