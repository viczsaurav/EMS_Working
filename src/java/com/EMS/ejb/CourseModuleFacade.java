/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.ejb;

import com.EMS.entities.CourseModule;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Saurav
 */
@Stateless
public class CourseModuleFacade extends AbstractFacade<CourseModule> {
    @PersistenceContext(unitName = "EMSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CourseModuleFacade() {
        super(CourseModule.class);
    }
    
    public CourseModule findByModuleId(String courseModuleName){
        TypedQuery<CourseModule> query = em.createNamedQuery("CourseModule.findByModuleId", CourseModule.class);
        
        query.setParameter("name", courseModuleName);
        List<CourseModule> courseModules = query.getResultList();
        if (courseModules.size() > 0) {
            return courseModules.get(0);
        } else {
            return null;
        }
    }
    
}
