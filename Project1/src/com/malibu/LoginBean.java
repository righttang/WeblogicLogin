package com.malibu;

import java.security.Principal;

import java.util.ArrayList;
import java.util.Iterator;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.security.idm.Property;
import oracle.security.idm.UserProfile;


public class LoginBean {
    String username;
    String uid;
    String userLdapInfo;
    HttpSession session;
    UserProfile userProfile;
    public ArrayList<UserProperty> userpropertyArray = new  ArrayList<UserProperty>();

    private String[] attristr =
    { "entryuid","nagaccessrole","uid", "naguid", "cn", "givenname", "sn", "nagaccessrole", "jbwid", "jbwmarketondemandid", "jbwsecureiteaseid", "nagpwdmustchange", "objectclass", "objectclass", "objectclass", "objectclass",
      "objectclass", "objectclass", "userpassword", "authpassword", "authpassword", "authpassword" };

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

    public void setUserLdapInfo(String userLdapInfo) {
        this.userLdapInfo = userLdapInfo;
    }

    public String getUserLdapInfo() {
        return userLdapInfo;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        try {
            for (Iterator<Property> itr = userProfile.getProperties(attristr).getAll(); itr.hasNext(); ) {
                Property prop = itr.next();
                userpropertyArray.add(new UserProperty(prop.getName(), prop.getValues()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public UserProfile getUserProfile() {
        return userProfile;
    }


    public ArrayList<UserProperty> getUserpropertyArray() {
        return userpropertyArray;
    }
}
