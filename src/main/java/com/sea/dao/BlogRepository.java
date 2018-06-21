package com.sea.dao;

import com.sea.modal.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/6/21.
 */
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

}
