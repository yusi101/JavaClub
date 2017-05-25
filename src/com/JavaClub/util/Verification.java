package com.JavaClub.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.SecurityHelper;

import com.JavaClub.entity.Page;

public class Verification {
	private static final String STATUS = "status";
	private static final String TOKEN = "token";
	private static final String KEYNO = "KeyNo";
	private static final String DEVICEID = "deviceId";
	private static final String VERSION = "version";
	private static final String MESSAGE = "message";
	private static final String SUCCESS = "success";
	
    public static Boolean VerificationMd5(PageData pd){
        String token=pd.getString(TOKEN);
        String KeyNo=pd.getString(KEYNO);
        String deviceId=pd.getString(DEVICEID);
        String tokenmd=MD5.md5(KeyNo+deviceId);
        String tokenmd2=MD5.md5(SecurityHelper.getEncrypt(KeyNo)+deviceId);
        if(!tokenmd.equals(token)){
            if(tokenmd2.equals(token)){
                return true;  
            }else{
                return false;    
            }
        }else{
            return true;
        }
    }

    /**
     * 
     * @descript (判断调用接口返回的JSON是否为null 和的返回状态是否为成功)
     * @author 李海涛
     * @since 2016年9月14日上午11:01:11
     * @param map
     * @return
     */
    public static Boolean StatusIsSuccess(Map<String,Object> map){
        if(null==map || map.isEmpty()){
            return false;
        }else{
            if(EnumUtil.STATUS_SUCCESS.getName().equals(map.get(STATUS))){
                return true;
            }else{
                return false;
            }
        }
    }

    /**
     * 
     * @descript 将KeyNo解码，并删除token和deviceId
     * @author 李海涛
     * @since 2016年9月9日上午9:45:58
     * @param pd
     * @param Decode  KeyNo对应的参数名称
     * @return
     */
    public static void DecodeKeyNo(PageData pd,String Decode){
        if(!Decode.trim().equals("")){
            pd.put(Decode, pd.getString(KEYNO));
        }
        pd.remove(KEYNO);
        pd.remove(DEVICEID);
        pd.remove(TOKEN);
    }
    public static void DecodesearchKey(PageData pd,String Decode){
        if(!Decode.trim().equals("")){
            pd.put(Decode, pd.getString("searchKey"));
        }
        pd.remove("searchKey");
        pd.remove(DEVICEID);
        pd.remove(TOKEN);
    }
    /**
     * 
     * @descript 将Encode变成KeyNo,并加上token和deviceId匹配接口的加密规则
     * @author 李海涛
     * @since 2016年9月9日上午9:45:58
     * @param pd
     * @param Encode 需要变成KeyNo的参数
     * @return
     */
    public static void EncodeKeyNo(PageData pd,String Encode){
        pd.put(KEYNO, pd.getString(Encode)==null? "":pd.getString(Encode));
        pd.remove(Encode);
        pd.put(DEVICEID,"123");
        pd.put(TOKEN,MD5.md5(pd.getString(KEYNO)+pd.getString(DEVICEID)));
    }
    /**
     * 
     * @descript (将Encode变成KeyNo,并加上token和deviceId匹配接口的加密规则 生成PDF专用)
     * @author 李文海
     * @since 2016年10月24日下午3:54:53
     * @param pd
     * @param Encode 需要变成KeyNo的参数
     */
    public static void EncodeKeyNoPDF(PageData pd,String Encode){
        pd.put(KEYNO, pd.getString(Encode)==null? "":pd.getString(Encode));
        pd.put(DEVICEID,"123");
        pd.put(TOKEN,MD5.md5(pd.getString(KEYNO)+pd.getString(DEVICEID)));
    }
    public static void EncodesearchKey(PageData pd,String Encode){
        pd.put("searchKey", pd.getString(Encode)==null? "":pd.getString(Encode));
        pd.remove(Encode);
        pd.put(DEVICEID,"123");
        pd.put(TOKEN,MD5.md5(pd.getString(KEYNO)+pd.getString(DEVICEID)));
    }
    public static String VerificationParameter(PageData pd,String Parameter){
        String Parameters[]=Parameter.split("@");
        String Parameternull="";
        for (int i = 0; i < Parameters.length; i++) {
            int status=1;
            for (Object key : pd.keySet()) {

                if(key.equals(Parameters[i])){
                    status=0;
                    break;
                }
            }
            if(status==1){
                Parameternull+=Parameters[i]+",";
            }
        }
        return Parameternull;

    }
    //设置页码
    public static Map<String,Object> getPageMessage(Map<String,Object> map,Page page){
        Map<String,Object> pagemessage=new HashMap<>();
        pagemessage.put("ShowCount",page.getShowCount()+"");//每页显示记录数
        pagemessage.put("CurrentPage", page.getCurrentPage()+"");//当前页
        pagemessage.put("CurrentResult", page.getCurrentResult()+"");//当前记录起始索引
        pagemessage.put("TotalPage",page.getTotalPage()+"");//总页数
        pagemessage.put("TotalResult",page.getTotalResult()+"");//总记录数
        map.put("Paging", pagemessage);
        return map;

    }

