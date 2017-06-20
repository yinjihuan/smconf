package org.cxytiandi.conf.web.service;

import java.util.List;

import org.cxytiandi.conf.client.util.CommonUtil;
import org.cxytiandi.conf.web.domain.Conf;
import org.cxytiandi.conf.web.repository.ConfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class ConfServiceImpl implements ConfService {
	
	@Autowired
	private ConfRepository confRepository;
	
	public List<Conf> listForPage(String env, String systemName, String fileName, String key, int page, int limit) {
		return confRepository.listForPage(env, systemName, fileName, key, page, limit);
	}

	public List<Conf> list(String env, String systemName, String fileName) {
		return confRepository.list(env, systemName, fileName);
	}

	public List<Conf> list(String env, String systemName) {
		return confRepository.list(env, systemName);
	}

	public List<Conf> list(String env) {
		return confRepository.list(env);
	}

	@Override
	public void save(Conf conf) {
		confRepository.save(conf);
	}

	@Override
	public Conf get(String id) {
		return confRepository.get(id);
	}

	@Override
	public List<Conf> get(String env, String systemName, String fileName, String key) {
		List<Conf> list = confRepository.listForPage(env, systemName, fileName, key, 1, 1);
		return list;
	}

	@Override
	public void remove(String id) {
		confRepository.remove(id);
	}

	@Override
	public List<String> getNodes(String env, String systemName, String fileName) {
		List<String> nodes = Lists.newArrayList();
		List<String> clients = CommonUtil.getZkClient().getClientServers(env, systemName);
		for (String client : clients) {
			if (client.split("&")[1].equals(fileName)) {
				nodes.add(client.split("&")[0]);
			}
		}
		return nodes;
	}

}
