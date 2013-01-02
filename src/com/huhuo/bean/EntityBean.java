package com.huhuo.bean;

/**
 * 基实体实现类
 * @author wuyuxuan
 */
public class EntityBean implements IEntityBean<Long>{
	
	private static final long serialVersionUID = -1886722766352377056L;
	
	private Long id;
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id=id;
	}
}
