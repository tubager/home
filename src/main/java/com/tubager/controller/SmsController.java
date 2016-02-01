package com.tubager.controller;

import java.io.IOException;
import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tubager.service.SmsService;
import com.tubager.utility.SmsCache;

@RestController
public class SmsController {
	
	@RequestMapping(value="/sms/send", method = RequestMethod.POST)
	public String getCode(@RequestParam("mobile") String mobile) throws IOException{
		String code = SmsCache.getInstance().get(mobile);
				
		if(code == null){
			code = generateCode();
			SmsCache.getInstance().set(mobile, code);
		}
		
		String content = "\u3010\u9014\u516b\u54e5\u3011\u60a8\u7684\u9a8c\u8bc1\u7801\u662f" + code + "\uff0c\u8bf7\u5728"+10+"\u5206\u949f\u5185\u4f7f\u7528\u3002";
		
		return SmsService.sendSms(content, mobile);
	}
	
	private String generateCode(){
		
		int max=9999;
		int min=1000;
		Random random = new Random();
		int s = random.nextInt(max)%(max-min+1) + min;
		return s + "";
	}
}
