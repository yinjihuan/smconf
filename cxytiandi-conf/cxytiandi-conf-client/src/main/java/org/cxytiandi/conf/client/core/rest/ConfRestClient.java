package org.cxytiandi.conf.client.core.rest;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.cxytiandi.conf.client.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import com.google.common.collect.Lists;

public class ConfRestClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfRestClient.class);
	
	private static ConfRestClient confRestClient = new ConfRestClient();
	
	private static RestTemplate restTemplate;

	private static final String TOKEN = "58eef205c24381110802b011";
	
    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(5000);
        requestFactory.setConnectTimeout(5000);

        // 添加转换器
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
    }
    
    public static ConfRestClient getInstance() {
    	return confRestClient;
    }
    
	public List<Conf> list(String env) {
		ResponseDatas response = doRequest("/rest/conf/list/{env}", env);
		return response.getData();
	}
	
	public List<Conf> list(String env, String systemName) {
		ResponseDatas response = doRequest("/rest/conf/list/{env}/{systemName}", env, systemName);
		return response.getData();
	}
	
	public List<Conf> list(String env, String systemName, String confFileName) {
		ResponseDatas response = doRequest("/rest/conf/list/{env}/{systemName}/{confFileName}", env, systemName, confFileName);
		return response.getData();
	}
	
	public Conf get(String env, String systemName, String confFileName, String key) {
		ResponseDatas response = doRequest("/rest/conf/list/{env}/{systemName}/{confFileName}/{key}", env, systemName, confFileName, key);
		List<Conf> list = response.getData();
		if (list != null && list.size() > 0) {
			return response.getData().get(0);
		}
		return null;
	}
	
	/**
	 * 执行获取配置数据请求<br>
	 * 首先用注册中心的服务列表进行请求，全部执行一遍，只要有一个成功即可<br>
	 * 全部执行错误后，再回调自己进行重试，休眠1秒
	 * @author yinjihuan
	 * @param url
	 * @param urlVariables
	 * @return
	 */
	private ResponseDatas doRequest(String url, Object... urlVariables) {
		List<String> restApiUrls = getRestApiServers();
		for (String base : restApiUrls) {
			try {
				HttpHeaders requestHeaders = new HttpHeaders();
			    requestHeaders.add("Authorization", TOKEN);
			    HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
			    return restTemplate.exchange(base + url, HttpMethod.GET, requestEntity, ResponseDatas.class, urlVariables).getBody();
			} catch (Exception e) {
				LOGGER.error(url + " doRequest error", e);
			}
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
		}
		return doRequest(url, urlVariables);
	}

	static List<String> apis = Lists.newArrayList();
	/**
	 * 获取配置API提供的服务地址<br>
	 * 如果返回null 则说明链接zk出了异常，默认用上次缓存的配置信息<br>
	 * 如果size==0 则说明注册中心确实没有提供方，抛出异常
	 * @author yinjihuan
	 * @return
	 */
	private List<String> getRestApiServers() {
		List<String> restApiUrls = CommonUtil.getRestServers();
		if (restApiUrls == null) {
			restApiUrls = apis;
		}
		if (restApiUrls.size() == 0) {
			throw new RuntimeException("找不到配置服务提供者，请先启动配置管理中心");
		}
		
		List<String> list = Lists.newArrayList(); 
		for (String api : restApiUrls) {
			list.add("http://" + api);
		}
		
		apis = list;
		return list;
	}
	
	public boolean save(Conf conf) {
		List<String> restApiUrls = getRestApiServers();
		for (String base : restApiUrls) {
			try {
				HttpHeaders requestHeaders = new HttpHeaders();
			    requestHeaders.add("Authorization", TOKEN);
			    HttpEntity<Conf> requestEntity = new HttpEntity<Conf>(conf, requestHeaders);
			    return restTemplate.exchange(base + "/rest/conf", HttpMethod.POST, requestEntity, ResponseDatas.class).getBody().getStatus();
			} catch (Exception e) {
				LOGGER.error(base + " save error", e);
			}
		}
		return false;
	}
}
