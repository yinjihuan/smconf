package org.cxytiandi.conf.web.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

/**
 * 配置信息集合
 * @author yinjihuan
 * @date 2017-02-21
 */
@Getter
@Setter
@Document(collection="conf")
@CompoundIndexes({
	@CompoundIndex(name = "conf_index", def = "{env:1, s_name:1, c_fname:1, key:1}", unique = true, background = true)
})
public class Conf {
	@Id
	private String id;
	
	/**
	 * 环境
	 */
	@Field("env")
	private String env;
	
	/**
	 * 系统名称
	 */
	@Field("s_name")
	private String systemName;
	
	/**
	 * 配置文件名称
	 */
	@Field("c_fname")
	private String confFileName;
	
	/**
	 * 配置Key
	 */
	@Field("key")
	private String key;
	
	/**
	 * 配置Value
	 */
	@Field("value")
	private String value;
	
	/**
	 * 描述
	 */
	@Field("desc")
	private String desc;
	
	/**
	 * 创建时间
	 */
	@Field("c_date")
	private Date createDate;
	
	/**
	 * 修改时间
	 */
	@Field("m_date")
	private Date modifyDate;

	
}
