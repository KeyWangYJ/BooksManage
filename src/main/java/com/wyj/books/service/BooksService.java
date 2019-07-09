package com.wyj.books.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyj.books.entity.Books;
import com.wyj.books.mapper.BooksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {
    @Autowired
    BooksMapper booksmapper;

    public PageInfo<Books> getBookByClassification(Integer page,Integer size,String classification){
        PageHelper.startPage(page, size);
        List<Books> Books = booksmapper.getBookByClassification(classification);
        PageInfo<Books> pageInfo = new PageInfo<>(Books);
        return pageInfo;
    }

    public List<String> getClassification(){
        List<String> classification = booksmapper.getClassification();
        return classification;
    }

    public PageInfo<Books> selectAllByPage(Integer page,Integer size){
        PageHelper.startPage(page, size);
        List<Books> BookList = booksmapper.selectAllBooks();
        PageInfo<Books> pageInfo = new PageInfo<>(BookList);
        return pageInfo;
    }

    //模糊查询
    public List<Books> selectBooksLike(String searchStr){
        List<Books> BookList = booksmapper.selectBooksLike(searchStr);
        return BookList;
    }

    public void addBook(Books books){
        booksmapper.addBook(books);
    }

    public List<Books> getByNameAndAuthor(String name,String author){
        List<Books> list = booksmapper.getByNameAndAuthor(name,author);
        return list;
    }

    public Books selectBookById(int id){
        Books books = booksmapper.selectBookById(id);
        return books;
    }

    public void updateBookById(Books books){
       booksmapper.updateBookById(books);
    }

    public Boolean deleteBookById(int id){
        Boolean istrue = booksmapper.deleteBookById(id);
        return istrue;
    }
}
