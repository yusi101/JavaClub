package com.JavaClub.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.crypto.spec.SecurityHelper;
import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("rawtypes")
public class PageData extends HashMap implements Map{
	
	private static final long serialVersionUID = 1L;
	
	Map map = null;
	HttpServletRequest request;
	
	@SuppressWarnings({ "unchecked"})
	public PageData(HttpServletRequest request){
		this.request = request;
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap(); 
		Iterator entries = properties.entrySet().iterator(); 
		Map.Entry entry; 
		String name = "";  
		String value = "";  
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next(); 
			name = (String) entry.getKey(); 
			Object valueObj = entry.getValue(); 
			value = "";
			if(valueObj instanceof String[]){ 
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){ 
					value = value+ values[i] + ",";
				}
				value = value.substring(0, value.length()-1); 
			}else{
				value = valueObj.toString(); 
			}
			//字符串解码
			value=SecurityHelper.getDecrypt(value);
			//替换脚本，防脚本注入
			value=StringUtil.replaceJavaScript(value);
			//敏感词过滤
			/*if("remark".equalsIgnoreCase(name) || "CONTENT".equalsIgnoreCase(name)){
				try {
					SensitivewordFilter filter = new SensitivewordFilter();
					long beginTime = System.currentTimeMillis();
					value=filter.replaceSensitiveWord(value, 1, "*");
					long endTime = System.currentTimeMillis();
					System.out.println("总共消耗时间为：" + (endTime - beginTime));
					
				} catch (Exception e) {
				}
			}*/
			returnMap.put(name, SecurityHelper.getDecrypt(value)); 
		}
		map = returnMap;
	}
	
	public PageData() {
		map = new HashMap();
	}
	
	@Override
	public Object get(Object key) {
		Object obj = null;
		if(map.get(key) instanceof Object[]) {
			Object[] arr = (Object[])map.get(key);
			obj = request == null ? arr:(request.getParameter((String)key) == null ? arr:arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}
	
	public String getString(Object key) {
		return (String)get(key);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}
	
	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return map.containsValue(value);
	}

	public Set entrySet() {
		// TODO Auto-generated method stub
		return map.entrySet();
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return map.isEmpty();
	}

	public Set keySet() {
		// TODO Auto-generated method stub
		return map.keySet();
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map t) {
		// TODO Auto-generated method stub
		map.putAll(t);
	}

	public int size() {
		// TODO Auto-generated method stub
		return map.size();
	}

	public Collection values() {
		// TODO Auto-generated method stub
		return map.values();
	}
	
}
