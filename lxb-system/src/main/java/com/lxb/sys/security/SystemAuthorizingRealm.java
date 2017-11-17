package com.lxb.sys.security;

import com.lxb.common.utils.AESUtil;
import com.lxb.sys.entity.SysUserEntity;
import com.lxb.sys.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/11/14 0014 16:26:26
 */
@Component
@DependsOn(value = "sysUserDao")
public class SystemAuthorizingRealm extends AuthorizingRealm{

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
/*        // 校验登录验证码
        if (LoginController.isValidateCodeLogin(token.getUsername(), false, false)){
            Session session = ShiroUtil.getSession();
            String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
            if (authenticationToken.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)){
                throw new AuthenticationException("msg:验证码错误, 请重试.");
            }
        }*/

        // 获取校验用户名密码
        String password = AESUtil.AESDecode(token.getPassword().toString());
        String username = token.getUsername();
        if ("unknown".equals(username)) {
            throw new UnknownAccountException("用户不存在");
        }
        SysUserEntity user = sysUserService.getSysUserEntity(username, password);
        if (user != null) {
            // 返回认证后的信息 SimpleAuthenticationInfo（PrincipalCollection，credentials）
            return new SimpleAuthenticationInfo(user.getAccout(), user.getPassword(), getName());
        } else {
            throw new UnknownAccountException("账号不存在");
        }
    }
}
