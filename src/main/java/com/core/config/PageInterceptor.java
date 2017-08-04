package com.core.config;


import com.datasource.DBContextHolder;
import com.pageable.FrameworkPageable;
import com.util.MutiDataSourceHelper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class PageInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(PageInterceptor.class);

	public Object intercept(Invocation invocation) throws Throwable {
		RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
		StatementHandler delegate = (StatementHandler) PageInterceptor.ReflectUtil.getFieldValue(handler, "delegate");
		BoundSql boundSql = delegate.getBoundSql();
		Object obj = boundSql.getParameterObject();
		FrameworkPageable page = null;
		if (obj instanceof FrameworkPageable) {
			page = (FrameworkPageable) obj;
		} else if (obj instanceof Map) {
			Iterator sql = ((Map) obj).values().iterator();

			while (sql.hasNext()) {
				Object pageSql = sql.next();
				if (pageSql instanceof FrameworkPageable) {
					page = (FrameworkPageable) pageSql;
					break;
				}
			}
		}

		if (page != null) {
			String sql1 = boundSql.getSql();
			if (page.getTotalRecord() < 0L) {
				this.setTotalRecord(invocation, page);
			}

			String pageSql1 = this.getPageSql(page, sql1);
			PageInterceptor.ReflectUtil.setFieldValue(boundSql, "sql", pageSql1);
		}

		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	}

	private String getDatabaseType() {
		return MutiDataSourceHelper.getDbType(DBContextHolder.getDBType());
	}

	private String getPageSql(FrameworkPageable page, String sql) {
		StringBuffer sqlBuffer = new StringBuffer(sql);
		return "mysql".equalsIgnoreCase(this.getDatabaseType()) ? this.getMysqlPageSql(page, sqlBuffer)
				: ("oracle".equalsIgnoreCase(this.getDatabaseType()) ? this.getOraclePageSql(page, sqlBuffer)
						: sqlBuffer.toString());
	}

	private String getMysqlPageSql(FrameworkPageable page, StringBuffer sqlBuffer) {
		int offset = (page.getPageNo() - 1) * page.getPageSize();
		sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
		return sqlBuffer.toString();
	}

	private String getOraclePageSql(FrameworkPageable page, StringBuffer sqlBuffer) {
		int offset = (page.getPageNo() - 1) * page.getPageSize() + 1;
		sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ")
				.append(offset + page.getPageSize());
		sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
		return sqlBuffer.toString();
	}

	private void setTotalRecord(Invocation invocation, FrameworkPageable page) {
		RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
		StatementHandler delegate = (StatementHandler) PageInterceptor.ReflectUtil.getFieldValue(handler, "delegate");
		BoundSql boundSql = delegate.getBoundSql();
		String sql = boundSql.getSql();
		String countSql = this.getCountSql(sql);
		PageInterceptor.ReflectUtil.setFieldValue(boundSql, "sql", countSql);
		Connection connection = (Connection) invocation.getArgs()[0];
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(countSql);
			delegate.getParameterHandler().setParameters(pstmt);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int e = rs.getInt(1);
				page.setTotalRecord((long) e);
			}
		} catch (SQLException arg18) {
			logger.error("mybatis��ҳ��������ѯ�ܼ�¼������" + arg18.getMessage(), arg18);
			throw new RuntimeException("mybatis��ҳ��������ѯ�ܼ�¼������" + arg18.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException arg17) {
				logger.error(arg17.getMessage(), arg17);
			}

		}

	}

	private String getCountSql(String sql) {
		return "select count(0) from (" + sql + ") tmp";
	}

	private static class ReflectUtil {
		public static Object getFieldValue(Object obj, String fieldName) {
			Object result = null;
			Field field = getField(obj, fieldName);
			if (field != null) {
				field.setAccessible(true);

				try {
					result = field.get(obj);
				} catch (IllegalArgumentException arg4) {
					PageInterceptor.logger.error(arg4.getMessage(), arg4);
				} catch (IllegalAccessException arg5) {
					PageInterceptor.logger.error(arg5.getMessage(), arg5);
				}
			}

			return result;
		}

		private static Field getField(Object obj, String fieldName) {
			Field field = null;
			Class clazz = obj.getClass();

			while (clazz != Object.class) {
				try {
					field = clazz.getDeclaredField(fieldName);
					break;
				} catch (NoSuchFieldException arg4) {
					PageInterceptor.logger.error(arg4.getMessage(), arg4);
					clazz = clazz.getSuperclass();
				}
			}

			return field;
		}

		public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
			Field field = getField(obj, fieldName);
			if (field != null) {
				try {
					field.setAccessible(true);
					field.set(obj, fieldValue);
				} catch (IllegalArgumentException arg4) {
					PageInterceptor.logger.error(arg4.getMessage(), arg4);
				} catch (IllegalAccessException arg5) {
					PageInterceptor.logger.error(arg5.getMessage(), arg5);
				}
			}

		}
	}
}