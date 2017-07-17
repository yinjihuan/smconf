package org.cxytiandi.conf.client.zk;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.cxytiandi.conf.client.common.Constant;
import org.cxytiandi.conf.client.core.RefreshConfCallBack;
import org.cxytiandi.conf.client.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import com.google.common.collect.Lists;

public class ZkClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ZkClient.class);
	
	//是否第一次注册，在需要启动时加载配置的时候会注册2次
	private static AtomicBoolean isFirstReg = new AtomicBoolean(true);
	
	private static class ZkClientChild {
		private static ZkClient instance = new ZkClient();
	}
	
	private ZkClient() {}
	
	private static CuratorFramework client = null;
	
	/**
	 * 获取zk client
	 * @param url   192.168.10.47:2181
	 * @return
	 */
	public synchronized static ZkClient getInstance(String url) {
		if (client == null) {
			client = CuratorFrameworkFactory.builder()
					.connectString(url)
					.sessionTimeoutMs(1000) //可以解决程序退出后临时节点没删除的问题，尽快删除
					.retryPolicy(new ExponentialBackoffRetry(1000, 3))
					.build();
			client.start();
		}
		return ZkClientChild.instance;
	}
	
	/**
	 * 获取所有服务端地址,服务端启动时会自动将地址注册到zk中
	 * @return ["192.168.10.100:8080", "192.168.10.101:8080"]
	 */
	public List<String> getAllServer() {
		try {
			return client.getChildren().forPath(Constant.ZK_SERVER_LIST_PATH);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return null;
	}
	
	/**
	 * 创建根目录
	 * @author yinjihuan
	 */
	public void createRootPath() {
		try {
			Stat stat = client.checkExists().forPath(Constant.ZK_ROOT_PATH);
			if (stat == null) {
				client.create().withMode(CreateMode.PERSISTENT).forPath(Constant.ZK_ROOT_PATH);
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
	}
	
	/**
	 * 创建API服务节点
	 * @author yinjihuan
	 * @param address
	 */
	public void createServerList(String address) {
		doCreateServerList(client, address);
		addRetryServerListener(address);
	}

	public void doCreateServerList(CuratorFramework client, String address) {
		try {
			Stat stat = client.checkExists().forPath(Constant.ZK_SERVER_LIST_PATH);
			if (stat == null) {
				client.create().withMode(CreateMode.PERSISTENT).forPath(Constant.ZK_SERVER_LIST_PATH);
			}
			String path = CommonUtil.buildPath(Constant.ZK_SERVER_LIST_PATH, address);
			
			//客户端断开，然后马上启动后节点消失会有一定时间的延迟，这边循环注册下
			//这边是启动注册服务，所以肯定是需要进行注册的
			for (int i = 1; i <= Integer.getInteger(Constant.ZK_CHECK_TEMP_TIME, 30); i++) {
				if (client.checkExists().forPath(path) == null) {
					client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
					break;
				}
				Thread.sleep(1000);
				LOGGER.info("注册服务中...");
			}
			
		} catch (Exception e) {
			LOGGER.error("", e);
		}
	}
	
	public void createServerList(CuratorFramework client, String address) {
		doCreateServerList(client, address);
	}
	
	/**
	 * 创建节点
	 * @author yinjihuan
	 * @param path
	 * @param mode
	 */
	public void createNode(String path, CreateMode mode) {
		try {
			if (mode == CreateMode.EPHEMERAL) {
				for (int i = 1; i <= Integer.getInteger(Constant.ZK_CHECK_TEMP_TIME, 30); i++) {
					Stat stat = client.checkExists().forPath(path);
					if (stat == null) {
						client.create().withMode(mode).forPath(path);
						isFirstReg.compareAndSet(true, false);
						break;
					}
					//首次注册才需要判断，否则就证明节点在之前已经注册过了，解决项目中注册2次的问题
					if (isFirstReg.get()) {
						Thread.sleep(1000);
						LOGGER.info("注册节点中..." + path);
					}
				}
			} else {
				Stat stat = client.checkExists().forPath(path);
				if (stat == null) {
					client.create().creatingParentsIfNeeded().withMode(mode).forPath(path);
				}
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
	}
	
	/**
	 * 监控配置是否被修改
	 * @author yinjihuan
	 * @param path
	 * @param callBack
	 */
	@SuppressWarnings("resource")
	public void monitor(final String path, final RefreshConfCallBack callBack) {
		try {
			NodeCache nodeChahe = new NodeCache(client, path);
			nodeChahe.getListenable().addListener(new NodeCacheListener() {
				
				public void nodeChanged() throws Exception {
					callBack.call(path);
				}
			});
			nodeChahe.start();
		} catch (Exception e) {
			LOGGER.error("", e);
		}
	}
	
	/**
	 * 设置节点的值
	 * @author yinjihuan
	 * @param path
	 * @param value
	 */
	public void setValue(String path, String value) {
		try {
			if (StringUtils.hasText(value)) {
				client.setData().forPath(path, value.getBytes());
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
	}
	
	/**
	 * 添加断开链接重新链接监听器
	 * @author yinjihuan
	 * @param value
	 */
	public void addRetryServerListener(String value) {
		ServerConnectionStateListener stateListener = new ServerConnectionStateListener(value, "REG_SERVER", this);
		client.getConnectionStateListenable().addListener(stateListener);
	}
	
	/**
	 * 获取环境下系统下的所有client节点信息
	 * @author yinjihuan
	 * @param env
	 * @param system
	 * @return
	 */
	public List<String> getClientServers(String env, String system) {
		try {
			return client.getChildren().forPath(CommonUtil.buildPath(Constant.ZK_ROOT_PATH, env, system));
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return Lists.newArrayList();
	}
}
