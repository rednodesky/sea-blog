package com.sea.shiroRedis;

import java.io.Serializable;

public abstract class  AuthCachePrincipal implements Serializable {

    private static final long serialVersionUID = 5845938747063123122L;

    /**
     * AuthCacheKey used to store authorization cache. The authorization cache key in Redis format is {cacheManagePrefix} + {realmName} + ":" + {authCacheKey}.
     * For example:
     * cacheManagePrefix = shiro:mycache:
     * realmName = exampleRealm.authorizationCache
     * authCacheKey = 123
     * authorization redis key = shiro:mycache:exampleRealm.authorizationCache:123
     *
     * In most cases, authCacheKey should be userId or userName
     * @return
     */
    public String getAuthCacheKey(){
        return "";
    }


}
