package com.sea.controller;

import com.sea.modal.Blog;
import com.sea.modal.HashMapResult;
import com.sea.service.BlogService;
import com.sea.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2018/12/11.
 */
@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/{categoryId}")
    public ModelAndView categoryBlog(@PathVariable Long categoryId, @RequestParam(value = "page",defaultValue = "1")Integer page){
        ModelAndView modelAndView = new ModelAndView("categoryResult");
        Page<Blog> data = blogService.findByCategoryId(categoryId,page);
        modelAndView.addObject("data",data);
        modelAndView.addObject("categoryName",categoryService.getOne(categoryId).getName());
        modelAndView.addObject("pageCount",data.getTotalPages());
        modelAndView.addObject("categoryId",categoryId);
        return modelAndView;
    }
}
