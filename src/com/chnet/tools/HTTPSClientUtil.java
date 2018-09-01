package com.chnet.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HTTPSClientUtil {
	private static final String DEFAULT_CHARSET = "UTF-8";

	public static String doPost(CloseableHttpClient httpClient, String url, Map<String, String> paramHeader,
			Map<String, String> paramBody,HttpClientContext context) throws Exception {
		return doPost(httpClient, url, paramHeader, paramBody, DEFAULT_CHARSET,context);
	}

	public static String doPost(CloseableHttpClient httpClient, String url, Map<String, String> paramHeader,
			Map<String, String> paramBody, String charset,HttpClientContext context) throws Exception {

		String result = null;
		HttpPost httpPost = new HttpPost(url);
		setHeader(httpPost, paramHeader);
		setBody(httpPost, paramBody, charset);

		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost,context);
			if (response != null) {
				InputStream in = response.getEntity().getContent();
				result = istostr(in);
				in.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
		// response = httpClient.execute(httpPost);
		// if (response != null) {
		// HttpEntity resEntity = response.getEntity();
		// if (resEntity != null) {
		// result = EntityUtils.toString(resEntity, charset);
		// }
		// }

		return result;
	}

	private static String istostr(InputStream is) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			String s = br.readLine();
			while (s != null) {
				sb.append(s).append("\n");
				s = br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String doGet(CloseableHttpClient httpClient, String url, Map<String, String> paramHeader,
			Map<String, String> paramBody) throws Exception {
		return doGet(httpClient, url, paramHeader, paramBody, DEFAULT_CHARSET);
	}

	public static String doGet(CloseableHttpClient httpClient, String url, Map<String, String> paramHeader,
			Map<String, String> paramBody, String charset) throws Exception {

		String result = null;
		HttpGet httpGet = new HttpGet(url);
		setHeader(httpGet, paramHeader);

		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			if (response != null) {
				InputStream in = response.getEntity().getContent();
				result = istostr(in);
				in.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
//		HttpResponse response = httpClient.execute(httpGet);
//		if (response != null) {
//			HttpEntity resEntity = response.getEntity();
//			if (resEntity != null) {
//				result = EntityUtils.toString(resEntity, charset);
//			}
//		}

		return result;
	}

	private static void setHeader(HttpRequestBase request, Map<String, String> paramHeader) {
		// 设置Header
		if (paramHeader != null) {
			Set<String> keySet = paramHeader.keySet();
			for (String key : keySet) {
				request.addHeader(key, paramHeader.get(key));
			}
		}
	}

	private static void setBody(HttpPost httpPost, Map<String, String> paramBody, String charset) throws Exception {
		// 设置参数
		if (paramBody != null) {
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Set<String> keySet = paramBody.keySet();
			for (String key : keySet) {
				list.add(new BasicNameValuePair(key, paramBody.get(key)));
			}

			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
		}
	}
}
