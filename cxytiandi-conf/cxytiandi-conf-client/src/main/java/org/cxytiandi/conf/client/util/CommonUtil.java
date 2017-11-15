package org.cxytiandi.conf.client.util;

import java.lang.reflect.Field;
import java.util.List;

import org.cxytiandi.conf.client.common.Constant;
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
		String zkUrl = System.getProperty(Constant.ZK_URL);
		if (zkUrl != null) {
			return ZkClient.getInstance(zkUrl);
		}
		
		zkUrl = ProUtils.getProperty(Constant.ZK_URL);
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
		String systemProperty = System.getProperty(Constant.PROFILE_ACTIVE);
		if (StringUtils.hasText(systemProperty)) {
			return systemProperty;
		}
		
		String env = ProUtils.getProperty(Constant.PROFILE_ACTIVE);
		if (!StringUtils.hasText(env)) {
			env = EnvConstants.DEV;
		}
		
		if (!EnvConstants.ENVS.contains(env)) {
			throw new RuntimeException(Constant.PROFILE_ACTIVE + "可选值只有：" + EnvConstants.ENVS.toString());
		}
		
		return env;
	}
	
	/**
	 * 获取本服务的端口
	 * @author yinjihuan
	 * @return
	 */
	public static String getServerPort() {
		String port = System.getProperty(Constant.SERVER_PORT);
		if (StringUtils.hasText(port)) {
			return port;
		}
		port = ProUtils.getProperty(Constant.SERVER_PORT);
		if (!StringUtils.hasText(port)) {
			throw new RuntimeException("请配置" + Constant.SERVER_PORT + "用来区分一台机器上的多个服务");
		}
		//如果属性文件中配置的值是${spring.port}
		if (port.startsWith("$")) {
			port = System.getProperty(port.substring(2, port.length() - 1));
		}
		return port;
	}
	
	/**
	 * 获取本机IP
	 * @author yinjihuan
	 * @return
	 */
	public static String getLocalIp() {
		String ip = System.getProperty(Constant.SERVER_IP);
		if (StringUtils.hasText(ip)) {
			return ip;
		}
		ip = ProUtils.getProperty(Constant.SERVER_IP);
		if (StringUtils.hasText(ip)) {
			return ip;
		}
		String localIp = NetUtils.getLocalAddress().getHostAddress();
		return localIp;
	}

	/**
	 * 获取是否覆盖配置中心的配置状态
	 * @return
	 */
	public static boolean getConfOverwrite() {
		String status = System.getProperty(Constant.OVERWRITE_STATUS);
		if (StringUtils.hasText(status)) {
			return Boolean.parseBoolean(status);
		}
		status = ProUtils.getProperty(Constant.OVERWRITE_STATUS);
		if (StringUtils.hasText(status)) {
			return Boolean.parseBoolean(status);
		}
		return false;
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
	
	/**
	 * 获取rest api token
	 * @author yinjihuan
	 * @return
	 */
	public static String getRestApiToken() {
		String token = System.getProperty(Constant.REST_API_TOKEN);
		if (StringUtils.hasText(token)) {
			return token;
		}
		
		token = ProUtils.getProperty(Constant.REST_API_TOKEN);
		if (StringUtils.hasText(token)) {
			return token;
		}
		
		return "58eef205c24381110802b011";
	}
	
	/**
	 * 获取本地配置数据状态（local只加载本地默认配置  remote加载配置中心配置）
	 * @author yinjihuan
	 * @return
	 */
	public static String getLocalDataStatus() {
		String status = System.getProperty(Constant.DATA_STATUS);
		if (StringUtils.hasText(status)) {
			return status;
		}
		status = ProUtils.getProperty(Constant.DATA_STATUS);
		if (StringUtils.hasText(status)) {
			return status;
		}
		return "remote";
	}
}
