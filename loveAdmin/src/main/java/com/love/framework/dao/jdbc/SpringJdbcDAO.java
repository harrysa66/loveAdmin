package com.love.framework.dao.jdbc;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

public class SpringJdbcDAO {

	@Resource
	protected TayhJdbcTemplate<?> tayhJdbcTemplate;
	
	@Resource
	protected JdbcTemplate jdbcTemplate;

	/**
	 * @return　the TayhJdbcTemplate
	 */
	public TayhJdbcTemplate<?> getTayhJdbcTemplate() {
		return tayhJdbcTemplate;
	}

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}