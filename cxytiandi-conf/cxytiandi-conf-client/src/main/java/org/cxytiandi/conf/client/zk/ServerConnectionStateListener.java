package org.cxytiandi.conf.client.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
/**
 * zk断开重连监听器，可以用来做扩展操作
 * @author yinjihuan
 */
public class ServerConnectionStateListener implements ConnectionStateListener {
	private String value;
	private String type;
	private ZkClient zkClient;

	public ServerConnectionStateListener(String value, String type, ZkClient zkClient) {
		this.value = value;
		this.type = type;
		this.zkClient = zkClient;
	}

	public void stateChanged(CuratorFramework client, ConnectionState newState) {
		if (type.equals("REG_SERVER")) {
			zkClient.doCreateServerList(client, value);
		}
	}

}
