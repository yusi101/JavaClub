package com.JavaClub.util;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import net.sf.json.JSONObject;

/**
 * 获取经纬度工具类
 * @descript (用一句话描述改方法的作用)
 * @author 龚志强
 * @createTime 2016年10月11日下午2:08:27
 * @version 1.0
 */
public class LatAndLngUtil {

	/**
	 * 
	 * @descript 通过地址获取经纬度
	 * @author 龚志强
	 * @since 2016年10月11日下午2:08:11
	 * @param address
	 * @return
	 */
	public static Map<String, Double> getLngAndLat(String address) {
		Map<String, Double> map = new HashMap<String, Double>();
		//百度秘钥
		String ak = "vXYOmfFNqXGrd1TISQgt62rw1RzNOgfw";
		//String ak = "PR6dQGLjeiFEp69EEjXwDpNs";
		//百度请求地址
		String url = "http://api.map.baidu.com/geocoder/v2/?address=" + address.replaceAll(" ", "") + "&output=json&ak=" + ak;

		try{
			//发送请求
			String json = loadJSON(url);
			JSONObject obj = JSONObject.fromObject(json);
			
			if ("0".equals(obj.get("status") + "")) {
				double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
				double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
				map.put("lng", lng);
				map.put("lat", lat);
			} else {
				System.out.println("未找到相匹配的经纬度！");
			}
		}catch(Exception ex){
			System.out.println("出问题了！");
		}

		return map;
	}

	/**
	 * 
	 * @descript 请求百度接口获取经纬度
	 * @author 龚志强
	 * @since 2016年10月11日下午2:10:13
	 * @param url
	 * @return
	 */
	public static String loadJSON(String url) {
		StringBuilder json = new StringBuilder();
		
		try {
			URL oracle = new URL(url);
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			
		} catch (IOException e) {
			
		}
		
		return json.toString();
	}

	public static void main(String[] args) {
		Map<String,Double> map = getLngAndLat("江西省南昌市东湖区八一大道357号");
		System.out.println("经度：" + map.get("lng") + "\n纬度：" + map.get("lat"));
	}
}