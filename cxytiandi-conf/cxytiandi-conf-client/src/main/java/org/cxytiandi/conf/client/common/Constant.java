package org.cxytiandi.conf.client.common;

import java.util.Arrays;
import java.util.List;

public final class Constant {
	
	/**
	 * 根目录
	 */
	public static final String ZK_ROOT_PATH = "/cxytiandi_conf";
	
	/**
	 * 服务端注册地址目录
	 */
	public static final String ZK_SERVER_LIST_PATH = ZK_ROOT_PATH + "/reg_servers";
	
	/**
	 * 配置支持的数据类型
	 */
	public static List<String> VALUE_TYPES = Arrays.asList(
			"java.lang.String", 
			"java.lang.Long", 
			"java.lang.Double", 
			"java.lang.Integer",
			"java.util.Map", "map",
			"int", "long", "double");
	
	/**
	 * zk创建临时节点时检查的时间,单位秒<br>
	 * 创建时需检查节点是否存在，不存在则创建<br>
	 * 由于临时节点就算客户端断开了链接，也会出现临时节点需要延迟N秒后才会消失<br>
	 * 所以需要循环检查是否没有，如果超过了一定的检测时间则认为这个节点是存在的临时节点
	 */
	public static final String ZK_CHECK_TEMP_TIME = "zookeeper.check.temp.time";
	
	/**
	 * 修改日志显示条数
	 */
	public static final String SMCONF_LOG_LIMIT = "smconf.log.limit";
	
	/**
	 * web后台项目名称
	 */
	public static final String SMCONF_PROJECT_NAME = "smconf.projectName";
	
	/**
	 * zookeeper 链接地址
	 */
	public static final String ZK_URL = "zookeeper.url";
	
	/**
	 * 运行环境
	 */
	public static final String PROFILE_ACTIVE = "spring.profiles.active";
	
	/**
	 * client项目所在的机器ip
	 */
	public static final String SERVER_IP = "server.ip";
	
	/**
	 * client项目暴露的端口
	 */
	public static final String SERVER_PORT = "server.port";
	
	/**
	 * rest api token
	 */
	public static final String REST_API_TOKEN = "smconf.rest.token";
	
	/**
	 * 获取本地配置数据状态（local只加载本地默认配置  remote加载配置中心配置）
	 */
	public static final String DATA_STATUS = "smconf.data.status";

	/**
	 * 获取是否覆盖配置中心的配置状态（true覆盖  false不覆盖）
	 */
	public static final String OVERWRITE_STATUS = "smconf.overwrite.status";
	
	/**
	 * 配置所在包路径，多个用逗号隔开
	 */
	public static final String CONF_PACKAGES = "smconf.conf.package";
	
}
