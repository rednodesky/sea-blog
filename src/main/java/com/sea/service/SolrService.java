package com.sea.service;

import com.sea.dao.BlogRepository;
import com.sea.modal.Blog;
import com.sea.modal.HashMapResult;
import com.sea.modal.SolrBlog;
import com.sea.util.SolrUtil;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2018/7/27.
 */
@Service
public class SolrService {

    @Autowired
    private SolrClient client;

//    @Autowired
//    private BlogService blogService;

    @Autowired
    private BlogRepository blogRepository;

    public String addAllArticle(){
        List<SolrBlog> blogs =blogRepository.findAllBlog();
        try {
            client.addBeans("corename",blogs);
            client.commit("corename");
        }catch (IOException e){

        }catch (SolrServerException e2){

        }
        return "success";
    }

//    public String add(){
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        try {
//            SolrInputDocument doc = new SolrInputDocument();
//            doc.setField("id", uuid);
//            doc.setField("content_ik", "我是中国人, 我爱中国");
//
//            /* 如果spring.data.solr.host 里面配置到 core了, 那么这里就不需要传 collection1 这个参数
//             * 下面都是一样的
//             */
//            client.add("corename", doc);
//            //client.commit();
//            client.commit("corename");
//            return uuid;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "error";
//    }

//    /**
//     * 根据id删除索引
//     * @param id
//     * @return
//     */
//    public String delete(String id)  {
//        try {
//            client.deleteById("corename",id);
//            client.commit("corename");
//
//            return id;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "error";
//    }

    /**
     * 删除所有的索引
     * @return
     */
    public String deleteAll(){
        try {

            client.deleteByQuery("corename","*:*");
            client.commit("corename");

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

//    /**
//     * 根据id查询索引
//     * @return
//     * @throws Exception
//     */
//    public String getById() throws Exception {
//        SolrDocument document = client.getById("corename", "1");
//        System.out.println(document);
//        return document.toString();
//    }

    /**
     * 综合查询: 在综合查询中, 有按条件查询, 条件过滤, 排序, 分页, 高亮显示, 获取部分域信息
     * @return
     */
    public HashMapResult search(String q, Integer page, Integer limit){

        try {
            SolrQuery params = new SolrQuery();

            //查询条件, 这里的 q 对应 下面图片标红的地方
            q="content:"+q+" OR title:"+q+" OR id:"+q+" OR categoryName:"+q+" OR authorName:"+q;
            params.set("q", q);

            //过滤条件
//            params.set("fq", "id:[0 TO 100000]");

            //排序
            params.addSort("id", SolrQuery.ORDER.asc);

            //分页
            params.setStart(page);
            params.setRows(limit);

            //默认域
            params.set("df", "content");

            //只查询指定域
            params.set("fl", "id,content,title");

            //高亮
            //打开开关
            params.setHighlight(false);
            //指定高亮域
//            params.addHighlightField("content");
            //设置前缀
//            params.setHighlightSimplePre("<span style='color:red'>");
            //设置后缀
//            params.setHighlightSimplePost("</span>");

            QueryResponse queryResponse = client.query("corename",params);

            SolrDocumentList results = queryResponse.getResults();


            List<Blog> data=new ArrayList<>();
            for (SolrDocument result : results) {
                Blog blog = SolrUtil.getBean(Blog.class, result);
                data.add(blog);
            }

            Long numFound = results.getNumFound(); //总数量

            int pageSize = Double.valueOf(numFound/limit).intValue();
            pageSize = (Double.valueOf(numFound/limit)-pageSize)>0?pageSize+1:pageSize;

            HashMapResult hashMapResult = HashMapResult.success();
            hashMapResult.put("page",page);
            hashMapResult.put("data",data);
            hashMapResult.put("pageSize",pageSize);
            return hashMapResult;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
