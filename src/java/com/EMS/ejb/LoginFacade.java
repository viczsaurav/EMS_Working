package com.EMS.ejb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sreepal
 */
@Stateless
public class LoginFacade{

    public LoginFacade() {
    }
    
//    @PersistenceContext(unitName = "EMSPU")
//    private EntityManager em;
    
//    @Resource(name = "jndi/emsJNDI")
//    DataSource dataSource;
    
    String username,password,isFirstLogin;

    public String loginCheck(String username,String password) throws SQLException{
        String msg = "User ID or Password incorrect";
        Connection connection = null;
        try{
            Properties prop = new Properties();
            prop.put("user", "root");
            prop.put("password", "root");
            
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems",prop);
            //PreparedStatement pre = connection.prepareStatement("Select * FROM USERS where USERID=? and PASSWORD=?");
            PreparedStatement pre = connection.prepareStatement("Select * FROM USERS where USERID=? and PASSWORD=?");
            pre.setString(1, username);
            pre.setString(2, password);
            
            ResultSet result = pre.executeQuery();
            //pre.close();
            
            
            if(result.next()){
                msg = "SUCCESS";
                while(result.next()){
                    System.out.println(result.getString("USERID")+ ":" + result.getString("PASSWORD"));
                }
                pre.close();
                return msg;
            }
            /*int idIndex   = result.findColumn("USERID");
            int passwordIndex    = result.findColumn("PASSWORD");
            int isFirstLoginIndex  = result.findColumn("ISFIRSTLOGIN");*/
            
            /*
            while(result.next()) {
                String  userid    = result.getString     (idIndex);
                String  pwd    = result.getString      (passwordIndex);
                String  isFirstLogin   = result.getString (isFirstLoginIndex);
                
                if(username.equals(userid)&& password.equals(pwd))
                    return "SUCCESS";
                break;
            } */
            
            //return null;    
             }catch (SQLException e) {
                  return e.getMessage();
             }
            finally{
            if(connection!=null) try {
                connection.close();
            } catch (SQLException ex) {
              Logger.getLogger(LoginFacade.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
        return msg;

    }
}
