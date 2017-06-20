package org.cxytiandi.conf.client.core;

import java.lang.reflect.Field;
import java.util.List;
import org.cxytiandi.conf.client.ConfApplication;
import org.cxytiandi.conf.client.core.rest.Conf;
import org.cxytiandi.conf.client.core.rest.ConfRestClient;
import org.cxytiandi.conf.client.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefreshConfCallBackImpl implements RefreshConfCallBack {
	private static final Logger LOGGER = LoggerFactory.getLogger(RefreshConfCallBackImpl.class);
	
	public void call(String path) {
		LOGGER.info(path + " update...");
		String[] values = path.split("/");
		String env = values[2];
		String system = values[3];
		String confFileName = values[4].split("&")[1];
		Object confBean = ConfApplication.getBean(env, system, confFileName);
		if (confBean == null) {
			return;
		}
		Field[] fieds = confBean.getClass().getDeclaredFields();
		
		ConfRestClient restClient = CommonUtil.getConfRestClient();
		List<Conf> confs = restClient.list(env, system, confFileName);
		for (Conf conf : confs) {
        	for (Field field : fieds) {
				field.setAccessible(true);
				if (field.getName().equals(conf.getKey())) {
					try {
						LOGGER.info(field.getName() + "\tOldVlaue:" + field.get(confBean) + "\tNewValue:" + conf.getValue());
						CommonUtil.setValue(field, confBean, conf.getValue());
					} catch (Exception e) {
						LOGGER.error("", e);
					}
					break;
				}
			}
		}
	}

}
