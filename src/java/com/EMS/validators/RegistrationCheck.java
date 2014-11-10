/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.validators;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sreepal
 */
public class RegistrationCheck {
    
    Map<String,String> errormsgs = null;
    
    public Map<String,String> validate(String userID,String password){
        errormsgs = new HashMap<>();
        if(userID == null || userID.equals("")){
            errormsgs.put("userID", "USER ID is Required");
        }
        if(password == null || password.equals("")){
            errormsgs.put("password","Password is Required");
        }
        return errormsgs;
    }
}
