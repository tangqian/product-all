package com.ofweek.live.core.common.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * @author tangqian
 *
 */
public class ExternalResourceUtils {

	public static boolean isExternal(String location){
		return location.startsWith("config:");	
	}
	
	public static Resource get(String location){
		if(isExternal(location)){
			String path = System.getProperty("config.path") + StringUtils.substringAfter(location, ":");
			return new FileSystemResource(path);			
		}
		return null;
	}
	

}
