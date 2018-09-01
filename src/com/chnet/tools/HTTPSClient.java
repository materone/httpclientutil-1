package com.chnet.tools;

import org.apache.http.HttpHost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.omg.CORBA.COMM_FAILURE;  
  
public abstract class HTTPSClient extends HttpClientBuilder {  
    private CloseableHttpClient client;  
    protected ConnectionSocketFactory connectionSocketFactory;  
    private PoolingHttpClientConnectionManager connManager ;
  
    /** 
     * 初始化HTTPSClient 
     *  
     * @return 返回当前实例 
     * @throws Exception 
     */  
    public CloseableHttpClient init() throws Exception {  
        this.prepareCertificate();  
        this.regist();  
  
        return this.client;  
    }  
    
    public PoolingHttpClientConnectionManager getConnManager() {
    	return connManager;
    }
  
    /** 
     * 准备证书验证 
     *  
     * @throws Exception 
     */  
    public abstract void prepareCertificate() throws Exception;  
  
    /** 
     * 注册协议和端口, 此方法也可以被子类重写 
     */  
    protected void regist() {  
        // 设置协议http和https对应的处理socket链接工厂的对象  
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
                .register("http", PlainConnectionSocketFactory.INSTANCE)  
                .register("https", this.connectionSocketFactory)  
                .build();  
        connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
        System.out.println(connManager.getMaxTotal()+":"+connManager.getDefaultMaxPerRoute()+":"+connManager.getRoutes().size());
        connManager.setMaxTotal(200);
        connManager.setDefaultMaxPerRoute(20);
        HttpHost host = new HttpHost("api.cmhit.com",8080);
        connManager.setMaxPerRoute(new HttpRoute(host), 100);
        System.out.println(connManager.getMaxTotal()+":"+connManager.getDefaultMaxPerRoute()+":"+connManager.getMaxPerRoute(new HttpRoute(host))+":"+connManager.getRoutes().size());
//        HttpClients.custom().setConnectionManager(connManager);  
        // 创建自定义的httpclient对象  
        this.client = HttpClients.custom().setConnectionManager(connManager).build();  
        // CloseableHttpClient client = HttpClients.createDefault();  
    }  
}  