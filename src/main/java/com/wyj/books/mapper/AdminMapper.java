package com.wyj.books.mapper;

import com.wyj.books.entity.Admin;
import com.wyj.books.entity.AdminRolePer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "adminmapper")
public interface AdminMapper {
    Admin selectByAdminName(@Param("adminName") String adminName);
    List<AdminRolePer> getAdminRolePer(@Param("searchStr") String searchStr);
    Admin getAdminByPhone(@Param("adminphone") String adminphone);
    void updateAdmin(Admin admin);
    void updateAdminRole(@Param("adminId") int adminId,@Param("roleId") int roleId);
    void insertAdmin(Admin admin);
    void insertAdminRole(@Param("adminId") int adminId,@Param("roleId") int roleId);
    void deleteAdmin(@Param("id") int id);
}
