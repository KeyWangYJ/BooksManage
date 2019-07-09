package com.wyj.books.controller;

import com.github.pagehelper.PageInfo;
import com.wyj.books.entity.Admin;
import com.wyj.books.entity.AdminRolePer;
import com.wyj.books.entity.User;
import com.wyj.books.service.AdminService;
import com.wyj.books.service.UserService;
import com.wyj.books.utils.MD5Utils;
import com.wyj.books.utils.UserToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    //登录
    @RequestMapping(value = {"/adminLogin"})
    public String login(String username, String password, HttpSession session){
        UserToken token = new UserToken(username, password,"Admin");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);//登陆成功的话，放到session中
            Admin admin = (Admin) subject.getPrincipal();
            session.setAttribute("admin", admin);
            System.out.println("success");
            return "admin";
        }catch(Exception e){
            return "login";//返回登录页面
        }
    }

    //获取所有管理员
    @RequestMapping(value = {"/superAdmin/getAdminRolePer"})
    @ResponseBody
    public Map getAdminRolePer(String searchVal,HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        PageInfo<AdminRolePer> pageinfo = adminService.getAdminRolePer(searchVal,page,rows);
        System.out.println(pageinfo.getList());
        Map resultMap=new HashMap();
        resultMap.put("total",pageinfo.getTotal());
        resultMap.put("rows", pageinfo.getList());
        return resultMap;
    }

    //添加管理员
    @RequestMapping(value = {"/superAdmin/addAdmin"})
    @ResponseBody
    public void addAdmin(Admin admin,String rolename,String code,HttpSession session){
        String verifyCode = (String) session.getAttribute("code");
        if (code.equals(verifyCode)){
            String password = MD5Utils.md5(admin.getAdminPassword());
            admin.setAdminPassword(password);
            adminService.insertAdmin(admin);
            if (rolename.equals("booksAdmin")){
                adminService.insertAdminRole(admin.getId(),2);
            }else if (rolename.equals( "userAdmin")){
                adminService.insertAdminRole(admin.getId(),3);
            }
        }

    }

    //更新管理员角色
    @RequestMapping(value = {"/superAdmin/updateAdminRole"})
    @ResponseBody
    public void updateAdminRole(int id,String rolename){
        System.out.println(rolename);
        if (rolename.equals("booksAdmin")){
            System.out.println(2);
            adminService.updateAdminRole(id,2);
        }else if (rolename.equals( "userAdmin")){
            System.out.println(3);
            adminService.updateAdminRole(id,3);
        }
    }

    //删除管理员
    @RequestMapping(value = {"/superAdmin/deleteAdmin"})
    @ResponseBody
    public void deleteAdmin(int id){
        adminService.deleteAdmin(id);
    }

    //登录页面修改密码
    @RequestMapping(value = {"/editPassword"})
    @ResponseBody
    public String editPassword(String newPassword,String phone,String code,HttpSession session){
        User user = userService.getUserByPhone(phone);
        String verifyCode = (String) session.getAttribute("code");
        if (code.equals(verifyCode)){
            newPassword = MD5Utils.md5(newPassword);
            if (user != null){
                user.setPassword(newPassword);
                userService.updateUser(user);
                return "1";
            }else {
                Admin admin = adminService.getAdminByPhone(phone);
                admin.setAdminPassword(newPassword);
                adminService.updateAdmin(admin);
                return "1";
            }
        }else {
            return "0";
        }
    }

    //退出登录
    @RequestMapping(value = {"/logout"})
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
}
