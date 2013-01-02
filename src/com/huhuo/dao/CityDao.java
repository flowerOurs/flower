package com.huhuo.dao;

import org.springframework.stereotype.Repository;

import com.huhuo.bean.City;

@Repository("cityDao")
public class CityDao extends AbstrDBDao<City> {

	@Override
	public String getTableName() {
		return "w_city";
	}
	
}
