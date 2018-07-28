package com.sea.dao;

import com.sea.modal.Blog;
import com.sea.modal.SolrBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/6/21.
 */
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>  {

    @Query(value = "SELECT A.*,B.NICK_NAME AS authorName FROM blog.blog A INNER JOIN blog.user B ON A.AUTHOR = B.USER_ID WHERE A.DELETED = '0' AND B.DELETED = '0' ",
            countQuery = "SELECT count(1) AS authorName FROM blog.blog A INNER JOIN blog.user B ON A.AUTHOR = B.USER_ID WHERE A.DELETED = '0' AND B.DELETED = '0'", nativeQuery = true)
    Page<Blog> findAll(Pageable pageable);



    @Query(value = "SELECT A.*,B.NICK_NAME AS authorName FROM blog.blog A INNER JOIN blog.user B ON A.AUTHOR = B.USER_ID WHERE A.DELETED = '0' AND B.DELETED = '0' AND A.AUTHOR = ?1 ",
            countQuery = "SELECT count(1) AS authorName FROM blog.blog A INNER JOIN blog.user B ON A.AUTHOR = B.USER_ID WHERE A.DELETED = '0' AND B.DELETED = '0' AND A.AUTHOR = ?1", nativeQuery = true)
    Page<Blog> findAllByAuthor(Long authorId,Pageable pageable);


    @Query(value = "SELECT new com.sea.modal.SolrBlog(A.blogId,A.title,A.content,A.createTime,B.nickName,C.name) FROM Blog A ,User B,Category C  WHERE  C.categoryId = A.categoryLevel AND A.author = B.userId AND  A.deleted = '0' AND B.deleted = '0' ")
    List<SolrBlog> findAllBlog();


}
