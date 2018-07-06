package com.sea.modal;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/6.
 */
@Entity
@Setter
@Getter
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bannerId;

    private Integer type;

    private String src;

    private String target;

    private String title;

    private String link;

    private String content;

    private Date createTime;
}
