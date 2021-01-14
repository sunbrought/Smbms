package com.xiaoning.dao.user;

import com.xiaoning.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 接口普通成员变量使用public final static
 * 接口方法默认使用Abstract修饰
 */
public interface UserDao {
    //登录
    public User getLoginUser(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet, String userCode) throws SQLException;
    //使用用户ID修改密码
    public int updatePwd(Connection connection,PreparedStatement preparedStatement,int id,String password);
    //通过姓名与角色查询用户总数
    public int getUserCount(Connection connection,String username,int userRole);
    //获取用户列表
    public List<User> getUserList(Connection connection,String username,int userRole,int currentPage,int pageSize);

}