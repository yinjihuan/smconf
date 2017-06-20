package org.cxytiandi.conf.web.repository;

import org.cxytiandi.conf.web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public User getUser(String username, String pass) {
		Query query = Query.query(Criteria.where("username").is(username).and("pass").is(pass));
		return mongoTemplate.findOne(query, User.class);
	}
	
	public void save(User user) {
		mongoTemplate.save(user);
	}
	
	public boolean exists(String username) {
		Query query = Query.query(Criteria.where("username").is(username));
		return mongoTemplate.exists(query, User.class);
	}
}
