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
    @SolrFieldMapping
    @Field("id")
    private Long blogId;

    private String pic;

    private String categoryLevel;

    @Field
    @SolrFieldMapping
    private String title;

    @Field
    @SolrFieldMapping
    private String content;

    private Long viewCount;

    private Long priseCount;

    private Integer stick;

    private Integer sort;

    private Integer status;

    private Integer secret;

    private Long commentCount;

    private Date createTime;

    private Long author;

    @Transient
    private String authorName;

    private Date updateTime;

    private Integer deleted;

}
