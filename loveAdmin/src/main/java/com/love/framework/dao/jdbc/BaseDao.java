package com.love.framework.dao.jdbc;

import com.love.framework.exception.DataStorageException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class BaseDao<T, PK extends Serializable> extends SqlSessionDaoSupport {
	public static final String POSTFIX_INSERT = ".insert";
	public static final String POSTFIX_UPDATE = ".update";
	public static final String POSTFIX_UPDATEBYMAP = ".updateByMap";
	public static final String POSTFIX_DELETEBYID = ".deleteById";
	public static final String POSTFIX_DELETEBYIDS = ".deleteByIds";
	public static final String POSTFIX_DELETEBYMAP = ".deleteByMap";
	public static final String POSTFIX_SELECTBYID = ".selectById";
	public static final String POSTFIX_COUNTBYID = ".countById";
	public static final String POSTFIX_SELECTBYPROPERTY = ".selectByProperty";
	public static final String POSTFIX_SELECTLISTBYPROPERTY = ".selectListByProperty";
	public static final String POSTFIX_SELECTBYMAP = ".selectByMap";
	public static final String POSTFIX_SELECTLISTBYMAP = ".selectListByMap";
	public static final String POSTFIX_COUNTBYPROPERTY = ".countByProperty";
	public static final String POSTFIX_COUNTBYMAP = ".countByMap";
	public static final String POSTFIX_QUERYPAGE = ".queryPage";
	public static final String POSTFIX_QUERYPAGECOUNT = ".queryPageCount";
	protected Class<T> entityClass;
	protected String clazzName;
	protected T t;

	public BaseDao() {
		this.entityClass = ((Class) ((java.lang.reflect.ParameterizedType) super
				.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		this.clazzName = this.entityClass.getSimpleName();
	}

	public BaseDao(SqlSessionFactory sqlSessionFactory, Class<T> entityClass) {
		this.entityClass = entityClass;
		this.clazzName = entityClass.getSimpleName();
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	public Serializable insert(T entity) throws DataStorageException {
		try {
			return Integer.valueOf(getSqlSession().insert(
					this.clazzName + ".insert", entity));
		} catch (Exception e) {
			throw new DataStorageException(0, e);
		}
	}

	public Serializable insert(String sqlStatement, T entity)
			throws DataStorageException {
		try {
			return Integer.valueOf(getSqlSession().insert(
					this.clazzName + "." + sqlStatement, entity));
		} catch (Exception e) {
			throw new DataStorageException(0, e);
		}
	}

	public Serializable insertObject(String sqlStatement, Object obj)
			throws DataStorageException {
		try {
			return Integer.valueOf(getSqlSession().insert(
					this.clazzName + "." + sqlStatement, obj));
		} catch (Exception e) {
			throw new DataStorageException(0, e);
		}
	}

	public T update(T entity) throws DataStorageException {
		try {
			getSqlSession().update(this.clazzName + ".update", entity);
			return entity;
		} catch (Exception e) {
			throw new DataStorageException(1, e);
		}
	}

	public T update(String sqlStatement, T entity) throws DataStorageException {
		try {
			getSqlSession().update(this.clazzName + "." + sqlStatement, entity);
			return entity;
		} catch (Exception e) {
			throw new DataStorageException(1, e);
		}
	}

	public Object updateObject(String sqlStatement, Object obj)
			throws DataStorageException {
		try {
			getSqlSession().update(this.clazzName + "." + sqlStatement, obj);
			return obj;
		} catch (Exception e) {
			throw new DataStorageException(1, e);
		}
	}

	public int update(PK id, String[] properties, Object[] propertyValues)
			throws DataStorageException {
		try {
			Map map = new HashMap();
			for (int i = 0; i < properties.length; ++i) {
				map.put(properties[i], propertyValues[i]);
			}
			map.put("id", id);
			return getSqlSession().update(this.clazzName + ".updateByMap", map);
		} catch (Exception e) {
			throw new DataStorageException(1, e);
		}
	}

	public int update(String sqlStatement, PK id, String[] properties,
			Object[] propertyValues) throws DataStorageException {
		try {
			Map map = new HashMap();
			for (int i = 0; i < properties.length; ++i) {
				map.put(properties[i], propertyValues[i]);
			}
			map.put("id", id);
			return getSqlSession().update(this.clazzName + "." + sqlStatement,
					map);
		} catch (Exception e) {
			throw new DataStorageException(1, e);
		}
	}

	public void deleteById(PK id) throws DataStorageException {
		try {
			getSqlSession().delete(this.clazzName + ".deleteById", id);
		} catch (Exception e) {
			throw new DataStorageException(2, e);
		}
	}

	public void deleteById(String sqlStatement, PK id)
			throws DataStorageException {
		try {
			getSqlSession().delete(this.clazzName + "." + sqlStatement, id);
		} catch (Exception e) {
			throw new DataStorageException(2, e);
		}
	}

	public void deleteByIds(List<PK> ids) throws DataStorageException {
		try {
			getSqlSession().delete(this.clazzName + ".deleteByIds", ids);
		} catch (Exception e) {
			throw new DataStorageException(2, e);
		}
	}

	public void deleteByIds(String sqlStatement, List<PK> ids)
			throws DataStorageException {
		try {
			getSqlSession().delete(this.clazzName + "." + sqlStatement, ids);
		} catch (Exception e) {
			throw new DataStorageException(2, e);
		}
	}

	public void deleteByIds(PK[] ids) throws DataStorageException {
		try {
			getSqlSession().delete(this.clazzName + ".deleteByIds", ids);
		} catch (Exception e) {
			throw new DataStorageException(2, e);
		}
	}

	public void deleteByIds(String sqlStatement, PK[] ids)
			throws DataStorageException {
		try {
			getSqlSession().delete(this.clazzName + "." + sqlStatement, ids);
		} catch (Exception e) {
			throw new DataStorageException(2, e);
		}
	}

	public int deleteByMap(Map<String, Object> map) throws DataStorageException {
		try {
			return getSqlSession().delete(this.clazzName + ".deleteByMap", map);
		} catch (Exception e) {
			throw new DataStorageException(2, e);
		}
	}

	public int deleteByMap(String sqlStatement, Map<String, Object> map)
			throws DataStorageException {
		try {
			return getSqlSession().delete(this.clazzName + "." + sqlStatement,
					map);
		} catch (Exception e) {
			throw new DataStorageException(2, e);
		}
	}

	public T findById(PK id) {
		return getSqlSession().selectOne(this.clazzName + ".selectById", id);
	}

	public T findById(String sqlStatement, PK id) {
		return getSqlSession().selectOne(this.clazzName + "." + sqlStatement,
				id);
	}

	public T findByProperty(Object property) {
		return getSqlSession().selectOne(this.clazzName + ".selectByProperty",
				property);
	}

	public T findByProperty(String sqlStatement, Object property) {
		return getSqlSession().selectOne(this.clazzName + "." + sqlStatement,
				property);
	}

	public List<T> findListByProperty(Object property) {
		return getSqlSession().selectList(
				this.clazzName + ".selectListByProperty", property);
	}

	public List<T> findListByProperty(String sqlStatement, Object property) {
		return getSqlSession().selectList(this.clazzName + "." + sqlStatement,
				property);
	}

	public T findByMap(Map map) {
		if (map == null) {
			return getSqlSession().selectOne(this.clazzName + ".selectByMap");
		}
		return getSqlSession().selectOne(this.clazzName + ".selectByMap", map);
	}

	public T findByMap(String sqlStatement, Map map) {
		if (map == null) {
			return getSqlSession().selectOne(
					this.clazzName + "." + sqlStatement);
		}
		return getSqlSession().selectOne(this.clazzName + "." + sqlStatement,
				map);
	}

	public List<T> findListByMap(Map map) {
		if (map == null) {
			return getSqlSession().selectList(
					this.clazzName + ".selectListByMap");
		}
		return getSqlSession().selectList(this.clazzName + ".selectListByMap",
				map);
	}

	public List<T> findListByMap(String sqlStatement, Map map) {
		if (map == null) {
			return getSqlSession().selectList(
					this.clazzName + "." + sqlStatement);
		}
		return getSqlSession().selectList(this.clazzName + "." + sqlStatement,
				map);
	}

	public Integer countById(PK id) {
		return ((Integer) getSqlSession().selectOne(
				this.clazzName + ".countById", id));
	}

	public Integer countById(String sqlStatement, PK id) {
		return ((Integer) getSqlSession().selectOne(
				this.clazzName + "." + sqlStatement, id));
	}

	public Integer countByProperty(Object property) {
		return ((Integer) getSqlSession().selectOne(
				this.clazzName + ".countByProperty", property));
	}

	public Integer countByProperty(String sqlStatement, Object property) {
		return ((Integer) getSqlSession().selectOne(
				this.clazzName + "." + sqlStatement, property));
	}

	public Integer countByMap(Map map) {
		if (map == null) {
			return ((Integer) getSqlSession().selectOne(
					this.clazzName + ".countByMap"));
		}
		return ((Integer) getSqlSession().selectOne(
				this.clazzName + ".countByMap", map));
	}

	public Integer countByMap(String sqlStatement, Map map) {
		if (map == null) {
			return ((Integer) getSqlSession().selectOne(
					this.clazzName + "." + sqlStatement));
		}
		return ((Integer) getSqlSession().selectOne(
				this.clazzName + "." + sqlStatement, map));
	}

	public Integer queryPageCount(Map map) {
		if (map == null) {
			return ((Integer) getSqlSession().selectOne(
					this.clazzName + ".queryPageCount"));
		}
		return ((Integer) getSqlSession().selectOne(
				this.clazzName + ".queryPageCount", map));
	}

	public Integer queryPageCount(String sqlStatement, Map map) {
		if (map == null) {
			return ((Integer) getSqlSession().selectOne(
					this.clazzName + "." + sqlStatement));
		}
		return ((Integer) getSqlSession().selectOne(
				this.clazzName + "." + sqlStatement, map));
	}

	public Page<T> pageQueryBy(Map<String, Object> map, Page<T> page) {
		page.setTotalCount(Long.valueOf(queryPageCount(map).toString())
				.longValue());
		if (map == null) {
			map = new HashMap();
		}
		map.put("orderBy", page.getOrderBy());
		map.put("order", page.getOrder());
		page.setResult((List<T>) getSqlSession().selectList(
				this.clazzName + ".queryPage",
				map,
				new RowBounds(page.pageSize * (page.pageNumber - 1),
						page.pageSize)));
		return page;
	}

	public Page<T> pageQueryBy(String sqlStatement, Map<String, Object> map,
			Page<T> page) {
		page.setTotalCount(Long.valueOf(
				queryPageCount(sqlStatement + "Count", map).toString())
				.longValue());
		if (map == null) {
			map = new HashMap();
		}
		map.put("orderBy", page.getOrderBy());
		map.put("order", page.getOrder());
		page.setResult((List<T>) getSqlSession().selectList(
				this.clazzName + "." + sqlStatement,
				map,
				new RowBounds(page.pageSize * (page.pageNumber - 1),
						page.pageSize)));
		return page;
	}

	public Integer queryPageCount(Object obj) {
		if (obj == null) {
			return ((Integer) getSqlSession().selectOne(
					this.clazzName + ".queryPageCount"));
		}
		return ((Integer) getSqlSession().selectOne(
				this.clazzName + ".queryPageCount", obj));
	}

	public Page<T> pageQueryBy(Object obj, Page<T> page) {
		page.setTotalCount(Long.valueOf(queryPageCount(obj).toString())
				.longValue());
		page.setResult((List<T>) getSqlSession().selectList(
				this.clazzName + ".queryPage",
				obj,
				new RowBounds(page.pageSize * (page.pageNumber - 1),
						page.pageSize)));
		return page;
	}

	public Integer queryPageCount(String sqlStatement, Object obj) {
		if (obj == null) {
			return ((Integer) getSqlSession().selectOne(
					this.clazzName + "." + sqlStatement));
		}
		return ((Integer) getSqlSession().selectOne(
				this.clazzName + "." + sqlStatement, obj));
	}

	public Page<T> pageQueryBy(String sqlStatement, Object obj, Page<T> page) {
		page.setTotalCount(Long.valueOf(
				queryPageCount(sqlStatement + "Count", obj).toString())
				.longValue());
		page.setResult((List<T>) getSqlSession().selectList(
				this.clazzName + "." + sqlStatement,
				obj,
				new RowBounds(page.pageSize * (page.pageNumber - 1),
						page.pageSize)));
		return page;
	}
}