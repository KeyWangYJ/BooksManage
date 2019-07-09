package com.wyj.books.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyj.books.entity.User;
import com.wyj.books.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserMapper usermapper;

    public User selectByUsername(String username){
        User u = usermapper.selectByUsername(username);
        return u;
    }

    public User getUserByPhone(String phonenum){
        User user = usermapper.getUserByPhone(phonenum);
        return user;
    }

    public User getUserByIdCode(String identitycode){
        User user = usermapper.getUserByIdCode(identitycode);
        return user;
    }

    public User getUserByEmail(String email){
        User user = usermapper.getUserByEmail(email);
        return user;
    }

    public PageInfo<User> selectAllUserByPage(String searchVal,Integer page, Integer size){
        PageHelper.startPage(page, size);
        List<User> UserList = usermapper.selectAllUser(searchVal);
        PageInfo<User> pageInfo = new PageInfo<>(UserList);
        return pageInfo;
    }

    public Boolean resetPassword(int id){
        Boolean istrue = usermapper.resetPassword(id);
        return istrue;
    }

    public void updateUser(User user){
        usermapper.updateUser(user);
    }

    public Boolean deleteUser(int id){
        Boolean istrue = usermapper.deleteUser(id);
        return istrue;
    }

    public void insertUser(User user){
        usermapper.insertUser(user);
    }

    public User getIdentifiedUser(String username, String password, String identitycode){
        User user = usermapper.getIdentifiedUser(username,password,identitycode);
        return user;
    }
}
