package com.sea.modal;

import com.sea.annotation.SolrFieldMapping;
import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/6/21.
 */
@Entity
@Setter
@Getter
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SolrFieldMapping("id")
    @Field("id")
    private Long blogId;

    private String pic;

    private Long categoryId;

    @Field
    @SolrFieldMapping("title")
    private String title;

    @Field
    @SolrFieldMapping("content")
    private String content;

    private Long viewCount;

    private Long priseCount;

    private Integer stick;

    private Integer sort;

    private Integer status;

    private Integer secret;

    private Long commentCount;

    @Field
    @SolrFieldMapping
    private Date createTime;

    private Long author;

    @Transient
    @Field
    @SolrFieldMapping("authorName")
    private String authorName;

    private Date updateTime;

    private Integer deleted;

}
