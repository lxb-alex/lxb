package com.lxb.sys.controller.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/11/14 0014 16:26:26
 */
public class SystemAuthorizingRealm extends AuthorizingRealm{

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1.在此处统一认证登录（不同类型用户登录时需要多个认证）
        // 2.在登录Controller类中认证（对不同类型用户登录没影响）
       /* // 转换为自定义的Token类，自定义类中添加了登录字段（username，password，logingtype，isMobileLogin()）
        //UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 校验登录验证码
        if (LoginController.isValidateCodeLogin(token.getUsername(), false, false)){
            Session session = ShiroUtil.getSession();
            String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
            if (authenticationToken.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)){
                throw new AuthenticationException("msg:验证码错误, 请重试.");
            }
        }

        // 校验用户名密码
        User user = getSystemService().getUserByLoginName(authenticationToken.getUsername());
        if (user != null) {
            if (Global.NO.equals(user.getLoginFlag())){
                throw new AuthenticationException("msg:该已帐号禁止登录.");
            }
            byte[] salt = Encodes.decodeHex(user.getPassword().substring(0,16));
            return new SimpleAuthenticationInfo(new Principal(user, authenticationToken.isMobileLogin()),
                    user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
        } else {
            return null;
        }*/
        return null;
    }
}
