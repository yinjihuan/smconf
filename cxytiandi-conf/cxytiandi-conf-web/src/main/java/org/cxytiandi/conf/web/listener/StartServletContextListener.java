package org.cxytiandi.conf.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.cxytiandi.conf.client.common.EnvConstants;
import org.cxytiandi.conf.client.util.CommonUtil;
import org.cxytiandi.conf.client.zk.ZkClient;
import org.cxytiandi.conf.web.domain.User;
import org.cxytiandi.conf.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * 启动监听器
 * @author yinjihuan
 *
 */
@WebListener
public class StartServletContextListener implements ServletContextListener {
	@Value("${zookeeper.url}")
	private String zkUrl;
	
	@Value("${server.port}")
	private String port;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		//初始化默认管理员账号
		if (!userService.exists("root")) {
			userService.save(User.builder().username("root").pass("root123456").envs(EnvConstants.ENVS).build());
		}
		
		System.setProperty("zookeeper.url", zkUrl);
		ZkClient zkClient = CommonUtil.getZkClient();
		zkClient.createRootPath();
		if (port == null) {
			port = "8080";
		}
		String localIp = CommonUtil.getLocalIp() + ":" + port;
		zkClient.createServerList(localIp);
	}

}
