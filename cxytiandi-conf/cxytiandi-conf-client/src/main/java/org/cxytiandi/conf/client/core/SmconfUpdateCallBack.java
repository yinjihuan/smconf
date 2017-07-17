package org.cxytiandi.conf.client.core;
/**
 * 用户可以在配置文件中实现该接口，对接口修改变动做一些自定义的处理<br>
 * 该方法被触发的时候，修改的值已经被加载到了对应的bean对象中
 * @author yinjihuan
 *
 */
public interface SmconfUpdateCallBack {
	
	public void reload();
	
}
