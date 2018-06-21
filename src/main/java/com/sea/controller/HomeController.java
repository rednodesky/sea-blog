package com.sea.controller;

import com.sea.modal.User;
import com.sea.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/5/22.
 */
@Controller
@RequestMapping(value = "/")
public class HomeController {


    @Autowired
    private UserService userService;

    @RequestMapping({"/","/index"})
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("user",SecurityUtils.getSubject().getPrincipal());
        return modelAndView;
    }

//    @RequestMapping(value = "/all",method = RequestMethod.GET)
//    public List<User> findAll(){
//        return userRepository.findAll();
//    }


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
//            request.getSession().setAttribute("user",);
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

    //错误页面展示
    @RequestMapping(value = "/error",method = RequestMethod.POST)
    public String error(){
        return "error ok!";
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


}
