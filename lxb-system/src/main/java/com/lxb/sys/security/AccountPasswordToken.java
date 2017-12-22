package com.lxb.sys.security;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @description 自定义登录验证Token
 * @author Liaoxb
 * @date 2017/12/21 17:40:40
 */
public class AccountPasswordToken extends UsernamePasswordToken{

    private static final long serialVersionUID = 1L;

    private String account; // 账号
    private String captcha;

    public AccountPasswordToken() {
        super();
    }

    public AccountPasswordToken(String username, char[] password, boolean rememberMe, String account, String captcha) {
        super(username, password, rememberMe);
        this.account = account;
        this.captcha = captcha;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
