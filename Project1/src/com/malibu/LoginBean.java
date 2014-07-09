package com.malibu;

import java.security.Principal;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import weblogic.security.principal.WLSUserImpl;

public class LoginBean {
    String username;
    String uid;
    HttpSession session;

    public LoginBean() {
        super();
    }

    public LoginBean(HttpServletRequest request, HttpSession session) {
        super();
        this.session = session;
        Principal princple = request.getUserPrincipal();
        username = princple.getName();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String toString() {
        return "This is the login bean";
    }

    public String doLogout() {
        // Add event code here...
        if (session == null) {
            session = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
        }
        session.invalidate();
        return "/faces/welcome.jspx";
    }
}
