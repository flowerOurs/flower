package com.huhuo.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.util.ReflectionUtils;

import com.huhuo.bean.EntityBean;
import com.huhuo.bean.IEntityBean;
import com.huhuo.exception.ProxyBeanException;

/**
 * 反射帮助类
 * @author wuyuxuan
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class BeanHelper<T> {
	
	public static class GetterSetter{
		public String propertyName;
		public Method getter;
		public Method setter;
	}
	
	static final Log logger = LogFactory.getLog(BeanHelper.class);
	
	public static <T> boolean isProxy(final IEntityBean<T> entityBean){
		return entityBean instanceof Advised;
	}
	
	public static <T> boolean isTransientField(Class<T> clazz, final String propertyName) {
		Field propertyField = null;
		while(true){
			try{
				propertyField = clazz.getDeclaredField(propertyName);
				break;
			}catch(final NoSuchFieldException nsfe){
				clazz = (Class<T>) clazz.getSuperclass();
				if(clazz.equals(Object.class))
					break;
				continue;
			}
		}
		if(propertyField!=null){
			return Modifier.isTransient(propertyField.getModifiers());
		}
		return false;
	}
	
	public static <T> IEntityBean<T> getTargetBean(IEntityBean<T> entityBean){
		if(!BeanHelper.isProxy(entityBean)){
			return entityBean;
		}
		try{
			return (IEntityBean<T>)((Advised)entityBean).getTargetSource().getTarget();
		}catch(final Exception e){
			throw new ProxyBeanException("The target object can't be resoled.",e);
		}
	}
	
	public static <T> GetterSetter[] getGetterSetter(final IEntityBean<T> entityBean) {
		return BeanHelper.getGetterSetter(entityBean.getClass());
	}
	
	private static Map<Class<?>,GetterSetter[]> getterSetterMap = new HashMap<Class<?>,GetterSetter[]>();
	
	public static <T> GetterSetter[] getGetterSetter(final Class<T> beanClazz){
		GetterSetter[] getterSetterArr = getterSetterMap.get(beanClazz);
		if(getterSetterArr!=null){
			return getterSetterArr;
		}
		BeanInfo beanInfo = null;
		try{
			if(EntityBean.class.isAssignableFrom(beanClazz))
				beanInfo = Introspector.getBeanInfo(beanClazz, EntityBean.class);
			else
				beanInfo = Introspector.getBeanInfo(beanClazz);
		}catch(final IntrospectionException ex){
			BeanHelper.logger.error("null", ex);
		}
		final PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();	
		Method setter,getter;
		final List<GetterSetter> ret = new ArrayList<GetterSetter>();
		for(final PropertyDescriptor element : pds){
			final String propertyName = element.getName();
			if(BeanHelper.isTransientField(beanClazz, propertyName)){
				continue;
			}
			getter = element.getReadMethod();
			setter = element.getWriteMethod();
			if((getter!=null)&&(setter!=null)&& (getter.getParameterTypes().length==0)&&(setter.getParameterTypes().length==1)){
				final GetterSetter getterSetter = new GetterSetter();
				getterSetter.getter = getter;
				getterSetter.setter = setter;
				getterSetter.propertyName = propertyName;
				ret.add(getterSetter);
			}
		}
		getterSetterMap.put(beanClazz, getterSetterArr=ret.toArray(new GetterSetter[ret.size()])); 
		return getterSetterArr;
	}
	
	public static Object invokeMethod(final Method method, final Object target) {
		return ReflectionUtils.invokeMethod(method, target);
	}

	public static Object invokeMethod(final Method method, final Object target,
			final Object[] args) {
		return ReflectionUtils.invokeMethod(method, target, args);
	}
}
