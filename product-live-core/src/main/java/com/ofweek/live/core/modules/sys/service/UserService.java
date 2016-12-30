package com.ofweek.live.core.modules.sys.service;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.audience.dao.AudienceDao;
import com.ofweek.live.core.modules.audience.entity.Audience;
import com.ofweek.live.core.modules.speaker.dao.SpeakerDao;
import com.ofweek.live.core.modules.speaker.dao.SpeakerWaiterDao;
import com.ofweek.live.core.modules.speaker.entity.Speaker;
import com.ofweek.live.core.modules.speaker.entity.SpeakerWaiter;
import com.ofweek.live.core.modules.sys.dao.UserDao;
import com.ofweek.live.core.modules.sys.entity.DefaultUser;
import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.enums.UserStatusEnum;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class UserService extends CrudService<UserDao, User> {

    @Resource
    private AudienceDao audienceDao;

    @Resource
    private SpeakerDao speakerDao;

    @Resource
    private SpeakerWaiterDao speakerWaiterDao;

    @Override
    @Transactional(readOnly = false)
    public void save(User entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(SequenceUtils.getNextString("LiveUser"));
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            entryptPassword(entity);
            dao.update(entity);
        }
    }

    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public String saveUser(User entity) {
        if (StringUtils.isBlank(entity.getId())) {
            String id = SequenceUtils.getNextString("LiveUser");
            entity.setId(id);
            entity.preInsert();
            dao.insert(entity);
            return id;
        } else {
            entity.preUpdate();
            entryptPassword(entity);
            dao.update(entity);
        }
        return entity.getId();
    }

    public String saveWaiterUser(SpeakerWaiter entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setType(UserTypeEnum.WAITER.getCode());
        user.setAccount(entity.getAccount());
        user.setStatus(UserStatusEnum.NORMAL.getCode());
        user.setEmail("");
        setPassword(entity, user);
        return saveUser(user);
    }

    private static void setPassword(SpeakerWaiter entity, User user) {
        String password = entity.getUser().getPassword();
        if (StringUtils.isNotBlank(entity.getId())) {
            if (StringUtils.isNotBlank(password)) {
                user.setPassword(SystemService.entryptPassword(password));
            }
        } else {
            user.setPassword(SystemService.entryptPassword(password));
        }
    }

    private static void entryptPassword(User entity) {
        if (StringUtils.isNotBlank(entity.getNewPassword())) {
            entity.setPassword(SystemService.entryptPassword(entity.getNewPassword()));
        }
    }

    public GeneralUser getGeneralUser(String id) {
        GeneralUser ret = null;
        User user = dao.get(id);
        if (user != null) {
            UserTypeEnum typeEnum = UserTypeEnum.getEnum(user.getType());
            switch (typeEnum) {
                case AUDIENCE:
                    Audience audience = audienceDao.get(id);
                    audience.setUser(user);
                    ret = audience;
                    break;
                case ADMIN:
                    DefaultUser admin = new DefaultUser();
                    admin.setUser(user);
                    ret = admin;
                    break;
                case SPEAKER:
                    Speaker speaker = speakerDao.get(id);
                    speaker.setUser(user);
                    ret = speaker;
                    break;
                case WAITER:
                    SpeakerWaiter waiter = speakerWaiterDao.get(id);
                    waiter.setUser(user);
                    waiter.setSpeaker(speakerDao.get(waiter.getSpeakerId()));
                    ret = waiter;
                    break;
                case VISITOR:
                    break;
            }
        }
        return ret;
    }

    /**
     * 根据账号和类型获取用户
     *
     * @param account
     * @param type
     * @return
     */
    public User get(String account, Integer type) {
        return dao.getByAccount(account, type);
    }

    public User getSpeakerOrWaiter(String account) {
        List<User> users = dao.getSpeakerOrWaiter(account);
        if (users.size() > 1)
            logger.error("There are more than one user in db by account={}", account);
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * 是否本房间的雇员
     *
     * @param userId    当前用户id
     * @param type      当前用户类型
     * @param speakerId 本房间所属直播公司id
     * @return
     */
    public boolean isRoomEmployee(String userId, Integer type, String speakerId) {
        if (userId.equals(speakerId)) {
            return true;
        }
        if (UserTypeEnum.isAdmin(type)) {// 管理员账号是所有房间的雇员
            return true;
        }
        return speakerWaiterDao.getBySpeaker(userId, speakerId) != null;
    }

    public String updateUserEmail(Speaker entity) {
        User user = new User();
        user.setId(entity.getId());
        if (StringUtils.isBlank(entity.getEmail()))
            user.setEmail(StringUtils.EMPTY);
        else
            user.setEmail(entity.getEmail());
        return saveUser(user);
    }

}
