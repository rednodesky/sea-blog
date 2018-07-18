package com.sea.dao;

import com.sea.modal.Blog;
import com.sea.modal.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/6/21.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM blog.category A  WHERE A.DELETED = '0' ", nativeQuery = true)
    List<Category> findAllCategory();
}
