package com.wyj.books.mapper;

import com.wyj.books.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "usermapper")
public interface UserMapper {

    User selectByUsername(@Param("username") String username);

    User getUserByPhone(@Param("phonenum") String phonenum);

    User getUserByIdCode(@Param("identitycode") String identitycode);

    User getUserByEmail(@Param("email") String email);

    List<User> selectAllUser(@Param("searchStr") String searchStr);

    Boolean resetPassword(@Param("id") int id);

    void updateUser(User user);

    Boolean deleteUser(@Param("id") int id);

    void insertUser(User user);

    User getIdentifiedUser(@Param("username") String username, @Param("password") String password, @Param("identitycode") String identitycode);
}