    /**
     * 
     * @descript 设置分页的页码和每页显示的条数
     * @author 李海涛
     * @since 2016年9月9日下午12:23:38
     * @param pd
     * @return
     */
    public static PageData setPageParameter(PageData pd){
        try {
            pd.put("pageSize", pd.get("page.showCount")==null ? "10" : pd.get("page.showCount")); 
            pd.put("pageIndex", pd.get("page.currentPage")==null ? "1" : pd.get("page.currentPage")); 
        } catch (Exception e) {}
        return pd;

    }
    
    /**
     * 
     * @descript (企业查询专用)
     * @author 魏旋
     * @since 2017年2月16日上午9:16:22
     * @param pd
     * @return
     */
    public static PageData setPageParameter2(PageData pd){
        try {
            pd.put("pageSize", pd.get("page.showCount")==null ? "50" : pd.get("page.showCount")); 
            pd.put("pageIndex", pd.get("page.currentPage")==null ? "1" : pd.get("page.currentPage")); 
        } catch (Exception e) {}
        return pd;

    }

    /**
     * 
     * @descript 拿到接口中的分页参数，拼接成page
     * @author 李海涛
     * @since 2016年9月9日下午12:19:08
     * @param dataMap
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Page getPage(Map<String,Object> dataMap){
        Map<String,Object> Paging=(Map<String, Object>)dataMap.get("Paging");
        Page page=new Page();
        page.setShowCount(Integer.parseInt(Paging.get("ShowCount").toString()));
        page.setCurrentPage(Integer.parseInt(Paging.get("CurrentPage").toString()));
        page.setCurrentResult(Integer.parseInt(Paging.get("CurrentResult").toString()));
        page.setTotalPage(Integer.parseInt(Paging.get("TotalPage").toString()));
        page.setTotalResult(Integer.parseInt(Paging.get("TotalResult").toString()));
        return page;

    }
    public static Map<String,Object> Success(){
        Map<String,Object> map =new HashMap<>();
        map.put(STATUS, "1");
        map.put(VERSION, Const.VERSION);
        map.put(MESSAGE, SUCCESS);
        return map;

    }

    //缺少参数
    public static Map<String,Object> LackParameter(String Parameter){
        Map<String,Object> map =new HashMap<>();
        map.put(STATUS, "0");
        map.put(VERSION, Const.VERSION);
        map.put(MESSAGE, "参数错误！"+Parameter);
        return map;

    }

    //加密匹配错误
    public static Map<String,Object> Md5Match(){
        Map<String,Object> map =new HashMap<>();
        map.put(STATUS, "200");
        map.put(VERSION, Const.VERSION);
        map.put(MESSAGE, "加密匹配错误!");
        return map;

    }
    //密码重复错误
    public static Map<String,Object> pwdError(){
        Map<String,Object> map =new HashMap<>();
        map.put(STATUS, "3");
        map.put(VERSION, Const.VERSION);
        map.put(MESSAGE, "旧密码错误");
        return map;
    }



    public static Map<String,Object> putDataToList(Object object,String type){
        Map<String,Object> map =new HashMap<>();
        map.put("data", object);
        map.put("type", type);
        return map;
    }

    public static void putDataToList(List<Map<String,Object>> list,Object object,String type){
        Map<String,Object> map =new HashMap<>();
        map.put("data", object);
        map.put("type", type);
        list.add(map);
    }
    /*
     * 错误信息条件判断	
     */
    public static Map<String,Object> ResultMessage(int status){
        Map<String,Object> map =new HashMap<>();
        switch(status){
        case 0:
            map.put(STATUS, "0");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, "参数错误！");
        break;

        case 1:
            map.put(STATUS, "1");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, SUCCESS);
        break;

