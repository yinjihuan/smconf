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
		SpringApplication.run(App.class, args);
	}
}
