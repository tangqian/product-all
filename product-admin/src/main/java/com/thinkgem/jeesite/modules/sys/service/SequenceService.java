package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;

import com.thinkgem.jeesite.modules.live.base.service.ExCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.sys.dao.SequenceDao;
import com.thinkgem.jeesite.modules.sys.entity.Sequence;

/**
 * Service
 * 
 * @author tangqian
 * @version 2016-02-25
 */
@Service
@Transactional(readOnly = true)
public class SequenceService extends ExCrudService<SequenceDao, Sequence> {

	@Transactional(readOnly = false)
	public int nextInt(Object object) {
		String id = getSequenceId(object);
		Sequence seq = get(id);
		int next = 1;
		if (seq != null) {
			next = seq.getNextId() + 1;
			seq.setLastOperationTime(new Date());
			save(seq);
		} else {
			seq = new Sequence();
			seq.setIsNewRecord(true);
			seq.setUpdateDate(new Date());
			seq.setId(id);
			seq.setNextId(2);
			save(seq);
		}
		return next;
	}

	@Transactional(readOnly = false)
	public String nextIntString(Object seq) {
		int id = nextInt(seq);
		return String.valueOf(id == 1 ? 1 : id - 1);
	}

	private String getSequenceId(Object seq) {
		String id = null;
		if (seq != null) {
			if (seq instanceof Sequence) {
				Sequence sequence = (Sequence) seq;
				id = sequence.getId();
			} else if (seq instanceof String) {
				id = (String) seq;
			} else if (seq instanceof Class) {
				if (id == null) {
					id = ((Class<?>) seq).getSimpleName();
				}
			} else {
				id = seq.getClass().getSimpleName();
			}
		}
		return id;
	}
}
