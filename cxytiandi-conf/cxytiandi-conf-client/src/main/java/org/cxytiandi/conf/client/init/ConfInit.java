package org.cxytiandi.conf.client.init;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.zookeeper.CreateMode;
import org.cxytiandi.conf.client.annotation.ConfField;
import org.cxytiandi.conf.client.annotation.CxytianDiConf;
import org.cxytiandi.conf.client.common.Constant;
import org.cxytiandi.conf.client.common.JsonUtils;
import org.cxytiandi.conf.client.core.RefreshConfCallBackImpl;
import org.cxytiandi.conf.client.core.rest.Conf;
import org.cxytiandi.conf.client.core.rest.ConfRestClient;
import org.cxytiandi.conf.client.util.CommonUtil;
import org.cxytiandi.conf.client.util.ReflectUtils;
import org.cxytiandi.conf.client.zk.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * 初始化配置信息
 * @author yinjihuan
 * @date 2017-02-20
 *
 */
@Component
@Configuration
public class ConfInit implements ApplicationContextAware, InitializingBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfInit.class);
	
	/**
	 * 存储所有配置信息
	 * key:类的全名
	 * value:类的实例
	 */
	private static Map<String, Object> localConfDataMap = new ConcurrentHashMap<String, Object>();
	
	/**
	 * 存储配置信息的key对应的bean
	 * key:/cxytiandi_conf/env/system/confFileName
	 * value:org.cxytiandi.conf.demo.conf.Biz
	 */
	private static Map<String, String> confNameLocalConfDataMap = new ConcurrentHashMap<String, String>();
	
	public static Object getConfData(String className) {
		if (className == null) {
			return null;
		}
		return localConfDataMap.get(className);
	}
	
	public static String getConfBeanClass(String key) {
		if (key == null) {
			return null;
		}
		return confNameLocalConfDataMap.get(key);
	}
	
	public void check(Map<String, Object> beanMap) {
		if (beanMap != null && !beanMap.isEmpty()) {
            for (Object confBean : beanMap.values()) {
            	String className = confBean.getClass().getName();
            	
            	CxytianDiConf cxytianDiConf = confBean.getClass().getAnnotation(CxytianDiConf.class);
            	String systemName = cxytianDiConf.system();
            	
            	if (!StringUtils.hasText(systemName)) {
            		throw new NullPointerException(className+"中的CxytianDiConf注解必须配置system值");
				}
            	
            	if (systemName.contains(":") || systemName.contains("&") || systemName.contains("：")) {
            		throw new RuntimeException(className + "配置类中systemName不能包含:和&字符");
            	}
            	
            	String defaultFileName = cxytianDiConf.value();
            	if (StringUtils.hasText(defaultFileName)) {
            		if (defaultFileName.contains(":") || defaultFileName.contains("&") || defaultFileName.contains("：")) {
                		throw new RuntimeException(className + "配置类中配置名称不能包含:和&字符");
                	}
				}
            	Field[] fieds = confBean.getClass().getDeclaredFields();
            	for (Field field : fieds) {
					field.setAccessible(true);
					if (!Constant.VALUE_TYPES.contains(field.getType().getName())) {
						throw new RuntimeException(className + "配置类中的字段只支持这几种数据类型：" + Constant.VALUE_TYPES.toString());
					}
					
					Object value = ReflectUtils.callGetMethod(field.getName(), confBean);
        			if (StringUtils.isEmpty(value)) {
        				throw new NullPointerException(className + "配置类中的所有字段必须有默认值");
        			}
        			
					if (!field.isAnnotationPresent(ConfField.class)) {
						throw new NullPointerException(className + "配置类中的所有字段必须加上ConfField注解");
					}
					
					ConfField confField = field.getAnnotation(ConfField.class);
					String desc = confField.value();
					if (!StringUtils.hasText(desc)) {
						throw new NullPointerException(className + "配置类中ConfField注解必须有值");
					}
				}
              
            }
        }
	}
	
	public void init(Object conf) {
		Map<String, Object> beanMap = new HashMap<String, Object>();
		beanMap.put("conf", conf);
		init(beanMap, false, true);
	}
	
	public void init(Map<String, Object> beanMap, boolean isRegWatchNode, boolean overwrite) {
		
        if (beanMap != null && !beanMap.isEmpty()) {
            for (Object confBean : beanMap.values()) {
            	String className = confBean.getClass().getName();
            	
            	CxytianDiConf cxytianDiConf = confBean.getClass().getAnnotation(CxytianDiConf.class);
            	String fileName = getFileName(confBean, cxytianDiConf);
            	String systemName = cxytianDiConf.system();
            	String prefix = cxytianDiConf.prefix();
            	boolean env = cxytianDiConf.env();
            	
            	if (isRegWatchNode && CommonUtil.getLocalDataStatus().equals("remote")) {
            		//初始化需要监控的节点，一个配置文件一个节点
                	String localIp = CommonUtil.getLocalIp() + ":" + CommonUtil.getServerPort();
                	ZkClient zkClient = CommonUtil.getZkClient();
                	zkClient.createNode(CommonUtil.buildPath(Constant.ZK_ROOT_PATH, CommonUtil.getEnv(), systemName), CreateMode.PERSISTENT);
                	zkClient.createNode(CommonUtil.buildPath(Constant.ZK_ROOT_PATH, CommonUtil.getEnv(), systemName, localIp + "&" + fileName), CreateMode.EPHEMERAL);
                	zkClient.monitor(CommonUtil.buildPath(Constant.ZK_ROOT_PATH, CommonUtil.getEnv(), systemName,  localIp + "&" + fileName), new RefreshConfCallBackImpl());
				}
            	
            	Field[] fieds = confBean.getClass().getDeclaredFields();
            	for (Field field : fieds) {
					field.setAccessible(true);
					ConfField confField = field.getAnnotation(ConfField.class);
					String desc = confField.value();
					String key = field.getName();
					Object value = ReflectUtils.callGetMethod(key, confBean);
					Conf conf = new Conf();
					conf.setEnv(CommonUtil.getEnv());
					conf.setSystemName(systemName);
					conf.setConfFileName(fileName);
					conf.setKey(key);
					conf.setValue(value);
					conf.setDesc(desc);
					if (CommonUtil.getLocalDataStatus().equals("local")) {
						initLocalConf(conf, field, confBean, env, prefix.equals("") ? "" : prefix + ".");
					} else {
						initConf(conf, field, confBean, env, prefix.equals("") ? "" : prefix + ".", overwrite);
					}
				}
            	
                localConfDataMap.put(className, confBean);
                confNameLocalConfDataMap.put(CommonUtil.buildPath(CommonUtil.getEnv(), systemName, fileName), className);
                LOGGER.debug("init conf class " + className);
              
            }
        }
	}
	
	private void initLocalConf(Conf conf, Field field, Object obj, boolean env, String prefix) {
		try {
			if (env) {
	      		System.setProperty(prefix + conf.getKey(), conf.getValue().toString());
			}
			CommonUtil.setValue(field, obj, conf.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("", e);
		}
	}
	
	private void convertValue(Conf conf) {
		if (conf.getValue() instanceof Map) {
			conf.setValue(JsonUtils.toJson(conf.getValue()).replaceAll("\"", "'"));
		}
	}
	
	private void initConf(Conf conf, Field field, Object obj, boolean env, String prefix, boolean overwrite) {
        ConfRestClient confRestClient = CommonUtil.getConfRestClient();
	    try {
	    	Conf result = confRestClient.get(conf.getEnv(), conf.getSystemName(), conf.getConfFileName(), conf.getKey());
	        if (result == null) {
	          	if (env) {
	          		System.setProperty(prefix + conf.getKey(), conf.getValue().toString());
	  			}
	          	convertValue(conf);
	          	confRestClient.save(conf);
	            LOGGER.info("save conf to db " + conf.toString());
	  		} else {
	  			if (env) {
	  				System.setProperty(prefix + conf.getKey(), result.getValue().toString());
	  			}
	  			// 是否覆盖远程配置
	  			if (overwrite) {
					result.setDesc(conf.getDesc());
					result.setValue(conf.getValue());
					convertValue(result);
					confRestClient.update(result);
				}
	  			CommonUtil.setValue(field, obj, result.getValue());
	  		}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("", e);
		}
	}
	
	private String getFileName(Object confBean, CxytianDiConf cxytianDiConf) {
		String fileName = cxytianDiConf.value();
		if (!StringUtils.hasText(fileName)) {
			fileName = confBean.getClass().getSimpleName();
		}
		return fileName;
	}

	public void afterPropertiesSet() throws Exception {
	
	}

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		Map<String, Object> beanMap = ctx.getBeansWithAnnotation(CxytianDiConf.class);
		check(beanMap);
		// 优化，之前重新初始化到spring中需要重新去配置中心请求一次数据，先改成从本地获取，有可能在启动之前已经初始化一次了
		// 可以通过设置 System.setProperty("smconf.conf.package", "com.fangjia.fsh.api.config"); 来决定是否启动加载数据
		try {
			if (beanMap != null && !beanMap.isEmpty()) {
				for (Object confBean : beanMap.values()) {
					String className = confBean.getClass().getName();
					Object oldBean = localConfDataMap.get(className);
					if (oldBean != null) {
						Field[] fieds = confBean.getClass().getDeclaredFields();
						for (Field field : fieds) {
							field.setAccessible(true);
							String key = field.getName();
							Object value = ReflectUtils.callGetMethod(key, oldBean);
							if (value instanceof Map) {
								CommonUtil.setValue(field, confBean, JsonUtils.toJson(value).replaceAll("\"", "'"));
							} else {
								CommonUtil.setValue(field, confBean, value);
							}
							
						}
						localConfDataMap.put(className, confBean);
					} else {
						// 初始化配置属性到spring 容器中的bean,方便Autowired直接注入使用
						init(beanMap, true, CommonUtil.getConfOverwrite());
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
}
