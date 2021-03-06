package com.web.core.mapper;

import com.web.core.pojo.Role;
import com.web.core.pojo.RoleInfo;

import java.util.List;

public interface RoleMapper {

    /**
     * 查询所有的角色
     * @return
     */
    List<Role> queryAllRole(int begin,int limit);


    /**
     * 查询所有的角色
     * @return
     */
    List<Role> queryAll();

    /**
     * 查询角色恶的数量
     * @return
     */
    int queryRoleCount();


    /**
     * 插入角色
     * @param roleName
     * @param roleMsg
     */
    void insertRole(String roleName,String roleMsg);

}
