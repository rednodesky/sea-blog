package com.sea.controller;

import com.sea.service.BlogService;
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

    @RequestMapping(value = "/{blogId}",method = RequestMethod.GET)
    public ModelAndView blogDetail(@PathVariable Long blogId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("blogId",blogId);
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{blogId}",method = RequestMethod.GET)
    public ModelAndView editBlog(@PathVariable Long blogId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("blogId",blogId);
        return modelAndView;
    }


    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public ModelAndView add(@PathVariable Long blogId){
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

    @RequestMapping(value = "/preview/{blogId}",method = RequestMethod.GET)
    public ModelAndView preview(@PathVariable Long blogId){
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

}
