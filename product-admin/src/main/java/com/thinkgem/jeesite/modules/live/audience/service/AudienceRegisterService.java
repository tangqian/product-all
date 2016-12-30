package com.thinkgem.jeesite.modules.live.audience.service;

import java.util.List;

import com.thinkgem.jeesite.modules.live.audience.entity.AudienceRegister;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.audience.dao.AudienceRegisterDao;
import com.thinkgem.jeesite.modules.live.audience.form.AudienceRegisterPageForm;
import com.thinkgem.jeesite.modules.live.audience.vo.AudienceRegisterVo;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;

/**
 * @version 1.0
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class AudienceRegisterService extends AbstractModuleService<AudienceRegisterDao, AudienceRegister> {
	
	@Autowired
	private DictService dictService;
	
	@Override
	@Transactional(readOnly = false)
	public void save(AudienceRegister entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(sequenceService.nextIntString("LiveAudienceRegister"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	public AudienceRegisterVo queryAudienceRegisterForId(Integer id) {
		AudienceRegisterVo vo = dao.findAudienceRegisterForId(id);
		formatDict(vo);
		return vo;
	}
	
	public List<AudienceRegisterVo> queryAudienceRegisterForObj(AudienceRegisterPageForm form) {
		List<AudienceRegisterVo> list = dao.findAudienceRegisterForObj(form);
		setAudienceRegisterDict(list);
		return list;
	}
	
	private void setAudienceRegisterDict(List<AudienceRegisterVo> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			for (AudienceRegisterVo vo : list) {
				formatDict(vo);
			}
		}
	}
	
	private void formatDict(AudienceRegisterVo vo) {
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
	
	public Integer countAudienceRegisterForRoomId(String roomId) {
		return dao.countAudienceRegisterForRoomId(roomId);
	}
	
	public Page<AudienceRegister> findAudienceRegisterForRoomId(Page<AudienceRegister> page, AudienceRegister entity) {
		entity.setPage(page);
		page.setList(dao.findAudienceRegisterForRoomId(entity));
		return page;
	}
	
}
