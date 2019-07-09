package com.wyj.books.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyj.books.entity.Lends;
import com.wyj.books.mapper.LendsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LendsService {
    @Autowired
    LendsMapper lendsMapper;

    public PageInfo<Lends> getAllLend(String searchVal,Integer page, Integer size){
        PageHelper.startPage(page, size);
        List<Lends> lends = lendsMapper.getAllLend(searchVal);
        PageInfo<Lends> pageInfo = new PageInfo<>(lends);
        return pageInfo;
    }

    public Lends getLendById(int id){
        Lends lends = lendsMapper.getLendById(id);
        return lends;
    }

    public PageInfo<Lends> getUserLends(String searchStr,int id,Integer page, Integer size){
        PageHelper.startPage(page, size);
        List<Lends> lends = lendsMapper.getUserLends(searchStr,id);
        PageInfo<Lends> pageInfo = new PageInfo<>(lends);
        return pageInfo;
    }

    public void updateLendById(Lends lends){
        lendsMapper.updateLendById(lends);
    }

    public Boolean deleteLend(int id){
        Boolean istrue = lendsMapper.deleteLend(id);
        return istrue;
    }

    public Boolean insertLend(Lends lends){
        Boolean istrue = lendsMapper.insertLend(lends);
        return istrue;
    }
}
