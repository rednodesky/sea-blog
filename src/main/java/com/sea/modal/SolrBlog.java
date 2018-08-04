package com.sea.modal;

import com.sea.annotation.SolrFieldMapping;
import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/6/21.
 */
@Setter
@Getter
public class SolrBlog implements Serializable {

    private static final long serialVersionUID = 7351098417823632168L;

    @SolrFieldMapping("id")
    @Field("id")
    private Long blogId;


    @Field
    @SolrFieldMapping("title")
    private String title;

    @Field
    @SolrFieldMapping("content")
    private String content;

    @Field
    @SolrFieldMapping("createTime")
    private Date createTime;

    @Field
    @SolrFieldMapping("authorName")
    private String authorName;

    @Field
    @SolrFieldMapping("categoryName")
    private String categoryName;

    public SolrBlog(){

    }

    public SolrBlog(Long blogId, String title, String content, Date createTime,String authorName,String categoryName) {
        this.blogId = blogId;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.authorName = authorName;
        this.categoryName = categoryName;
    }

}
