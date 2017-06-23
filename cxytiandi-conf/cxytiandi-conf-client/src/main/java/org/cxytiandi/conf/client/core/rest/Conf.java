package org.cxytiandi.conf.client.core.rest;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 配置信息集合
 * @author yinjihuan
 * @date 2017-02-21
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Conf {
	private String id;
	
	/**
	 * 环境
	 */
	private String env;
	
	/**
	 * 系统名称
	 */
	private String systemName;
	
	/**
	 * 配置文件名称
	 */
	private String confFileName;
	
	/**
	 * 配置Key
	 */
	private String key;
	
	/**
	 * 配置Value
	 */
	private Object value;
	
	/**
	 * 描述
	 */
	private String desc;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 修改时间
	 */
	private Date modifyDate;

}
