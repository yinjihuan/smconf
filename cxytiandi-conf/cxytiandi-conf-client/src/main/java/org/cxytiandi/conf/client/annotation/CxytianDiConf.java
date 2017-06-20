package org.cxytiandi.conf.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

/**
 * 标识Bean为配置文件的注解
 * @author yinjihuan
 * @date 2017-02-20
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface CxytianDiConf {
	
	/**
	 * 配置的名称, 不填默认为类名
	 * @return
	 */
	String value() default "";
	
	/**
	 * 系统名称, 标识在哪个系统使用此配置
	 * @return
	 */
	String system() default "";
	
	/**
	 * 是否设置到环境变量中
	 * @author yinjihuan
	 * @return
	 */
	boolean env() default false;
	
	/**
	 * key的前缀,只针对需要设置到环境变量中的值有作用
	 * @author yinjihuan
	 * @return
	 */
	String prefix() default "";
}
