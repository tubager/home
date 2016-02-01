package com.tubager.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SmsService {
	private final static String API_KEY = "3f7d9e3e020b93671cdb82954e2c7c3b";
			
	// ���˻���Ϣ��http��ַ
	private static String URI_GET_USER_INFO = "http://yunpian.com/v1/user/get.json";

	//ͨ�÷��ͽӿڵ�http��ַ
	private static String URI_SEND_SMS = "http://yunpian.com/v1/sms/send.json";
	//�����ʽ�����ͱ����ʽͳһ��UTF-8
	private static String ENCODING = "UTF-8";
	
	/**
	 * ͨ�ýӿڷ�����
	 *
	 * @param apikey apikey
	 * @param text   ����������
	 * @param mobile �����ܵ��ֻ���
	 * @return json��ʽ�ַ���
	 * @throws IOException
	 */
	public static String sendSms(String text, String mobile) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", API_KEY);
		params.put("text", text);
		params.put("mobile", mobile);
		return post(URI_SEND_SMS, params);
	}
	
	/**
	 * ����HttpClient 4.3��ͨ��POST����
	 *
	 * @param url       �ύ��URL
	 * @param paramsMap �ύ<������ֵ>Map
	 * @return �ύ��Ӧ
	 */
	public static String post(String url, Map<String, String> paramsMap) {
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost method = new HttpPost(url);
			if (paramsMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
					paramList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
			}
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}
}
