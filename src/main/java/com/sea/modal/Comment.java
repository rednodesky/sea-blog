package com.sea.modal;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/4/3.
 */
@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    private String content;

    private Long blogId;

    private Long replyId;

    private Long parentId;

    private String email;

    private String userName;

    @Transient
    private List<Comment> children;

    @Transient
    private String replyName;

    private Date createTime;

    protected Comment() {
    }


    public Comment(Long commentId,String content, Long blogId, Long replyId, Long parentId, String email, String userName,  Date createTime,String replyName) {
        this.commentId = commentId;
        this.content = content;
        this.blogId = blogId;
        this.replyId = replyId;
        this.parentId = parentId;
        this.email = email;
        this.userName = userName;
        this.replyName = replyName;
        this.createTime = createTime;
    }
}
