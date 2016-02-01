package com.tubager.utility;

import org.springframework.scheduling.annotation.Scheduled;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class SmsCache {
	private static Ehcache restApiAuthTokenCache = CacheManager.getInstance().getEhcache("smsCache");
	private static final int TIME_IN_MILLISECONDS = 10 * 60 * 1000;
	private static SmsCache instance = new SmsCache();
	
	private SmsCache(){
		CacheManager.getInstance().addCache("smsCache");
		restApiAuthTokenCache = CacheManager.getInstance().getEhcache("smsCache");
	}
	
	public static SmsCache getInstance(){
		return instance;
	}
	
	 @Scheduled(fixedRate = TIME_IN_MILLISECONDS)
    public void evictExpiredTokens() {
       restApiAuthTokenCache.evictExpiredElements();
    }
	 
	public void set(String mobile, String code){
		Element element = new Element(mobile, code);
		restApiAuthTokenCache.put(element);
	}
	
	public String get(String mobile){
		Element element = restApiAuthTokenCache.get(mobile);
		 if(element != null){
			 return (String) element.getObjectValue();
		 }
		 return null;
	}
	
	public void remove(String mobile){
		restApiAuthTokenCache.remove(mobile);
	}
}
