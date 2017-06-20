package org.cxytiandi.conf.client.core.rest;

import java.util.List;

public class ResponseDatas {
	private Boolean status = true;
	private int code = 200;
	private String message;
	private List<Conf> data;
	
	public ResponseDatas(String messgae) {
		this.message = messgae;
	}
	
	public ResponseDatas() {
		super();
	}
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public List<Conf> getData() {
		return data;
	}

	public void setData(List<Conf> data) {
		this.data = data;
	}
	
}
