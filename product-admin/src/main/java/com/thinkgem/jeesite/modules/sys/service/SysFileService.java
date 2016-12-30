package com.thinkgem.jeesite.modules.sys.service;

import java.util.Map;

import com.thinkgem.jeesite.modules.live.base.service.ExCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.modules.live.base.utils.FastJsonUtils;
import com.thinkgem.jeesite.modules.live.base.utils.NewHttpClient;
import com.thinkgem.jeesite.modules.live.base.config.LiveEnv;
import com.thinkgem.jeesite.modules.sys.dao.SysFileDao;
import com.thinkgem.jeesite.modules.sys.entity.SysFile;
import com.thinkgem.jeesite.modules.live.base.enums.LiveFileSubjectTypeEnum;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class SysFileService extends ExCrudService<SysFileDao, SysFile> {

	@Override
	@Transactional(readOnly = false)
	public void save(SysFile entity) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 调用直播系统提供远程接口，将文件由临时文件转存到正式文件
	 * 
	 * @param fileId
	 * @return 新生成文件id
	 */
	public String remoteSave(String fileId, LiveFileSubjectTypeEnum typeEnum) {
		SysFile sysFile = dao.get(fileId);
		if(sysFile != null && sysFile.getIsTemp() == 1){//表示是临时文件，需要生成正式文件
			Map<String, Object> params = Maps.newHashMap();
			params.put("fileId", fileId);
			params.put("type", typeEnum.getSubjectType());
			String json = NewHttpClient.post(LiveEnv.getWebSite() + "/sys/file/secretSave", params);
			JSONObject obj = null;
			try {
				obj = FastJsonUtils.parseObject(json);
				fileId = obj.getString("id");
			} catch (Exception e) {
				throw new ServiceException("调用直播系统临时图片保存服务失败");
			}	
		}
		return fileId;
	}
}
