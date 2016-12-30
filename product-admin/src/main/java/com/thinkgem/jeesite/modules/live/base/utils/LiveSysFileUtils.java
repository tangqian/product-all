/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.live.base.utils;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.SysFileDao;
import com.thinkgem.jeesite.modules.sys.entity.SysFile;

/**
 * 直播文件表工具类
 *
 * @author tangqian
 */
public class LiveSysFileUtils {

    private static SysFileDao sysFileDao = SpringContextHolder.getBean(SysFileDao.class);

    public static SysFile get(String id) {
        return sysFileDao.get(id);
    }

    public static String getCloudFileName(String id) {
        SysFile sysFile = get(String.valueOf(id));
        if (sysFile == null)
            return null;
        String fileName = sysFile.getUri();
        int pos = fileName.lastIndexOf('/');
        if (pos != -1) {
            fileName = fileName.substring(pos + 1);
        }
        return "L" + fileName;
    }
}
