package org.cxytiandi.conf.web.controller;

import java.util.Map;
import org.cxytiandi.conf.web.service.ConfService;
import org.cxytiandi.conf.web.service.UpdateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UpdateLogController {
	
	@Autowired
	private UpdateLogService updateLogService;
	
	@Autowired
	private ConfService confService;
	
	@GetMapping("/logs/{id}")
	public String logPage(@PathVariable String id, Map<String, Object> model) {
		model.put("conf", confService.get(id));
		model.put("logs", updateLogService.list(id));
		return "conf/logs";
	}
	
}
