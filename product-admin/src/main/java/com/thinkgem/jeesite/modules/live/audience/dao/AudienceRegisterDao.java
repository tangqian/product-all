package com.thinkgem.jeesite.modules.live.audience.dao;

import java.util.List;

import com.thinkgem.jeesite.modules.live.audience.entity.AudienceRegister;
import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.live.audience.form.AudienceRegisterPageForm;
import com.thinkgem.jeesite.modules.live.audience.vo.AudienceRegisterVo;

/**
 * @version 1.0
 * @author tangqian
 */
@MyBatisDao
public interface AudienceRegisterDao extends CrudDao<AudienceRegister> {

	List<AudienceRegisterVo> findAudienceRegisterForObj(AudienceRegisterPageForm form);
	
	AudienceRegisterVo findAudienceRegisterForId(Integer id);
	
	Integer countAudienceRegisterForRoomId(@Param("roomId") String roomId);
	
	List<AudienceRegister> findAudienceRegisterForRoomId(AudienceRegister entity);

}