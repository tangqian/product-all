package com.thinkgem.jeesite.modules.live.room.utils;

import java.util.List;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.live.speaker.dao.SpeakerDao;
import com.thinkgem.jeesite.modules.live.speaker.entity.Speaker;

public class SpeakerUtils {

	private static SpeakerDao speakerDao = SpringContextHolder
			.getBean(SpeakerDao.class);

	public static List<Speaker> getSpeakerList() {
		List<Speaker> notOvers = Lists.newArrayList();
		for (Speaker speaker : speakerDao.findList(new Speaker())) {
			notOvers.add(speaker);
		}
		return notOvers;
	}

}
