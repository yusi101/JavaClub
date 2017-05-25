package com.JavaClub.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.SecurityHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyGson {

	public static Gson getMyGson(){
		Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		return gson;
	}
	public static Gson getMyGson2(){
		Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setDateFormat("yyyy-MM-dd").create();
		return gson;
	}
	public static String toJson2(Object object){
		Gson gson =getMyGson2();
		String result= gson.toJson(object);
		return result;
	}
	public static String toJson(Object object){
		Gson gson =getMyGson();
		String result= gson.toJson(object);
		return result;
	}
	
	 public static void main(String[] args) {
			List<Map<String,Object>> list=new ArrayList<>();
			Map<String,Object> map=new HashMap<>();
			map.put("aaa", "123");
			map.put("bbb", "234.0");
			map.put("ccc", "345");
			list.add(map);
			com.google.gsons.Gson gson=new com.google.gsons.Gson();
			String str=gson.toJson2(list);
			System.out.println(str);
			System.out.println(SecurityHelper.getDecrypt("RlUvWnNkR0JPRjA9OE96SGcxVmR0Y2tMc0tDTGJzR0JkUT09"));
			
		}
}
