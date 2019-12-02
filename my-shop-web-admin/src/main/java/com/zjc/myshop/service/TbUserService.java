package com.zjc.myshop.service;

import com.zjc.myshop.commons.constant.dto.BaseResult;
import com.zjc.myshop.commons.constant.dto.PageInfo;
import com.zjc.myshop.domain.TbUser;

import java.util.List;

public interface TbUserService {
    public List<TbUser> selectAll();
    BaseResult save(TbUser tbUser);
    void delete (Long id);
    TbUser getById(Long id);
    void update(TbUser tbUser);
    List<TbUser> selectByUserName(String username);
    TbUser login(String email,String password);
    //模糊搜索 可能传的是一个搜索框  传一个关键字过来就行了
    List<TbUser> search(TbUser tbUser);
    void deleteMulti(String[] ids);
    //分页 start 记录数开始的位置 length每页的记录数
    PageInfo<TbUser> page(int draw, int start, int length);
    //查询总比数
    int count();
}
