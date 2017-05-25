package com.JavaClub.plugin;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import com.JavaClub.util.PropertyUtils;
import com.JavaClub.util.ReflectHelper;
import com.JavaClub.util.StringUtil;
/**
 * 
* @ClassName: PagePlugin 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author XWang
* @date 2016年3月21日 下午5:58:40 
*
 */
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class LoggerPlugin implements Interceptor {

	
	public Object intercept(Invocation ivk) throws Throwable {
		// TODO Auto-generated method stub
		if(ivk.getTarget() instanceof RoutingStatementHandler){
			RoutingStatementHandler statementHandler = (RoutingStatementHandler)ivk.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
			createDbLog(delegate);
		}
		return ivk.proceed();
	}
	
	
	public Map<String,Object>  createDbLog(BaseStatementHandler delegate) throws Exception{
		 //拦截需要分页的SQL
		Map<String,Object>  loginfo = new HashMap<String,Object>();
		BoundSql boundSql = delegate.getBoundSql();
		Object parameterObject = boundSql.getParameterObject();		
		if(parameterObject==null){
			throw new NullPointerException("parameterObject尚未实例化！");
		}else{
			String sql = boundSql.getSql();
			String logType = getLoggerType(sql);
			if(StringUtil.isNotEmpty(logType)){
				loginfo.put("logtype", logType);
				loginfo.put("createdtime", new Date());	
				loginfo.put("sqlinfo", sql);
				loginfo.put("account", sql);
				loginfo.put("ip", sql);
				loginfo.put("address", sql);
				loginfo.put("loginfo", sql);
				}
			}
		
		return loginfo;

	
	}
	
	
	
	
	public String getLoggerType(String sql){
		String logType = PropertyUtils.getPropertyValueByKey("syslog.properties","database.logtype");
		String[] logTypes = StringUtil.sliptTitle(logType, ",");
		for(String type:logTypes){
			if(sql.toUpperCase().startsWith(type.toUpperCase())){
				return type;
			}
		}
		return null;
	}

	@Override
	public Object plugin(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
}
