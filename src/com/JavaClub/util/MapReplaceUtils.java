package com.JavaClub.util;

import java.util.List;
import java.util.Map;

import javax.crypto.spec.SecurityHelper;

public class MapReplaceUtils{

	public static void handleLsitMapData(List<Map<String, Object>> list){
		//判断集合是否为空
		if(ListUtils.isNotEmpty(list) && list.size()>0){
			//遍历集合
			for (int i = 0; i < list.size(); i++) {
				//获取每个list集合中的map集合
				Map<String, Object> map=list.get(i);
				//遍历map集合，获取键，值
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					Object value = entry.getValue();	//获取值
					String key = entry.getKey();		//获取键
					//判断值是否为空，如果为空就把键设置为空
					if(value==null||value.equals("")||value.equals("null")){
						map.put(key,"");
					}else{
						//判断key的是否为pripid
						if(key.toUpperCase().equals("PRIPID")){
                            if(map.get("REGSTATE_CN")!=null){
                                String regstate = (String)map.get("REGSTATE_CN");
                                int index = regstate.indexOf('（');
                                if (index > 0) {
                                    String result = regstate.substring(0, index);
                                    map.put("REGSTATE_CN", result);
                                } 
                            }else if(map.get("REGSTATE_CN")!=null && "注销".equals(map.get("REGSTATE_CN"))){
                            	map.put(key,"");
                            }
	                        map.put(key,SecurityHelper.getEncrypt(value.toString()));
	                    //判断注册资金，设置为科学技术法，例如：1000,000,0.00
						}else if(key.toUpperCase().equals("REGCAP")){
						    map.put(key, StringUtil.decimalFormat(map.get(key)));
						//
                        }else if(StringUtil.isInteger(value.toString())){
							value = replaceSign(value.toString());
							map.put(entry.getKey(), value+"");
						}else{
							map.put(entry.getKey(), value);
						}
					}
				
				}
				list.set(i, map);
			}
		}
	}
	
	public static void handleMapData(Map<String,Object> dataMap){
		if(dataMap !=null && !dataMap.isEmpty() ){
			for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
				Object value = entry.getValue();
				String key = entry.getKey();
				if(value==null||value.equals("")||value.equals("null")){
					dataMap.put(entry.getKey(),"");
				}else{
					if(key.toUpperCase().equals("PRIPID")){
					    if(dataMap.get("REGSTATE_CN")==null && dataMap.get("EnterAddtionID")==null){
					        dataMap.put(key,"");
						}else{
                            if(dataMap.get("REGSTATE_CN")!=null){
                                String regstate = (String)dataMap.get("REGSTATE_CN");
                                int index = regstate.indexOf('（');
                                if (index > 0) {
                                    String result = regstate.substring(0, index);
                                    dataMap.put("REGSTATE_CN", result);
                                }
                            }
						    dataMap.put(key,SecurityHelper.getEncrypt(value.toString()));
						}
					}else if(key.toUpperCase().equals("REGCAP")){
					    dataMap.put(key, StringUtil.decimalFormat(dataMap.get(key)));
                    }else if(StringUtil.isInteger(value.toString())){
						value = replaceSign(value.toString());
						dataMap.put(entry.getKey(), value+"");
					}else{
						dataMap.put(entry.getKey(), value);
					}
				}

			}
		}
	}
	
	public static String replaceRule(String strValue){
		strValue = strValue.replaceAll("\r","");
		strValue = strValue.replaceAll("\n","");
		strValue = strValue.replaceAll("\r\n","");
		return StringUtil.subZeroAndDot(strValue);
	}
	
	public static Object replaceSign(String strValue){
	    strValue =StringUtil.subZeroAndDot(strValue);
		return strValue;
	}
}
