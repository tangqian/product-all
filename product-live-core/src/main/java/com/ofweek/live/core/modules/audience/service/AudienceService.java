package com.ofweek.live.core.modules.audience.service;

import com.ofweek.live.core.common.persistence.Page;
import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.PhoneNumberUtils;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.audience.dao.AudienceDao;
import com.ofweek.live.core.modules.audience.entity.Audience;
import com.ofweek.live.core.modules.rpc.InvokeSmartlifein;
import com.ofweek.live.core.modules.rpc.dto.AddressDto;
import com.ofweek.live.core.modules.rpc.dto.UserDto;
import com.ofweek.live.core.modules.sys.dao.UserDao;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.enums.SexEnum;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.core.modules.sys.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class AudienceService extends CrudService<AudienceDao, Audience> {

    @Resource
    private UserService userService;

    @Resource
    private UserDao userDao;

    /**
     * 部分更新观众个人信息，不可做为一般性用户信息更新操作
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Audience entity) {
        if (StringUtils.isNotEmpty(entity.getId())) {
            Audience exsits = dao.get(entity.getId());
            if (entity.getTelephone() != null) {
                if (PhoneNumberUtils.isCellPhone(entity.getTelephone())) {
                    entity.setMobilePhone(entity.getTelephone());
                    entity.setTelephone(exsits.getTelephone());
                } else {
                    entity.setMobilePhone(exsits.getMobilePhone());
                }
            } else {
                entity.setTelephone(exsits.getTelephone());
                entity.setMobilePhone(exsits.getMobilePhone());
            }
            entity.setCountry(exsits.getCountry());
            entity.setProvince(exsits.getProvince());
            entity.setAddress(exsits.getAddress());
            entity.setName(exsits.getName());
            entity.setSex(exsits.getSex());
            entity.setFax(exsits.getFax());

            User user = userDao.get(entity.getId());
            entity.setUser(user);
            InvokeSmartlifein.updateUser(entity);
            entity.preUpdate();
            dao.update(entity);
        }
    }

    /**
     * @param userDto
     */
    @Transactional(readOnly = false)
    public Audience save(UserDto userDto) {
        User exsitUser = userService.get(userDto.getUsername(), UserTypeEnum.AUDIENCE.getCode());
        if (exsitUser == null) {
            Audience audience = from(userDto);

            User newUser = audience.getUser();
            userService.save(newUser);
            audience.setId(newUser.getId());
            audience.preInsert();
            dao.insert(audience);
            return audience;
        } else {// 切记此处不要更新live_audience中的数据，因为从智慧生活网中传过来的name,company等关键字段可能为空，会冲掉观众在直播系统填的登记信息
            String newEmail = userDto.getEmail();
            if (StringUtils.isNotBlank(newEmail) && !newEmail.equals(exsitUser.getEmail())) {
                User updateUser = new User(exsitUser.getId());
                updateUser.setEmail(newEmail);
                userService.save(updateUser);
                exsitUser.setEmail(newEmail);
            }
            Audience audience = get(exsitUser.getId());
            if (StringUtils.isEmpty(audience.getName()) && StringUtils.isNotEmpty(userDto.getNickname())) {
                updateAudienceFrom(audience, userDto);
                audience.preUpdate();
                dao.update(audience);
            }
            audience.setUser(exsitUser);
            return audience;
        }
    }

    /**
     * @param account
     */
    @Transactional(readOnly = false)
    public Audience saveFromSmartlifein(String account) {
        UserDto userDto = InvokeSmartlifein.getUser(account);
        if (userDto != null) {
            return save(userDto);
        }
        return null;
    }

    private Audience from(UserDto userDto) {
        Audience audience = new Audience();

        User user = new User();
        user.setType(UserTypeEnum.AUDIENCE.getCode());
        user.setAccount(userDto.getUsername());
        user.setPassword("null");
        user.setEmail(userDto.getEmail());
        audience.setUser(user);
        updateAudienceFrom(audience, userDto);
        return audience;
    }

    private void updateAudienceFrom(Audience audience, UserDto userDto) {
        audience.setName(userDto.getNickname());
        audience.setSex(SexEnum.fromSmartlifein(userDto.getSex()).getCode());
        audience.setMobilePhone(userDto.getMobile());

        AddressDto addressDto = userDto.getAddress();
        if (addressDto != null) {
            audience.setCountry(addressDto.getCountry());
            audience.setProvince(addressDto.getProvince());
            audience.setAddress(addressDto.getDetail());
        }
    }

    public Page<Audience> findDownloadAudienceList(Page<Audience> page, Audience entity) {
        entity.setPage(page);
        page.setList(dao.findDownloadAudienceList(entity));
        return page;
    }

    public Page<Audience> findAudienceChatList(Page<Audience> page, Audience entity) {
        entity.setPage(page);
        page.setList(dao.findAudienceChatList(entity));
        return page;
    }

    /**
     * 导出全部观众
     *
     * @param entity
     * @return
     */
    public List<Audience> findAllAudienceList(Audience entity) {
        return dao.findList(entity);
    }

    /**
     * 导出下载资料观众
     *
     * @param entity
     * @return
     */
    public List<Audience> findDownloadAudienceList(Audience entity) {
        return dao.findDownloadAudienceList(entity);
    }

    /**
     * 导出聊天观众
     *
     * @param entity
     * @return
     */
    public List<Audience> findAudienceChatList(Audience entity) {
        return dao.findAudienceChatList(entity);
    }

}
