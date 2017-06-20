package org.cxytiandi.conf.client.util;

import java.lang.reflect.Field;
import java.util.List;
import org.cxytiandi.conf.client.common.EnvConstants;
import org.cxytiandi.conf.client.core.rest.ConfRestClient;
import org.cxytiandi.conf.client.zk.ZkClient;
import org.springframework.util.StringUtils;

public class CommonUtil {
	
	/**
	 * 获取配置信息Rest Client
	 * @author yinjihuan
	 * @return
	 */
	public static ConfRestClient getConfRestClient() {
        ConfRestClient confRestClient = ConfRestClient.getInstance();
        return confRestClient;
	}
	
	/**
	 * 获取所有提供Rest API服务的地址
	 * @author yinjihuan
	 * @return
	 */
	public static List<String> getRestServers() {
		return getZkClient().getAllServer();
	}
	
	/**
	 * 获取zookeeper Client
	 * @author yinjihuan
	 * @return
	 */
	public static ZkClient getZkClient() {
		String zkUrl = System.getProperty("zookeeper.url");
		if (zkUrl != null) {
			return ZkClient.getInstance(zkUrl);
		}
		
		zkUrl = ProUtils.getProperty("zookeeper.url");
		if (zkUrl == null) {
			throw new RuntimeException("请在application.properties中配置zookeeper.url");
		}
		return ZkClient.getInstance(zkUrl);
	}
	
	/**
	 * 获取本服务的环境
	 * @author yinjihuan
	 * @return
	 */
	public static String getEnv() {
		String systemProperty = System.getProperty("spring.profiles.active");
		if (StringUtils.hasText(systemProperty)) {
			return systemProperty;
		}
		
		String env = ProUtils.getProperty("spring.profiles.active");
		if (!StringUtils.hasText(env)) {
			env = EnvConstants.DEV;
		}
		
		if (!EnvConstants.ENVS.contains(env)) {
			throw new RuntimeException("spring.profiles.active可选值只有：" + EnvConstants.ENVS.toString());
		}
		
		return env;
	}
	
	/**
	 * 获取本服务的端口
	 * @author yinjihuan
	 * @return
	 */
	public static String getServerPort() {
		String port = System.getProperty("server.port");
		if (StringUtils.hasText(port)) {
			return port;
		}
		port = ProUtils.getProperty("server.port");
		if (!StringUtils.hasText(port)) {
			throw new RuntimeException("请配置server.port用来区分一台机器上的多个服务");
		}
		return port;
	}
	
	/**
	 * 获取本机IP
	 * @author yinjihuan
	 * @return
	 */
	public static String getLocalIp() {
		String ip = System.getProperty("server.ip");
		if (StringUtils.hasText(ip)) {
			return ip;
		}
		ip = ProUtils.getProperty("server.ip");
		if (StringUtils.hasText(ip)) {
			return ip;
		}
		String localIp = NetUtils.getLocalAddress().getHostAddress();
		return localIp;
	}
	
	/**
	 * 拼接路径
	 * @author yinjihuan
	 * @param objects
	 * @return
	 */
	public static String buildPath(Object...objects) {
		StringBuilder sb = new StringBuilder();
		for (Object obj : objects) {
			sb.append(obj);
			sb.append("/");
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}
	
	/**
	 * 将值设置到Field中
	 * @author yinjihuan
	 * @param field
	 * @param bean
	 * @param value
	 * @throws Exception
	 */
	public static void setValue(Field field, Object bean, Object value) throws Exception {
		if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
			field.set(bean, Integer.parseInt(value.toString()));
		} else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
			field.set(bean, Long.parseLong(value.toString()));
		} else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
			field.set(bean, Double.parseDouble(value.toString()));
		} else {
			field.set(bean, value);
		}
	}
}
