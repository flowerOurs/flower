package com.huhuo.constant;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
/**
 * load properties file from classes path
 * @author wuyuxuan
 */
public class Config {
	
	private static Logger logger = LoggerFactory.getLogger(Config.class);
	
	private static Properties prop;
	
	static {	// initialize configuration properties
		try {
			prop = PropertiesLoaderUtils.loadAllProperties("system.properties");
			logger.debug("loading property file[system.properties] with content: {}", prop);
		} catch (Exception e) {
			logger.debug("property file loading failed for system.properties");
			e.printStackTrace();
		}
	}
	
	/**
	 * Searches for the property with the specified key in this property list. 
	 * If the key is not found in this property list, the default property list, and its defaults, 
	 * recursively, are then checked. The method returns the default value argument if the property is not found.
	 * @param key
	 * @param defaultValue a default value
	 * @return the value in this property list with the specified key value.
	 */
	public static String getProperty(String key, String defaultValue) {
		return prop.getProperty(key, defaultValue);
	}
	/**
	 * The method returns null if the property is not found.
	 * @see #getProperty(String, String)
	 * @param key the property key.
	 * @return null if the property is not found.
	 */
	public static String getProperty(String key) {
		return getProperty(key, null);
	}
	
	/**
	 * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
	 * @param key the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
	 */
	public static Object get(String key) {
		return prop.get(key);
	}
	/**
	 * @see #get(String)
	 * @param key
	 * @return the value to which the specified key is mapped, or "" if this map contains no mapping for the key
	 */
	public static String getString(String key) {
		return getProperty(key, "");
	}
	/**
	 * @see #get(String)
	 * @param key
	 * @return the value to which the specified key is mapped, or 0 if this map contains no mapping for the key
	 */
	public static Integer getInt(String key) {
		return Integer.parseInt(getProperty(key, "0"));
	}
	/**
	 * @see #get(String)
	 * @param key
	 * @return the value to which the specified key is mapped, or 0 if this map contains no mapping for the key
	 */
	public static long getLong(String key) {
		return Long.parseLong(getProperty(key, "0"));
	}
	/**
	 * @see #get(String)
	 * @param key
	 * @return the value to which the specified key is mapped, or false if this map contains no mapping for the key
	 */
	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(getProperty(key, "false"));
	}
}
