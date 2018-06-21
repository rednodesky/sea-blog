package com.sea.modal;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/22.
 */
@Entity
@Setter
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String roleName;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="USER_ID")
//    private User user;
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "role")
    private List<Permission> permissions;
}
