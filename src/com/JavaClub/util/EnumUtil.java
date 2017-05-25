package com.JavaClub.util;



/**
 * 
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年9月14日上午10:00:06
 * @version 1.0
 */
public enum EnumUtil {
   
    /**
     * 调用成功
     */
    STATUS_SUCCESS("1", "调用成功"),
    
    /**
     * 参数错误
     */
    STATUS_ERROR_PARAMETERS("0", "参数错误"),
    
    /**
     * 0 用户ID未知
     */
    STATUS_ERROR_USERUNKNOWN("2", "用户ID未知"),
    
    /**
     * 原始密码错误
     */
    STATUS_ERROR_OLDPASSWORD("3", "原始密码错误"),
    
    /**
     * 该邮箱已存在
     */
    STATUS_ERROR_EMAILEXIST("4", "该邮箱已存在"),
    /**
     * 密码错误
     */
    STATUS_ERROR_PASSWORD("5", "密码错误"),
    /**
     * 系统异常
     */
    STATUS_ERROR_SYSTEM("200", "系统异常"),
    
    SEARCH_BY_ENTNAME("0", "企业名称搜索"),
    SEARCH_BY_LEGAL("1", "法定代表人名称搜索企业"),
    SEARCH_BY_OPSCOPE("2", "经营范围搜索企业"),
    SEARCH_BY_COURTCASE("3", "失信人搜索企业"),
    SEARCH_BY_NEW("4", "新企业"),
    SEARCH_BRAND("21", "商标搜索"),
    SEARCH_PATENT("22", "专利搜索"),
    SEARCH_WORKCOPYRIGHT("23", "著作权搜索"),
    SEARCH_COPYRIGHT("24", "软件著作权搜索"),
    SEARCH_BIDDING("25", "招投标搜索"),
    SEARCH_COURTCASE("26", "失信人搜索")
    ;  
    private String name;  
    private String value;  
    //自定义的构造函数，参数数量，名字随便自己取  
    //构造器默认也只能是private, 从而保证构造函数只能在内部使用   
    private EnumUtil(String name, String value)  
    {  
        this.name = name;  
        this.value = value;  
    }  
  
    public String getName()  
    {  
        return name;  
    }  
  
    public void setName(String name)  
    {  
        this.name = name;  
    }  
  
    public String getValue()  
    {  
        return value;  
    }  
  
    public void setValue(String value)  
    {  
        this.value = value;  
    }  
    //重新toString方法，默认的toString方法返回的就是枚举变量的名字，和name()方法返回值一样  
    @Override  
    public String toString()  
    {  
        return this.name+":"+this.value;  
          
    }
    
    public static void main(String[] args) {
        System.err.println(EnumUtil.STATUS_ERROR_USERUNKNOWN);
    }
}
