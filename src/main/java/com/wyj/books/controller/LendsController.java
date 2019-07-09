package com.wyj.books.controller;

import com.github.pagehelper.PageInfo;
import com.wyj.books.entity.Admin;
import com.wyj.books.entity.Books;
import com.wyj.books.entity.Lends;
import com.wyj.books.entity.User;
import com.wyj.books.service.BooksService;
import com.wyj.books.service.LendsService;
import com.wyj.books.service.UserService;
import com.wyj.books.utils.MD5Utils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class LendsController {
    @Autowired
    LendsService lendsService;

    @Autowired
    UserService userService;

    @Autowired
    BooksService booksService;

    //获取所有借阅记录
    @RequestMapping(value = {"/BookAdmin/getAllLend"})
    @ResponseBody
    public Map getAllLend(HttpServletRequest request,String searchVal){
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        PageInfo<Lends> pageInfo = lendsService.getAllLend(searchVal,page,rows);
        Map resultMap=new HashMap();
        resultMap.put("total",pageInfo.getTotal());
        resultMap.put("rows", pageInfo.getList());
        return resultMap;
    }

    //删除借阅记录
    @RequestMapping(value = {"/deleteLend"})
    @ResponseBody
    public Boolean deleteLend(int id){
        Boolean istrue = lendsService.deleteLend(id);
        return istrue;
    }

    //获取用户借阅记录
    @RequestMapping(value = {"/getUserLends"})
    @ResponseBody
    public Map getUserLends(HttpServletRequest request,String searchVal){
        System.out.println(searchVal);
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        int id = user.getId();
        PageInfo<Lends> pageInfo = lendsService.getUserLends(searchVal,id,page,rows);
        System.out.println(pageInfo.getList());
        Map resultMap=new HashMap();
        resultMap.put("rows", pageInfo.getList());
        resultMap.put("total",pageInfo.getTotal());
        return resultMap;
    }

    //添加借阅记录
    @RequestMapping(value = {"/BookAdmin/generateLend"})
    @ResponseBody
    public String generateLend(int id, String username, String password, String identitycode){
        password = MD5Utils.md5(password);
        User user = userService.getIdentifiedUser(username,password,identitycode);
        Books books = booksService.selectBookById(id);
        if (books.getStatus() == 0){
            if (user != null){
                Subject subject = SecurityUtils.getSubject();
                Admin admin = (Admin) subject.getPrincipal();
                Lends lends = new Lends();
                SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                String lendTime = myFmt.format(new Date());
                calendar.setTime(new Date());
                calendar.add(Calendar.MONTH,3);
                String deadline = myFmt.format(calendar.getTime());
                String contact = user.getPhonenum() + "/" + user.getEmail();
                lends.setUserId(user.getId());
                lends.setContact(contact);
                lends.setBookId(books.getId());
                lends.setBookName(books.getName());
                lends.setOutTime(lendTime);
                lends.setDeadline(deadline);
                lends.setOperatorId(admin.getId());
                Boolean istrue = lendsService.insertLend(lends);
                if (istrue){
                    int r = books.getRestnum();
                    r = r-1;
                    books.setRestnum(r);
                    books.setStatus(1);
                    booksService.updateBookById(books);
                    return "1";
                }else {
                    return "2";
                }
            }else {
                return "3";
            }
        }else {
            return "4";
        }
    }

    //登记还书
    @RequestMapping(value = {"/BookAdmin/returnBook"})
    @ResponseBody
    public void returnBook(int id){
        Lends lends = lendsService.getLendById(id);
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String returnTime = myFmt.format(new Date());
        lends.setReturnStatus(1);
        lends.setReturnTime(returnTime);
        lendsService.updateLendById(lends);
        Books books = booksService.selectBookById(lends.getBookId());
        books.setStatus(0);
        int r = books.getRestnum();
        r = r+1;
        books.setRestnum(r);
        booksService.updateBookById(books);
    }

    //续借
    @RequestMapping(value = {"/BookAdmin/renew"})
    @ResponseBody
    public void renew(int id) throws ParseException {
        Lends lends = lendsService.getLendById(id);
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date deadline = myFmt.parse(lends.getDeadline());
        calendar.setTime(deadline);
        calendar.add(Calendar.MONTH,3);
        String newDeadline = myFmt.format(calendar.getTime());
        lends.setDeadline(newDeadline);
        lendsService.updateLendById(lends);
    }
}
