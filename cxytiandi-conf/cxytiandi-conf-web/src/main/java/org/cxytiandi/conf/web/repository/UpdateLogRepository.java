package org.cxytiandi.conf.web.repository;

import java.util.List;
import org.cxytiandi.conf.client.common.Constant;
import org.cxytiandi.conf.web.domain.UpdateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UpdateLogRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate; 
	
	public void save(UpdateLog log) {
		mongoTemplate.save(log);
	}
	
	public List<UpdateLog> list(String updateObjId) {
		Query query = Query.query(Criteria.where("updateObjId").is(updateObjId));
		query.limit(Integer.getInteger(Constant.SMCONF_LOG_LIMIT, 200));
		query.with(new Sort(Direction.DESC, "id"));
		return mongoTemplate.find(query, UpdateLog.class);
	}
}
