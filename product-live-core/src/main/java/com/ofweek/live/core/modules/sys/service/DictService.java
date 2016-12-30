package com.ofweek.live.core.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.CacheUtils;
import com.ofweek.live.core.modules.sys.dao.DictDao;
import com.ofweek.live.core.modules.sys.entity.Dict;
import com.ofweek.live.core.modules.sys.utils.DictUtils;

/**
 * 字典Service
 * @author tangqian
 * 
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		//super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

}
