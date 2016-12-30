package com.ofweek.live.core.modules.sys.service;

import java.util.List;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.sys.dao.HomeBannerDao;
import com.ofweek.live.core.modules.sys.dto.HomeBannerDto;
import com.ofweek.live.core.modules.sys.entity.HomeBanner;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;

import javax.annotation.Resource;

/**
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class HomeBannerService extends CrudService<HomeBannerDao, HomeBanner> {

    @Resource
    private SysFileService sysFileService;

    @Override
    @Transactional(readOnly = false)
    public void save(HomeBanner entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(SequenceUtils.getNextString("LiveHomeBanner"));
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
    }

    public List<HomeBannerDto> findIndexBannerList() {
        List<HomeBanner> banners = dao.findList(new HomeBanner());
        List<HomeBannerDto> dtos = Lists.newArrayList();
        banners.forEach(b -> dtos.add(new HomeBannerDto(b, sysFileService.get(b.getFileId()).getUrl())));
        return dtos;
    }

}
