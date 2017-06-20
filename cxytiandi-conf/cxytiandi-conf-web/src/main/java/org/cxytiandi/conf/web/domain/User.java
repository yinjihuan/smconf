package org.cxytiandi.conf.web.domain;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统用户表
 * @author yinjihuan
 */
@Getter
@Setter
@Builder
@Document(collection="users")
public class User {
	
	@Id
	private String id;
	
	@Field("uname")
	@Indexed(unique=true, background=true)
	private String username;
	
	@Field("pass")
	private String pass;
	
	/**
	 * 对应的环境集合，能操作几个环境的数据就配置几个环境
	 */
	@Field("envs")
	private List<String> envs;
	
}
