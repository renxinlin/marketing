package com.jgw.supercodeplatform.marketing.config.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * es配置类
 * @author liujianqiang
 * @date 2017年12月27日
 */
@Configuration
public class ElasticsearchConfig {
	@Value("${elastic.clusterName}")
	private String clusterName;
	@Value("${elastic.ip1}")
	private String ip1;
	@Value("${elastic.ip2}")
	private String ip2;
	@Value("${elastic.ip3}")
	private String ip3;
	@Value("${elastic.ip4}")
	private String ip4;
	@Value("${elastic.port}")
	private Integer port;
	
	/**
	 * 重写TransportClient Bean
	 * @author liujianqiang
	 * @data 2017年12月27日
	 * @return
	 * @throws UnknownHostException
	 */
	@Bean
	public TransportClient elClient() throws UnknownHostException {
		Settings settings = Settings.builder()
		        .put("cluster.name", clusterName).put("client.transport.sniff", true).build();
		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new TransportAddress(InetAddress.getByName(ip1), port))
				.addTransportAddress(new TransportAddress(InetAddress.getByName(ip2), port))
				.addTransportAddress(new TransportAddress(InetAddress.getByName(ip3), port))
				.addTransportAddress(new TransportAddress(InetAddress.getByName(ip4), port));
		return client;
	}
	
}
