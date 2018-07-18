package com.sea.api;

import com.sea.constant.CommonConstant;
import com.sea.modal.Blog;
import com.sea.modal.HashMapResult;
import com.sea.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by Administrator on 2018/6/22.
 */
@RestController
@RequestMapping(value = CommonConstant.apiPublicPath+"/blog")
public class PublicBlogApiController {

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public Page<Blog> findAll(@RequestParam(value = "page")Integer page){
        return blogService.findAll(page);
    }


    @RequestMapping(value = "",method = RequestMethod.POST)
    public HashMapResult addBlog(@ModelAttribute Blog blog,@RequestParam(value = "file") CommonsMultipartFile file){
        blogService.addBlog(blog,file);
        return HashMapResult.success();
    }


    @RequestMapping(value = "",method = RequestMethod.PUT)
    public HashMapResult updateBlog(@ModelAttribute Blog blog){
        blogService.updateBlog(blog);
        return HashMapResult.success();
    }


    @RequestMapping(value = "/{blogId}",method = RequestMethod.DELETE)
    public HashMapResult deleteBlog(@PathVariable Long blogId){
        blogService.delete(blogId);
        return HashMapResult.success();
    }



}
