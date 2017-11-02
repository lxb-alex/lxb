package com.lxb.common.utils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/11/1 0001 14:40:40
 */
public class CaptchaUtil {
    /**
     * 图片
     */
    private ByteArrayInputStream image;

    /**
     * 验证码
     */
    private String str;
    /**
     * 图片宽度
     */
    private static int width = 58;
    /**
     * 图片高度
     */
    private static int height = 20;

    /**
     *  创建图片验证码
     * */
    private CaptchaUtil()
    {
        init();
    }

    /**
     * 取得ValidateCodeCreator实例
     * 返回图片验证码 (默认58*20)
     */
    public static CaptchaUtil Instance()
    {
        return new CaptchaUtil();
    }

    /**
     * 取得ValidateCodeCreator实例
     * 返回图片验证码 (默认58*20)
     */
    public static CaptchaUtil Instance(int image_width, int image_height)
    {
        width = image_width;
        height = image_height;
        return new CaptchaUtil();
    }

    /**
     * 取得验证码图片
     */
    public ByteArrayInputStream getImage()
    {
        return this.image;
    }

    /**
     * 取得图片的验证码
     */
    public String getString()
    {
        return this.str;
    }

    private void init()
    {
        // 在内存中创建图象
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 生成随机类
        Random random = new Random();
        //设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++)
        {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 取随机产生的认证码(6位数字)
        String sRand = "";
        for (int i = 0; i < 4; i++)
        {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));
            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand, 13 * i + 6, 16);
        }
        // 赋值验证码
        this.str = sRand;

        // 图象生效
        g.dispose();
        ByteArrayInputStream input = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try
        {
            ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
            ImageIO.write(image, "JPEG", imageOut);
            imageOut.close();
            input = new ByteArrayInputStream(output.toByteArray());
        }
        catch (Exception e)
        {
            System.out.println("验证码图片产生出现错误：" + e.toString());
        }

        this.image = input;/* 赋值图像 */
    }

    /*
     * 给定范围获得随机颜色
     */
    private Color getRandColor(int fc, int bc)
    {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 调用输出验证码图片到页面
     * */
    /*public void validateCode(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Set-Cookie", "name=value; HttpOnly");//设置HttpOnly属性,防止Xss攻击
        response.setDateHeader("Expire", 0);

        CaptchaUtil rdnu = CaptchaUtil.Instance();
        InputStream inputStream = rdnu.getImage();			// 取得带有随机字符串的图片
        //将验证码存入Session
        request.getSession().removeAttribute("validateCode");
        request.getSession().setAttribute("validateCode",rdnu.getString());
        // 通过流的方式输出到浏览器
        try {
            OutputStream out = response.getOutputStream();
            int length = 0;
            byte buffer[] = new byte[1024];
            while((length = inputStream.read(buffer)) != -1){
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
