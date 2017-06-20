package org.cxytiandi.conf.client.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProUtils {
	
    final static Logger LOGGER = LoggerFactory.getLogger(ProUtils.class);
    
	private ProUtils() {
		
	}

	private static Properties propertie = null;
	
	static {
		try {
			propertie = new Properties();
			InputStream inputStream = ProUtils.class.getResourceAsStream("/application.properties");
			BufferedReader buff = new BufferedReader(new InputStreamReader(inputStream));
			propertie.load(buff);
			inputStream.close();
		} catch (Exception e) {
			//如果环境变量中设置了则可以不用cxytiandi-conf.properties中的配置
			String zkUrl = System.getProperty("zookeeper.url");
			if (zkUrl == null) {
				LOGGER.error("加载application.properties文件出错", e);
				throw new RuntimeException("加载application.properties文件出错", e);
			}
		}
	}
	
	public static String getProperty(String key) {
		return propertie.getProperty(key);
	}
}
