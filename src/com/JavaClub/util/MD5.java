package com.JavaClub.util;

import java.security.MessageDigest;

public class MD5 {

	public static final String ENCODEING_TRYPE ="UTF-8";

	public static void main(String[] args) {
		System.out.println(md5("123李海涛"));
	}
	
	public final static String md5(String s) {  
        try {  
           
            MessageDigest mdInst = MessageDigest.getInstance("MD5");  
            byte[] btInput = s.getBytes(ENCODEING_TRYPE);  
            mdInst.update(btInput);  
            byte[] md = mdInst.digest();  
            StringBuffer sb = new StringBuffer();  
            for (int i = 0; i < md.length; i++) {  
                int val = ((int) md[i]) & 0xff;  
                if (val < 16)  
                    sb.append("0");  
                sb.append(Integer.toHexString(val));  
  
            }  
            return sb.toString();  
        } catch (Exception e) {  
            return null;  
        }  
    }  
  
}
