package org.cxytiandi.conf.web.service;

import java.util.List;
import org.cxytiandi.conf.web.domain.UpdateLog;

public interface UpdateLogService {
	
	List<UpdateLog> list(String updateObjId);
	
	void save(UpdateLog log);
	
}
