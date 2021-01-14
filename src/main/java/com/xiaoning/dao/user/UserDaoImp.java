package com.xiaoning.dao.user;

import com.mysql.jdbc.StringUtils;
import com.xiaoning.dao.BaseDao;
import com.xiaoning.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDaoImp implements UserDao{
    @Override
    public User getLoginUser(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet, String userCode) throws SQLException {

        User user=null;
        if(connection!=null){
            String sql="select *from smbms_user where userCode=?";
            Object[] params={userCode};
            resultSet=BaseDao.executeSelect(connection,preparedStatement,resultSet,sql,params);
            while (resultSet.next()){
                user=new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getDate("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getDate("modifyDate"));
            }
            BaseDao.closeResource(connection,preparedStatement,resultSet);
        }
        return user;
    }

    @Override
    public int updatePwd(Connection connection, PreparedStatement preparedStatement, int id, String password) {
        int row=0;
        try {
            connection=BaseDao.getConnection();
            String sql="update smbms_user set userPassword=? where id=?";
            Object[] params={password,id};
            row=BaseDao.executeAlter(connection,preparedStatement,sql,params);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,preparedStatement,null);
        }
        return row;
    }

    @Override
    public int getUserCount(Connection connection, String username, int userRole) {
        int count=0;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        List<Object> list=new ArrayList<>();
        StringBuffer sql=new StringBuffer();
        if(connection!=null){
            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole=r.id");
            if(!StringUtils.isNullOrEmpty(username)){
                sql.append(" and u.userName like ?");
                list.add("%"+username+"%");
            }else if(userRole>0){
                sql.append(" and u.userRole = ? ");
                list.add(userRole);
            }
        }
        Object[] o=list.toArray();
        try {
            resultSet=BaseDao.executeSelect(connection,preparedStatement,resultSet,sql.toString(),o);
            if(resultSet.next()){
                count=resultSet.getInt("count");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,preparedStatement,resultSet);
        }
        return count;
    }

    @Override
    public List<User> getUserList(Connection connection, String username, int userRole, int currentPage, int pageSize) {
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        List<Object> list=new ArrayList<>();
        List<User> listUser=new ArrayList<>();
        StringBuffer sql=new StringBuffer();
        if(connection!=null){
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole=r.id");
            if(!StringUtils.isNullOrEmpty(username)){
                sql.append(" and u.userName like ?");
                list.add("%"+username+"%");
            }
            if(userRole>0){
                sql.append(" and u.userRole = ? ");
                list.add(userRole);
            }
        }
        sql.append(" order by creationDate DESC limit ?,?");
        int startIndex=(currentPage-1)*pageSize;
        list.add(startIndex);
        list.add(pageSize);
        Object[] o=list.toArray();
        System.out.println("用户管理查询SQL:"+sql);
        User user;
        try {
            resultSet=BaseDao.executeSelect(connection,preparedStatement,resultSet,sql.toString(),o);
            while(resultSet.next()){
                user=new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setUserRoleName(resultSet.getString("userRoleName"));
                listUser.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(null,null,resultSet);
        }

        return listUser;
    }
}
