package com.ofweek.live.core.common.spring;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.ofweek.live.core.common.utils.ExternalResourceUtils;

/**
 * @author tangqian
 *
 */
public class NewWebApplicationContext extends XmlWebApplicationContext {
	
    @Override
    public Resource getResource(String location) {
    	try {
			if (ExternalResourceUtils.isExternal(location)) {
				return ExternalResourceUtils.get(location);
			} else {
				return super.getResource(location);
			}
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid location=" + location, e);
        }
    }
    
	@Override
	public Resource[] getResources(String locationPattern) throws IOException {
		if (ExternalResourceUtils.isExternal(locationPattern)) {
			return new Resource[] { ExternalResourceUtils.get(locationPattern) };
		} else {
			return super.getResources(locationPattern);
		}
	}
    
}
