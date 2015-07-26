package com.love.framework.dao.jdbc;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.love.framework.exception.DataStorageException;

/**
 * mybatis通用dao
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BaseDao<T, PK extends Serializable> extends SqlSessionDaoSupport{
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
		// 通过泛型反射，取得在子类中定义的class.
		entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		clazzName = entityClass.getSimpleName();
	}
	
	public BaseDao(final SqlSessionFactory sqlSessionFactory, final Class<T> entityClass) {
		// 通过泛型反射，取得在子类中定义的class.
		this.entityClass = entityClass;
		clazzName = entityClass.getSimpleName();
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	/**
	 * 新增对象  (insert)
	 * @param entity
	 * @return
	 * @throws DataStorageException 
	 * @throws Exception
	 */
	public Serializable insert(T entity) throws DataStorageException {
		try{
			return (Serializable) getSqlSession().insert(this.clazzName	+ POSTFIX_INSERT, entity);
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.INSERT_ERROR, e);
		}
	}
	
	/**
	 * 新增对象
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Serializable insert(String sqlStatement, T entity) throws DataStorageException {
		try{
			return (Serializable) getSqlSession().insert(this.clazzName	+ "." + sqlStatement, entity);
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.INSERT_ERROR, e);
		}
	}
	
	/**
	 * 新增对象
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Serializable insertObject(String sqlStatement, Object obj) throws DataStorageException {
		try{
			return (Serializable) getSqlSession().insert(this.clazzName	+ "." + sqlStatement, obj);
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.INSERT_ERROR, e);
		}
	}
	
	/**
	 * 更新对象  (update)
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public T update(T entity) throws DataStorageException {
		try{
			getSqlSession().update(this.clazzName + POSTFIX_UPDATE, entity);
			return entity;
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.UPDATE_ERROR, e);
		}
	}
	
	/**
	 * 更新对象
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public T update(String sqlStatement, T entity) throws DataStorageException {
		try{
			getSqlSession().update(this.clazzName +  "." + sqlStatement, entity);
			return entity;
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.UPDATE_ERROR, e);
		}
	}
	
	/**
	 * 更新对象
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Object updateObject(String sqlStatement, Object obj) throws DataStorageException {
		try{
			getSqlSession().update(this.clazzName +  "." + sqlStatement, obj);
			return obj;
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.UPDATE_ERROR, e);
		}
	}

	/**
	 * 更新对象的部分属性  (updateByMap)
	 * @param id
	 * @param properties
	 * @param propertyValues
	 * @return
	 * @throws Exception
	 */
	public int update(PK id, String[] properties, Object[] propertyValues) throws DataStorageException {
		try{
			// 更新数据库
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < properties.length; i++) {
				map.put(properties[i], propertyValues[i]);
			}
			map.put("id", id);
			return getSqlSession().update(this.clazzName + POSTFIX_UPDATEBYMAP, map);
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.UPDATE_ERROR, e);
		}
	}
	
	/**
	 * 更新对象的部分属性
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param id
	 * @param properties
	 * @param propertyValues
	 * @return
	 * @throws Exception
	 */
	public int update(String sqlStatement, PK id, String[] properties, Object[] propertyValues) throws DataStorageException {
		try{
			// 更新数据库
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < properties.length; i++) {
				map.put(properties[i], propertyValues[i]);
			}
			map.put("id", id);
			return getSqlSession().update(this.clazzName + "." + sqlStatement, map);
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.UPDATE_ERROR, e);
		}
	}

	/**
	 * 根据ID删除对象  (deleteById)
	 * @param id
	 * @throws Exception
	 */
	public void deleteById(PK id) throws DataStorageException {
		try{
			getSqlSession().delete(this.clazzName + POSTFIX_DELETEBYID,	id);
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.DELETE_ERROR, e);
		}
	}
	
	/**
	 * 根据ID删除对象
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param id
	 * @throws Exception
	 */
	public void deleteById(String sqlStatement, PK id) throws DataStorageException {
		try{
			getSqlSession().delete(this.clazzName + "." + sqlStatement,	id);
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.DELETE_ERROR, e);
		}
	}
	
	/**
	 * 根据ID删除对象  (deleteByIds)
	 * @param ids
	 * @throws Exception
	 */
	public void deleteByIds(List<PK> ids) throws DataStorageException {
		try{
			getSqlSession().delete(this.clazzName + POSTFIX_DELETEBYIDS, ids);
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.DELETE_ERROR, e);
		}
	}
	
	/**
	 * 根据ID删除对象
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param ids
	 * @throws Exception
	 */
	public void deleteByIds(String sqlStatement, List<PK> ids) throws DataStorageException {
		try{
			getSqlSession().delete(this.clazzName + "." + sqlStatement, ids);
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.DELETE_ERROR, e);
		}
	}
	
	/**
	 * 根据ID删除对象  (deleteByIds)
	 * @param ids
	 * @throws Exception
	 */
	public void deleteByIds(PK[] ids) throws DataStorageException {
		try{
			getSqlSession().delete(this.clazzName + POSTFIX_DELETEBYIDS, ids);
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.DELETE_ERROR, e);
		}
	}
	
	/**
	 * 根据ID删除对象
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param ids
	 * @throws Exception
	 */
	public void deleteByIds(String sqlStatement, PK[] ids) throws DataStorageException {
		try{
			getSqlSession().delete(this.clazzName + "." + sqlStatement, ids);
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.DELETE_ERROR, e);
		}
	}

	/**
	 * 根据条件删除对象  (deleteByMap)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int deleteByMap(Map<String, Object> map) throws DataStorageException {
		try{
			return getSqlSession().delete(this.clazzName	+ POSTFIX_DELETEBYMAP, map);
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.DELETE_ERROR, e);
		}
	}
	
	/**
	 * 根据条件删除对象
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int deleteByMap(String sqlStatement, Map<String, Object> map) throws DataStorageException {
		try{
			return getSqlSession().delete(this.clazzName	+ "." + sqlStatement, map);
		}catch(Exception e){
			throw new DataStorageException(DataStorageException.DELETE_ERROR, e);
		}
	}

	/**
	 * 通过id得到实体对象  (selectById)
	 * @param id
	 * @return
	 */
	public T findById(PK id) {
		return (T) getSqlSession().selectOne(this.clazzName + POSTFIX_SELECTBYID, id);
	}
	
	/**
	 * 通过id得到实体对象
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param id
	 * @return
	 */
	public T findById(String sqlStatement, PK id) {
		return (T) getSqlSession().selectOne(this.clazzName + "." + sqlStatement, id);
	}
	
	/**
	 * 通过某个property属性得到单一实体对象  (selectByProperty)
	 * @param property 属性参数
	 * @return
	 */
	public T findByProperty(Object property) {
		return (T) getSqlSession().selectOne(this.clazzName + POSTFIX_SELECTBYPROPERTY, property);
	}
	
	/**
	 * 通过某个property属性得到单一实体对象
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param property
	 * @return
	 */
	public T findByProperty(String sqlStatement, Object property) {
		return (T) getSqlSession().selectOne(this.clazzName + "." + sqlStatement, property);
	}
	
	/**
	 * 通过某个property属性得到实体对象List  (selectListByProperty)
	 * @param property 属性参数
	 * @return
	 */
	public List<T> findListByProperty(Object property) {
		return (List<T>) getSqlSession().selectList(this.clazzName + POSTFIX_SELECTLISTBYPROPERTY, property);
	}
	
	/**
	 * 通过某个property属性得到实体对象List
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param property
	 * @return
	 */
	public List<T> findListByProperty(String sqlStatement, Object property) {
		return (List<T>) getSqlSession().selectList(this.clazzName + "." + sqlStatement, property);
	}
	
	/**
	 * 通过map得到实体对象  (selectByMap)
	 * @param map 参数
	 * @return
	 */
	public T findByMap(Map map) {
		if(map == null){
			return (T) getSqlSession().selectOne(this.clazzName + POSTFIX_SELECTBYMAP);
		} else {
			return (T) getSqlSession().selectOne(this.clazzName + POSTFIX_SELECTBYMAP, map);
		}
	}
	
	/**
	 * 通过map得到实体对象
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param map
	 * @return
	 */
	public T findByMap(String sqlStatement, Map map) {
		if(map == null){
			return (T) getSqlSession().selectOne(this.clazzName + "." + sqlStatement);
		} else {
			return (T) getSqlSession().selectOne(this.clazzName + "." + sqlStatement, map);
		}
	}
	
	/**
	 * 通过map得到实体对象List  (selectListByMap)
	 * @param map 参数
	 * @return
	 */
	public List<T> findListByMap(Map map) {
		if(map == null){
			return (List<T>) getSqlSession().selectList(this.clazzName + POSTFIX_SELECTLISTBYMAP);
		} else {
			return (List<T>) getSqlSession().selectList(this.clazzName + POSTFIX_SELECTLISTBYMAP, map);
		}
	}
	
	/**
	 * 通过map得到实体对象List
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param map
	 * @return
	 */
	public List<T> findListByMap(String sqlStatement, Map map) {
		if(map == null){
			return (List<T>) getSqlSession().selectList(this.clazzName + "." + sqlStatement);
		} else {
			return (List<T>) getSqlSession().selectList(this.clazzName + "." + sqlStatement, map);
		}
	}
	
	/**
	 * 根据id得到记录数  (countById)
	 * @param id 
	 * @return
	 */
	public Integer countById(PK id) {
		return getSqlSession().selectOne(this.clazzName + POSTFIX_COUNTBYID, id);
	}
	
	/**
	 * 根据id得到记录数  (countById)
	 * @param id 
	 * @return
	 */
	public Integer countById(String sqlStatement, PK id) {
		return getSqlSession().selectOne(this.clazzName + "." + sqlStatement, id);
	}
	
	/**
	 * 根据某个property属性得到记录数  (countByProperty)
	 * @param property 属性参数
	 * @return
	 */
	public Integer countByProperty(Object property) {
		return getSqlSession().selectOne(this.clazzName + POSTFIX_COUNTBYPROPERTY, property);
	}
	
	/**
	 * 根据某个property属性得到记录数
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param property
	 * @return
	 */
	public Integer countByProperty(String sqlStatement, Object property) {
		return getSqlSession().selectOne(this.clazzName + "." + sqlStatement, property);
	}
	
	/**
	 * 通过map得到记录数  (countByMap)
	 * @param map 参数
	 * @return
	 */
	public Integer countByMap(Map map) {
		if(map == null){
			return getSqlSession().selectOne(this.clazzName + POSTFIX_COUNTBYMAP);
		} else {
			return getSqlSession().selectOne(this.clazzName + POSTFIX_COUNTBYMAP, map);
		}
	}
	
	/**
	 * 通过map得到记录数
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param map
	 * @return
	 */
	public Integer countByMap(String sqlStatement, Map map) {
		if(map == null){
			return getSqlSession().selectOne(this.clazzName + "." + sqlStatement);
		} else {
			return getSqlSession().selectOne(this.clazzName + "." + sqlStatement, map);
		}
	}
	
	/**
	 * 通过map得到记录数  (queryPageCount)
	 * @param map 参数
	 * @return
	 */
	public Integer queryPageCount(Map map) {
		if(map == null){
			return getSqlSession().selectOne(this.clazzName + POSTFIX_QUERYPAGECOUNT);
		} else {
			return getSqlSession().selectOne(this.clazzName + POSTFIX_QUERYPAGECOUNT, map);
		}
	}
	
	/**
	 * 通过map得到记录数
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param map
	 * @return
	 */
	public Integer queryPageCount(String sqlStatement, Map map) {
		if(map == null){
			return getSqlSession().selectOne(this.clazzName + "." + sqlStatement);
		} else {
			return getSqlSession().selectOne(this.clazzName + "." + sqlStatement, map);
		}
	}

	/**
	 * 分页查询  (queryPage)
	 * @param map 参数
	 * @param page 分页对象
	 * @return
	 */
	public Page<T> pageQueryBy(Map<String, Object> map, Page<T> page) {
		page.setTotalCount(Long.valueOf(queryPageCount(map).toString()));
		if(map == null){
			map = new HashMap<String, Object>();
		}
		map.put("orderBy", page.getOrderBy());
		map.put("order", page.getOrder());
		page.setResult((List<T>) getSqlSession().selectList(this.clazzName + POSTFIX_QUERYPAGE, map, new RowBounds(page.pageSize*(page.pageNumber-1), page.pageSize)));
		return page ;
	}
	
	/**
	 * 分页查询
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param map
	 * @param page
	 * @return
	 */
	public Page<T> pageQueryBy(String sqlStatement, Map<String, Object> map, Page<T> page) {
		page.setTotalCount(Long.valueOf(queryPageCount(sqlStatement + "Count", map).toString()));
		if(map == null){
			map = new HashMap<String, Object>();
		}
		map.put("orderBy", page.getOrderBy());
		map.put("order", page.getOrder());
		page.setResult((List<T>) getSqlSession().selectList(this.clazzName + "." + sqlStatement, map, new RowBounds(page.pageSize*(page.pageNumber-1), page.pageSize)));
		return page ;
	}
	
	/**
	 * 通过obj得到记录数  (queryPageCount)
	 * @param obj 参数
	 * @return
	 */
	public Integer queryPageCount(Object obj) {
		if(obj == null){
			return getSqlSession().selectOne(this.clazzName + POSTFIX_QUERYPAGECOUNT);
		} else {
			return getSqlSession().selectOne(this.clazzName + POSTFIX_QUERYPAGECOUNT, obj);
		}
	}
	
	/**
	 * 分页查询  (queryPage)
	 * @param obj 参数
	 * @param page 分页对象
	 * @return
	 */
	public Page<T> pageQueryBy(Object obj, Page<T> page) {
		page.setTotalCount(Long.valueOf(queryPageCount(obj).toString()));
		page.setResult((List<T>) getSqlSession().selectList(this.clazzName + POSTFIX_QUERYPAGE, obj, new RowBounds(page.pageSize*(page.pageNumber-1), page.pageSize)));
		return page ;
	}
	
	/**
	 * 通过map得到记录数
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param map
	 * @return
	 */
	public Integer queryPageCount(String sqlStatement, Object obj) {
		if(obj == null){
			return getSqlSession().selectOne(this.clazzName + "." + sqlStatement);
		} else {
			return getSqlSession().selectOne(this.clazzName + "." + sqlStatement, obj);
		}
	}
	
	/**
	 * 分页查询
	 * @param sqlStatement mybatis文件中sql mapper 的id
	 * @param map
	 * @param page
	 * @return
	 */
	public Page<T> pageQueryBy(String sqlStatement, Object obj, Page<T> page) {
		page.setTotalCount(Long.valueOf(queryPageCount(sqlStatement + "Count", obj).toString()));
		page.setResult((List<T>) getSqlSession().selectList(this.clazzName + "." + sqlStatement, obj, new RowBounds(page.pageSize*(page.pageNumber-1), page.pageSize)));
		return page ;
	}
}