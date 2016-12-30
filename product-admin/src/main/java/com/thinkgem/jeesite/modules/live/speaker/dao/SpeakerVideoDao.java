package com.thinkgem.jeesite.modules.live.speaker.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.live.speaker.entity.SpeakerVideo;

/**
 * @version 1.0
 * @author tangqian
 */
@MyBatisDao
public interface SpeakerVideoDao extends CrudDao<SpeakerVideo> {

	/**
	 * 获取未结束与房间已关联的所有视频
	 * @return
	 */
	List<SpeakerVideo> findAllRoomList();

}