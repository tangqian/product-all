package com.ofweek.live.core.modules.sys.service;

import com.ofweek.live.core.common.utils.CookieUtils;
import com.ofweek.live.core.modules.sys.utils.WebUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;

import com.ofweek.live.core.modules.sys.dao.VisitorDao;
import com.ofweek.live.core.modules.sys.entity.Visitor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class VisitorService extends CrudService<VisitorDao, Visitor> {

	private static final String VISITOR_COOKIE_KEY = "live_visitor";

	private static final int VISITOR_COOKIE_EXPIRE = 60 * 60 * 24 * 365;
	
	@Override
	@Transactional(readOnly = false)
	public void save(Visitor entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(SequenceUtils.getNextString("LiveVisitor"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}

	@Transactional(readOnly = false)
	public Visitor save(HttpServletRequest request, HttpServletResponse response) {
		String idStr = WebUtils.getCookieValue(request, VISITOR_COOKIE_KEY);
		Visitor visitor = dao.get(idStr);
		if (visitor == null) {
			visitor = new Visitor();
			visitor.setIp(WebUtils.getIp(request));
			save(visitor);
			CookieUtils.setCookie(response, VISITOR_COOKIE_KEY, visitor.getId(), "/", VISITOR_COOKIE_EXPIRE);
		}
		return visitor;
	}
    
}
