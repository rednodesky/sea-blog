package com.sea.api;

import com.sea.constant.CommonConstant;
import com.sea.modal.Blog;
import com.sea.modal.Comment;
import com.sea.modal.HashMapResult;
import com.sea.modal.User;
import com.sea.service.BlogService;
import com.sea.service.CommentService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/22.
 */
@RestController
@RequestMapping(value = CommonConstant.apiPublicPath+"/blog")
public class PublicBlogApiController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    //需要修改为后台用户
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public HashMapResult login(HttpServletRequest request,@ModelAttribute User loginUser){
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            User user = (User) subject.getPrincipal();
            HashMapResult hashMapResult = HashMapResult.success();
            hashMapResult.put("data",user);
            hashMapResult.put("token",subject.getSession().getId());
            return hashMapResult;
        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                loginUser.getLoginName(),
                loginUser.getPassword());
        try {
            subject.login(usernamePasswordToken);
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);

            User user = (User) subject.getPrincipal();
            request.getSession().setAttribute("user",user);
            HashMapResult hashMapResult = HashMapResult.success();
            hashMapResult.put("data",user);
            hashMapResult.put("token",subject.getSession().getId());
            // 获取保存的URL
//            if (savedRequest == null || savedRequest.getRequestUrl() == null) {
//                return HashMapResult.success(usernamePasswordToken.getPrincipal().toString());
//            }
            return hashMapResult;
        }catch (IncorrectCredentialsException e){
            String msg = "登录密码错误!";
            return HashMapResult.failur(msg);
        }catch (AuthenticationException e1){
            String msg = "登录密码错误!";
            return HashMapResult.failur(msg);
        }
    }


    @RequestMapping(value = "/{categoryId}")
    public Page<Blog> categoryBlog(@PathVariable Long categoryId, @RequestParam(value = "page",defaultValue = "1")Integer page){
        Page<Blog> data = blogService.findByCategoryId(categoryId,page);
        for(Blog blog:data.getContent()){
            String withoutHtmlContent = blog.getContent().replaceAll("<(!|/)?(.|\\n)*?>", "");
            blog.setContent(withoutHtmlContent);
        }
        return data;
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public Page<Blog> findAll(@RequestParam(value = "page",defaultValue = "1")Integer page){
        Page<Blog> data = blogService.findAll(page);
        for(Blog blog:data.getContent()){
            String withoutHtmlContent = blog.getContent().replaceAll("<(!|/)?(.|\\n)*?>", "");
            blog.setContent(withoutHtmlContent);
        }
        return data;
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


    @RequestMapping(value = "/comment/{blogId}",method = RequestMethod.POST)
    public HashMapResult comment(@PathVariable Long blogId,
                                 @ModelAttribute Comment comment){
        commentService.addComment(blogId,comment);
        return HashMapResult.success();
    }


    @RequestMapping(value = "/comment/{blogId}",method = RequestMethod.GET)
    public List<Comment> getAllComment(@PathVariable Long blogId){
        return commentService.findAllByBlogId(blogId);
    }

}
