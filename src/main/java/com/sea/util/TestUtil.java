package com.sea.util;

import com.sea.modal.User;
import com.sea.shiroRedis.AuthCachePrincipal;

public class TestUtil {

    public static void main(String[] args) {

        User user = new User();
        user.setLoginName("123");
        Object object = user;
        AuthCachePrincipal authCachePrincipal = (AuthCachePrincipal) object;
        System.out.println(authCachePrincipal.getAuthCacheKey());

    }
}
