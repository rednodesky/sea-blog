package com.sea.util;

import com.sea.annotation.SolrFieldMapping;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.StringUtils;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2018/7/27.
 */
public class SolrUtil {

    public static <T> T getBean(Class<T> beanClass, SolrDocument document) {
        try {
            //获取实例对象
            Object bean = beanClass.newInstance();

            // 反射获得所有字段
            Field[] fields = beanClass.getDeclaredFields();
            // 遍历bean中的字段
            for (Field field : fields) {
                SolrFieldMapping anno = field.getAnnotation(SolrFieldMapping.class);
                if (anno != null) {
                    String filedName = field.getName();

                    //获得注解中标记的对应的索引域
                    String indexName = anno.value();
                    Object val = document.get(indexName);
                    if(val==null||StringUtils.isEmpty(val.toString())){
                        //如果注解不指定solr的索引,默认用字段名来作为索引名
                        val = filedName;
                    }
                    // 判断字段的类型
                    BeanUtils.setProperty(bean,filedName,val);
                }
            }
            return (T) bean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}