package com.wyj.books.mapper;

import com.wyj.books.entity.Books;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "booksmapper")
public interface BooksMapper {
    List<Books> selectAllBooks();
    List<Books> getBookByClassification(@Param("classification") String classification);
    List<String> getClassification();
    Books selectBookById(@Param("id") int id);
    //图书模糊查询
    List<Books> selectBooksLike(@Param("searchStr") String searchStr);
    void updateBookById(Books books);
    void addBook(Books books);
    Boolean deleteBookById(@Param("id") int id);
    List<Books> getByNameAndAuthor(@Param("name") String name, @Param("author")String author);
}
