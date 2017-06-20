package org.cxytiandi.conf.client;

import org.cxytiandi.conf.client.init.ConfInit;

public class ConfApplication {
	
	/**
	 * 类（Biz.class）
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		return (T)ConfInit.getConfData(clazz.getName());
	}
	
	/**
	 * 类的全称（org.cxytiandi.conf.demo.conf.Biz）
	 * @param className
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String className) {
		return (T)ConfInit.getConfData(className);
	}
	
	/**
	 * 获取配置Bean
	 * @author yinjihuan
	 * @param env     环境
	 * @param system  系统
	 * @param confFileName  配置名称
	 * @return
	 */
	public static <T> T getBean(String env, String system, String confFileName) {
		StringBuilder key = new StringBuilder(env);
		key.append("/");
		key.append(system);
		key.append("/");
		key.append(confFileName);
		String className = ConfInit.getConfBeanClass(key.toString());
		return getBean(className);
	}
}
