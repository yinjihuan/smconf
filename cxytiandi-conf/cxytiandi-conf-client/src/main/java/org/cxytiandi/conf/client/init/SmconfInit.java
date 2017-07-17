package org.cxytiandi.conf.client.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cxytiandi.conf.client.annotation.CxytianDiConf;
import org.cxytiandi.conf.client.util.ClasspathPackageScannerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动时需要配置来做连接，需要在spring启动前将一些配置信息加载到环境变量使用
 * @author yinjihuan
 *
 */
public class SmconfInit {
	private static Logger logger = LoggerFactory.getLogger(SmconfInit.class);
	public static void init(String basePackgae) {
		ConfInit confInit = new ConfInit();
		Map<String, Object> beanMap = new HashMap<String, Object>();
		ClasspathPackageScannerUtils scan = new ClasspathPackageScannerUtils(basePackgae);
        try {
			List<String> classList = scan.getFullyQualifiedClassNameList();
			for (String clazz : classList) {
				Class<?> clz = Class.forName(clazz);
	        	if (clz.isAnnotationPresent(CxytianDiConf.class)) {
	        		beanMap.put(clazz, clz.newInstance());
	        	}
			}
			
			confInit.check(beanMap);
			confInit.init(beanMap);
		} catch (Exception e) {
			logger.error("", e);
		}
		
	}
}
