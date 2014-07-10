package com.malibu;

import com.malibu.security.ldap.IdentityStoreAdapter;

import java.io.IOException;

import java.util.Iterator;

import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.security.idm.IdentityStore;
import oracle.security.idm.Property;
import oracle.security.idm.PropertySet;
import oracle.security.idm.UserProfile;

public class LoginFilter implements Filter {
    public LoginFilter() {
        super();
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/welcome.jspx"); // so redirect to login page.
        } else {
            // Logged-in user found, so just continue request.
            LoginBean loginbean = (LoginBean)session.getAttribute("LoginBean"); //Check whether login bean exsit or not
            if (loginbean == null) {
                loginbean = new LoginBean(request, session);
                session.setAttribute("LoginBean", loginbean);
                IdentityStoreAdapter identityAdapter = IdentityStoreAdapter.getInstance();
                UserProfile userProfile = identityAdapter.getUserProfile(loginbean.getUsername());
                if (userProfile != null) {
                    try {

                        PropertySet propertyset = userProfile.getAllUserProperties();
                        System.out.println(identityAdapter.getPropertyVal(userProfile, "jbwSecureItEaseId"));
                        String[] attristr =
                        { "naguid", "cn", "givenname", "sn", "nagaccessrole", "jbwid", "jbwmarketondemandid", "jbwsecureiteaseid", "nagpwdmustchange", "objectclass", "objectclass", "objectclass",
                          "objectclass", "objectclass", "objectclass", "userpassword", "authpassword", "authpassword", "authpassword" };


                        for (Iterator<Property> itr = userProfile.getProperties(attristr).getAll(); itr.hasNext(); ) {
                            Property currentproperty = itr.next();
                            System.out.println(currentproperty.getName() + " = " + currentproperty.getValues());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);

        }

    }

    public void destroy() {
    }
}
