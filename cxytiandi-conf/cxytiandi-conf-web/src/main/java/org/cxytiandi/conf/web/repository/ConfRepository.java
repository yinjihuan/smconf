package org.cxytiandi.conf.web.repository;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.cxytiandi.conf.web.domain.Conf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ConfRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate; 
	
	public List<Conf> listForPage(String env, String systemName, String fileName, String key, int page, int limit) {
		Query query = new Query(Criteria.where("env").is(env));
		if (StringUtils.isNotBlank(systemName)) {
			query.addCriteria(Criteria.where("systemName").is(systemName));
		}
		if (StringUtils.isNotBlank(fileName)) {
			query.addCriteria(Criteria.where("confFileName").is(fileName));
		}
		if (StringUtils.isNotBlank(key)) {
			query.addCriteria(Criteria.where("key").is(key));
		}
		query.with(new Sort(Direction.DESC, "id"));
		return mongoTemplate.find(query, Conf.class);
	}
	
	public List<Conf> list(String env, String systemName, String fileName) {
		Query query = new Query(Criteria.where("env").is(env));
		query.addCriteria(Criteria.where("systemName").is(systemName));
		query.addCriteria(Criteria.where("confFileName").is(fileName));
		return mongoTemplate.find(query, Conf.class);
	}
	
	public List<Conf> list(String env, String systemName) {
		Query query = new Query(Criteria.where("env").is(env));
		query.addCriteria(Criteria.where("systemName").is(systemName));
		return mongoTemplate.find(query, Conf.class);
	}
	
	public List<Conf> list(String env) {
		Query query = new Query(Criteria.where("env").is(env));
		return mongoTemplate.find(query, Conf.class);
	}
	
	public void save(Conf conf) {
		mongoTemplate.save(conf);
	}
	
	public Conf get(String id) {
		return mongoTemplate.findById(id, Conf.class);
	}
	
	public void remove(String id) {
		mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), Conf.class);
	}
}
