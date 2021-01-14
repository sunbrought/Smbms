package com.xiaoning.servlet;

import com.xiaoning.pojo.User;
import com.xiaoning.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User session=(User)req.getSession().getAttribute(Constants.user_Session);
        if(session!=null){
            req.getSession().removeAttribute(Constants.user_Session);
            resp.sendRedirect("/login.jsp");
            return;
        }
        resp.sendRedirect("/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
