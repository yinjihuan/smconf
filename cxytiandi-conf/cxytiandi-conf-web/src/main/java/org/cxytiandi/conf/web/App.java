package org.cxytiandi.conf.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Smconf程序启动入口<br>
 * simple manage conf 
 * @author yinjihuan
 */
@SpringBootApplication
@ServletComponentScan
public class App {
	public static void main(String[] args) {
		//将参数设置到环境变量中
		if (args != null) {
			for (String arg : args) {
				if (arg.startsWith("--")) {
					String[] strs = arg.split("=");
					System.setProperty(strs[0].substring(2), strs[1]);
				}
			}
		}
		SpringApplication.run(App.class, args);
	}
}
