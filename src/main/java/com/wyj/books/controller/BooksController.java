package com.wyj.books.controller;

import com.github.pagehelper.PageInfo;
import com.wyj.books.entity.Books;
import com.wyj.books.service.BooksService;
import com.wyj.books.utils.UploadUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BooksController {
    @Autowired
    BooksService booksService;

    //按分类获取图书
    @RequestMapping(value = {"/getBooks"})
    @ResponseBody
    public Map getBooks(String classification,int page,int rows){
        if (classification.equals("所有")){
            PageInfo<Books> pageInfo = booksService.selectAllByPage(page,rows);
            Map result=new HashMap();
            result.put("rows", pageInfo.getList());
            result.put("total",pageInfo.getTotal());
            result.put("nextPage", pageInfo.getNextPage());
            result.put("prePage", pageInfo.getPrePage());
            result.put("page", page);
            result.put("pages", pageInfo.getPages());
            return result;
        }else {
            Map result=new HashMap();
            PageInfo<Books> pageInfo = booksService.getBookByClassification(page,rows,classification);
            result.put("total",pageInfo.getTotal());
            result.put("rows", pageInfo.getList());
            result.put("page", page);
            result.put("pages", pageInfo.getPages());
            result.put("nextPage", pageInfo.getNextPage());
            result.put("prePage", pageInfo.getPrePage());
            return result;
        }
    }

    //分页查询所有图书
    @RequestMapping(value = {"/BookAdmin/getBooksByPage"})
    @ResponseBody
    public Map getBooksByPage(HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        PageInfo<Books> pageInfo = booksService.selectAllByPage(page,rows);
        Map resultMap=new HashMap();
        resultMap.put("total",pageInfo.getTotal());
        resultMap.put("rows", pageInfo.getList());
        return resultMap;
    }

    //获取所有分类
    @RequestMapping(value = {"/getClassification"})
    @ResponseBody
    public List<String> getClassification(){
        List<String> classification = booksService.getClassification();
        return classification;
    }

    //添加图书
    @RequestMapping(value = {"/BookAdmin/addBook"})
    public String addBook(Books books, MultipartFile book_image){
        //当上传的图片不为空的时候才去存储路径,否则不存
        if (book_image.getSize() != 0) {
            //将上传的文件保存到磁盘中
            String path = "D:\\mine\\books\\src\\main\\webapp\\images";
            String imageName = UploadUtil.upload(book_image,path);
            //将图片路径封装到Scenic中
            books.setPicture("images/"+imageName);
        }
        List<Books> list = booksService.getByNameAndAuthor(books.getName(),books.getAuthor());
        int i = list.size();
        if (i != 0){
            Books b = list.get(0);
            int restNum = b.getRestnum();
            for (Books book : list){
                book.setTotalnum(i+1);
                book.setRestnum(restNum+1);
                booksService.updateBookById(book);
            }
            books.setTotalnum(i+1);
            books.setRestnum(restNum+1);
        }else {
            books.setTotalnum(1);
            books.setRestnum(1);
        }
        booksService.addBook(books);
        return "redirect:/admin";
    }

    //通过ID查询图书
    @RequestMapping(value = {"/selectBookById"})
    @ResponseBody
    public Books selectBookById(int id){
        Books books = booksService.selectBookById(id);
        return books;
    }

    //模糊查询
    @RequestMapping(value = {"/selectBooksLike"})
    @ResponseBody
    public List<Books> selectBooksLike(String searchStr){
        List<Books> list = booksService.selectBooksLike(searchStr);
        System.out.println(list.size());
        return list;
    }

    //更新图书
    @RequestMapping(value = {"/BookAdmin/updateBookById"})
    @RequiresPermissions("manageBook")
    public String updateBookById(Books books, MultipartFile img){
        //当上传的图片不为空的时候才去存储路径,否则不存
        if (img.getSize() != 0) {
            //将上传的文件保存到磁盘中
            String path = "D:\\mine\\books\\src\\main\\webapp\\images";
            String imageName = UploadUtil.upload(img,path);
            //将图片路径封装到Scenic中
            books.setPicture("images/"+imageName);
        }
        booksService.updateBookById(books);
        return "redirect:/admin";
    }

    //删除图书
    @RequestMapping(value = {"/BookAdmin/deleteBookById"})
    @ResponseBody
    public String deleteUser(int id){
        Books b1 = booksService.selectBookById(id);
        List<Books> list = booksService.getByNameAndAuthor(b1.getName(),b1.getAuthor());
        if (b1.getStatus() == 0){
            int i = list.size();
            if (i != 0){
                Books b = list.get(0);
                int restNum = b.getRestnum();
                for (Books book : list){
                    book.setTotalnum(i-1);
                    book.setRestnum(restNum-1);
                    booksService.updateBookById(book);
                }
            }
            boolean istrue = booksService.deleteBookById(id);
            return "1";
        }else {
            return "2";
        }
    }
}
