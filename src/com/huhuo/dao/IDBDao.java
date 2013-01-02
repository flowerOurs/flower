package com.huhuo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

/**
 * 基本数据操作
 *@author wuyuxuan
 * @param <T>
 */
public interface IDBDao<T> {

	/**
	 * 添加
	 * @param t
	 * @return
	 * @throws Exception
	 */
	boolean insert(T t) throws Exception;
	/**
	 * 修改
	 * @param t
	 * @return
	 * @throws Exception
	 */
	boolean update(T t) throws Exception;
	/**
	 * 删除
	 * @param t
	 * @return
	 * @throws Exception
	 */
	boolean delete(T t) throws Exception;
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	<PK> boolean deleteById(PK id) throws Exception;
	/**
	 * 根据id查找对象
	 * @param clazz
	 * @param id
	 * @return
	 * @throws Exception
	 */
	<PK> T findById(Class<? extends T> clazz, PK id) throws Exception;
	/**
	 * 查找所有的用户对象
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	List<T> findAll(Class<T> clazz) throws Exception;
	/**
	 * 分页返回对象
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	List<T> findBeans(Class<T> clazz, Integer start, Integer limit) throws Exception;
	/**
	 * 根据sql查询，返回elementType指定的bean
	 * @param sql
	 * @param elementType
	 * @param args
	 * @return
	 * @throws DataAccessException
	 */
	public List<T> queryForList(String sql, Class<T> elementType, Object... args) throws DataAccessException;
}
