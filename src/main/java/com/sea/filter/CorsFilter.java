package com.sea.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2019/3/2.
 */
@WebFilter(filterName = "corsFilter", urlPatterns = "/*",
        initParams = {@WebInitParam(name = "allowOrigin", value = "1"), //此处需配置成正式前端地址  设置成*   allowCredentials这个就没用了
                @WebInitParam(name = "allowMethods", value = "GET,POST,PUT,DELETE,OPTIONS"),
                @WebInitParam(name = "allowCredentials", value = "true"),
                @WebInitParam(name = "allowHeaders", value = "Content-Type,X-Token")})
public class CorsFilter implements Filter {

    private String allowOrigin;
    private String allowMethods;
    private String allowCredentials;
    private String allowHeaders;
    private String exposeHeaders;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowOrigin = filterConfig.getInitParameter("allowOrigin");
        allowMethods = filterConfig.getInitParameter("allowMethods");
        allowCredentials = filterConfig.getInitParameter("allowCredentials");
        allowHeaders = filterConfig.getInitParameter("allowHeaders");
        exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if ("OPTIONS".equals(request.getMethod())){//这里通过判断请求的方法，判断此次是否是预检请求，如果是，立即返回一个204状态吗，标示，允许跨域；预检后，正式请求，这个方法参数就是我们设置的post了
            response.setStatus(HttpStatus.SC_NO_CONTENT); //HttpStatus.SC_NO_CONTENT = 204
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, DELETE");//当判定为预检请求后，设定允许请求的方法
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, Token"); //当判定为预检请求后，设定允许请求的头部类型
            response.addHeader("Access-Control-Max-Age", "1");
            response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
            String currentOrigin = request.getHeader("Origin");
            response.setHeader("Access-Control-Allow-Origin", currentOrigin);
            filterChain.doFilter(servletRequest, servletResponse);
        }else{

            if (!StringUtils.isEmpty(allowOrigin)) {
                if(allowOrigin.equals("*")){
                    response.setHeader("Access-Control-Allow-Origin", allowOrigin);
                }else{
                    List<String> allowOriginList = Arrays.asList(allowOrigin.split(","));
    //                if (allowOriginList != null && allowOriginList.size() > 0) {
                        String currentOrigin = request.getHeader("Origin");
    //                    if (allowOriginList.contains(currentOrigin)) {
                            response.setHeader("Access-Control-Allow-Origin", currentOrigin);
    //                    }
    //                }
                }
            }
            if (!StringUtils.isEmpty(allowMethods)) {
                response.setHeader("Access-Control-Allow-Methods", allowMethods);
            }
            if (!StringUtils.isEmpty(allowCredentials)) {
                response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
            }
            if (!StringUtils.isEmpty(allowHeaders)) {
                response.setHeader("Access-Control-Allow-Headers", allowHeaders);
            }
            if (!StringUtils.isEmpty(exposeHeaders)) {
                response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}