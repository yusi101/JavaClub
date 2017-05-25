package com.JavaClub.util;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 字符串相关方法
 *
 */
public class StringUtil {
    public static final String[] hanziArr = new String[]{"个", "十", "百", "千", "万", "十", "百", "千", "亿"};
    public static final String[] numberArr = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "点"};
    /**
     * 
     * @descript (将阿拉布数字转换成中文)
     * @author 李海涛
     * @since 2016年10月25日下午8:12:20
     * @param s
     * @return
     */
    public static String toHanzi(String s) {
        StringBuilder result = new StringBuilder();
        int pointIndex = s.indexOf(".");
        String integer;
        String decimal = "";
        if (-1 == pointIndex) {
            integer = s;
        } else {
            integer = s.substring(0, pointIndex);
            decimal = s.substring(pointIndex + 1);
        }
        for (int i = 0; i < integer.length(); i++) {
            char c = integer.charAt(i);
            if (c == '0') {
                /*result.append(numberArr[0]);*/
            } else {
                result.append(numberArr[Integer.parseInt(String.valueOf(c))]);
                if (i != integer.length() - 1)
                    result.append(hanziArr[integer.length() - 1 - i]);
            }
        }
        if (null != decimal && !"".equals(decimal)) {
            result.append(numberArr[11]);
        }
        for (int i = 0; i < decimal.length(); i++) {
            char c = decimal.charAt(i);
            result.append(numberArr[Integer.parseInt(String.valueOf(c))]);
        }
        return result.toString().replace("一十", "十");
    }
	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr){
	    int i = 0;
	    String TempStr = valStr;
	    String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
	    valStr = valStr + ",";
	    while (valStr.indexOf(',') > 0)
	    {
	        returnStr[i] = valStr.substring(0, valStr.indexOf(','));
	        valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());
	        
	        i++;
	    }
	    return returnStr;
	}
	
	public static final String DEFAULT_DELIM = ";";

    public static boolean isEmpty(String str) {
        if (str!=null && !"".equals(str) && !"null".equals(str) && str.trim().length()>0) {
            return false;
        }
        return true;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String[] sliptTitle(String titles, String regex) {

        if (isNotEmpty(regex)) {
            return titles.split(regex);
        } else {
            return titles.split(",");
        }
    }

    /**
     * 把字符串数组连接成一个字符串。 默认的连接 ；
     * 
     * @param list
     * @return
     */
    public static String join(String[] list) {
        return join(list, DEFAULT_DELIM);
    }

    /**
     * 把字符串数组连接成一个字符串。
     * 
     * @param str
     * @param delim
     *            指定的连接
     * @return
     */
    public static String join(String[] str, String delim) {
        final int length = str.length;
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buf.append(str[i]);
            if (i != length - 1) {
                buf.append(delim);
            }
        }
        return new String(buf);
    }

    public static String join(List<String> str, String delim) {
        final int length = str.size();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buf.append(str.get(i));
            if (i != length - 1) {
                buf.append(delim);
            }
        }
        return new String(buf);
    }

    public static String combineStringArraySql(String[] array, String delim) {
        int length = array.length - 1;
        if (delim == null) {
            delim = "";
        }
        StringBuffer result = new StringBuffer(length * 8);
        for (int i = 0; i < length; i++) {
            result.append("'");
            result.append(array[i]);
            result.append("'");
            result.append(delim);
        }
        result.append("'");
        result.append(array[length]);
        result.append("'");
        return result.toString();
    }

    public static Map<String, Object> setNullValue(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {

            if (entry.getValue() == null) {
                map.put(entry.getKey(), "");
            }

        }
        return map;
    }

    public String getcode(String code, HttpServletRequest request) {
        if (request.getParameter(code) != null) {
            return request.getParameter(code);
        }
        return "";
    }

    public String getcodeint(String code, HttpServletRequest request) {
        if (request.getParameter(code) != null) {
            return request.getParameter(code);
        }
        return "0";
    }

    // 截取指定前14为数字后面变成*
    public static String encryptionStr(String str) {
        String value = str.substring(0, 14);

        for (int i = 0; i < str.length() - 14; i++) {
            value += "*";
        }

        return value;
    }

   
    /**
     * 
     * @descript (获取0-9指定长度的随机码)
     * @author 李海涛
     * @since 2016年10月19日上午9:50:05
     * @param length
     * @return
     */
    public static String getRandom(int length) {
        String allChar = "0123456789";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }
    
    
    
    /**
     * 
     * @descript (去除工商信息的敏感信息)
     * @author 李海涛
     * @since 2016年10月19日上午9:54:31
     * @param str
     * @return
     */
    public static String replace(String str){
        //身份证号码
        Pattern pattern = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0[1-9])||(1[0-2]))((0[1-9])||(1\\d)||(2\\d)||(3[0-1]))\\d{3}([0-9]||X||x)$");  
        Matcher isIdCode = pattern.matcher(str);
        if(str==null || str.isEmpty()){
            return "";
        }else if(str.length()>=2 && "91".equals(str.substring(0, 2))){
            return str;
        }else if(isIdCode.matches()){
           return "******";
        }else{
            str=str.replaceAll("[0-9]{4}-[0-9]{2}-[0-9]{2}", "***")
                    .replaceAll("[0-9]{17}[A-Z a-z 0-9_]{1}", "******")
                    .replaceAll("[0-9]{1,8}[.][0-9]{1,}", "**万元")
                    .replaceAll("[0-9]{1,8}万", "**万");
            return str;  
        }
        /*String matchString2 = "^[1-9]\\d{5}[1-9]\\d{3}((0[1-9])||(1[0-2]))((0[1-9])||(1\\d)||(2\\d)||(3[0-1]))\\d{3}([0-9]||X)$";  


       if()*/
    }
    
    /**
     * 
     * @descript (将数值以每三位一个逗号的形式现实)
     * @author 李海涛
     * @since 2016年12月27日下午2:24:10
     * @param obj 
     * @return
     */
    public static Object decimalFormat(Object obj){
    	try {
    		if(obj!=null && !"".equals(obj)){
    			double d=Double.parseDouble(obj.toString());
    			DecimalFormat df=new DecimalFormat("#,###.#####################");
           	 	return df.format(d);
    		}else{
    			return obj;
    		}
    		
		} catch (Exception e) {
			return obj;
		}
    }
    public static boolean isInteger(String str) {
        try {
            Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]*)?$");  
            Matcher isNum = pattern.matcher(str);
            if (!isNum.matches()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
    
    
    public static boolean isInt(String str) {
        try {
           if(!isEmpty(str) && null!=str && Integer.parseInt(str)>0){
               return true; 
           }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    /** 
     * 使用java正则表达式去掉多余的.与0 
     * @param s 
     * @return  
     */  
    public static String subZeroAndDot(String s){
        if(s!=null && !"".equals(s)){
            if(s.indexOf(".") > 0){  
                s = s.replaceAll("0+?$", "");//去掉多余的0  
                s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
            }   
        }
        
        return s;  
    }
    
    public static String replaceJavaScript(String str){
        if(str!=null && !"".equals(str)){
            str=str.replaceAll("<script.*?(?<=/script>)","");
            str=str.replaceAll("[e|E][v|V][a|A][l|L]\\((.*)\\)","");
            str=str.replaceAll("<", "&lt;");
            str=str.replaceAll(">", "&gt;");
            str=str.replaceAll("\\(", "(");
            str=str.replaceAll("\\)", ")");
            str=str.replaceAll("'", "'");
        }
        return str;
    }
    
    public static Object replaceJavaScript(Object obj){
        if(obj!=null && !"".equals(obj) && "java.lang.String".equals(obj.getClass().getName())){
            try {
                return replaceJavaScript(obj.toString());
            } catch (Exception e) {}
        }
        return obj;
    }
    // 去除小数点
    public static String clearDian(String str) {
        if(str.indexOf(".") != -1){
        	str = str.replaceAll(".0+?$", "");
        }
        return str;
    }
    public static void main(String[] args) {
     /*  System.out.println(subZeroAndDot("66696.054688"));
        
       // String str="李海涛1<script>aler(123)</script>李海涛1";
      //  System.out.println(str.replaceAll("<script.*?(?<=/script>)",""));
        System.out.println(decimalFormat("1662500.0"));
        String str1="李海涛1eval(alert('5555'))</script>李海涛1";
        System.out.println(str1.replaceAll("[e|E][v|V][a|A][l|L]\\((.*)\\)",""));
        System.out.println(isInteger("0"));*/
    	clearDian("12343.0");
    }
}
