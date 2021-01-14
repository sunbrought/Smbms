package com.xiaoning.service.user;

import com.xiaoning.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface LoginService {

    public User login(String userCode,String userPassword) throws SQLException;

    public Boolean updatePwd(int id,String password);

    public int getUserCount(String username, int userRole);

    public List<User> getUserList(String username,int userRole,int currentPage,int pageSize);
}
