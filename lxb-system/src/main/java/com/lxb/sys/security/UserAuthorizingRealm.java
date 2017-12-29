package com.lxb.sys.security;

import com.lxb.common.utils.AESUtil;
import com.lxb.sys.entity.SysUserEntity;
import com.lxb.sys.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description
 * @author Liaoxb
 * @date 2017/11/14 0014 16:26:26
 */
@Service
public class UserAuthorizingRealm extends AuthorizingRealm{

    @Autowired
    private SysUserService sysUserService;
    /**
     * 授权查询（账号具有的权限查询）回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 登录时账户认证调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1.在此处统一认证登录（不同类型用户登录时需要多个认证）
        // 2.在登录Controller类中认证（对不同类型用户登录没影响）
        // 转换为自定义的Token类，自定义类中添加了登录字段（username，password，logingtype，isMobileLogin()）
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        // 获取校验用户名密码
        SysUserEntity user = sysUserService.getSysUserEntity(token.getUsername());
        if (user != null){
            String password = String.valueOf(token.getPassword());
            // 返回认证后的信息 SimpleAuthenticationInfo（PrincipalCollection，credentials）
            return new SimpleAuthenticationInfo(user, AESUtil.AESDecode(user.getPassword()), getName());
        }
        throw new UnknownAccountException("账号不存在");
    }
}
