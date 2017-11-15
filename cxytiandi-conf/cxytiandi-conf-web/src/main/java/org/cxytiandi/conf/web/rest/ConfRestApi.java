package org.cxytiandi.conf.web.rest;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.cxytiandi.conf.web.common.GlobalException;
import org.cxytiandi.conf.web.common.ParamException;
import org.cxytiandi.conf.web.common.ResponseData;
import org.cxytiandi.conf.web.common.ServerException;
import org.cxytiandi.conf.web.domain.Conf;
import org.cxytiandi.conf.web.service.ConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置信息rest接口
 * @author yinjihuan
 * @date 2017-02-22
 */
@RestController
public class ConfRestApi {
	
	@Autowired
	private ConfService confService;
	
	@GetMapping("/rest/conf/list/{env}")
	public ResponseData list(@PathVariable("env") String env) throws GlobalException {
		try {
			if (StringUtils.isBlank(env)) {
				throw new ParamException("env not null");
			}
			return ResponseData.ok(confService.list(env));
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}
	
	@GetMapping("/rest/conf/list/{env}/{systemName}")
	public ResponseData list(@PathVariable("env") String env, @PathVariable("systemName") String systemName) throws GlobalException {
		try {
			if (StringUtils.isBlank(env)) {
				throw new ParamException("env not null");
			}
			if (StringUtils.isBlank(systemName)) {
				throw new ParamException("systemName not null");
			}
			return ResponseData.ok(confService.list(env, systemName));
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}
	
	@GetMapping("/rest/conf/list/{env}/{systemName}/{confFileName}")
	public ResponseData list(@PathVariable("env") String env, 
			@PathVariable("systemName") String systemName,
			@PathVariable("confFileName") String confFileName) throws GlobalException {
		try {
			if (StringUtils.isBlank(env)) {
				throw new ParamException("env not null");
			}
			if (StringUtils.isBlank(systemName)) {
				throw new ParamException("systemName not null");
			}
			if (StringUtils.isBlank(confFileName)) {
				throw new ParamException("confFileName not null");
			}
			return ResponseData.ok(confService.list(env, systemName, confFileName));
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}
	
	@GetMapping("/rest/conf/list/{env}/{systemName}/{confFileName}/{key}")
	public ResponseData list(@PathVariable("env") String env, 
			@PathVariable("systemName") String systemName,
			@PathVariable("confFileName") String confFileName,
			@PathVariable("key") String key) throws GlobalException {
		try {
			if (StringUtils.isBlank(env)) {
				throw new ParamException("env not null");
			}
			if (StringUtils.isBlank(systemName)) {
				throw new ParamException("systemName not null");
			}
			if (StringUtils.isBlank(confFileName)) {
				throw new ParamException("confFileName not null");
			}
			if (StringUtils.isBlank(key)) {
				throw new ParamException("key not null");
			}
			return ResponseData.ok(confService.get(env, systemName, confFileName, key));
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}
	
	@GetMapping("/rest/conf/{id}")
	public ResponseData get(@PathVariable("id") String id) throws GlobalException {
		try {
			if (StringUtils.isBlank(id)) {
				throw new ParamException("id not null");
			}
			return ResponseData.ok(confService.get(id));
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}
	
	@PostMapping("/rest/conf")
	public ResponseData save(@RequestBody Conf conf) throws GlobalException {
		if (StringUtils.isBlank(conf.getEnv())) {
			throw new ParamException("env not null");
		}
		if (StringUtils.isBlank(conf.getSystemName())) {
			throw new ParamException("systemName not null");
		}
		if (StringUtils.isBlank(conf.getConfFileName())) {
			throw new ParamException("confFileName not null");
		}
		if (StringUtils.isBlank(conf.getKey())) {
			throw new ParamException("key not null");
		}
		if (conf.getValue() == null) {
			throw new ParamException("value not null");
		}
		if (StringUtils.isBlank(conf.getDesc())) {
			throw new ParamException("desc not null");
		}
		conf.setCreateDate(new Date());
		conf.setModifyDate(new Date());
		confService.save(conf);
		return ResponseData.ok();
	}
	
	@PostMapping("/rest/conf/update")
	public ResponseData redesc(@RequestBody Conf conf) throws GlobalException {
		if (StringUtils.isBlank(conf.getId())) {
			throw new ParamException("id not null");
		}
		if (StringUtils.isBlank(conf.getEnv())) {
			throw new ParamException("env not null");
		}
		if (StringUtils.isBlank(conf.getSystemName())) {
			throw new ParamException("systemName not null");
		}
		if (StringUtils.isBlank(conf.getConfFileName())) {
			throw new ParamException("confFileName not null");
		}
		if (StringUtils.isBlank(conf.getKey())) {
			throw new ParamException("key not null");
		}
		if (conf.getValue() == null) {
			throw new ParamException("value not null");
		}
		if (StringUtils.isBlank(conf.getDesc())) {
			throw new ParamException("desc not null");
		}
		conf.setModifyDate(new Date());
		confService.save(conf);
		return ResponseData.ok();
	}
}
