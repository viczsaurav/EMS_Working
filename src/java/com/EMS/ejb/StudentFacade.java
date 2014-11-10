/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.ejb;

import com.EMS.entities.Student;
import com.EMS.entities.User;
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
public class StudentFacade extends AbstractFacade<Student> {

    @PersistenceContext(unitName = "EMSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentFacade() {
        super(Student.class);
    }

    public Student fetchStudent(User appUser) {
        TypedQuery<Student> query = em.createNamedQuery("Student.findByUser", Student.class);
        query.setParameter("studentId", appUser);
        List<Student> students = query.getResultList();
        if (students.size() > 0) {
            return students.get(0);
        } else {
            return null;
        }
    }
}
