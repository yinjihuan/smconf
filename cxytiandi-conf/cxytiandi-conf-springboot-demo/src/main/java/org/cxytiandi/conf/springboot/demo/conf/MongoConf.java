package org.cxytiandi.conf.springboot.demo.conf;

import java.util.HashMap;
import java.util.Map;

import org.cxytiandi.conf.client.annotation.ConfField;
import org.cxytiandi.conf.client.annotation.CxytianDiConf;

@CxytianDiConf(system = "spring-boot", env = true, prefix = "spring.data.mongodb")
public class MongoConf {
	@ConfField("数据库名称")
	private String database = "test";
	
	@ConfField("数据库地址")
	private String host = "localhost";
	
	@ConfField("数据库端口")
	private int port = 27017;

	@ConfField("数据库其他参数")
	private Map<String, Object> map = new HashMap<String, Object>(){{
		put("maxSize", 100);
	}};
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
