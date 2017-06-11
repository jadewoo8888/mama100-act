package com.biostime.config;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/** 
 * @ClassName: RestTemplateConfiger 
 * @Description: 使用PoolingHttpClientConnectionManager提升性能
 * @author 			guowei_liu@kingdee.com
 * @date 		2016年11月22日 下午4:06:03 
 *  
 */ 
@Configuration
public class RestTemplateConfiger {
	
	@Bean
	public HttpClient httpClient() throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException{
		return acceptsUntrustedCertsHttpClient(); 
	}
	
	
	@Bean
	@Autowired
	public ClientHttpRequestFactory clientHttpRequestFactory(HttpClient httpClient){
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}


	@Bean(name="customerRestTemplate")
	@Autowired
	public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
		return new RestTemplate(clientHttpRequestFactory);
	}
	
	
	public static CloseableHttpClient acceptsUntrustedCertsHttpClient()
			throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		
		HttpClientBuilder b = HttpClientBuilder.create();
		//信任所有
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				return true;
			}
		}).build();
		b.setSSLContext(sslContext);
		
		// 去掉主机验证
		HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
		
		
		// 注册相关策略
	
		SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory)
				.build();
		
		// 使用多线程
		PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connMgr.setMaxTotal(200);
		connMgr.setDefaultMaxPerRoute(100);
		b.setConnectionManager(connMgr);
		
		//生成httpclient
		CloseableHttpClient client = b.build();
		return client;
	}
}
