package com.zjc.myshop.admin.service.test;

import com.zjc.myshop.domain.TbUser;
import com.zjc.myshop.service.TbUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml", "classpath:spring-context-druid.xml", "classpath:spring-context-mybatis.xml"}) //上下文配置
public class TbUserServiceTest {
@Autowired
    private TbUserService tbUserService;

@Test
public void testSelectAll(){
    List<TbUser> tbUsers=tbUserService.selectAll();
    for (TbUser tbUser:tbUsers){
        System.out.println(tbUser.getUsername());
    }
}

@Test
    public void insertTest(){
    TbUser tbUser=new TbUser();
    tbUser.setUsername("Lucy");
    tbUser.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
    tbUser.setPhone("1302890998");
    tbUser.setEmail("2692369712@qq.com");
    tbUser.setCreated(new Date());
    tbUser.setUpdated(new Date());
    tbUserService.save(tbUser);
}
@Test
    public void testDelete(){
    tbUserService.delete(41L);
    }
    @Test
    public void testGetById(){
    TbUser tbUser=tbUserService.getById(7L);
    System.out.println("getById--------"+tbUser.getUsername());
    }

    @Test
    public void testUpdate(){
    TbUser tbUser=tbUserService.getById(36L);
    tbUser.setUsername("张建超");
    tbUserService.update(tbUser);
    }

    @Test
    public void testSelectByUserName(){
    List<TbUser> tbUsers=tbUserService.selectByUserName("张");
    for (TbUser tbUser:tbUsers){
        System.out.println(tbUser);
     }
    }
}
