/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.ejb;

import com.EMS.entities.Student;
import com.EMS.entities.AppUser;
import com.EMS.entities.Lecturer;
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
public class LecturerFacade extends AbstractFacade<Lecturer> {

    @PersistenceContext(unitName = "EMSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LecturerFacade() {
        super(Lecturer.class);
    }

    public Lecturer fetchLecturer(AppUser appUser) {
        TypedQuery<Lecturer> query = em.createNamedQuery("Lecturer.findByUser", Lecturer.class);
        query.setParameter("appUser", appUser);
        List<Lecturer> lecturers = query.getResultList();
        if (lecturers.size() > 0) {
            return lecturers.get(0);
        } else {
            return null;
        }
    }
}
