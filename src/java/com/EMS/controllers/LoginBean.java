/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EMS.controllers;

//import com.EMS.entities.LoginFacade;
import com.EMS.ejb.UserFacade;
import com.EMS.entities.AppUser;
import com.EMS.enums.UserAgentType;
import java.io.Serializable;
import java.sql.SQLException;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Shravani
 */
@SessionScoped
@Named
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username, password;
    //@EJB private LoginFacade loginFacade;

    @Inject
    private AppUser appUser;

    @EJB
    private UserFacade userFacade;

    private AppUser user;

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() throws SQLException, ServletException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        String headerName;
        int userAgent = 0; // For Web, default = 0, for Mobile=1

        if (this.password.equalsIgnoreCase("password")) {
            return "/register?faces-redirect=true";
        }

        // Login
        try {
            if (req.getRemoteUser() == null) {
                req.login(username, password);
                headerName = req.getHeaders("user-agent").nextElement().toLowerCase();
                if (headerName.contains(UserAgentType.android.toString())
                        || headerName.contains(UserAgentType.iphone.toString())) {
                    userAgent = 1; // For Mobile
                }
                appUser = userFacade.fetchUser(req.getRemoteUser());
                user = userFacade.fetchUser(req.getRemoteUser());
            }
        } catch (ServletException e) {
            fc.addMessage(null, new FacesMessage(e.getMessage()));
            return "login";
        }

        //if(loginFacade.loginCheck(username, password).equals(msg)){
        if (req.isUserInRole("student")) {
            String returnLink = (userAgent == 1) ? "Student/StudentExamTimeTable?faces-redirect=true" : "Student/ExamView?faces-redirect=true";
            return returnLink;
        } else if (req.isUserInRole("lecturer")) {
            return "/Lecturer/question/List.xhtml?faces-redirect=true";
        } else if (req.isUserInRole("admin")) {
            return "/Admin/uploadDetails.xhtml?faces-redirect=true";
        } else {
            return "template?faces-redirect=true";
        }
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.logout();
            context.getExternalContext().invalidateSession();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
            return null;
        }
        return "/login?faces-redirect=true";
    }

}
