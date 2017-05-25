/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2017年1月5日上午11:28:51
 * @version 1.0
 */
package com.JavaClub.handler;





import java.io.BufferedReader;
import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2017年1月5日上午11:28:51
 * @version 1.0
 */

public class ClobTypeHandler extends BaseTypeHandler<Object> {

	
	@Override
	public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
		try {
			if(rs.getObject(columnName).getClass().getName()!=null && rs.getObject(columnName).getClass().getName().equals("oracle.sql.CLOB")){
				return ClobToString(rs.getClob(columnName));	
			}
			return rs.getObject(columnName);
		} catch (Exception e) {
			return "";
		}
	}
	public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getObject(columnIndex);
	}


	public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getString(columnIndex);
	}
	public static String ClobToString(Clob clob){
		  try {
			  String reString = "";
				Reader is = clob.getCharacterStream();// 得到流
				BufferedReader br = new BufferedReader(is);
				String s = br.readLine();
				StringBuffer sb = new StringBuffer();
				while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
					sb.append(s);
					s = br.readLine();
				}
				reString = sb.toString();
				return reString;
		} catch (Exception e) {
			return null;
		}
		
	}
	public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
	    ps.setObject(i, parameter);
	}
}
