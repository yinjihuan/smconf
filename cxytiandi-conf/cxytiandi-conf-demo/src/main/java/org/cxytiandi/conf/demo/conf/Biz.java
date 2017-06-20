package org.cxytiandi.conf.demo.conf;

import org.cxytiandi.conf.client.annotation.ConfField;
import org.cxytiandi.conf.client.annotation.CxytianDiConf;

@CxytianDiConf(system="testsystem", env=true, prefix="com.cxytiandi")
public class Biz {
	@ConfField("最大值")
	private String max = "920120";

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}
	
}
