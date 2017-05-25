package com.JavaClub.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * 字符串工具类
 * @author gongzhiqiang
 *
 */
public class StrUtil {

	protected static Logger logger = Logger.getLogger(StrUtil.class);
	
    //截取为后面的4位数字变成*
    public static String encryptionStr(String str){
    	if(4 >= str.length()){
    		return str;
    	}
    	
    	int num = str.length() - 4; 	
        String value = str.substring(0, num);

        for(int i = 0; i < 4; i++){
            value += "*";
        }

        return value;
    }

    //获取随机数
    public static int getRandom(int num){
        Random random = new Random();
        int result = random.nextInt(num);

        return result;
    }

    //隐藏数字
    public static String replace(String str){
        str=str.replaceAll("[0-9]{4}-[0-9]{2}-[0-9]{2}", "***")
                .replaceAll("[0-9]{17}[A-Z a-z 0-9_]{1}", "******")
                .replaceAll("[0-9]{1,}.[0-9]{1,}", "**")
                .replaceAll("[0-9]{1,}万", "**万");
        return str;
    }

    //去小数点.00
    public static String updataZero(String str){   
    	if("".equals(str) || null == str){
    		return "";
    	}
    	
        int distance = str.lastIndexOf(".");
        String lastString = str.substring(distance + 1);
        int lastNum = Integer.parseInt(lastString);

        if (lastNum > 0) {
            return str;
        } else {
            return str.substring(0, distance);
        }
    }
    
    //去掉null
    public static String delNull(Object obj){
    	if(null == obj){
    		return "";
    	}

    	return obj + "";
    }
    
    //获取数组的值，如果下标越界则返回空字符串
    public static String getArrValue(String[] str, int num){
    	if(str.length > num){
    		return str[num];
    	}else{
    		return "";
    	}
    }
    
    public static String readTxtFile(String filePath){
        String str="";
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                        str+=lineTxt;
                    }
                    read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            logger.error(e.toString(),e);
        }
       return str;
    }    
}