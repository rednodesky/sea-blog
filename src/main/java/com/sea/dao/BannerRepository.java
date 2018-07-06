package com.sea.dao;

import com.sea.modal.Banner;
import com.sea.modal.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/6/21.
 */
@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    public List<Banner> findByType(Integer type);
}
