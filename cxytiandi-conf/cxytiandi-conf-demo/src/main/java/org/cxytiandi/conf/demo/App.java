package org.cxytiandi.conf.demo;

import org.cxytiandi.conf.client.ConfApplication;
import org.cxytiandi.conf.demo.conf.Biz;
import org.cxytiandi.conf.demo.conf.DbConf;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 配置测试类
 * @author yinjihuan
 */
public class App {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		//可以通过环境变量设置zk和env的值
		//System.setProperty("zookeeper.url", "192.168.10.47:2181");
		//System.setProperty("spring.profiles.active", "test");
		
		new ClassPathXmlApplicationContext("spring.xml").start();
		System.out.println(ConfApplication.getBean(DbConf.class).getMaxTime());
		
		//测试有前缀的并且要设置到环境变量中的值
		System.out.println(System.getProperty("com.cxytiandi.max"));
		
		Biz biz = ConfApplication.getBean(Biz.class);
		String max = biz.getMax();
		System.out.println(max);
		try {
			Thread.sleep(1000 * 60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String max2 = biz.getMax();
		System.out.println(max2);
	}
}
 