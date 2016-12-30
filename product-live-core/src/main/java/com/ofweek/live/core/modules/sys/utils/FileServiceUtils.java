package com.ofweek.live.core.modules.sys.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class FileServiceUtils {

	private static Logger logger = LoggerFactory.getLogger(FileServiceUtils.class);

	private static boolean isMSIE(HttpServletRequest request) {
		String agent = request.getHeader("User-Agent");
		boolean isMSIE = (agent.contains("MSIE") || agent.contains("Trident"));
		return isMSIE;
	}
	
	public static String getDownloadName(String fileName, HttpServletRequest request){
		String name = "attachment; filename=";
		String ext = getExt(fileName);
		if(ext != ""){//文件后缀名统一为小写
			fileName = fileName.substring(0, fileName.lastIndexOf('.')) + "." + ext;
		}
		try {
			fileName = fileName.replace(" ", "");//去掉文件名中的空格
			if(isMSIE(request)){
					name += java.net.URLEncoder.encode(fileName, "UTF-8");
			}else{
					name += new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("文件名称编码出错, 文件={}", fileName);
			if(ext == "")
				name += "unknown";
			else
				name += "unknown." + ext;
		}
		return name;
	}
	
	/**
	 * 文件后缀统一小写
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExt(String fileName) {
		return fileName.lastIndexOf('.') != -1 ? fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase() : "";
	}


}