        case 2:
            map.put(STATUS, "2");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, "用户ID未知");
        break;

        case 3:
            map.put(STATUS, "3");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, "原始密码错误");
        break;
        case 4:
            map.put(STATUS, "4");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, "该手机号码已注册");
        break;
        case 5:
            map.put(STATUS, "5");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, "密码错误");
        break;
        case 6:
            map.put(STATUS, "6");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, "用户不存在");
        break;
        case 7:
            map.put(STATUS, "7");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, "激活失败！您的激活地址有误");
        break;
        case 8:
            map.put(STATUS, "8");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, "验证码过期");
        break;
        case 9:
            map.put(STATUS, "9");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, "验证码错误");
        break;
        case 10:
            map.put(STATUS, "10");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, "短信发送失败");
        break;
        case 11:
            map.put(STATUS, "11");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, "邮件发送失败");
        break;
        case 12:
            map.put(STATUS, "12");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, "该邮箱已注册");
        break;
        case 13:
            map.put(STATUS, "13");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, "邮件发送成功");
        break;
        case 14:
            map.put(STATUS, "14");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, SUCCESS);
        break;
        }
        return map;
    }

    //系统异常
    public static Map<String,Object> ExceptionError(String Exception){
        Map<String,Object> map =new HashMap<>();
        map.put(STATUS, "200");
        map.put(VERSION, Const.VERSION);
        map.put(MESSAGE, "系统异常:"+Exception);
        return map;

    }

    //系统异常
    public static Map<String,String> getResultMap(int status){
        Map<String,String> map =new HashMap<>();
        switch(status){
        case 0:
            map.put(STATUS, "fail");
            map.put(MESSAGE, "失败");
            break;
        case 1:
            map.put(STATUS, SUCCESS);
            map.put(MESSAGE, "成功");
            break;
        default :
            map.put(STATUS, SUCCESS);
            map.put(MESSAGE, "成功");
            break;
        }
        
        return map;

    }
    
  
    public static String getResultString(int status){
        Map<String,String> map =new HashMap<>();
        switch(status){
        case 0:
            map.put(STATUS, "fail");
            map.put(MESSAGE, "失败");
            break;
        case 1:
            map.put(STATUS, SUCCESS);
            map.put(MESSAGE, "成功");
            break;
        case 10:
        	map.put(STATUS, "repeat");
        	map.put(MESSAGE, "模板名不能重复");
            break;
        default :
            map.put(STATUS, SUCCESS);
            map.put(MESSAGE, "成功");
            break;
        }
        
        return MyGson.toJson(map);

    }
    /**
     * 
     * @descript (根据返回的结果，得到状态信息，用于接口的增删改)
     * @author 李海涛
     * @since 2016年9月26日下午3:15:07
     * @param status 数据库返回的影响行数
     * @return
     */
    public static String getMessageByResult(int status){
        Map<String,String> map =new HashMap<>();
        switch(status){
        case 0:
            map.put(STATUS, "0");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, "失败");
            break;
        case 1:
            map.put(STATUS, "1");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, SUCCESS);
            break;
        default :
            map.put(STATUS, "1");
            map.put(VERSION, Const.VERSION);
            map.put(MESSAGE, SUCCESS);
            break;
        }
        
        return MyGson.toJson(map);

    }
    public static void main(String[] args) {
        PageData pd=new PageData();
        pd.put(KEYNO, "1222");
        pd.put("dec", "1222");
        pd.put(TOKEN, "1222");
        pd.put("reg", "1222");
        System.out.println(VerificationParameter(pd, "keyno@dec@toke"));
    }


    public static Page SetPageList(PageData pd,Page page){
        int   pageIndex  =getcodeInt("pageIndex", pd);//搜索请求的的页数 	默认为1
        page.setCurrentPage(pageIndex);
        int    pageSize   =getcodeInt("pageSize", pd);//搜索请求的条数 	默认为10 
        if(pageSize==1 && null==pd.getString("pageSize")){
            pageSize=10;
        }
        page.setShowCount(pageSize);
        page.setPd(pd);
        return page;
    }


    public static String getcode(String code,PageData pd){
        if(pd.getString(code)!=null){
            return pd.getString(code);
        }
        return "";
    }

    public static int  getcodeInt(String code,PageData pd){
        if(pd.getString(code)!=null){
            try {
                return Integer.parseInt( pd.getString(code));
            } catch (Exception e) {
                return 1;
            }
        }
        return 1;
    }
}
