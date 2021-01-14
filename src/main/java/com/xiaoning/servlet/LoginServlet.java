package com.xiaoning.servlet;

import com.xiaoning.pojo.User;
import com.xiaoning.service.user.LoginService;
import com.xiaoning.service.user.LoginServiceImp;
import com.xiaoning.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userCode=req.getParameter("userCode");
        String userPassword=req.getParameter("userPassword");

        LoginService loginService=new LoginServiceImp();
        User user= null;
        try {
            user = loginService.login(userCode,userPassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(user!=null&&userCode.equals(user.getUserCode())&&userPassword.equals(user.getUserPassword())){
            req.getSession().setAttribute(Constants.user_Session,user);
            resp.sendRedirect("/jsp/frame.jsp");
        }else {
            req.setAttribute("error","账户或密码错误，请重新输入！");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
