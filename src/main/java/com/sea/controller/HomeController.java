package com.sea.controller;

import com.sea.dao.UserRepository;
import com.sea.modal.Role;
import com.sea.modal.User;
import com.sea.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/22.
 */
@Controller
@RequestMapping(value = "/")
public class HomeController {



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

    //错误页面展示
    @RequestMapping(value = "/error",method = RequestMethod.POST)
    public String error(){
        return "error ok!";
    }

//    //数据初始化
//    @RequestMapping(value = "/addUser")
//    public String addUser(@RequestBody Map<String,Object> map){
//        User user = loginService.addUser(map);
//        return "addUser is ok! \n" + user;
//    }
//
//    //角色初始化
//    @RequestMapping(value = "/addRole")
//    public String addRole(@RequestBody Map<String,Object> map){
//        Role role = loginService.addRole(map);
//        return "addRole is ok! \n" + role;
//    }
//
//    //注解的使用
//    @RequiresRoles("admin")
//    @RequiresPermissions("create")
//    @RequestMapping(value = "/create")
//    public String create(){
//        return "Create success!";
//    }
}
