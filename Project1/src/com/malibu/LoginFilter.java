package com.malibu;

import java.io.IOException;

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
            response.sendRedirect(request.getContextPath() + "/login"); // No logged-in user found, so redirect to login page.
        } else {
          // Logged-in user found, so just continue request.
            LoginBean loginbean = new LoginBean(request,session);
            session.setAttribute("LoginBean", loginbean);
            //LoginBean loginbean = (LoginBean)session.getAttribute("LoginBean");//This is the value inside web.xml
            //TODO: We need to initialize the login bean here.
            filterChain.doFilter(servletRequest, servletResponse);
        }
        
    }

    public void destroy() {
    }
}
