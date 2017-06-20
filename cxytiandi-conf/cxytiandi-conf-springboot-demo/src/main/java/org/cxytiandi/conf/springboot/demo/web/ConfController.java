package org.cxytiandi.conf.springboot.demo.web;

import org.cxytiandi.conf.springboot.demo.conf.MongoConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfController {
	@Autowired
	private MongoConf mongoConf;
	
	@GetMapping("/conf")
	public MongoConf get() {
		return mongoConf;
	}
	
}
