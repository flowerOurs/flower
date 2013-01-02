package com.huhuo.constant;


/**
 * 管理配置信息
 * @author wuyuxuan
 */
public class Constant {

	// system configuration
	public final static String GLOBAL_CONFIG_ENCODING= Config.getString("global.config.encoding");
	public final static String GLOBAL_CONFIG_DATEFORMAT= Config.getString("global.config.dateformat");
	// 是否开启memcached
	public final static boolean MEMCACHED_ON = Config.getBoolean("memcached.on");
	// memcached存储前缀
	public final static String MEMCACHED_PREFIX_SYS_DICTIONARY= Config.getString("memcached.prefix.sysDictionary");
	public final static String MEMCACHED_PREFIX_SYS_CITY = Config.getString("memcached.prefix.sysCity");
	public final static String MEMCACHED_PREFIX_SYS_PROVINCE = Config.getString("memcached.prefix.sysProvince");
	public final static String MEMCACHED_PREFIX_SYS_DICTIONARY_PROVINCEITEM = Config.getString("memcached.prefix.sysDictionaryProvinceitem");
	
	
	public static void main(String[] args) {
		System.out.println(MEMCACHED_ON);
		System.out.println(MEMCACHED_PREFIX_SYS_DICTIONARY);
	}
	
}
