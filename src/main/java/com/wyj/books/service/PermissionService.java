package com.wyj.books.service;

import com.wyj.books.entity.Permission;
import com.wyj.books.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    public List<Permission> getPerByRoleId(int roleid){
        List<Permission> list = permissionMapper.getPerByRoleId(roleid);
        return list;
    }
}
