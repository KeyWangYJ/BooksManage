package com.wyj.books.mapper;

import com.wyj.books.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "permissionmapper")
public interface PermissionMapper {
    List<Permission> getPerByRoleId(@Param("roleid") int roleid);
}
