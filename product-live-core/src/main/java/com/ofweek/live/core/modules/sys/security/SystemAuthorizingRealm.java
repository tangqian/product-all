package com.ofweek.live.core.modules.sys.security;

import com.google.common.collect.Maps;
import com.ofweek.live.core.common.config.Global;
import com.ofweek.live.core.common.servlet.ValidateCodeServlet;
import com.ofweek.live.core.common.utils.CacheUtils;
import com.ofweek.live.core.common.utils.Encodes;
import com.ofweek.live.core.modules.audience.entity.Audience;
import com.ofweek.live.core.modules.audience.service.AudienceService;
import com.ofweek.live.core.modules.rpc.InvokeSmartlifein;
import com.ofweek.live.core.modules.rpc.dto.UserDto;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.core.modules.sys.service.SystemService;
import com.ofweek.live.core.modules.sys.service.UserService;
import com.ofweek.live.core.modules.sys.utils.UserUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 系统认证授权实现类
 * 
 * @author tangqian
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private SystemService systemService;

	@Resource
	private UserService userService;

	@Resource
	private AudienceService audienceService;

	/**
	 * 默认通行密码,保证经过ofweek验证通过的观众永远能登录成功，对应明文为123456
	 */
	private static final String DEFAULT_PWD = "0f4f6729665fb527debcc93bdb78c097af8aa4714998d27ef496274e";
	
	private static final String DEFAULT_PLAIN_TEXT = "123456";

	private static final String suffixPwd;

	private static final ByteSource byteSource;

	static {
		suffixPwd = DEFAULT_PWD.substring(16);
		byte[] salt = Encodes.decodeHex(DEFAULT_PWD.substring(0, 16));
		byteSource = ByteSource.Util.bytes(salt);
	}

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		LiveUsernamePasswordToken token = (LiveUsernamePasswordToken) authcToken;

		int activeSessionSize = systemService.getSessionDao().getActiveSessions(false).size();
		if (logger.isDebugEnabled()) {
			logger.debug("login submit, active session size: {}, username: {}", activeSessionSize, token.getUsername());
		}

		// 校验登录验证码
		if (isValidateCodeLogin(token.getUsername(), token.getType())){
			Session session = UserUtils.getSession();
			String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
			if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)){
				logger.error("用户输入验证码不匹配!, sessionCode={}, inputCode={}", code, token.getCaptcha());
				throw new AuthenticationException("msg:验证码错误, 请重试.");
			}
		}

		// 校验用户名密码
		if (UserTypeEnum.isAudience(token.getType())) {
			UserDto userDto = InvokeSmartlifein.login(token.getUsername(), new String(token.getPassword()), token.getResponse());
			if (userDto != null) {
				Audience audience = audienceService.save(userDto);
				token.setPassword(DEFAULT_PLAIN_TEXT.toCharArray());
				return new SimpleAuthenticationInfo(new Principal(audience.getUser(), token.isMobileLogin()), suffixPwd, byteSource, getName());
			} else {
				return null;
			}
		} else {
			User user = userService.getSpeakerOrWaiter(token.getUsername());
			if (user != null) {
				byte[] salt = Encodes.decodeHex(user.getPassword().substring(0, 16));
				return new SimpleAuthenticationInfo(new Principal(user, token.isMobileLogin()), user.getPassword().substring(16),
						ByteSource.Util.bytes(salt), getName());
			} else {
				return null;
			}
		}
	}

	public static boolean isValidateCodeLogin(String account, Integer type) {
		String failKey = account + "_" + type;
		Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
		if (loginFailMap == null) {
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(failKey);
		if (loginFailNum == null) {
			loginFailNum = 0;
		}
		return loginFailNum >= 3;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		// 获取当前已登录的用户
		if (!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))) {
			Collection<Session> sessions = systemService.getSessionDao().getActiveSessions(true, principal, UserUtils.getSession());
			if (sessions.size() > 0) {
				// 如果是登录进来的，则踢出已在线用户
				if (UserUtils.getSubject().isAuthenticated()) {
					for (Session session : sessions) {
						systemService.getSessionDao().delete(session);
					}
				}
				// 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
				else {
					UserUtils.getSubject().logout();
					throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
				}
			}
		}
		User user = userService.get(principal.getId());
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 添加用户权限
			info.addStringPermission("user");
			// 更新登录IP和时间
			// getSystemService().updateUserLoginInfo(user);
			return info;
		} else {
			return null;
		}
	}

	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}

	@Override
	protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
			for (Permission permission : permissions) {
				authorizationValidate(permission);
			}
		}
		return super.isPermitted(permissions, info);
	}

	@Override
	public boolean isPermitted(PrincipalCollection principals, Permission permission) {
		authorizationValidate(permission);
		return super.isPermitted(principals, permission);
	}

	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
			for (Permission permission : permissions) {
				authorizationValidate(permission);
			}
		}
		return super.isPermittedAll(permissions, info);
	}

	/**
	 * 授权验证方法
	 * 
	 * @param permission
	 */
	private void authorizationValidate(Permission permission) {
		// 模块授权预留接口
	}

	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SystemService.HASH_ALGORITHM);
		matcher.setHashIterations(SystemService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}

	// /**
	// * 清空用户关联权限认证，待下次使用时重新加载
	// */
	// public void clearCachedAuthorizationInfo(Principal principal) {
	// SimplePrincipalCollection principals = new
	// SimplePrincipalCollection(principal, getName());
	// clearCachedAuthorizationInfo(principals);
	// }

	/**
	 * 清空所有关联认证
	 * 
	 * @Deprecated 不需要清空，授权缓存保存到session中
	 */
	@Deprecated
	public void clearAllCachedAuthorizationInfo() {
		// Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		// if (cache != null) {
		// for (Object key : cache.keys()) {
		// cache.remove(key);
		// }
		// }
	}

	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;

		private String id; // 编号
		private String account; // 用户名
		private Integer type; // 用户类型
		private boolean mobileLogin; // 是否手机登录

		public Principal(User user, boolean mobileLogin) {
			id = user.getId();
			account = user.getAccount();
			type = user.getType();
			this.mobileLogin = mobileLogin;
		}

		public String getId() {
			return id;
		}

		public String getAccount() {
			return account;
		}

		public Integer getType() {
			return type;
		}

		public boolean isMobileLogin() {
			return mobileLogin;
		}

		/**
		 * 获取SESSIONID
		 */
		public String getSessionid() {
			try {
				return (String) UserUtils.getSession().getId();
			} catch (Exception e) {
				return "";
			}
		}

		@Override
		public String toString() {
			return id;
		}

	}
}
