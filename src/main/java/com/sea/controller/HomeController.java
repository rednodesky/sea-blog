package com.sea.controller;

import com.sea.constant.CommonConstant;
import com.sea.constant.enums.BannerType;
import com.sea.modal.Blog;
import com.sea.modal.HashMapResult;
import com.sea.modal.Message;
import com.sea.modal.User;
import com.sea.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.apache.solr.common.util.Hash;
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
@RequestMapping(value = "")
public class HomeController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SolrService solrService;

    @RequestMapping({"/","/index"})
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("user",SecurityUtils.getSubject().getPrincipal());
        modelAndView.addObject("carousels",bannerService.findByType(BannerType.CAROUSEL.getCode())); //轮播
        modelAndView.addObject("topics",bannerService.findByType(BannerType.TOPIC.getCode())); //副标题
        Page<Blog> blogs = blogService.findAll(1);
        modelAndView.addObject("blogs",blogs);
        modelAndView.addObject("pageCount",blogs.getTotalPages());

        return modelAndView;
    }


//    @RequestMapping({"/beg"}) //在线讨饭系统  需商户号  暂时搁浅
//    public ModelAndView beg(){
//        ModelAndView modelAndView = new ModelAndView("beg");
//        return modelAndView;
//    }


    @RequestMapping({"/analysis"}) //视频解析系统
    public ModelAndView analysis(){
        ModelAndView modelAndView = new ModelAndView("analysis");
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

        blog.setCommentCount(blog.getCommentCount()+1);
        blogService.updateBlog(blog);

        return modelAndView;
    }


    @RequestMapping(value = "/category",method = RequestMethod.GET)
    public ModelAndView category(){
        ModelAndView modelAndView = new ModelAndView("category");
        modelAndView.addObject("categorys",categoryService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/about",method = RequestMethod.GET)
    public ModelAndView about(){
        ModelAndView modelAndView = new ModelAndView("about");
        return modelAndView;
    }

    @RequestMapping(value = "/comment",method = RequestMethod.GET)
    public ModelAndView comment(){
        ModelAndView modelAndView = new ModelAndView("comment");
        Page<Message> messages = messageService.findAll(1);
        modelAndView.addObject("messages",messages);
        modelAndView.addObject("pageCount",messages.getTotalPages());
        return modelAndView;
    }

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public String submitComment(@ModelAttribute Message message){
        messageService.addMessage(message);
        return "redirect:/comment";
    }


    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public ModelAndView search(@RequestParam String q,@RequestParam(value = "page",defaultValue = "0")Integer page,
                                @RequestParam(value = "limit",defaultValue = CommonConstant.PAGE_LIMIT)Integer limit){
        ModelAndView modelAndView = new ModelAndView("searchResult");
        HashMapResult hashMapResult = solrService.search(q,page,limit);
        modelAndView.addObject("data",hashMapResult.get("data"));
        modelAndView.addObject("pageSize",hashMapResult.get("pageSize"));
        return modelAndView;
//        return solrService.search(q,page,limit);
    }
}
