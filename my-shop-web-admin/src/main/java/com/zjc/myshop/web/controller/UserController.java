package com.zjc.myshop.web.controller;

import com.zjc.myshop.commons.constant.dto.BaseResult;
import com.zjc.myshop.commons.constant.dto.PageInfo;
import com.zjc.myshop.domain.TbUser;
import com.zjc.myshop.service.TbUserService;
import com.zjc.myshop.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.client.ResponseActions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/user")//所有访问都是user开头 然后往后加上方法名的RequestMapping
public class UserController {
    @Autowired
    private TbUserService tbUserService;
    @ModelAttribute
    public TbUser getTbUser(Long id){
        //这个方法会在所有的RequestMapping前执行，由于需要配合form方法，所以在这获取或则传一个TbUser
        TbUser tbUser=null;
        //id不为空则从数据库获取
        if (id!=null){
            tbUser=tbUserService.getById(id);
        }
        else
        {
            tbUser=new TbUser();
        }
        return tbUser;
    }
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String list(Model model){
        List<TbUser> tbUsers=tbUserService.selectAll();
        model.addAttribute("tbUsers",tbUsers);
        return "user_list";
    }
    //增改都是这个方法的界面
    @RequestMapping(value="/form",method = RequestMethod.GET)
    public String form(Model model){
        //有了@ModelAttribute
       /* TbUser tbUser=new TbUser();
        model.addAttribute("tbUser",tbUser);*/
        return "user_form";
    }
    //修改和新增都是这个方法
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(TbUser tbUser, Model model,RedirectAttributes redirectAttributes){
     BaseResult baseResult=tbUserService.save(tbUser);
     //保存成功
     if (baseResult.getStatus()==200){
         redirectAttributes.addFlashAttribute("baseResult",baseResult);
         return "redirect:/user/list";
     }
     //保存失败
     else
     {
         //model里放的是Request的Attribute，跳用这个 。跳转有请求
         //redirectAttributes里放的是session的Attribute,重定向用这个
         model.addAttribute("baseResult",baseResult);
         return "user_form";
     }
    }

    //搜索
    @RequestMapping(value = "search",method = RequestMethod.POST)
    public String search( TbUser tbUser,Model model){
        System.out.println("tbUser------------"+tbUser);
        List<TbUser> tbUsers=tbUserService.search(tbUser);
        System.out.println("tbUsers------------"+tbUsers);
        model.addAttribute("tbUsers",tbUsers);
        return  "user_list";
    }

    @ResponseBody
    @RequestMapping(value="delete",method=RequestMethod.POST)
    public BaseResult delete(String ids){
        BaseResult baseResult=null;
        //如果不为空的话
         if (StringUtils.isNotBlank(ids)){
             //逗号分隔成数组
            String[] idArray=ids.split(",");
            tbUserService.deleteMulti(idArray);
             baseResult=BaseResult.success("删除用户成功！");
         }
       else{
             baseResult=BaseResult.fail("删除用户失败！");
         }
        return baseResult;
    }
/**
 * 分页请求
 */

    @ResponseBody
    @RequestMapping(value="page",method = RequestMethod.GET)
    public PageInfo<TbUser> page(HttpServletRequest request){
        Map<String,Object> result=new HashMap<>();
/*DataTable服务器自动返回分页数据*/
       String strDraw=request.getParameter("draw");
       String strStart=request.getParameter("start");
       String strLength=request.getParameter("length");

       int draw=strDraw==null?0:Integer.parseInt(strDraw);
       int start=strStart==null?0:Integer.parseInt(strStart);
       int length=strLength==null?0:Integer.parseInt(strLength);
       /*
       *封装 Datable重要的数据
       **/
        PageInfo<TbUser> pageInfo=tbUserService.page(start,length,draw);


      /*
      遍历tbUsers就变成了result.put("data",tbUsers)
      for (TbUser tbUser:tbUsers){
           System.out.println(tbUser.getUsername());
       }*//*
       int count=tbUserService.count();
       result.put("draw",draw);
       //总比数
       result.put("recordsTotal",count);
       //过滤后的笔数
       result.put("recordsFiltered",count);
       result.put("data",tbUsers);
       result.put("error","");*/
        return pageInfo;

    }
}
