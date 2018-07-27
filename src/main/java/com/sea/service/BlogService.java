package com.sea.service;

import com.sea.constant.CommonConstant;
import com.sea.dao.BlogRepository;
import com.sea.modal.Blog;
import com.sea.modal.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/6/22.
 */
@Service
@Transactional
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> findAll(){
        return blogRepository.findAll();
    }

    public Blog getBlogById(Long blogId){
        return blogRepository.getOne(blogId);
    }

    public void addBlog(Blog blog, CommonsMultipartFile multipartFile){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        blog.setAuthor(user.getUserId());
        blog.setViewCount(0L);
        blog.setPriseCount(0L);
        blog.setCommentCount(0L);
        blog.setCreateTime(new Date());
        blog.setDeleted(CommonConstant.RECORD_NOT_DELETED);
        blogRepository.save(blog);
    }

    public void delete(Long blogId){
        Blog blog = blogRepository.getOne(blogId);
        blog.setDeleted(CommonConstant.RECORD_DELETED);
        blogRepository.save(blog);
    }

    public void updateBlog(Blog blog){
        Blog data = blogRepository.getOne(blog.getBlogId());
        data.setUpdateTime(new Date());
        data.setTitle(blog.getTitle());
        data.setContent(blog.getContent());
        data.setStick(blog.getStick());
        data.setSort(blog.getSort());
        data.setStatus(blog.getStatus());
        data.setSecret(blog.getSecret());
        blogRepository.save(data);
    }

    public Page<Blog> findAll(Integer page){
        return blogRepository.findAll(PageRequest.of(page,CommonConstant.PAGE_SIZE));
    }

    public Page<Blog> findAllByAuthor(Long authorId,Integer page){
        return blogRepository.findAllByAuthor(authorId, PageRequest.of(page,CommonConstant.PAGE_SIZE));
    }
}
