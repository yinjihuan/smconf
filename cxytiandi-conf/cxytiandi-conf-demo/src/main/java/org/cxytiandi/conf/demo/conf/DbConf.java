package org.cxytiandi.conf.demo.conf;

import org.cxytiandi.conf.client.annotation.ConfField;
import org.cxytiandi.conf.client.annotation.CxytianDiConf;

@CxytianDiConf(system="testsystem")
public class DbConf {
	@ConfField("超时时间")
	private int timeOut;
	
	@ConfField("最大时间")
	private long maxTime = 100;
	
	public long getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(long maxTime) {
		this.maxTime = maxTime;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	
}
