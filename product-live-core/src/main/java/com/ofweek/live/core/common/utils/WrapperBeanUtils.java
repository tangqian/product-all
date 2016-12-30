package com.ofweek.live.core.common.utils;

import com.ofweek.live.core.common.service.ServiceException;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by tangqian on 2016/9/24.
 */
public class WrapperBeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(WrapperBeanUtils.class);

    /**
     * Wrapper BeanUtils.copyProperties method, throws Runtime exception
     * @param dest
     * @param orig
     * @throws ServiceException
     */
    public static void copyProperties(Object dest, Object orig) throws ServiceException {
        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (IllegalAccessException e) {
            logger.error("bean copy IllegalAccessException!", e);
            throw new ServiceException(e);
        } catch (InvocationTargetException e) {
            logger.error("bean copy InvocationTargetException!", e);
            throw new ServiceException(e);
        }
    }
}
