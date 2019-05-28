package com.sea.api;

import com.sea.constant.CommonConstant;
import com.sea.modal.Blog;
import com.sea.modal.HashMapResult;
import com.sea.service.BlogService;
import com.sea.service.SolrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/22.
 */
@RestController
@RequestMapping(value = CommonConstant.apiPublicPath+"/solr")
public class SolrController {

    @Autowired
    private SolrService solrService;


//    @RequestMapping(value = "/add",method = RequestMethod.GET)
//    public String add(){
//        return solrService.add();
//    }

    @RequestMapping(value = "/add-all",method = RequestMethod.GET)
    public HashMapResult addAll(){
        return solrService.addAllArticle();
    }

//    /**
//     * 根据id删除索引
//     * @param id
//     * @return
//     */
//    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
//    public String delete(@PathVariable  String id)  {
//        return solrService.delete(id);
//    }

    /**
     * 删除所有的索引
     * @return
     */
    @RequestMapping(value = "/delete-all",method = RequestMethod.GET)
    public String deleteAll(){
        return solrService.deleteAll();
    }

//    /**
//     * 根据id查询索引
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/get",method = RequestMethod.GET)
//    public String getById() throws Exception {
//        return solrService.getById();
//    }

    @RequestMapping(value = "/search/{q}",method = RequestMethod.GET)
    public HashMapResult search(@PathVariable String q,@RequestParam(value = "page",defaultValue = "0")Integer page,
                         @RequestParam(value = "limit",defaultValue = CommonConstant.PAGE_LIMIT)Integer limit){
        return solrService.search(q,page,limit);
    }
}
