package com.sea.api;

import com.sea.constant.CommonConstant;
import com.sea.modal.*;
import com.sea.service.BlogService;
import com.sea.service.CategoryService;
import com.sea.service.MessageService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

/**
 * Created by Administrator on 2018/6/22.
 */
@RestController
@RequestMapping(value = CommonConstant.apiPrivatePath+"/blog")
public class BlogApiController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/category",method = RequestMethod.GET)
    public List<Category> findAllCategory(){
        return categoryService.findAll();
    }

    @RequestMapping(value = "/message",method = RequestMethod.GET)
    public Page<Message> findAllMessage(@RequestParam(value = "page")Integer page,@RequestParam(value = "limit",defaultValue =CommonConstant.PAGE_LIMIT )Integer limit){
        return messageService.findAll(page,limit);
    }


    @RequestMapping(value = "/message/{messageId}",method = RequestMethod.DELETE)
    public HashMapResult deleteMessageById(@PathVariable Long messageId){
        return messageService.deleteMessage(messageId);
    }


    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public HashMapResult getUserDetail(){
         User user = (User) SecurityUtils.getSubject().getPrincipal();
         HashMapResult hashMapResult = HashMapResult.success();
         hashMapResult.put("data",user);
         return hashMapResult;
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public Page<Blog> findAll(@RequestParam(value = "page")Integer page,@RequestParam(value = "limit",defaultValue =CommonConstant.PAGE_LIMIT )Integer limit){
        return blogService.findAll(page,limit);
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
