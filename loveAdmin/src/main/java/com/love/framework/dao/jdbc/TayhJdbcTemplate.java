package com.love.framework.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * 扩展NamedParameterJdbcTemplate，提供分页的查询方法
 * @author 苏霞
 * @history
 * <TABLE id="HistoryTable" border="1">
 * 	<TR><TD>时间</TD><TD>描述</TD><TD>作者</TD></TR>
 *	<TR><TD>2014-2-25</TD><TD>创建初始版本</TD><TD>苏霞</TD></TR>
 * </TABLE>
 */
public class TayhJdbcTemplate<T> extends NamedParameterJdbcTemplate {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	ColumnMapRowMapper mapper = new ColumnMapRowMapper();

	public TayhJdbcTemplate(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * 覆盖原有方法设置指针类型为TYPE_SCROLL_INSENSITIVE
	 * 
	 * @param sql
	 * @param paramSource
	 * @return
	 */
	protected PreparedStatementCreator getPreparedStatementCreator(String sql, SqlParameterSource paramSource) {
		ParsedSql parsedSql = getParsedSql(sql);
		String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
		Object[] params = NamedParameterUtils.buildValueArray(parsedSql, paramSource, null);
		List<SqlParameter> declaredParameters = NamedParameterUtils.buildSqlParameterList(parsedSql, paramSource);
		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(sqlToUse, declaredParameters);
		pscf.setResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
		return pscf.newPreparedStatementCreator(params);
	}
	
	/**
	 * 使用Query对象进行查询
	 * 
	 * @param query
	 * @return　
	 */
	public Page<T> query(String  sql, Map<String, Object> params, final Page<T> page) {
		logger.debug("Query SQL:{} ", sql);
		logger.debug("Query Parameters:{}", params);
		return this.query(sql, params, new ResultSetExtractor<Page<T>>() {

			@SuppressWarnings("unchecked")
			public Page<T> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<T> result = new ArrayList<T>(); 
				if(page.getFirst() > 1){
					rs.absolute(page.getFirst()-1);
				}
				while (rs.next() && result.size() < page.getPageSize()) {
					result.add((T) mapper.mapRow(rs, result.size()+1));
				}
				page.setTotalCount(rs.getRow());
				page.setResult(result);
				rs.last();
				return page;
			}

		});
	}
}
