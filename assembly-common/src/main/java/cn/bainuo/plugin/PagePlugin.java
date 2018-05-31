package cn.bainuo.plugin;

import cn.bainuo.util.Tools;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import javax.xml.bind.PropertyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


/**
 * 
 * 类名称：PagePlugin.java 类描述：
 * 
 * @version 1.0
 * @Intercepts 是mybaits的拦截器注解
 * @Signature 表明要拦截的接口、方法以及对应的参数类型。
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class ,Integer.class}) })
public class PagePlugin implements Interceptor {

	private static String dialect = ""; // 数据库方言
	private static String pageSqlId = ""; // mapper.xml中需要拦截的ID(正则匹配)

	public Object intercept(Invocation ivk) throws Throwable {
		if (ivk.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler,
					"delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate,
					"mappedStatement");

			if (mappedStatement.getId().matches(pageSqlId)) { // 拦截需要分页的SQL
				BoundSql boundSql = delegate.getBoundSql();
				// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				Object parameterObject = boundSql.getParameterObject();
				if (parameterObject == null) {
					throw new NullPointerException("parameterObject尚未实例化！");
				} else {
					Connection connection = (Connection) ivk.getArgs()[0];
					String sql = boundSql.getSql();
					// String countSql = "select count(0) from (" + sql+ ") as
					// tmp_count"; //记录统计
					String countSql = "select "+PageTotalConstants.getPagaTotalSql(mappedStatement.getId())+" count(0) as cnt from (" + sql + ")  tmp_count"; // 记录统计
																						// ==
																						// oracle
																						// 加
																						// as
																						// 报错(SQL
																						// command
																						// not
																						// properly
																						// ended)
					PreparedStatement countStmt = connection.prepareStatement(countSql);
					BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
							boundSql.getParameterMappings(), parameterObject);
					setParameters(countStmt, mappedStatement, countBS, parameterObject);
					ResultSet rs = countStmt.executeQuery();
					int count = 0;
					ParameterMap parameMap = null;

					if (rs.next()) {
						count = rs.getInt("cnt");
						if(parameterObject instanceof ParameterMap){
							parameMap = (ParameterMap) parameterObject;
							doPageParm(parameMap,count);
							PageTotalConstants.setPagaTotal(mappedStatement.getId(),parameMap,rs);
						}
					}

					rs.close();
					countStmt.close();
					// System.out.println(count);

					String pageSql = generatePageSql(sql, parameMap);
					ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql); // 将分页sql语句反射回BoundSql.
				}
			}
		}
		return ivk.proceed();
	}

	/**
	 * 将总记录数封装到参数里面
	 * @param parameterMap
	 * @param count 总数
     */
	public void doPageParm(ParameterMap parameterMap ,int count){
			//如果总页数
			Object shwoCount = parameterMap.get(PageConstants.SHOW_COUNT_KEY);
			Integer show = PageConstants.INIT_SHOW_COUNT;
			if(shwoCount!=null&& StringUtils.isNumeric(String.valueOf(show))){
				show = Integer.parseInt(String.valueOf(shwoCount));
			}
			parameterMap.put(PageConstants.SHOW_COUNT_KEY,show);
			Integer currentPage = PageConstants.INIT_CURRENT_PAGE;
			Object curreantString = parameterMap.get(PageConstants.CURRENT_PAGE_KEY);
			if(curreantString!=null&& StringUtils.isNumeric(String.valueOf(curreantString))){
				currentPage = Integer.parseInt(String.valueOf(curreantString));
			}
			parameterMap.put(PageConstants.CURRENT_PAGE_KEY,currentPage);

			//总页数
			Integer totalPage = PageConstants.getTotalPage(count, show);
			parameterMap.put(PageConstants.TOTAL_PAGE_KEY,totalPage);
			//当前索引号
			Integer currentResult = PageConstants.getCurrentResult(currentPage, show);
			parameterMap.put(PageConstants.CURRENT_RESULT_KEY,currentResult);
			//总记录数
			parameterMap.put(PageConstants.TOTAL_RESULT_KEY,count);

	}


	/**
	 * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
	 * 
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
							&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value)
									.getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName
								+ " of statement " + mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}

	/**
	 * 根据数据库方言，生成特定的分页sql
	 * 
	 * @param sql
	 * @param parameMap
	 * @return
	 */
	private String generatePageSql(String sql, ParameterMap parameMap) {
		if (parameMap != null && Tools.notEmpty(dialect)) {
			StringBuffer pageSql = new StringBuffer();
			if ("mysql".equals(dialect)) {
				pageSql.append(sql);
				pageSql.append(" limit " +parameMap.get(PageConstants.CURRENT_RESULT_KEY) + "," + parameMap.get(PageConstants.SHOW_COUNT_KEY));
			} else if ("oracle".equals(dialect)) {
				pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
				pageSql.append(sql);
				// pageSql.append(") as tmp_tb where ROWNUM<=");
				pageSql.append(") tmp_tb where ROWNUM<=");
				pageSql.append(parameMap.get(PageConstants.CURRENT_RESULT_KEY)).append("+").append( parameMap.get(PageConstants.SHOW_COUNT_KEY));
				pageSql.append(") where row_id>");
				pageSql.append(parameMap.get(PageConstants.CURRENT_RESULT_KEY));
			}
			return pageSql.toString();
		} else {
			return sql;
		}
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (Tools.isEmpty(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
		pageSqlId = p.getProperty("pageSqlId");
		if (Tools.isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}

}
