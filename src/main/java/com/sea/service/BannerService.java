package com.sea.service;

import com.sea.dao.BannerRepository;
import com.sea.modal.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2018/7/6.
 */
@Transactional
@Service
public class BannerService {
    @Autowired
    private BannerRepository bannerRepository;

    public List<Banner> findByType(Integer type){
        return bannerRepository.findByType(type);
    }
}
