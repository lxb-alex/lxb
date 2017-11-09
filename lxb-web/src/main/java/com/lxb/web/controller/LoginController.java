package com.lxb.web.controller;

import com.lxb.common.utils.AESUtil;
import com.lxb.common.utils.CaptchaUtil;
import com.lxb.common.utils.MessageVo;
import com.lxb.common.utils.StringUtil;
import com.lxb.web.entity.SysUserEntity;
import com.lxb.web.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
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

    @Autowired
    SysUserService sysUserService;

    @RequestMapping(value = "/captcha.jpg")
    public void captcha(HttpServletResponse response) throws IOException {

        response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Set-Cookie", "name=value; HttpOnly");//设置HttpOnly属性,防止Xss攻击
        response.setDateHeader("Expire", 0);

        CaptchaUtil rdnu = CaptchaUtil.Instance(100,36, 25);
        // 获取验证码文字
        String code = rdnu.getString();
        //将验证码存入Session
        // 通过流的方式输出到浏览器
        BufferedImage bufferedImage = ImageIO.read(rdnu.getImage());
        OutputStream out = response.getOutputStream();
        ImageIO.write(bufferedImage,"jpg", out);
    }


    @RequestMapping(value = "/sys/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    @ResponseBody
    public MessageVo login(String account, String LoginPassword, String captcha){

/*        if (StringUtil.isBlank(captcha)){
            return MessageVo.error("验证码错误");
        }

        String password = AESUtil.AESEncode(LoginPassword);
        SysUserEntity user = sysUserService.getSysUserEntity(account, password);
        if (user!=null){*/
            return MessageVo.success();
//        }
//        return MessageVo.error("用户名或密码错误");
    }
}
