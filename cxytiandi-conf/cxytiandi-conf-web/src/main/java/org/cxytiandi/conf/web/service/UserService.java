package org.cxytiandi.conf.web.service;

import org.cxytiandi.conf.web.domain.User;

public interface UserService {
	
	User getUser(String username, String pass);
	
	void save(User user);
	
	boolean exists(String username);
	
}
