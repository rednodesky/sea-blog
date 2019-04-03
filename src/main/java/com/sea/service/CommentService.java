package com.sea.service;

import com.sea.dao.CommentRepository;
import com.sea.modal.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2019/4/3.
 */
@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findAllByBlogId(Long blogId){
        List<Comment> data = commentRepository.findAllByBlogId(blogId);
        data.forEach(i->{
            i.setChildren(commentRepository.findAllByParentId(i.getCommentId()));
        });
        return data;
    }
}
