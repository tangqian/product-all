package com.ofweek.live.core.modules.base.exception;

import com.fasterxml.jackson.databind.deser.Deserializers;

/**
 * NIO参数检查通用的Exception.
 * @author tangqian
 */
public class NioIllegalArgumentException extends BaseException {

	private static final long serialVersionUID = 1L;

	public NioIllegalArgumentException() {
		super();
	}

	public NioIllegalArgumentException(String message) {
		super(message);
	}

	public NioIllegalArgumentException(Throwable cause) {
		super(cause);
	}

	public NioIllegalArgumentException(String message, Throwable cause) {
		super(message, cause);
	}
}
