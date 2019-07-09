package com.wyj.books.mapper;

import com.wyj.books.entity.Lends;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "lendmapper")
public interface LendsMapper {
    List<Lends> getAllLend(@Param("searchStr") String searchStr);
    Lends getLendById(@Param("lendId") int id);
    List<Lends> getUserLends(@Param("searchStr") String searchStr,@Param("userId") int id);
    void updateLendById(Lends lends);
    Boolean deleteLend(@Param("lendId") int id);
    Boolean insertLend(Lends lends);
}
