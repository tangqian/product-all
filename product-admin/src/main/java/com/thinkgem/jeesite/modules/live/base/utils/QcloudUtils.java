/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.live.base.utils;

import com.google.common.collect.Maps;
import com.qcloud.api.callers.VodModuleCaller;
import com.qcloud.api.common.RequestClient;
import com.thinkgem.jeesite.modules.live.base.config.LiveEnv;

import java.util.Map;

/**
 * 腾讯云视频工具类
 *
 * @author tangqian
 */
public class QcloudUtils {

    public static final VodModuleCaller vodCaller;

    static {
        RequestClient client = new RequestClient(LiveEnv.getIdentityConfig());
        vodCaller = new VodModuleCaller(client);
    }

    public static class FileNameCache {

        private static final Map<String, String> fileNameFileIds = Maps.newHashMap();

        public static void put(String fileName, String fileId) {
            fileNameFileIds.put(fileName, fileId);
        }

        public static boolean contains(String fileName) {
            return fileNameFileIds.containsKey(fileName);
        }

        public static String get(String fileName) {
            return fileNameFileIds.get(fileName);
        }

        public static void clear() {
            fileNameFileIds.clear();
        }
    }

    public static class ClassNameCache {

        private static final Map<String, Integer> classNameClassIds = Maps.newHashMap();

        public static void put(String className, Integer classId) {
            classNameClassIds.put(className, classId);
        }

        public static Integer getId(String className) {
            return classNameClassIds.get(className);
        }

        public static boolean contains(String className) {
            return classNameClassIds.containsKey(className);
        }
    }

}
