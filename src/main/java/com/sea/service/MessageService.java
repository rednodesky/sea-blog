package com.sea.service;

import com.sea.constant.CommonConstant;
import com.sea.dao.BannerRepository;
import com.sea.dao.MessageRepository;
import com.sea.modal.Banner;
import com.sea.modal.HashMapResult;
import com.sea.modal.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/7/6.
 */
@Transactional
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Page<Message> findAll(Integer page){
        Sort sort=new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(page, CommonConstant.PAGE_SIZE, sort);
        return messageRepository.findAll(pageable);
    }

    public HashMapResult addMessage(Message message){
        message.setCreateTime(new Date());
        messageRepository.save(message);
        return HashMapResult.success();
    }
}
