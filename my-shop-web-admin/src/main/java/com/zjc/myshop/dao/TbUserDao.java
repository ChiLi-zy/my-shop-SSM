package com.zjc.myshop.dao;

import com.zjc.myshop.domain.TbUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TbUserDao {
    //查询所有信息
   public  List<TbUser> selectAll();
   public void insert(TbUser tbUser);
   public void delete (Long id);
   public TbUser getById(Long id);
   public void update(TbUser tbUser);
   public List<TbUser> selectByUserName(String username);
   public TbUser getByEmail(String email);
   //搜索
   public List<TbUser> search(TbUser tbUser);
   void deleteMulti(String[] ids);
   //分页 param需要两个参数 一个是start 记录数开始的位置 一个是length每页的记录数
   public  List<TbUser> page(Map<String,Object> param);
   //查询总比数
   int count();
}
