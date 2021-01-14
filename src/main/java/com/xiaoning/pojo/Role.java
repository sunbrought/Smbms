package com.xiaoning.pojo;

import java.util.Date;

public class Role {
    private Integer id;//id
    private String roleCode;//角色编码
    private String roleName;//角色名称
    private Integer createdBy;//创建者
    private Date creationDate;//更新者
    private Integer modifyBy;//更新时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModdifyDate() {
        return moddifyDate;
    }

    public void setModdifyDate(Date moddifyDate) {
        this.moddifyDate = moddifyDate;
    }

    private Date moddifyDate;
}
