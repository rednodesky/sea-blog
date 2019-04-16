package com.sea.dao;

import com.sea.modal.Category;
import com.sea.modal.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2019/4/3.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM blog.comment A  WHERE A.BLOG_ID = ?1 AND A.PARENT_ID IS NULL ORDER BY COMMENT_ID DESC ", nativeQuery = true)
    List<Comment> findAllByBlogId(Long blogId);

    @Query(value = "SELECT new com.sea.modal.Comment(A.commentId,A.content, A.blogId, A.replyId, A.parentId,A.email, A.userName,A.createTime,B.userName)  FROM Comment A LEFT JOIN Comment B ON A.replyId = B.commentId  WHERE  A.parentId = ?1 ORDER BY A.commentId ASC ")
    List<Comment> findAllByParentId(Long parentId);


}
