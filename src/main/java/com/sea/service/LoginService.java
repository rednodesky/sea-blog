package com.sea.service;

import com.sea.dao.RoleRepository;
import com.sea.dao.UserRepository;
import com.sea.modal.Permission;
import com.sea.modal.Role;
import com.sea.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/22.
 */

@Service
@Transactional
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    //添加用户
    public User addUser(Map<String, Object> map) {
        User user = new User();
        user.setLoginName(map.get("loginName").toString());
        user.setPassword(map.get("password").toString());
        userRepository.save(user);
        return user;
    }

    //添加角色
//    public Role addRole(Map<String, Object> map) {
//        User user = userRepository.getOne(Long.valueOf(map.get("userId").toString()));
//        Role role = new Role();
//        role.setRoleName(map.get("roleName").toString());
//        role.setUser(user);
//        Permission permission1 = new Permission();
//        permission1.setPermission("create");
//        permission1.setRole(role);
//        Permission permission2 = new Permission();
//        permission2.setPermission("update");
//        permission2.setRole(role);
//        List<Permission> permissions = new ArrayList<Permission>();
//        permissions.add(permission1);
//        permissions.add(permission2);
//        role.setPermissions(permissions);
//        roleRepository.save(role);
//        return role;
//    }

    //查询用户通过用户名
    public User findByLoginName(String loginName) {
        return userRepository.findByLoginName(loginName);
    }
}
