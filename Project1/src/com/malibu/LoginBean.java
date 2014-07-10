package com.malibu;

import java.security.Principal;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


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

    public void doLogout() {
        if (session != null) {
            session.invalidate();
        }
        redirectToPage("/faces/welcome.jspx");
    }

    private void redirectToPage(String redirectUrl) {
        final FacesContext fctx = FacesContext.getCurrentInstance();
        try {
            final ExternalContext ectx = fctx.getExternalContext();
            ectx.redirect(ectx.getRequestContextPath() + redirectUrl);
            fctx.responseComplete();
        } catch (Exception e) {
            fctx.responseComplete();
        }
    }
}
