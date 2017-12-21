package com.lxb.sys.security;

import com.lxb.common.utils.ShiroUtil;
import com.lxb.sys.service.impl.SysUserServiceImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
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
public class userRealm extends AuthorizingRealm {

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

        return null;
    }

    private static class Principal implements Serializable {

        private int id; // 主键id
        private String account; // 登录账号
        private String password; // 登录密码
        private boolean mobileLogin; // 移动登录

        public Principal(int id, String account, String password, boolean mobileLogin) {
            this.id = id;
            this.account = account;
            this.password = password;
            this.mobileLogin = mobileLogin;
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

        public boolean isMobileLogin() {
            return mobileLogin;
        }

        public void setMobileLogin(boolean mobileLogin) {
            this.mobileLogin = mobileLogin;
        }

        public String getSessionId() {
            Subject subject = ShiroUtil.getSubject();
            Session session = subject.getSession();
            return session.getId().toString();
        }
    }
}