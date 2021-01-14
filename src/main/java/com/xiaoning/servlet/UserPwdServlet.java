package com.xiaoning.servlet;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.xiaoning.pojo.Role;
import com.xiaoning.pojo.User;
import com.xiaoning.service.role.RoleService;
import com.xiaoning.service.role.RoleServiceImp;
import com.xiaoning.service.user.LoginService;
import com.xiaoning.service.user.LoginServiceImp;
import com.xiaoning.util.Constants;
import com.xiaoning.util.PageSupport;

import javax.crypto.spec.PSource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//密码修改（实现多个Servlet复用）
public class UserPwdServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取隐藏域中的值类判断调用哪个Servlet
        String method=req.getParameter("method");
        if(method.equals("savepwd")&&method!=null){
            updatePwd(req,resp);
        }else if(method.equals("pwdmodify")&&method!=null){
            pwdModify(req,resp);
        }else if(method.equals("query")&&method!=null){
            userList(req,resp);
        }
    }
    //密码修改
    public static void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password=req.getParameter("newpassword");
        Object o=req.getSession().getAttribute(Constants.user_Session);
        if(o!=null&& !StringUtils.isNullOrEmpty(password)){
            LoginService loginService=new LoginServiceImp();
            User user=(User)o;
            Boolean flag=false;
            flag=loginService.updatePwd(user.getId(),password);
            if(flag){
                req.setAttribute("message","密码修改成功，注销请重新登录！");
                req.getSession().removeAttribute(Constants.user_Session);
            }else{
                req.setAttribute("message","系统内部出现问题，密码无法修改！");
            }
        }else{
            req.setAttribute("message","你的密码已经过期,请重新登录！");
        }
        //过滤器无法过滤请求转发，在Servlet中，它只过滤外部请求
        req.getRequestDispatcher("/jsp/pwdmodify.jsp").forward(req,resp);
    }

    //验证旧密码
    public static void pwdModify(HttpServletRequest req, HttpServletResponse resp){
        Object o=req.getSession().getAttribute(Constants.user_Session);
        String oldPwd=req.getParameter("oldpassword");
        String pwd=((User)o).getUserPassword();
        Map<String,String> resultSet=new HashMap<String,String>();
        //Session失效
        if(o==null){
            resultSet.put("result","sessionerror");
        }else if(StringUtils.isNullOrEmpty(oldPwd)){
            resultSet.put("result","error");
        }else {
            if(oldPwd.equals(pwd)){
                resultSet.put("result","true");
            }else {
                resultSet.put("result","false");
            }
        }
        PrintWriter printWriter=null;
        try {
            //设置响应的类型
            resp.setContentType("application/json");
            printWriter=resp.getWriter();
            printWriter.write(JSONArray.toJSONString(resultSet));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            printWriter.flush();
            printWriter.close();
        }
    }
    //用户展示
    private void userList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取前端传递的信息
        String queryUserName=req.getParameter("queryUserName");
        String temp=req.getParameter("queryUserRole");
        String currentPageNo=req.getParameter("pageIndex");
        int queryUserRole=0;

        //获取用户列表
        LoginService userService=new LoginServiceImp();

        //第一次走这个请求一定是第一页，页面大小固定的，对三个参数进行赋值
        int pageSize=5;
        int currentPage=1;
        if(queryUserName==null){
            queryUserName="";
        }
        if(temp!=null){
            queryUserRole=Integer.parseInt(temp);
        }
        if(currentPageNo!=null){
            currentPage=Integer.parseInt(currentPageNo);
        }
        //获取用户总数(分页，上一页，下一页情况)
        int totalCount=userService.getUserCount(queryUserName,queryUserRole);
        //总页数支持
        PageSupport pageSupport=new PageSupport();
        pageSupport.setCurrentPageNo(currentPage);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        //总页数
        int totalPageCount=pageSupport.getTotalPageCount();
        //控制首页和尾页
        if(currentPage<1){
            currentPage=1;
        }else if(currentPage>totalPageCount){
            currentPage=totalPageCount;
        }
        //获取角色列表
        RoleService roleService=new RoleServiceImp();
        List<Role> roleList=roleService.getRoleList();
        req.setAttribute("roleList",roleList);
        //获取用户列表展示
        List<User> list=userService.getUserList(queryUserName,queryUserRole,currentPage,pageSize);
        req.setAttribute("userList",list);
        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPage);
        req.setAttribute("totalPageCount",totalPageCount);
        req.setAttribute("queryUserRole",queryUserRole);
        req.setAttribute("queryUserName",queryUserName);

        req.getRequestDispatcher("/jsp/userlist.jsp").forward(req,resp);
    }
}
