package com.sea.modal;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/22.
 */
@Entity
@Setter
@Getter
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String permission;

    private Long roleId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Role role;
}
