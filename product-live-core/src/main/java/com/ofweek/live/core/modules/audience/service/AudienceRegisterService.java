package com.ofweek.live.core.modules.audience.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.utils.WrapperBeanUtils;
import com.ofweek.live.core.modules.audience.dao.AudienceDao;
import com.ofweek.live.core.modules.audience.dao.AudienceRegisterDao;
import com.ofweek.live.core.modules.audience.entity.Audience;
import com.ofweek.live.core.modules.audience.entity.AudienceRegister;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;

/**
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class AudienceRegisterService extends CrudService<AudienceRegisterDao, AudienceRegister> {

    @Resource
    private AudienceDao audienceDao;

    @Override
    @Transactional(readOnly = false)
    public void save(AudienceRegister entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(SequenceUtils.getNextString("LiveAudienceRegister"));
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
    }

    @Transactional(readOnly = false)
    public void save(String userId, String roomId) {
        AudienceRegister register = dao.getByUserRoomId(userId, roomId);
        if (register == null) {
            Audience audience = audienceDao.get(userId);
            if (audience != null) {
                register = new AudienceRegister();
                WrapperBeanUtils.copyProperties(register, audience);
                register.setId(SequenceUtils.getNextString("LiveAudienceRegister"));
                register.setAudienceId(audience.getId());
                register.setRoomId(roomId);
                register.preInsert();
                dao.insert(register);
            }
        }
    }

    public boolean isRegistered(String userId, String roomId) {
        AudienceRegister register = dao.getByUserRoomId(userId, roomId);
        return register == null ? false : true;
    }


	public List<AudienceRegister> findSendEmailAudienceRegister() {
		return dao.findSendEmailAudienceRegister();
	}
	
}
