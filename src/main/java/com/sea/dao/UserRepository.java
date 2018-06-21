package com.sea.dao;

import com.sea.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/5/22.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginName(String loginName);
}
