package com.wyj.books.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyj.books.entity.Admin;
import com.wyj.books.entity.AdminRolePer;
import com.wyj.books.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    AdminMapper adminmapper;

    public Admin getAdminByName(String adminName){
        Admin a = adminmapper.selectByAdminName(adminName);
        return a;
    }

    public PageInfo<AdminRolePer> getAdminRolePer(String searchVal,Integer page, Integer size){
        PageHelper.startPage(page, size);
        List<AdminRolePer> list = adminmapper.getAdminRolePer(searchVal);
        PageInfo<AdminRolePer> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public Admin getAdminByPhone(String phone){
        Admin admin = adminmapper.getAdminByPhone(phone);
        return admin;
    }

    public void updateAdmin(Admin admin){
        adminmapper.updateAdmin(admin);
    }

    public void updateAdminRole(int adminId,int roleId){
        adminmapper.updateAdminRole(adminId,roleId);
    }

    public void insertAdmin(Admin admin){
        adminmapper.insertAdmin(admin);
    }

    public void insertAdminRole(int adminId,int roleId){
        adminmapper.insertAdminRole(adminId,roleId);
    }

    public void deleteAdmin(int id){
        adminmapper.deleteAdmin(id);
    }
}
