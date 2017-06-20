package org.cxytiandi.conf.springboot.demo.conf;

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
