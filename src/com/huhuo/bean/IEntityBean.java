package com.huhuo.bean;

import java.io.Serializable;

/**
 * 基实体类接口
 * @author wuyuxuan
 * @param <T>
 */
public interface IEntityBean<T> extends Serializable {
	public void setId(T id);
	public T getId();
}
