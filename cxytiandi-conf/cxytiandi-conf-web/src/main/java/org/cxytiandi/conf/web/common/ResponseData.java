package org.cxytiandi.conf.web.common;

public class ResponseData {
	private Boolean status = true;
	private int code = 200;
	private String message;
	private Object data;
	
	public static ResponseData ok(Object data) {
		return new ResponseData(data);
	}
	
	public static ResponseData ok() {
		return new ResponseData();
	}
	
	public static ResponseData ok(String messgae) {
		return new ResponseData(messgae);
	}
	
	public ResponseData(Object data) {
		this.data = data;
	}
	
	public ResponseData(String messgae) {
		this.message = messgae;
	}
	
	public ResponseData() {
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
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
