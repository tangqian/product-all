package com.thinkgem.jeesite.modules.live.room.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.live.room.entity.RoomBlacklist;

/**
 * @version 1.0
 * @author tangqian
 */
@MyBatisDao
public interface RoomBlacklistDao extends CrudDao<RoomBlacklist> {

	RoomBlacklist findBlacklistForObj(RoomBlacklist entity);

}