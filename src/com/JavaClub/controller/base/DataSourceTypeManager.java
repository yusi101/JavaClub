/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年10月28日下午5:56:40
 * @version 1.0
 */
package com.JavaClub.controller.base;


/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年10月28日下午5:56:40
 * @version 1.0
 */
public class DataSourceTypeManager {
    private static final ThreadLocal<DataSources> dataSourceTypes = new ThreadLocal<DataSources>(){
        @Override
        protected DataSources initialValue(){
            return DataSources.JX;
        }
    };
     
    public static DataSources get(){
        return dataSourceTypes.get();
    }
     
    public static void set(DataSources dataSourceType){
        dataSourceTypes.set(dataSourceType);
    }
     
    public static void reset(){
        dataSourceTypes.set(DataSources.JX);
    }
}
