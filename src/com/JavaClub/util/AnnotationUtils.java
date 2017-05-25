
package com.JavaClub.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.JavaClub.annotation.system.log.SysUserLog;
import com.JavaClub.annotation.system.log.SysUserLogSwitch;

public class AnnotationUtils {

	private static AnnotationUtils anno = null;

	public static AnnotationUtils getInstance() {
		if (anno == null) {
			anno = new AnnotationUtils();
		}
		return anno;
	}

	/**
	 * 读取注解值
	 * 
	 * @param annotationClasss 处理Annotation类名称
	 * @param annotationField 处理Annotation类属性名称
	 * @param className 处理Annotation的使用类名称
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public Map<String, String> loadVlaue(Class annotationClasss,
			String annotationField, String className) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		Method[] methods = Class.forName(className).getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(annotationClasss)) {
				Annotation p = method.getAnnotation(annotationClasss);
				Method m = p.getClass()
						.getDeclaredMethod(annotationField, null);
				String[] values = (String[]) m.invoke(p, null);
				for (String key : values) {
					map.put(key, key);
				}
			}
		}
		return map;
	}
	
	
	@SuppressWarnings("all")
	public Map<String, String> loadVlaue(Class annotationClasss,
			String annotationField, Method[] methods) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		for (Method method : methods) {
			if (method.isAnnotationPresent(annotationClasss)) {
				Annotation p = method.getAnnotation(annotationClasss);
				Method m = p.getClass()
						.getDeclaredMethod(annotationField, null);
				String[] values = (String[]) m.invoke(p, null);
				for (String key : values) {
					map.put(key, key);
				}
			}
		}
		return map;
	}
	
	@SuppressWarnings("all")
	public Map<String, Object> loadVlaue(Class annotationClasss,
			String annotationField, Annotation[] annotations) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		for (Annotation annotation : annotations) {
			if(annotation instanceof SysUserLog){
			Method m = annotation.getClass()
					.getDeclaredMethod(annotationField, null);
				String[] values = (String[]) m.invoke(annotation, null);
				for (String key : values) {
					map.put("LOGINFO", key);
			}
			}
		}
		return map;
	}
	
	
	@SuppressWarnings("all")
	public boolean loadBooleanVlaue(Class annotationClasss,
			String annotationField, Annotation[] annotations) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		for (Annotation annotation : annotations) {
			if(annotation instanceof SysUserLogSwitch){
			Method m = annotation.getClass()
					.getDeclaredMethod(annotationField, null);
				Boolean values = (Boolean) m.invoke(annotation, null);
				 return values.booleanValue();
			}
		}
		return true;
	}


}
