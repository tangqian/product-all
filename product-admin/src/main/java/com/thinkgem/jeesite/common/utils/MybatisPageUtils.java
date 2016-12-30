package com.thinkgem.jeesite.common.utils;

import org.apache.ibatis.session.RowBounds;

public class MybatisPageUtils {
    private final static int DEFAULT_PAGE_SIZE = 10;

    public static RowBounds convert(int pageSize, int pageNo) {
        Integer limit = DEFAULT_PAGE_SIZE, offset = 0;
        if(pageSize > 0){
            limit = pageSize;
        }
        if (pageNo > 1) {
            offset = (pageNo - 1) * limit;
        } else {
            offset = 0;
        }

        RowBounds rowBounds = RowBounds.DEFAULT;
        rowBounds = new RowBounds(offset, limit);
        return rowBounds;
    }

}