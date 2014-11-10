/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.controllers;

import com.EMS.ejb.RegisterFacade;
import com.EMS.validators.RegistrationCheck;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Sreepal
 */
@RequestScoped
@Named
public class RegisterBean {
    
    private String userID;
    private String password;
    
    @EJB private RegisterFacade registerFacade;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
     public String registerUser(){
         Map<String,String> errormsgs = new RegistrationCheck().validate(userID, password);
         FacesContext fc = FacesContext.getCurrentInstance();
         
         if(!errormsgs.isEmpty()){
             for(String key:errormsgs.keySet()){
                 FacesMessage error = new FacesMessage(errormsgs.get(key));
                 fc.addMessage(key, error);
             }
             return "register";
         }
         else{
             String msg = registerFacade.register(userID, password);
             
             if(!msg.equals("SUCCESS")){
                 FacesMessage error = new FacesMessage(msg);
                 fc.addMessage(null, error);
                 return "register";
             }else{
                 FacesMessage msg1 = new FacesMessage("Successfully registered");
                 fc.addMessage(userID, msg1);
                 //fc.addMessage("registered", msg1);
             }
         }
               
             return "login";    
     }
}
