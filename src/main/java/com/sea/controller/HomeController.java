package com.sea.controller;

import com.sea.constant.enums.BannerType;
import com.sea.modal.Blog;
import com.sea.modal.HashMapResult;
import com.sea.modal.User;
import com.sea.service.BannerService;
import com.sea.service.BlogService;
import com.sea.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * Created by Administrator on 2018/5/22.
 */
@Controller
@RequestMapping(value = "/")
public class HomeController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @RequestMapping({"/","/index"})
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("user",SecurityUtils.getSubject().getPrincipal());
        modelAndView.addObject("carousels",bannerService.findByType(BannerType.CAROUSEL.getCode()));
        modelAndView.addObject("topics",bannerService.findByType(BannerType.TOPIC.getCode()));
        Page<Blog> blogs = blogService.findAll(0);
        modelAndView.addObject("blogs",blogs);
        modelAndView.addObject("pageCount",blogs.getTotalPages());

        return modelAndView;
    }

    //退出的时候是get请求，主要是用于退出
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            return "redirect:index";
        }
        return "login";
    }

    //post登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model model){
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            return "index";
        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                request.getParameter("username").toString(),
                request.getParameter("password").toString());
//        usernamePasswordToken.setRememberMe(false);
        //进行验证，这里可以捕获异常，然后返回对应信息
        try {
            subject.login(usernamePasswordToken);
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);

            User user = (User) subject.getPrincipal();
            request.getSession().setAttribute("user",user);
            // 获取保存的URL
            if (savedRequest == null || savedRequest.getRequestUrl() == null) {
                return "redirect:/index";
            }
            return "redirect:" + savedRequest.getRequestUrl();
        }catch (IncorrectCredentialsException e){
            String msg = "登录密码错误!";
            model.addAttribute("message", msg);
        }catch (AuthenticationException e1){
            String msg = "登录密码错误!";
            model.addAttribute("message", msg);
        }
        return "login";
    }


    //登出
    @RequestMapping(value = "/logout")
    public String logout(){
        return "logout";
    }


    @RequestMapping(value = "/register")
    public String register(){
        return "register";
    }

    //注册
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@ModelAttribute User user,HttpServletRequest request){
        userService.registerUser(user);
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(user.getLoginName());
        token.setPassword(user.getPassword().toCharArray());
        SecurityUtils.getSubject().login(token);
        request.getSession().setAttribute("user",user);
        return "redirect:/index";
    }



    @RequestMapping(value = "/setting",method = RequestMethod.GET)
    public String setting(){
        return "setting";
    }



    //个人中心设置保存
    @RequestMapping(value = "/setting",method = RequestMethod.POST)
    public String setting(User user, CommonsMultipartFile multipartFile){
        return "setting";
    }


    @RequestMapping(value = "/blog/{blogId}",method = RequestMethod.GET)
    public ModelAndView blogDetail(@PathVariable Long blogId){
        ModelAndView modelAndView = new ModelAndView("blogDetail");
        Blog blog = blogService.getBlogById(blogId);
        modelAndView.addObject("blog",blog);
        modelAndView.addObject("author",userService.getByUserId(blog.getAuthor()));
        modelAndView.addObject("comments", Collections.EMPTY_LIST);
        return modelAndView;
    }

}
