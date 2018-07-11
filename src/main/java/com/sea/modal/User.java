package com.sea.modal;

import com.sea.shiroRedis.AuthCachePrincipal;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/22.
 */
@Entity
@Setter
@Getter
public class User extends AuthCachePrincipal   {


    @Override
    public String getAuthCacheKey() {
       return getLoginName();
    }

    @Id
    @GeneratedValue
    private Long userId;

    private String loginName;

    private String password;

    private String nickName;

    private String trueName;

    private String personSignature;

    private String icon;

    private int blogCount;

    private String emailAddress;

    private Integer deleted;

    private Date createTime;

}
