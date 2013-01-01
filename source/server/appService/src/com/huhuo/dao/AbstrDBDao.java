package com.huhuo.dao;


import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.huhuo.bean.IEntityBean;
import com.huhuo.util.BeanHelper;

/**
 * @author wuyuxuan
 * @param <T>
 */
public abstract class AbstrDBDao<T extends IEntityBean<?>> implements IDBDao<T>{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	protected SimpleJdbcInsert simpleJdbcInsert;
	
	protected DataSource dataSource;
	
	protected String tableName;
	
	public abstract String getTableName();
	
	/**
	 * Return the DataSource used by this template.
	 */
	public DataSource getDataSource() {
		return this.jdbcTemplate.getDataSource();
	}

	@Override
	public boolean insert(T bean) throws Exception{
		BeanHelper.GetterSetter[] getterSetterArray = BeanHelper.getGetterSetter(bean.getClass());
		final StringBuffer sb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append("INSERT INTO ").append(getTableName()).append("(");
		boolean first = true;
		for(final BeanHelper.GetterSetter gs : getterSetterArray){
			if(!first){
				sb.append(",");
			}else{
				first=false;
			}
			sb.append(gs.propertyName);
			Object value = null;
			try{
				value = gs.getter.invoke(bean);
			}catch(Exception e){
				logger.warn(null,e);
			}
			values.add(value==null?gs.getter.getDefaultValue():value);
		}
		sb.append(") values(");
		for(int i=0;i<values.size();i++){
			sb.append("?");
			if(i<values.size()-1)
				sb.append(",");
		}
		sb.append(")");
		final Object[] objects = values.toArray(new Object[values.size()]);
		return jdbcTemplate.update(sb.toString(), objects) == 1;
	}
	
	@Override
	public boolean update(T bean) throws DataAccessException{
		BeanHelper.GetterSetter[] getterSetterArray = BeanHelper.getGetterSetter(bean.getClass());
		final StringBuffer sb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append("UPDATE ").append(getTableName()).append(" SET ");
		boolean first = true;
		for(final BeanHelper.GetterSetter gs : getterSetterArray){
			if(!first){
				sb.append(",");
			}else{
				first=false;
			}
			sb.append(gs.propertyName+"=?");
			Object value = null;
			try{
				value = gs.getter.invoke(bean);
			}catch(Exception e){
				logger.warn(null,e);
			}
			values.add(value==null?gs.getter.getDefaultValue():value);
		}
		sb.append(" WHERE id=?");
		values.add(bean.getId());
		final Object[] objects = values.toArray(new Object[values.size()]);
		return jdbcTemplate.update(sb.toString(),objects)==1;
	}

	@Override
	public boolean delete(T t) throws Exception {
		if(t!=null)
			return deleteById(t.getId());
		else
			return false;
	}
	
	public boolean deleteById(Long id) throws Exception{
		String sql = String.format("DELETE FROM %s WHERE id=?", getTableName());
		return jdbcTemplate.update(sql, id) == 1;
	}
	
	public T findById(final Class<? extends T> clazz, Long id) throws Exception {
		String sql = String.format("SELECT * FROM %s WHERE id=?", getTableName());
		return jdbcTemplate.queryForObject(sql, clazz, id);
	}
	
	@Override
	public <PK> boolean deleteById(PK id) throws Exception {
		return deleteById(id);
	}

	@Override
	public <PK> T findById(Class<? extends T> clazz, PK id) throws Exception {
		return findById(clazz, id);
	}

	@Override
	public List<T> findAll(Class<T> clazz) throws Exception {
		return findBeans(clazz, null, null);
	}
	
	@Override
	public List<T> findBeans(Class<T> clazz, Integer start, Integer limit) throws Exception {
		String sql;
		if(start!=null && limit!=null) {
			sql = String.format("SELECT * FROM %s ORDER BY id DESC LIMIT %s, %s", getTableName(), start, limit);
		} else {
			sql = String.format("SELECT * FROM %s ORDER BY id DESC", getTableName());
		}
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(clazz));
	}

	@Override
	public List<T> queryForList(String sql, Class<T> elementType,
			Object... args) throws DataAccessException {
		return jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<T>(elementType));
	}
	
}
