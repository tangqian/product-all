package com.ofweek.live.core.modules.sys.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.ofweek.live.core.common.utils.SpringContextHolder;
import com.ofweek.live.core.modules.sys.dao.UserDao;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.security.SystemAuthorizingRealm.Principal;

/**
 * 用户工具类
 * 
 * @author tangqian
 * 
 */
public class UserUtils {

	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);

	/**
	 * 根据ID获取用户
	 * 
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id) {
		User user = userDao.get(id);
		return user;
	}
	
	/**
	 * 获取用户信息
	 * @param account
	 * @param type
	 * @return
	 */
	public static User get(String account, Integer type) {
		return userDao.getByAccount(account, type);
	}

	/**
	 * 获取当前用户
	 * 
	 * @return 取不到返回 new User()
	 */
	public static User getUser() {
		Principal principal = getPrincipal();
		if (principal != null) {
			User user = (User) getCache(principal.getId());
			if(user == null){
				user = get(principal.getId());
				putCache(principal.getId(), user);
			}
			return user;
		}
		return null;
	}

	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal) subject.getPrincipal();
			return principal;
		} catch (UnavailableSecurityManagerException e) {

		} catch (InvalidSessionException e) {

		}
		return null;
	}

	public static Session getSession() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) {
				session = subject.getSession();
			}
			return session;
		} catch (InvalidSessionException e) {

		}
		return null;
	}

	// ============== User Cache ==============

	private static Object getCache(String key) {
		return getCache(key, null);
	}

	private static Object getCache(String key, Object defaultValue) {
		Object obj = getSession().getAttribute(key);
		return obj == null ? defaultValue : obj;
	}

	private static void putCache(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	/*private static void removeCache(String key) {
		getSession().removeAttribute(key);
	}*/

}
