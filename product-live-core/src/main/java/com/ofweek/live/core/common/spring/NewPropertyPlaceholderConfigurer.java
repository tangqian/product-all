package com.ofweek.live.core.common.spring;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.ofweek.live.core.common.utils.PropertiesLoader;

/**
 * @author tangqian
 *
 */
public class NewPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    public void setPropFiles(String[] propFiles) {
        Properties properties = new PropertiesLoader(propFiles).getProperties();
        this.setProperties(properties);
    }
    
}
