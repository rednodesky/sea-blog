package com.sea.controller;

import com.sea.service.BlogService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2018/6/22.
 */
@Controller
@RequestMapping(value = "/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;


    @RequestMapping(value = "/edit/{blogId}",method = RequestMethod.GET)
    public ModelAndView editBlog(@PathVariable Long blogId){
        ModelAndView modelAndView = new ModelAndView("blog");
        modelAndView.addObject("blogId",blogId);
        return modelAndView;
    }


    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public ModelAndView add(){
        ModelAndView modelAndView = new ModelAndView("blog");
        modelAndView.addObject("user", SecurityUtils.getSubject().getPrincipal());
        return modelAndView;
    }

    @RequestMapping(value = "/preview/{blogId}",method = RequestMethod.GET)
    public ModelAndView preview(@PathVariable Long blogId){
        ModelAndView modelAndView = new ModelAndView("blog");
        return modelAndView;
    }

}
