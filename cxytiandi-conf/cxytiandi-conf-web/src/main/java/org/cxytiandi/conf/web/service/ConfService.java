package org.cxytiandi.conf.web.service;

import java.util.List;
import org.cxytiandi.conf.web.domain.Conf;

public interface ConfService {
	
	List<Conf> listForPage(String env, String systemName, String fileName, String key, int page, int limit);
	
	List<Conf> list(String env, String systemName, String fileName);
	
	List<Conf> list(String env, String systemName);
	
	List<Conf> list(String env);
	
	void save(Conf conf);
	
	Conf get(String id);
	
	List<Conf> get(String env, String systemName, String fileName, String key);
	
	void remove(String id);
	
	List<String> getNodes(String env, String systemName, String fileName);
	
}
