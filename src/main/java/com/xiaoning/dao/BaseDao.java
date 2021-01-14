package com.xiaoning.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

public class BaseDao {
    private static String driver;
    private static String url;
    private static String user;
    private static String pass;
    static {
        Properties properties=new Properties();
        //通过类加载器获取资源
        InputStream inputStream=BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver=properties.getProperty("driver");
        url=properties.getProperty("url");
        user=properties.getProperty("user");
        pass=properties.getProperty("pass");
    }
    //获取数据库的连接
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection= DriverManager.getConnection(url,user,pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("连接错误");
        }
        return connection;
    }
    //编写公共查询类
    public static ResultSet executeSelect(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet,String sql,Object[] params) throws SQLException {

        try {
            preparedStatement=connection.prepareStatement(sql);
            for(int i=0;i<params.length;i++){
                preparedStatement.setObject(i+1,params[i]);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("查询语句异常！");
        }
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }
    //编写公共增删改类
    public static Integer executeAlter(Connection connection,PreparedStatement preparedStatement,String sql,Object[] params) throws SQLException {
        try {
            preparedStatement=connection.prepareStatement(sql);
            for(int i=0;i<params.length;i++){
                preparedStatement.setObject(i+1,params[i]);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
         return preparedStatement.executeUpdate();
    }
    //编写释放资源类
    public static boolean closeResource(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
         boolean flag=true;
         if(resultSet!=null){
             try {
                 resultSet.close();
             } catch (SQLException throwables) {
                 throwables.printStackTrace();
                 flag=false;
             }
         }
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag=false;
            }
        }
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag=false;
            }
        }
        return flag;
    }
}
