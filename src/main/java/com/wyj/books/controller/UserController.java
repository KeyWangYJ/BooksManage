package com.wyj.books.controller;


import com.github.pagehelper.PageInfo;
import com.wyj.books.entity.Admin;
import com.wyj.books.entity.User;
import com.wyj.books.service.AdminService;
import com.wyj.books.service.UserService;
import com.wyj.books.utils.GetMessageCode;
import com.wyj.books.utils.MD5Utils;
import com.wyj.books.utils.UserToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    //注册
    @RequestMapping(value = {"/registerUser"})
    @ResponseBody
    public String registerUser(User user,String code,HttpSession session){
        String Scode = (String) session.getAttribute("code");
         if (code.equals(Scode)){
             String password = MD5Utils.md5(user.getPassword());
             user.setPassword(password);
             userService.insertUser(user);
             return "1";
         }else {
             return "0";
         }
    }

    //密码登录
    @RequestMapping(value = {"/userLogin"})
    public String login(User user,HttpSession session){
        UserToken token = new UserToken(user.getUsername(), user.getPassword(),"User");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);//登陆成功的话，放到session中
            User user2 = (User) subject.getPrincipal();
            session.setAttribute("user", user2);
            return "index";
        }catch(Exception e){
            System.out.println(e);
            return "login";//返回登录页面
        }
    }

    //手机短信登录
    @RequestMapping(value = {"/phoneLogin"})
    @ResponseBody
    public String phoneLogin(String phonenum,String verifyCode,HttpSession session){
        String code = (String) session.getAttribute("code");
        if (code.equals(verifyCode)){
            User user = userService.getUserByPhone(phonenum);
            UserToken token = new UserToken(user.getUsername(), user.getPassword(),"Phone");
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);//登陆成功的话，放到session中
                User user2 = (User) subject.getPrincipal();
                session.setAttribute("user", user2);
                return "1";
            }catch(Exception e){
                System.out.println(e);
                return "2";
            }
        }else {
            return "0";
        }
    }

    //验证手机号是否已存在
    @RequestMapping(value = "/checkPhone")
    @ResponseBody
    public Boolean checkPhone(String phonenum){
        User user = userService.getUserByPhone(phonenum);
        Admin admin = adminService.getAdminByPhone(phonenum);
        System.out.println(user+","+admin);
        if (user != null || admin != null){
            System.out.println("true");
            return true;
        }else {
            System.out.println("false");
            return false;
        }
    }

    //验证身份证号是否已存在
    @RequestMapping(value = "/checkIdCode")
    @ResponseBody
    public Boolean checkIdCode(String identitycode){
        User user = userService.getUserByIdCode(identitycode);
        if (user != null){
            return true;
        }else {
            return false;
        }
    }

    //验证邮箱
    @RequestMapping(value = "/checkEmail")
    @ResponseBody
    public Boolean checkEmail(String email){
        User user = userService.getUserByEmail(email);
        if (user != null){
            return true;
        }else {
            return false;
        }
    }

    //获取短信验证码
    @RequestMapping(value = "/sendSMS")
    @ResponseBody
    public void SendSms(String phonenum,HttpSession session) throws JSONException {
        //根据获取到的手机号发送验证码
        String code= GetMessageCode.getCode(phonenum);
        session.setAttribute("code",code);
        System.out.println(code);
    }

    //获取用户个人信息
    @RequestMapping(value = {"/getPersonal"})
    @ResponseBody
    public User getPersonal(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return user;
    }

    //修改手机号
    @RequestMapping(value = {"/submitPhoneNum"})
    @ResponseBody
    public Boolean editPhoneNum(String phonenum,String code,HttpSession session){
        String c = (String) session.getAttribute("code");
        if (code.equals(c)){
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            user.setPhonenum(phonenum);
            userService.updateUser(user);
            return true;
        }else {
            return false;
        }
    }

    //修改用户名
    @RequestMapping(value = {"/submitUserName"})
    @ResponseBody
    public void editUserName(String username){
        Subject subject = SecurityUtils.getSubject();
        User u = (User) subject.getPrincipal();
        u.setUsername(username);
        userService.updateUser(u);
    }

    //修改邮箱
    @RequestMapping(value = {"/submitEmail"})
    @ResponseBody
    public void editEmail(String email){
        Subject subject = SecurityUtils.getSubject();
        User u = (User) subject.getPrincipal();
        u.setEmail(email);
        userService.updateUser(u);
    }

    //修改个人描述
    @RequestMapping(value = {"/submitDescription"})
    @ResponseBody
    public void editDescription(String description){
        Subject subject = SecurityUtils.getSubject();
        User u = (User) subject.getPrincipal();
        u.setDescription(description);
        userService.updateUser(u);
    }

    //个人信息页面修改密码
    @RequestMapping(value = {"/editUserPassword"})
    @ResponseBody
    public String editUserPassword(String newPassword,String phone,String code,HttpSession session){
        User user = userService.getUserByPhone(phone);
        String verifyCode = (String) session.getAttribute("code");
        if (code.equals(verifyCode)){
            newPassword = MD5Utils.md5(newPassword);
            user.setPassword(newPassword);
            userService.updateUser(user);
            return "1";
        }else {
            return "0";
        }
    }

    @RequestMapping(value = {"/login"})
    public String tologin(){
        return "login";
    }

    @RequestMapping(value = {"/register"})
    public String toregister(){
        return "register";
    }

    @RequestMapping(value = {"/admin"})
    public String toadmin(){
        return "admin";
    }

    @RequestMapping(value = {"/index"})
    public String toindex(){
        return "index";
    }

    @RequestMapping(value = {"/bookDetail"})
    public String tobookDetail(){
        return "bookDetail";
    }

    @RequestMapping(value = {"/personal"})
    public String topersonal(){
        return "personal";
    }

    @RequestMapping(value = {"/lendsList"})
    public String tolendsList(){
        return "lendsList";
    }

    //获取所有用户
    @RequestMapping(value = {"/UserAdmin/getUser"})
    @ResponseBody
    public Map getUser(String searchVal,HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        PageInfo<User> pageinfo = userService.selectAllUserByPage(searchVal,page,rows);
        Map resultMap=new HashMap();
        resultMap.put("total",pageinfo.getTotal());
        resultMap.put("rows", pageinfo.getList());
        return resultMap;
    }

    //删除用户
    @RequestMapping(value = {"/UserAdmin/deleteUser"})
    @ResponseBody
    public boolean deleteUser(int id){
        boolean istrue = userService.deleteUser(id);
        if (istrue){
            return true;
        }
        else
            return false;
    }

    //重置密码
    @RequestMapping(value = {"/UserAdmin/resetPassword"})
    @ResponseBody
    public boolean resetPassword(int id){
        boolean istrue = userService.resetPassword(id);
        if (istrue){
            return true;
        }
        else {
            return false;
        }
    }
}
