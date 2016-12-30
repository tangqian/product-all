package com.ofweek.live.core.modules.sys.listener;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

import com.ofweek.live.core.common.utils.ExternalResourceUtils;

/**
 * @author tangqian
 */
public class LogbackConfigListener implements ServletContextListener {
    
    private static final Logger logger = LoggerFactory.getLogger(LogbackConfigListener.class);
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 从web.xml中加载指定文件名的日志配置文件
        String fn = null;
		try {
			fn = ExternalResourceUtils.get("config:logback.xml").getFile().getCanonicalPath();
		} catch (IOException e1) {
			System.out.println("加载日志文件出错");
			e1.printStackTrace();
		}
        try {
            LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
            loggerContext.reset();
            JoranConfigurator joranConfigurator = new JoranConfigurator();
            joranConfigurator.setContext(loggerContext);
            joranConfigurator.doConfigure(fn);
        }
        catch (JoranException e) {
            logger.error("Load logback configuration file error." + fn, e);
        }
    }
    
}
