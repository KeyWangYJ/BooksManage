package com.wyj.books.service;

import com.wyj.books.entity.Role;
import com.wyj.books.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;

    public Role getRoleByAdminId(int id){
        Role role = roleMapper.getRoleByAdminId(id);
        return role;
    }
}
