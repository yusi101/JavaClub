/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年10月28日下午5:59:38
 * @version 1.0
 */
package com.JavaClub.controller.base;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年10月28日下午5:59:38
 * @version 1.0
 */
@Aspect    // for aop
@Component // for auto scan
public class DataSourceInterceptor { 
    
    @Pointcut("execution(public * com.JavaClub.service.*..*(..))")
    public void dataSourceSlave(){};
     
    @Before("dataSourceSlave()")
    public void before(JoinPoint jp) {
        DataSourceTypeManager.set(DataSources.JX);
        try {
            for (int i = 0; i < jp.getArgs().length; i++) {
                if(jp.getArgs()[i].getClass() !=null){
                    if(Page.class.equals(jp.getArgs()[i].getClass())){
                        Page page=(Page) jp.getArgs()[i];
                        if("41".equals(page.getPd().getString("provinceCode"))){
                            DataSourceTypeManager.set(DataSources.HN);
                        }
                    }else if(PageData.class.equals(jp.getArgs()[i].getClass())){
                        if("41".equals(((PageData)jp.getArgs()[i]).getString("provinceCode"))){
                            DataSourceTypeManager.set(DataSources.HN);
                        }
                    } 
                }
                 
             }  
        } catch (Exception e) {
        }
        
    }
}
