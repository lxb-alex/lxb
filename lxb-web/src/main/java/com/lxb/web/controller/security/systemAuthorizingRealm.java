package com.lxb.web.controller.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/11/14 0014 16:26:26
 */
@Service(value = "systemAuthorizingRealm")
@DependsOn({ "sysUserDao", "sysRoleDao", "sysMenuDao" })
public class systemAuthorizingRealm extends AuthorizingRealm{

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Principal principal = (Principal) getAvailablePrincipal(principalCollection);
        return null;
    }

    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
