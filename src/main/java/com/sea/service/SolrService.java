package com.sea.service;

import com.sea.dao.BlogRepository;
import com.sea.dao.CategoryRepository;
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

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public HashMapResult addAllArticle(){
        List<SolrBlog> blogs =blogRepository.findAllBlog();
        try {
            client.addBeans("corename",blogs);
            client.commit("corename");
        }catch (IOException|SolrServerException e){
            e.printStackTrace();
            return HashMapResult.failur(e.getMessage());
        }
        return HashMapResult.success();
    }

    public HashMapResult add(Long blogId){
        try {
            Blog blog = blogRepository.getOne(blogId);
            SolrInputDocument doc = new SolrInputDocument();
            doc.setField("blogId", blog.getBlogId());
            doc.setField("title", blog.getTitle());
            doc.setField("content", blog.getContent());
            doc.setField("createTime", blog.getCreateTime());
            doc.setField("authorName", blog.getAuthorName());
            doc.setField("categoryName", categoryRepository.getOne(blog.getCategoryId()).getName());

            client.add("corename", doc);
            //client.commit();
            client.commit("corename");
            return HashMapResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return HashMapResult.failur(e.getMessage());
        }
    }

    /**
     * 根据id删除索引
     * @param id
     * @return
     */
    public HashMapResult delete(String id)  {
        try {
            client.deleteById("corename",id);
            client.commit("corename");
            return HashMapResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return HashMapResult.failur(e.getMessage());
        }
    }

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



    /**
     * 综合查询: 在综合查询中, 有按条件查询, 条件过滤, 排序, 分页, 高亮显示, 获取部分域信息
     * @return
     */
    public HashMapResult search(String q, Integer page, Integer limit){

        try {
            SolrQuery params = new SolrQuery();

            //查询条件, 这里的 q 对应 下面图片标红的地方
            q="_text_:\""+q+"\"";
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
            params.set("fl", "id,content,title,authorName,categoryName,createTime");

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


            List<SolrBlog> data=new ArrayList<>();
            for (SolrDocument result : results) {
                SolrBlog blog = SolrUtil.getBean(SolrBlog.class, result);
                String withoutHtmlContent = blog.getContent().replaceAll("<(!|/)?(.|\\n)*?>", "");
                blog.setContent(withoutHtmlContent);
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
            return HashMapResult.failur("");
        }
    }

}
