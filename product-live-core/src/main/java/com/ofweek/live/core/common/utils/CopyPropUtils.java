package com.ofweek.live.core.common.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;

/**
 * 复制属性工具
 * 
 * @author fengjingwei
 *
 */
public class CopyPropUtils extends BeanUtils {

	static {
		// 注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
		ConvertUtils.register(new SqlDateConverter(null), java.sql.Date.class);
		ConvertUtils.register(new SqlDateConverter(null), java.util.Date.class);
		ConvertUtils.register(new SqlTimestampConverter(null),
				java.sql.Timestamp.class);
		// 注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空
	}

	public static void copyProperties(Object target, Object source)
			throws InvocationTargetException, IllegalAccessException {
		// 支持对日期copy
		BeanUtils.copyProperties(target, source);
	}
}
