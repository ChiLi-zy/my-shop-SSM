package com.zjc.myshop.service.Impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.zjc.myshop.commons.constant.dto.BaseResult;
import com.zjc.myshop.commons.constant.dto.PageInfo;
import com.zjc.myshop.commons.constant.utils.RegexpUtils;
import com.zjc.myshop.dao.TbUserDao;
import com.zjc.myshop.domain.TbUser;
import com.zjc.myshop.service.TbUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TbUserServiceImpl implements TbUserService {
    @Autowired
    private TbUserDao tbUserDao;
    public List<TbUser> selectAll(){
        return tbUserDao.selectAll();
    }

    @Override
    public BaseResult save(TbUser tbUser) {
        BaseResult baseResult=checkTbUser(tbUser);
        //通过验证
        if (baseResult.getStatus()==BaseResult.STATUS_SUCCESS)
        {
            tbUser.setUpdated(new Date());
            //新增用户
            if(tbUser.getId()==null){
                tbUser.setCreated(new Date());
                //密码加密处理
                tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
                tbUserDao.insert(tbUser);
            }
            else{
                tbUserDao.update(tbUser);
            }
            baseResult.setMessage("新增用户成功！");
        }
        return baseResult;

    }

    public void delete(Long id)
    {
        tbUserDao.delete(id);
    }
    public TbUser getById(Long id)
    {
       return tbUserDao.getById(id);
    }
    public void update(TbUser tbUser){
        tbUserDao.update(tbUser);
    }
    public List<TbUser> selectByUserName(String username){
        return tbUserDao.selectByUserName(username);
    }

    public TbUser login(String email,String password){
      TbUser  tbUser=tbUserDao.getByEmail(email);
      if(tbUser!=null)
      {
          //明文密码加密
        String md5Password=DigestUtils.md5DigestAsHex(password.getBytes());//获得密码
          //判断加密后的密码和数据库中的存放的密码是否匹配，匹配则表示登陆成功
        if (md5Password.equals(tbUser.getPassword()))
        {
            return tbUser;
        }
      }
      return null;
    }
    //用户信息有效性验证
    public BaseResult checkTbUser(TbUser tbUser){
        BaseResult baseResult=BaseResult.success();
       /* if(tbUser.getUsername()==null||tbUser.getUsername().length()==0){

        }*/
        if (StringUtils.isBlank(tbUser.getEmail())){
            baseResult=BaseResult.fail("邮箱不能为空，请重新输入！");
        }else if (!RegexpUtils.checkEmail(tbUser.getEmail())){
            baseResult=BaseResult.fail("邮箱格式不正确，请重新输入！");
        }
        else if (StringUtils.isBlank(tbUser.getPassword())){
            baseResult=BaseResult.fail("密码不能为空，请重新输入！");
        }
        else if (StringUtils.isBlank(tbUser.getUsername())){
           baseResult=BaseResult.fail("姓名不能为空，请重新输入！");
       }
       else if (StringUtils.isBlank(tbUser.getPhone())){
            baseResult=BaseResult.fail("手机号不能为空，请重新输入！");
        }
        else if (!RegexpUtils.checkPhone(tbUser.getPhone())){
            baseResult=BaseResult.fail("手机格式不正确，请重新输入！");
        }

        return baseResult;
    }
    //搜索
    @Override
    public List<TbUser> search(TbUser tbUser) {
        return tbUserDao.search(tbUser);
    }

    @Override
    public void deleteMulti(String[] ids) {
        tbUserDao.deleteMulti(ids);
    }

    @Override
    public PageInfo<TbUser> page(int draw,int start, int length) {
        int count=tbUserDao.count();
        Map<String,Object> params=new HashMap<>();
        params.put("start",start);
        params.put("length",length);

        PageInfo<TbUser> pageInfo=new PageInfo<>();
        pageInfo.setDraw(draw);
        pageInfo.setRecordsTotal(count);
        pageInfo.setRecordsFiltered(count);
        pageInfo.setData(tbUserDao.page(params));

        return pageInfo;
    }

    @Override
    public int count() {
        return tbUserDao.count();
    }
}
