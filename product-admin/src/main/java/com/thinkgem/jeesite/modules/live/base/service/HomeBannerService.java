package com.thinkgem.jeesite.modules.live.base.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.base.dao.HomeBannerDao;
import com.thinkgem.jeesite.modules.live.base.entity.HomeBanner;
import com.thinkgem.jeesite.modules.live.base.enums.LiveFileSubjectTypeEnum;
import com.thinkgem.jeesite.modules.sys.service.SysFileService;

/**
 * @version 1.0
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class HomeBannerService extends AbstractModuleService<HomeBannerDao, HomeBanner> {
	
	@Resource
	private SysFileService sysFileService;
	
	@Override
	@Transactional(readOnly = false)
	public void save(HomeBanner entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(sequenceService.nextIntString("LiveHomeBanner"));
			entity.setFileId(sysFileService.remoteSave(entity.getFileId(), LiveFileSubjectTypeEnum.SYS_INDEX));
			entity.setSort(100);
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.setFileId(sysFileService.remoteSave(entity.getFileId(), LiveFileSubjectTypeEnum.SYS_INDEX));
			entity.preUpdate();
			dao.update(entity);
		}
	}

	/**
	 * @param banner
	 */
	public void updateSort(HomeBanner banner) {
		banner.preUpdate();
		dao.update(banner);
	}
    
}
