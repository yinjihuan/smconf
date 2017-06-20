package org.cxytiandi.conf.web.model;

import java.util.List;

import org.cxytiandi.conf.web.domain.Conf;

public class ConfModel extends Conf {
	private int page;
	private List<String> nodes;
	
	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
