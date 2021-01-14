package com.xiaoning.service.user;

import com.xiaoning.dao.BaseDao;
import com.xiaoning.dao.user.UserDao;
import com.xiaoning.dao.user.UserDaoImp;
import com.xiaoning.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginServiceImp implements LoginService{

    private UserDao userDao;
    public LoginServiceImp() {
        this.userDao = new UserDaoImp();
    }
    @Override
    public User login(String userCode, String userPassword) throws SQLException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        User user=null;
        try {
            connection=BaseDao.getConnection();
            user=userDao.getLoginUser(connection,preparedStatement,resultSet,userCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public Boolean updatePwd(int id, String password) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        boolean flag=false;
        if(userDao.updatePwd(connection,preparedStatement,id,password)>0){
            flag=true;
        }
        return flag;
    }

    @Override
    public int getUserCount(String username, int userRole) {
        int count=0;
        Connection connection=null;
        try {
            connection=BaseDao.getConnection();
            count=userDao.getUserCount(connection,username,userRole);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }

    @Override
    public List<User> getUserList(String username, int userRole, int currentPage, int pageSize) {
        Connection connection=null;
        List<User> list=new ArrayList<>();
        try {
            connection=BaseDao.getConnection();
            list=userDao.getUserList(connection,username,userRole,currentPage,pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return list;
    }

    @Test
    public void test(){
        System.out.println(getUserList("1",2,1,5));
    }
}
