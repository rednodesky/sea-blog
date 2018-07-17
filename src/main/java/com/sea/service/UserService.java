package com.sea.service;

import com.sea.dao.RoleRepository;
import com.sea.dao.UserRepository;
import com.sea.modal.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/21.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User getByUserId(Long userId){
        return userRepository.getOne(userId);
    }

    //添加用户
    public User addUser(Map<String, Object> map) {
        User user = new User();
        user.setLoginName(map.get("loginName").toString());
        user.setPassword(map.get("password").toString());
        userRepository.save(user);
        return user;
    }


    public User registerUser(User user){
        String password=new SimpleHash("MD5",user.getPassword(),user.getLoginName(),2).toHex();
        user.setPassword(password);
        user.setBlogCount(0);
        user.setCreateTime(new Date());
        user = userRepository.save(user);
        return user;
    }

    //查询用户通过用户名
    public User findByLoginName(String loginName) {
        return userRepository.findByLoginName(loginName);
    }
}
