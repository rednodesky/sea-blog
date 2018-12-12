package com.sea.service;

import com.sea.constant.CommonConstant;
import com.sea.dao.BlogRepository;
import com.sea.dao.CategoryRepository;
import com.sea.modal.Blog;
import com.sea.modal.Category;
import com.sea.modal.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/6/22.
 */
@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll(){
        return categoryRepository.findAllCategory();
    }

    public Category getOne(Long categoryId){
        return categoryRepository.getOne(categoryId);
    }
}
