package org.cxytiandi.conf.client.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public abstract class BeanUtils {

	/**
	 * 获取类中所有字段名称
	 * @param entityClass
	 * @return
	 */
	public static String[] getFieldNames(Class<?> entityClass) {
		Field[] fs = extractFieldsFromPOJO(entityClass);
		List<String> rslt = new java.util.LinkedList<String>();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];
			rslt.add(f.getName());
		}
		return rslt.toArray(new String[rslt.size()]);
	}

	public static Field[] extractFieldsFromPOJO(Class<?> pojoClazz) {
		List<Field> list = new ArrayList<Field>();
		do {
			Field[] dfs = pojoClazz.getDeclaredFields();
			if (dfs != null) {
				for (Field f : dfs) {
					if (Modifier.isPrivate(f.getModifiers()) && Modifier.isStatic(f.getModifiers()) == false
							&& Modifier.isFinal(f.getModifiers()) == false)
						list.add(f);
				}
			}
			pojoClazz = pojoClazz.getSuperclass();
		} while (pojoClazz != null && pojoClazz.getSimpleName().equals(Object.class.getSimpleName()) == false);
		return list.toArray(new Field[list.size()]);
	}

}
