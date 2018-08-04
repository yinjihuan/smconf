package org.cxytiandi.conf.client.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cxytiandi.conf.client.annotation.CxytianDiConf;
import org.cxytiandi.conf.client.util.ClassReadUtils;
import org.cxytiandi.conf.client.util.ClasspathPackageScannerUtils;
import org.cxytiandi.conf.client.util.CommonUtil;
import org.springframework.util.StringUtils;

/**
 * 启动时需要配置来做连接，需要在spring启动前将一些配置信息加载到环境变量使用,多个包用逗号隔开<br>
 * 可以不传参数，默认读取com包下所有的类信息
 * @author yinjihuan
 *
 */
public class SmconfInit {
	public static void init(String basePackgaes) {
		ConfInit confInit = new ConfInit();
		Map<String, Object> beanMap = new HashMap<String, Object>();
		try {
			if (!StringUtils.hasText(basePackgaes)) {
				basePackgaes = "com.cxytiandi";
			}
			String[] packs = basePackgaes.split(",");
			for (String pck : packs) {
				Set<Class<?>> classList = ClassReadUtils.getClassFromPackagePath(pck);
				for (Class<?> clz : classList) {
		        	if (clz.isAnnotationPresent(CxytianDiConf.class)) {
		        		beanMap.put(clz.getName(), clz.newInstance());
		        	}
				}
				
			}
			confInit.check(beanMap);
			confInit.init(beanMap, true, CommonUtil.getConfOverwrite());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		/*ClasspathPackageScannerUtils scan = new ClasspathPackageScannerUtils(basePackgae);
        try {
			List<String> classList = scan.getFullyQualifiedClassNameList();
			for (String clazz : classList) {
				Class<?> clz = Class.forName(clazz);
	        	if (clz.isAnnotationPresent(CxytianDiConf.class)) {
	        		beanMap.put(clazz, clz.newInstance());
	        	}
			}
			
			confInit.check(beanMap);
			confInit.init(beanMap, true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}*/
		
	}
}
