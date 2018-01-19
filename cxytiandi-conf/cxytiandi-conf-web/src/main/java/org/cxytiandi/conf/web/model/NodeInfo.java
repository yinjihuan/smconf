package org.cxytiandi.conf.web.model;

public class NodeInfo {
	// 配置ID
	private String id;
	// 配置值
	private String value;
	// 配置描述
	private String desc;
	// 配置节点（192.11.111.11:9090）
	private String node;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}
}
