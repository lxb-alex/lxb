package com.lxb.sys.security;

import com.lxb.common.utils.CaptchaUtil;
import com.lxb.common.utils.MessageVo;
import com.lxb.common.utils.ShiroUtils;
import com.lxb.common.utils.StringUtils;
import com.lxb.sys.entity.SysUserEntity;
import com.lxb.sys.service.SysUserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/11/1 14:09:09
 */
@Controller
@RequestMapping
public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    SysUserService sysUserService;

    @RequestMapping(value = "/captcha.jpg")
    public void captcha(HttpServletResponse response) throws IOException {

        response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Set-Cookie", "name=value; HttpOnly");//设置HttpOnly属性,防止Xss攻击
        response.setDateHeader("Expire", 0);

        CaptchaUtil rdnu = CaptchaUtil.Instance(100, 36, 25);
        // 获取验证码文字
        String code = rdnu.getString();
        ShiroUtils.getSession().setAttribute(ShiroUtils.CACHE_CAPTCHA_CODE, code);
        //将验证码存入Session
        // 通过流的方式输出到浏览器
        BufferedImage bufferedImage = ImageIO.read(rdnu.getImage());
        OutputStream out = response.getOutputStream();
        ImageIO.write(bufferedImage, "jpg", out);
    }


    @RequestMapping(value = "/sys/login", method = RequestMethod.GET)
    public String login() {
        return "redirect:/login.html";
    }

    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo login(String account, String LoginPassword, String captcha, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        SysUserEntity principal = (SysUserEntity)subject.getPrincipal();
        if (principal != null){
            // 登录对象存入session
            ShiroUtils.setSessionCache(ShiroUtils.CACHE_LOGIN_USER, principal);
            return MessageVo.success("已经登录");
        }
        try {
           /* // 方式一： 使用默认的 UsernamePasswordToken UserAuthorizingRealm
            SecurityUtils.getSubject().login(new org.apache.shiro.authc.UsernamePasswordToken(account, LoginPassword));*/

            // 方式二：使用自定义的 UsernamePasswordToken UserRealm
            char[] password = LoginPassword.toCharArray();
            String host = StringUtils.getRemoteAddr(request);
            SecurityUtils.getSubject().login(new AccountPasswordToken(account, password,false, host, captcha));
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return MessageVo.success("登录认证成功");
    }

    @RequestMapping(value = "/sys/logout", method = RequestMethod.GET)
    public Object logout() {
        return "redirect:/login.html";
    }

    @RequestMapping(value = "/sys/logout", method = RequestMethod.POST)
    public String logout(String account, String LoginPassword, String captcha) {

        return "redirect:/login.html";
    }

    /**
     * 页面跳转
     *
     * @param module 模块
     * @param url 表名称
     * @return HTML页面
     */
    @RequestMapping("{module}/{url}.html")
    public String page(@PathVariable("module") String module, @PathVariable("url") String url) {
        return "/" + module + "/" + url + ".html";
    }
}
