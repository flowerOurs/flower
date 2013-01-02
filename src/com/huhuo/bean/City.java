package com.huhuo.bean;

public class City extends EntityBean {

	private static final long serialVersionUID = -6822805495823797019L;

	private String city;
	
	private String province;
	
	private Integer pid;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
}
