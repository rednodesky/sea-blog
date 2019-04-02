package com.sea.api;

import com.sea.constant.CommonConstant;
import com.sea.modal.Blog;
import com.sea.modal.HashMapResult;
import com.sea.modal.Message;
import com.sea.service.BlogService;
import com.sea.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by Administrator on 2018/6/22.
 */
@RestController
@RequestMapping(value = CommonConstant.apiPublicPath+"/message")
public class PublicMessageApiController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public Page<Message> findAll(@RequestParam(value = "page",defaultValue = "1")Integer page){
        return messageService. findAll(page);
    }


    @RequestMapping(value = "",method = RequestMethod.POST)
    public HashMapResult addBlog(@ModelAttribute Message message){
        messageService.addMessage(message);
        return HashMapResult.success();
    }




}
