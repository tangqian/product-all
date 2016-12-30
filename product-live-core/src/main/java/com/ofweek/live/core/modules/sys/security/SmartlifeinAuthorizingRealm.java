package com.ofweek.live.core.modules.sys.security;

import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.ofweek.live.core.modules.sys.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;


public class SmartlifeinAuthorizingRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @PostConstruct
    public void initCredentialsMatcher() {
        setCredentialsMatcher(new AccountCredentialsMatcher());
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        SmartlifeinToken token = (SmartlifeinToken) authcToken;
        User user = userService.get(token.getUsername(), token.getType());
        if(user == null || !token.getPassword().equals(user.getPassword())){
            throw new UnknownAccountException("用户名或密码不正确,username:" + token.getUsername());
        }
        return new SimpleAccount(new Principal(user, false), token.getUsername(), getName());
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
