package org.cxytiandi.conf.springboot.demo.web;

import java.util.Map;

import org.cxytiandi.conf.client.init.ConfInit;
import org.cxytiandi.conf.springboot.demo.conf.MongoConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfController {
	
	@Autowired
	private MongoConf mongoConf;
	
	@Autowired
	private ConfInit confInit;
	
	@GetMapping("/conf")
	public MongoConf get() {
		return mongoConf;
	}
	
	/**
	 * 支持在程序运行过程中修改配置信息
	 * @return
	 */
	@GetMapping("/updateConf")
	public String updateConf() {
		Map<String, Object> map = mongoConf.getMap();
		map.put("core", 100);
		map.put("conn", "192.168.0.1");
		confInit.init(mongoConf);
		return "success";
	}
	
}
