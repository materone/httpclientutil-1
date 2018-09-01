package com.chnet.tools;

import java.util.HashMap;  
import java.util.Map;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;  
  
public class HttpClientsTest {  
  
    public static void main(String[] args) throws Exception {  
//    	testGet();
    	testPost();
    }

	private static void testPost() throws Exception {
		CloseableHttpClient httpClient = null;  

		HttpClientContext context = HttpClientContext.create();
  
        //httpClient = new HTTPSTrustClient().init();  
    	HTTPSTrustClient htc = new HTTPSTrustClient();
    	httpClient = htc.init();  
    	PoolingHttpClientConnectionManager pm = htc.getConnManager();
  
        String url = "https://api.cmhit.com:8080/api/getToken";
//        String url = "http://10.128.14.11:8801/lua";  
        for (int i = 0; i < 5; i++) {
        	Map<String, String> paramHeader = new HashMap<>();  
            paramHeader.put("Accept", "text/plain"); 
            paramHeader.put("Connection", "Keep-ALive");  
            Map<String, String> paramBody = new HashMap<>();  
            paramBody.put("client_id", "ankur.tandon.ap@xxx.com");  
            paramBody.put("client_secret", "P@ssword_1");  
            String result; 
            result = HTTPSClientUtil.doPost(httpClient, url, paramHeader, paramBody,context); 
            System.out.println(result);  
            System.out.println(pm.getTotalStats().getAvailable()+"||"+pm.getTotalStats().getLeased());
		}
	}  
	
	private static void testGet() throws Exception {
		CloseableHttpClient httpClient = null;  
  
        //httpClient = new HTTPSTrustClient().init();  
    	HTTPSTrustClient htc = new HTTPSTrustClient();
    	httpClient = htc.init();  
    	PoolingHttpClientConnectionManager pm = htc.getConnManager();
  
//        String url = "https://api.cmhit.com:8080/api/getToken";
        String url = "http://10.128.14.11:8801/lua";  
        for (int i = 0; i < 5; i++) {
        	Map<String, String> paramHeader = new HashMap<>();  
            paramHeader.put("Accept", "text/plain"); 
            paramHeader.put("Connection", "Keep-ALive");  
            Map<String, String> paramBody = new HashMap<>();  
            paramBody.put("client_id", "ankur.tandon.ap@xxx.com");  
            paramBody.put("client_secret", "P@ssword_1");  
            String result; 
            result = HTTPSClientUtil.doGet(httpClient, url, paramHeader, null);    
            System.out.println(result);  
            System.out.println(pm.getTotalStats().getAvailable()+"||"+pm.getTotalStats().getLeased());
		}
	}  
} 
