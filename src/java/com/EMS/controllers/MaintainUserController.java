/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.EMS.controllers;

import com.EMS.ejb.CourseModuleFacade;
import com.EMS.ejb.StudentFacade;
import com.EMS.ejb.UserFacade;
import com.EMS.entities.CourseModule;
import com.EMS.entities.Student;
import com.EMS.entities.AppUser;
import com.EMS.enums.UploadType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Santan
 */
@Named
@RequestScoped
public class MaintainUserController implements Serializable {

    private UploadType uploadTypes;
    private String selectedUploadType;
    @Resource
    UserTransaction tx;
    private static final long serialVersionUID = 1L;
    private Part file;
    private @EJB
    UserFacade uploadUserBean;
    private @EJB
    StudentFacade uploadStudentBean;
    private @EJB CourseModuleFacade courseModuleFacade;

    public MaintainUserController() {

    }

    public boolean upload()throws IOException{
        if(selectedUploadType.equals("LECTURER"))
            uploadLectures();
        else if(selectedUploadType.equals("STUDENT"))
            uploadStudents();
        else if(selectedUploadType.equals("SUBJECTS"))
            uploadSubjects();
        else
            return false;
        return true;
    } 
    
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public UploadType[] UploadTypes() {
        return uploadTypes.values();
    }

    public String getSelectedUploadType() {
        return selectedUploadType;
    }

    public void setSelectedUploadType(String selecteUploadType) {
        this.selectedUploadType = selecteUploadType;
    }

    public boolean uploadStudents() throws IOException {
        System.out.print(">>> in uploadStudents");
        String record = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        try {
            while ((record = br.readLine()) != null) {
                String recordValues[] = record.split(",");
                int i = 2;
                Student student = new Student();
                List<CourseModule> courseModules = new ArrayList<>();
                AppUser user = new AppUser();
                user.setLoginId(recordValues[0]);
                user.setPassword("password");
                user.setIsFirstLogin("y");
                user.setName(recordValues[1]);
                user.setRole("student");
//                student.setId(Long.parseLong(recordValues[0]));
                student.setName(recordValues[1]);
                while(i<recordValues.length){
                    CourseModule courseModule = new CourseModule();
                    courseModule = courseModuleFacade.findByModuleId(recordValues[i]);
                    courseModules.add(courseModule);
                    i++;
                }
                student.setEnrolledModules(courseModules);
                student.setUser(user);
//                userList.add(user);
                try {
                    tx.begin();
                    uploadStudentBean.create(student);
                    uploadUserBean.create(user);
                    tx.commit();
                } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException E) {
                    try {
                        tx.rollback();
                    } catch (IllegalStateException | SecurityException | SystemException exe) {
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
    }

    public boolean uploadLectures() {

        return false;
    }

    public boolean uploadSubjects() {

        return false;
    }
}
