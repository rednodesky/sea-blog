package com.sea.dao;

import com.sea.modal.Permission;
import com.sea.modal.Role;
import com.sea.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/5/22.
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findByRoleId(Long roleId);
}
