package com.sea.modal;

import lombok.Getter;
import lombok.Setter;

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
    private Long blogId;

    private String pic;

    private String categoryLevel;

    private String title;

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
