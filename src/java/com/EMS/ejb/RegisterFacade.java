/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.ejb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author Shravani
 */
@Stateless
public class RegisterFacade {
    
    public RegisterFacade(){}
    
    public String register(String userID,String password){
        String msg = "User ID not found or You are already registered";
        
        Connection connection = null;
        try{
            Properties pro = new Properties();
            pro.put("user", "root");
            pro.put("password", "root");
            
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems",pro);
            
            PreparedStatement pre = connection.prepareStatement("UPDATE USERACCESS set PASSWORD=?,ISFIRSTLOGIN='N' where USERID=?"); 
            pre.setString(1, password);
            pre.setString(2, userID);
            int result = pre.executeUpdate();
            pre.close();
            if(result==0){
                return msg;
            }
            else{
                msg = "SUCCESS";
                return msg;
            }
        }catch (SQLException e) {
            return e.getMessage();
        }
         finally{
            if(connection!=null) try {
                connection.close();
            } catch (SQLException ex) {
              Logger.getLogger(RegisterFacade.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
            
        }
}
