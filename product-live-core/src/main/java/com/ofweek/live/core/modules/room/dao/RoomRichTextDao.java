package com.ofweek.live.core.modules.room.dao;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.room.entity.RoomRichText;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface RoomRichTextDao extends CrudDao<RoomRichText> {

    List<RoomRichText> findRange(@Param("roomId") String roomId, @Param("endTime") Date endTime,
                                 @Param("offset") int offset, @Param("limit") int limit);
}