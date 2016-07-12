package com.tubager.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tubager.service.WechatService;

@RestController
public class WechatController {
	
	@Autowired
	private WechatService wechatService;
	
	@RequestMapping(value="/wechat", method = RequestMethod.POST)
	public void receiveMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        if(!wechatService.verifySignature(signature, timestamp, nonce)){
        	return;
        }
		
		StringBuffer sb = new StringBuffer();  
        InputStream is = request.getInputStream();  
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");  
        BufferedReader br = new BufferedReader(isr);  
        String s = "";  
        while ((s = br.readLine()) != null) {  
            sb.append(s);  
        }  
        String xml = sb.toString();
        
        String result = "";
        String echostr = request.getParameter("echostr");  
        if (echostr != null && echostr.length() > 1) {  
            result = echostr;  
        }
        
        try {  
            OutputStream os = response.getOutputStream();  
            os.write(result.getBytes("UTF-8"));  
            os.flush();  
            os.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
}
