package com.thinkgem.jeesite.common.i18n;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;

public class MessageSourceSingleton extends ResourceBundleMessageSource {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 资源文件所在文件夹名称
     */
    private final static String MESSAGE_FILE_FOLDER_NAME = "messages";

    private static class SingletonHolder {
        private static MessageSourceSingleton singleton = new MessageSourceSingleton();
    }

    public static MessageSourceSingleton getInstance() {
        return SingletonHolder.singleton;
    }

    private MessageSourceSingleton() {
        try {
            File folder = new ClassPathResource(MESSAGE_FILE_FOLDER_NAME).getFile();
            if (!folder.isDirectory()) {
                throw new IOException("messages 不是文件夹！");
            }
            List<String> fileNames = new ArrayList<String>();
            for (String str : folder.list()) {
                String _str = str.substring(0, str.lastIndexOf('.'));
                fileNames.add(MESSAGE_FILE_FOLDER_NAME + "/" + _str);
            }
            String[] sources = new String[fileNames.size()];
            super.setBasenames(fileNames.toArray(sources));
        } catch (IOException e) {
            logger.error("资源文件加载失败，请确保类路径下有 \"messages\" 文件夹！", e);
        }
    }

    public String getMessage(String code) {
        return super.getMessage(code, null, null);
    }

    public String getMessage(String code, Object args[]) {
        return super.getMessage(code, args, null);
    }
}