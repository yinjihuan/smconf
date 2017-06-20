package org.cxytiandi.conf.client.util;

import java.lang.reflect.Method;
import org.springframework.util.StringUtils;

public abstract class ReflectUtils {
	
	/**
	 * 根据字段名调用对象的getter方法，如果字段类型为boolean，则方法名可能为is开头，也有可能只是以setFieleName的普通方法
	 * @param fieldName
	 * @param instance
	 * @return getter方法调用后的返回值
	 */
	public static Object callGetMethod(String fieldName, Object instance){
		Object result = null;
		try{
			String mn = "get"+StringUtils.capitalize(fieldName);
			Method method = null;
			try{
				method = getMethod(instance.getClass(), mn);
			}catch(NoSuchMethodException nsme){
				mn = "is"+StringUtils.capitalize(fieldName);
				method = getMethod(instance.getClass(), mn);
			}
			result = method.invoke(instance, new Object[]{});
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 得到给写的类或其父类中声明的方法
	 * @param entityClass
	 * @param methodName
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static Method getMethod(Class<?> entityClass, String methodName, Class<?>... type) throws NoSuchMethodException {
		try{
			Method m = entityClass.getDeclaredMethod(methodName, type);
			if(m != null){
				return m;
			}
		}catch(NoSuchMethodException ex){
			if(entityClass.getSuperclass() != null && entityClass.getSuperclass() != Object.class){
				return getMethod(entityClass.getSuperclass(), methodName, type);
			}else{
				throw ex;
			}
		}
		return null;
	}
}
