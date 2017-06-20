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

	public ServerConnectionStateListener(String value, String type) {
		this.value = value;
		this.type = type;
	}

	public void stateChanged(CuratorFramework client, ConnectionState newState) {
		//重新连接成功状态
		if (newState == ConnectionState.RECONNECTED) {
			if (type.equals("REG_SERVER")) {
				
			}
		}
	}

}
