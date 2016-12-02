package com.jxlt.stage.common.shiro;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import com.jxlt.stage.common.utils.EhcacheUtil;
 

public class RetryLimitHashedCredentialsMatcher extends
		HashedCredentialsMatcher {
	    @Override
	    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
	    	String username = (String) token.getPrincipal();
	    	//retrycount + 1
	    	        Object element = EhcacheUtil.getItem(username);
	    	        if (element == null) {
	    	            EhcacheUtil.putItem(username, 1);
	    	            element=0;
	    	        }else{
	    	            int count=Integer.parseInt(element.toString())+1;
	    	            element=count;
	    	            EhcacheUtil.putItem(username,element);
	    	        }
	    	        AtomicInteger retryCount = new AtomicInteger(Integer.parseInt(element.toString()));
//	    	        if (retryCount.incrementAndGet() > 5) {
//	    	//if retrycount >5 throw
//	    	            throw new ExcessiveAttemptsException();
//	    	        }
	    	        boolean matches = super.doCredentialsMatch(token, info);
	    	        if (matches) {
	    	//clear retrycount
	    	            EhcacheUtil.removeItem(username);
	    	        }
	    	        return matches;
	    	    } 
	    
}
