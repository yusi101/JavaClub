package com.JavaClub.util;

import java.util.Map;

import com.util.ConnectUrl;

public class Connect {

	ConnectUrl connect=new ConnectUrl();
	
	public static String getPath(Map<String, String> parameters,String url){
        String PATH="";
        if("hn".equals(parameters.get("ADD_PROVINCE"))){
            PATH=Const.CONNECT_PATH_JX+url;
        }else{
            PATH=Const.CONNECT_PATH_JX+url;
        }
        return PATH;
    }
	/**
	 * 
	 * @descript 调用接口，参数为PageData(url系统会加上默认前缀)
	 * @author 李海涛
	 * @since 2016年9月9日下午2:59:24
	 * @param url 接口名称
	 * @param parameters 参数
	 * @param Method 提交方式 POST/GET
	 * @return  String
	 */
	@SuppressWarnings("unchecked")
	public static String sendConnectByPdToString(String url, PageData parameters,String Method){
		String result="";
		if(Method.toUpperCase().equals("POST")){
			result= ConnectUrl.sendPost(getPath(parameters,url), parameters);
		}else if(Method.toUpperCase().equals("GET")){
			result= ConnectUrl.sendGet(getPath(parameters,url), parameters);
		}
		return result;
	}
	
	/**
     * 
     * @descript 调用接口，参数为PageData(url系统不会加上默认前缀)
     * @author 李海涛
     * @since 2016年9月9日下午2:59:24
     * @param url 接口名称
     * @param parameters 参数
     * @param Method 提交方式 POST/GET
     * @return String
     */
	@SuppressWarnings("unchecked")
	public static String sendConnectByPdToString2(String url, PageData parameters,String Method){
		String result="";
		if(Method.toUpperCase().equals("POST")){
			result= ConnectUrl.sendPost(url, parameters);
		}else if(Method.toUpperCase().equals("GET")){
			result= ConnectUrl.sendGet(url, parameters);
		}
		return result;
	}
	
	/**
     * 
     * @descript 调用接口，参数为Map(url系统会加上默认前缀)
     * @author 李海涛
     * @since 2016年9月9日下午2:59:24
     * @param url 接口名称
     * @param parameters 参数
     * @param Method 提交方式 POST/GET
     * @return String
     */
	public static String sendConnectByMapToString(String url, Map<String, String> parameters,String Method){
		String result="";
		if(Method.toUpperCase().equals("POST")){
			result= ConnectUrl.sendPost(getPath(parameters,url), parameters);
		}else if(Method.toUpperCase().equals("GET")){
			result= ConnectUrl.sendGet(getPath(parameters,url), parameters);
		}
		return result;
	}
	
	/**
     * 
     * @descript 调用接口，参数为PageData(url系统会加上默认前缀)
     * @author 李海涛
     * @since 2016年9月9日下午2:59:24
     * @param url 接口名称
     * @param parameters 参数
     * @param Method 提交方式 POST/GET
     * @return Map<String, Object>
     */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> sendConnectByPdToMap(String url, PageData parameters,String Method){
		String result="";
		
		if(Method.toUpperCase().equals("POST")){
			result= ConnectUrl.sendPost(getPath(parameters,url), parameters);
		}else if(Method.toUpperCase().equals("GET")){
			result= ConnectUrl.sendGet(getPath(parameters,url), parameters);
		}
		Map<String, Object> list = JsonUtils.fromJsonToMap(result);
		return list;
	}
	
	/**
     * 
     * @descript 调用接口，参数为PageData(url系统不会加上默认前缀)
     * @author 李海涛
     * @since 2016年9月9日下午2:59:24
     * @param url 接口名称
     * @param parameters 参数
     * @param Method 提交方式 POST/GET
     * @return Map<String, Object>
     */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> sendConnectByPdToMap2(String url, PageData parameters,String Method){
		String result="";
		if(Method.toUpperCase().equals("POST")){
			result= ConnectUrl.sendPost(url, parameters);
		}else if(Method.toUpperCase().equals("GET")){
			result= ConnectUrl.sendGet(url, parameters);
		}
		Map<String, Object> list = JsonUtils.fromJsonToMap(result);
		return list;
	}
	
	/**
     * 
     * @descript 调用接口，参数为Map(url系统会加上默认前缀)
     * @author 李海涛
     * @since 2016年9月9日下午2:59:24
     * @param url 接口名称
     * @param parameters 参数
     * @param Method 提交方式 POST/GET
     * @return Map<String, Object>
     */
	public static Map<String, Object> sendConnectByMapToMap(String url, Map<String, String> parameters,String Method){
		String result="";
		if(Method.toUpperCase().equals("POST")){
			result= ConnectUrl.sendPost(getPath(parameters,url), parameters);
		}else if(Method.toUpperCase().equals("GET")){
			result= ConnectUrl.sendGet(getPath(parameters,url), parameters);
		}
		Map<String, Object> list = JsonUtils.fromJsonToMap(result);
		return list;
	}
	public static void main(String[] args) {
	    PageData parameters=new PageData();
	    parameters.put("aa", "1");
	    if(parameters.get("b")==null){
	        System.out.println("5555");
	    }else{
	        
	        System.out.println(parameters.get("b"));
	    }
    }
}
