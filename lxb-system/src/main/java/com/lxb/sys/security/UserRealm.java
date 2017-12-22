package com.lxb.sys.security;

import com.lxb.common.utils.ShiroUtil;
import com.lxb.sys.entity.SysUserEntity;
import com.lxb.sys.service.impl.SysUserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/12/21 0021 17:07:07
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserServiceImpl userService;

    /**
     * 登录成功，判断用户具有的权限（如：查询具有的目录）
     *
     * @param principalCollection principal
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 登录时认证
     *
     * @param authenticationToken token
     * @return SimpleAuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        AccountPasswordToken token = (AccountPasswordToken) authenticationToken;
        Session session = ShiroUtil.getSession();
        String code = (String)session.getAttribute(ShiroUtil.CAPTCHA_CODE);
        if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)){
            throw new AuthenticationException("msg:验证码错误, 请重试.");
        }
        String account = token.getAccount();
        String password = token.getPassword().toString();
        SysUserEntity user = userService.getSysUserEntity(account, password);
        if (user == null){
            throw new UnknownAccountException("msg: 账号不存在");
        }else {
            return new SimpleAuthenticationInfo(new Principal(user), user.getPassword(), user.getName());
        }
    }

    private static class Principal implements Serializable {

        private int id; // 主键id
        private String account; // 登录账号
        private String password; // 登录密码

        public Principal(SysUserEntity user) {
            this.id = user.getIsDeleted();
            this.account = user.getAccout();
            this.password = user.getPassword();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSessionId() {
            Subject subject = ShiroUtil.getSubject();
            Session session = subject.getSession();
            return session.getId().toString();
        }
    }
}