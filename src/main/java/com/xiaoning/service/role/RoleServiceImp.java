package com.xiaoning.service.role;

import com.xiaoning.dao.BaseDao;
import com.xiaoning.dao.role.RoleDao;
import com.xiaoning.dao.role.RoleDaoImp;
import com.xiaoning.pojo.Role;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleServiceImp implements RoleService{
    private RoleDao roleDao;
    public RoleServiceImp(){
        roleDao=new RoleDaoImp();
    }
    @Override
    public List<Role> getRoleList() {
        Connection connection=null;
        List<Role> roleList=new ArrayList<>();
        try {
            connection= BaseDao.getConnection();
            roleList=roleDao.getRoleList(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return roleList;
    }
    @Test
    public void test(){
        List<Role> roleList=new ArrayList();
        roleList=getRoleList();
        for(Role role :roleList){
            System.out.println(role.getRoleCode());
        }
    }
}
