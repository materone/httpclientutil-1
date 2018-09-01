package com.chnet.tools;

import java.io.FileInputStream;

import java.security.cert.Certificate;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;


public class HTTPSTrustClient extends HTTPSClient {

	public HTTPSTrustClient() {

	}

	@Override
	public void prepareCertificate() throws Exception {
		// 跳过证书验证
		SSLContext ctx = SSLContext.getInstance("TLSv1.2");
		X509TrustManager tm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		// add client certifile
//		String clientKeyStoreFile = "/home/tony/svr/svrdata/nginx/www/conf/certs/client.jks";
		String clientKeyStoreFile = "/home/tony/svr/svrdata/nginx/www/conf/certs/client-java.p12";
		String clientKeyStorePwd = "client";
		String clientKeyPwd = "client";
		KeyStore clientKeyStore = KeyStore.getInstance("JKS");
		clientKeyStore.load(new FileInputStream(clientKeyStoreFile), clientKeyStorePwd.toCharArray());
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(clientKeyStore, clientKeyPwd.toCharArray());
		// 设置成已信任的证书
		// ctx.init(null, new TrustManager[] { tm }, null);
		ctx.init(kmf.getKeyManagers(), new TrustManager[] { tm }, null);
		KeyManager kms[] = kmf.getKeyManagers();
//		for (int i = 0; i < kms.length; i++) {
//			System.out.println(kms[i]);
//			X509KeyManager km = (X509KeyManager) kms[i];
//			X509Certificate certs[] = km.getCertificateChain("cmhit");
//			PrivateKey pk = km.getPrivateKey("cmhit");
//			if (certs != null)
//				System.out.println("Certs in: " + certs.length);
//			if (pk != null)
//				System.out.println(pk);
//			Certificate cert = clientKeyStore.getCertificate("cmhit");
//			if (cert != null)
//				System.out.println("cert");
//		}
		this.connectionSocketFactory = new SSLConnectionSocketFactory(ctx);
	}
}