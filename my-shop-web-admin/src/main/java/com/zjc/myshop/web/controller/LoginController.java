package com.zjc.myshop.web.controller;

import com.zjc.myshop.commons.constant.ConstantUtils;
import com.zjc.myshop.domain.TbUser;
import com.zjc.myshop.domain.User;
import com.zjc.myshop.service.TbUserService;
import com.zjc.myshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @Autowired
    private TbUserService tbUserService;
    @RequestMapping(value = "/login",method=RequestMethod.GET)

    public String Login(){
        System.out.println("我在这！");
        return "login";
        }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(@RequestParam(required = true) String email, @RequestParam(required = true) String password, HttpServletRequest request, Model model){
    TbUser tbUser=tbUserService.login(email,password);
    //登录失败
    if (tbUser==null){
        model.addAttribute("message", "用户名或密码错误，请重新输入");
       return Login();
    }
    //登录成功
    else{
        request.getSession().setAttribute(ConstantUtils.SESSION_USER,tbUser);
        return "main";
     }
    }
    @RequestMapping(value="/logout",method=RequestMethod.GET)
    public String loginout(HttpServletRequest request){
        request.getSession().invalidate();
        return "login";
    }
}