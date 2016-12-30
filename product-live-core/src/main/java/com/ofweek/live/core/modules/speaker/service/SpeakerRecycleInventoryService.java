package com.ofweek.live.core.modules.speaker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.speaker.dao.SpeakerDataDao;
import com.ofweek.live.core.modules.speaker.dao.SpeakerDataRecycleDao;
import com.ofweek.live.core.modules.speaker.dao.SpeakerRecycleInventoryDao;
import com.ofweek.live.core.modules.speaker.dao.SpeakerVideoDao;
import com.ofweek.live.core.modules.speaker.dao.SpeakerVideoRecycleDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerData;
import com.ofweek.live.core.modules.speaker.entity.SpeakerRecycleInventory;
import com.ofweek.live.core.modules.speaker.entity.SpeakerVideo;
import com.ofweek.live.core.modules.sys.enums.RecycleTypeEnum;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;
import com.ofweek.live.core.modules.sys.utils.UserUtils;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional
public class SpeakerRecycleInventoryService extends CrudService<SpeakerRecycleInventoryDao, SpeakerRecycleInventory> {

	@Autowired
	private SpeakerDataRecycleDao speakerDataRecycleDao;
	
	@Autowired
	private SpeakerVideoRecycleDao speakerVideoRecycleDao;
	
	@Autowired
	private SpeakerDataDao speakerDataDao;
	
	@Autowired
	private SpeakerVideoDao speakerVideoDao;
	
	@Override
	@Transactional(readOnly = false)
	public void save(SpeakerRecycleInventory entity) {
		if (StringUtils.isBlank(entity.getId())) {
			entity.setId(SequenceUtils.getNextString("LiveSpeakerRecycleInventory"));
			entity.preInsert();
			dao.insert(entity);
		} else {
			entity.preUpdate();
			dao.update(entity);
		}
	}

	private static SpeakerRecycleInventory setSpeakerRecycleInventory(Object object) {
		SpeakerRecycleInventory inventory = new SpeakerRecycleInventory();
		if (object instanceof SpeakerData) {
			SpeakerData speakerData = (SpeakerData) object;
			inventory.setSubjectName(speakerData.getName());
			inventory.setSubjectId(speakerData.getId());
			inventory.setSubjectType(RecycleTypeEnum.DATA.getCode());
		} else if (object instanceof SpeakerVideo) {
			SpeakerVideo speakerVideo = (SpeakerVideo) object;
			inventory.setSubjectName(speakerVideo.getName());
			inventory.setSubjectId(speakerVideo.getId());
			inventory.setSubjectType(RecycleTypeEnum.VIDEO.getCode());
		}
		return inventory;
	}

	public void saveSpeakerRecycleInventoryForData(List<SpeakerData> dList) {
		for (SpeakerData speakerData : dList) {
			SpeakerRecycleInventory inventory = setSpeakerRecycleInventory(speakerData);
			save(inventory);
		}
	}

	public void saveSpeakerRecycleInventoryForVideo(List<SpeakerVideo> vList) {
		for (SpeakerVideo speakerVideo : vList) {
			SpeakerRecycleInventory inventory = setSpeakerRecycleInventory(speakerVideo);
			save(inventory);
		}
	}
	
	@Transactional(readOnly = false, rollbackFor = {Exception.class})
	public void reduction(SpeakerRecycleInventory recycle) {
		String subjectId = recycle.getSubjectId();
		switch (recycle.getSubjectType()) {
			case 256:
				SpeakerData speakerData = speakerDataRecycleDao.get(subjectId);// 获取回收站表数据
				speakerDataDao.delSpeakerDataForObj(speakerData);// 删除资料表数据
				speakerDataDao.insertFormDataRecycle2Data(speakerData);// 将回收站表数据写入资料表中
				speakerDataRecycleDao.delete(speakerData);// 删除回收站表数据
				break;
			case 257:
				SpeakerVideo speakerVideo = speakerVideoRecycleDao.get(subjectId);
				speakerVideoDao.delSpeakerVideoForObj(speakerVideo);
				speakerVideoDao.insertFormVideoRecycle2Video(speakerVideo);
				speakerVideoRecycleDao.delete(speakerVideo);
				break;
			default :
				break;
		}
		delete(recycle);
	}
	
	public void delete(SpeakerRecycleInventory recycle) {
		String speakerId = UserUtils.getUser().getId();
		recycle.setSpeakerId(speakerId); 
		dao.delete(recycle);
	}
	
	public String validateReport(SpeakerRecycleInventory recycle) {
		String subjectName = recycle.getSubjectName();
		String speakerId = UserUtils.getUser().getId();
		String result;
		switch (recycle.getSubjectType()) {
			case 256:
				if (getSpeakerDataForName(subjectName, speakerId)) {
					result = "repeat";
					return result;
				} else if (countSpeakerData()) {
					result = "limit";
					return result;
				}
				break;
			case 257:
				if (getSpeakerVideoForName(subjectName, speakerId)) {
					result = "repeat";
					return result;
				} else if (countSpeakerVideo()) {
					result = "limit";
					return result;
				}
				break;
			default :
				break;
		}
		result = "no-repeat";
		return result;
	}
	
	public Boolean getSpeakerDataForName(String name, String speakerId) {
		return speakerDataDao.getSpeakerDataForName(name, speakerId) != null ? Boolean.TRUE : Boolean.FALSE;
	}
	
	public Boolean getSpeakerVideoForName(String name, String speakerId) {
		return speakerVideoDao.getSpeakerVideoForName(name, speakerId) != null ? Boolean.TRUE : Boolean.FALSE;
	}
	
	public Boolean countSpeakerData() {
		return speakerDataDao.countSpeakerData(UserUtils.getUser().getId()) >= LiveEnv.getDataLimit() ?  Boolean.TRUE : Boolean.FALSE;
	}
	
	public Boolean countSpeakerVideo() {
		return speakerVideoDao.countSpeakerVideo(UserUtils.getUser().getId()) >= LiveEnv.getVediotLimit() ?  Boolean.TRUE : Boolean.FALSE;
	}
}
