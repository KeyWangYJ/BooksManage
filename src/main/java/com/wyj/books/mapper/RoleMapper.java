package com.wyj.books.mapper;
import com.wyj.books.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "rolemapper")
public interface RoleMapper {
    Role getRoleByAdminId(@Param("id") int id);
}
