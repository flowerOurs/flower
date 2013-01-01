package com.huhuo.exception;

import org.springframework.core.NestedRuntimeException;

public class ProxyBeanException extends NestedRuntimeException {

	private static final long serialVersionUID = 6298799972788553566L;

	public ProxyBeanException(final String msg) {
		super(msg);
	}

	public ProxyBeanException(final String msg, final Throwable ex) {
		super(msg, ex);
	}

}
