package com.ofweek.live.core.modules.sys.security;

import com.ofweek.live.core.modules.audience.entity.Audience;
import com.ofweek.live.core.modules.audience.service.AudienceService;
import com.ofweek.live.core.modules.rpc.InvokeSmartlifein;
import com.ofweek.live.core.modules.rpc.dto.UserDto;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.ofweek.live.core.modules.sys.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;

/**
 * @author tangqian
 */
public class AccountAuthorizingRealm extends AuthorizingRealm {

    @Resource
    private AudienceService audienceService;

    @Resource
    private UserService userService;

    /**
     * 系统管理员自动登录标识,通过点击后台房间主题上链接,会在前台系统自动登录id为1的管理员账号
     */
    public static String ADMIN_LOGIN_FLAG = "1";

    /**
     * 设定密码校验的Hash算法与迭代次数
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        setCredentialsMatcher(new AccountCredentialsMatcher());
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AccountToken accountToken = (AccountToken) token;
        if (ADMIN_LOGIN_FLAG.equals(accountToken.getAccount())) {//管理员自动登录
            User user = userService.get("1");
            accountToken.setAccount(user.getAccount());
            return new SimpleAccount(new Principal(user, false), accountToken.getAccount(), getName());
        } else {
            UserDto userDto = InvokeSmartlifein.getUser(accountToken.getAccount());
            if (userDto != null) {
                Audience audience = audienceService.save(userDto);
                return new SimpleAccount(new Principal(audience.getUser(), false), accountToken.getAccount(), getName());
            }
        }
        return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 添加用户权限
        info.addStringPermission("user");
        return info;
    }

    private class AccountCredentialsMatcher implements CredentialsMatcher {

        @Override
        public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
            if (token != null && info != null) {
                Principal principal = (Principal) getAvailablePrincipal(info.getPrincipals());
                if (token.getPrincipal().equals(principal.getAccount())) {
                    return true;
                }
            }
            return false;
        }

        protected Object getAvailablePrincipal(PrincipalCollection principals) {
            Object primary = null;
            if (!CollectionUtils.isEmpty(principals)) {
                @SuppressWarnings("rawtypes")
                Collection thisPrincipals = principals.fromRealm(getName());
                if (!CollectionUtils.isEmpty(thisPrincipals)) {
                    primary = thisPrincipals.iterator().next();
                } else {
                    //no principals attributed to this particular realm.  Fall back to the 'master' primary:
                    primary = principals.getPrimaryPrincipal();
                }
            }

            return primary;
        }

    }

}
