/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.ejb;

import com.EMS.entities.AbstractFacade;
import com.EMS.entities.AppUser;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Saurav
 */
@Stateless

public class UserFacade extends AbstractFacade<AppUser> {

    @PersistenceContext(unitName = "EMSPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(AppUser.class);
    }

    public AppUser fetchUser(String username) {
        TypedQuery<AppUser> query = em.createNamedQuery("User.findByUserLoginID", AppUser.class);
        query.setParameter("username", username);
        List<AppUser> users = query.getResultList();
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }
}
