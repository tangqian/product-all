package com.thinkgem.jeesite.modules.live.audience.service;

import java.util.List;

import com.thinkgem.jeesite.modules.live.audience.entity.Audience;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.audience.dao.AudienceDao;
import com.thinkgem.jeesite.modules.live.audience.vo.AudienceVo;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;

/**
 * @version 1.0
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class AudienceService extends AbstractModuleService<AudienceDao, Audience> {
	
	@Autowired
	private DictService dictService;
	
	@Override
	@Transactional(readOnly = false)
	public void save(Audience entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(sequenceService.nextIntString("LiveAudience"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	public List<AudienceVo> findAudienceForIds(List<String> ids) {
		List<AudienceVo> list = dao.findAudienceForIds(ids);
		setAudienceDict(list);
		return list;
	}
	
	private void setAudienceDict(List<AudienceVo> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			for (AudienceVo vo : list) {
				formatDict(vo);
			}
		}
	}
	
	private void formatDict(AudienceVo vo) {
		if (StringUtils.isNotBlank(vo.getCountry())) {
			Dict dict = new Dict();
			dict.setType("COUNTRY_CODE");
			dict.setValue(vo.getCountry());
			dict = dictService.findByTypeValue(dict);
			vo.setCountry(dict != null ? dict.getLabel() : null);
		}
		if (StringUtils.isNotBlank(vo.getProvince())) {
			Dict dict = new Dict();
			dict.setType("PROVINCE_CODE");
			dict.setValue(vo.getProvince());
			dict = dictService.findByTypeValue(dict);
			vo.setProvince(dict != null ? dict.getLabel() : null);
		}
	}
	
	public Page<Audience> findAudienceForRoomId(Page<Audience> page, Audience entity) {
		entity.setPage(page);
		page.setList(dao.findAudienceForRoomId(entity));
		return page;
	}
	
}
