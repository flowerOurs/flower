package com.huhuo.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.huhuo.bean.City;
import com.huhuo.constant.Constant;
import com.huhuo.dao.CityDao;

@ContextConfiguration(locations = "classpath:appContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstrDBDaoTest {

	@Resource(name = "cityDao")
	private CityDao cityDao;

	@Test
	public void find() throws Exception {
		String sql = "SELECT * FROM w_city LIMIT 0, 20";
		List<City> list = cityDao.queryForList(sql, City.class);
		System.out.println(list);
	}

	@Test
	public void findAll() {
		try {
//			List<City> allCity = cityDao.findAll(City.class);
			List<City> allCity = cityDao.findBeans(City.class, 0, 20);
			System.out.println(JSONObject.toJSONStringWithDateFormat(allCity,
					Constant.GLOBAL_CONFIG_DATEFORMAT));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
