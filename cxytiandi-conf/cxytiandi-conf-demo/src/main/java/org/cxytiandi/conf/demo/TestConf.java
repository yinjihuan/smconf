package org.cxytiandi.conf.demo;

import org.cxytiandi.conf.demo.conf.Biz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring.xml" })
public class TestConf {

	@Autowired
	private Biz biz;

	//可以在sleep的时候去后台进行值的修改，最后输出的值是修改后的值
	@Test
	public void test() {
		System.out.println(biz.getMax());
		try {
			Thread.sleep(1000 * 60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(biz.getMax());
	}
}
