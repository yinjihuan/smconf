package org.cxytiandi.conf.demo;

import org.cxytiandi.conf.client.ConfApplication;
import org.cxytiandi.conf.client.init.SmconfInit;
import org.cxytiandi.conf.demo.conf.DbConf;
/**
 * 非Spring环境中使用
 * @author yinjihuan
 *
 */
public class NoSpringEnvDemo {
	public static void main(String[] args) {
		SmconfInit.init("org.cxytiandi.conf.demo.conf");
		System.out.println(ConfApplication.getBean(DbConf.class).getMaxTime());
	}
}
