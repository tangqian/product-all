package com.thinkgem.jeesite.modules.sys.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanPropertiesCopy {
    /**
     * from的getter、to的setter方法需保持对应
     * @param from
     * @param to
     * @throws Exception
     */
    public static void copyProperties(Object from, Object to) throws Exception {
        try {
            Method[] fromMd = from.getClass().getMethods();
            List<Method> fromList = new ArrayList<Method>();
            for (Method md : fromMd) {
                if (md.getName().startsWith("get")) {
                    fromList.add(md);
                }
            }
            Method[] toMd = to.getClass().getMethods();
            List<Method> toList = new ArrayList<Method>();
            for (Method md : toMd) {
                if (md.getName().startsWith("set")) {
                    toList.add(md);
                }
            }
            for (Method setMd : toList) {
                for (Method getMd : fromList) {
                    if (setMd.getName().substring(3).equals(getMd.getName().substring(3))) {
                        Object val = getMd.invoke(from);
                        setMd.invoke(to, val);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("属性值复制失败", e);
        }
    }
}