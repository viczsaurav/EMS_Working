/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.entities;

import com.EMS.entities.Student;
import com.EMS.entities.User;
import com.EMS.ejb.UserFacade;
import com.EMS.ejb.StudentFacade;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;

/**
 *
 * @author Santan
 */
@Named
@RequestScoped
public class MaintainUser implements Serializable {

    @Resource
    UserTransaction tx;
    private static final long serialVersionUID = 1L;
    private Part file;
    private @EJB
    UserFacade uploadUserBean;
    private @EJB
    StudentFacade uploadStudentBean;

    public MaintainUser() {

    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public boolean uploadStudents() throws IOException {
        System.out.print(">>> in uploadStudents");
        String record = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        try {
            while ((record = br.readLine()) != null) {
                String recordValues[] = record.split(",");
                Student student = new Student();
                User user = new User();
                System.out.println(Long.parseLong(recordValues[0]));
                user.setLoginId(recordValues[0]);
                user.setPassword("password");
                user.setIsFirstLogin("y");
                user.setRole("student");
//                student.setId(Long.parseLong(recordValues[0]));
                student.setName(recordValues[1]);
                student.setUser(user);
//                userList.add(user);
                try {
                    tx.begin();
                    uploadStudentBean.create(student);
                    uploadUserBean.create(user);
                    tx.commit();
                } catch (Exception E) {
                    try {
                        tx.rollback();
                    } catch (Exception exe) {
                        System.out.println("Rollback failed: " + exe.getMessage());
                    }
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            FacesMessage message = new FacesMessage("CSV values are defective");
            System.out.println("CSV values are defective");
            return false;
        }

//        userList = parseFile(userList,br);
    }

//    private List<?> parseFile(List<?> list,BufferedReader br)throws IOException{
//        List<User> userList = new LinkedList<User>();
//        
//        String record = "";
//        while((record=br.readLine())!=null){
//            String recordValues[] = record.split(",");
//            User user = new User();
//            user.setId();
//        }
//        return userList;
//    }
    public boolean uploadLectures() {

        return false;
    }

    public boolean uploadSubjects() {

        return false;
    }
}
