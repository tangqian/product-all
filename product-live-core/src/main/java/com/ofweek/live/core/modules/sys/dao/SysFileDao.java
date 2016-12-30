package com.ofweek.live.core.modules.sys.dao;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.sys.entity.SysFile;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface SysFileDao extends CrudDao<SysFile> {

    List<SysFile> findTempList(@Param("endDay") Date endDay);

    List<SysFile> findListBySubjectType(@Param("subjectType") int subjectType, @Param("limitTime") Date limitTime);

}