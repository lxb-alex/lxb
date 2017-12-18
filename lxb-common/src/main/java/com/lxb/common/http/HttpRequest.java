package com.lxb.common.http;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @description HttpUrlConnect 工具类
 * 简介：
 * <li> 任何网络连接都需要经过socket才能连接，HttpURLConnection不需要设置socket，<li/>
 * <li> 所以，HttpURLConnection并不是底层的连接，而是在底层连接上的一个请求。<li/>
 * <li> 这就是为什么HttpURLConneciton只是一个抽象类，自身不能被实例化的原因。<li/>
 * <li> HttpURLConnection只能通过URL.openConnection()方法创建具体的实例。<li/>
 * <li> 虽然底层的网络连接可以被多个HttpURLConnection实例共享，但每一个HttpURLConnection实例只能发送一个请求。<li/>
 * <li> 请求结束之后，应该调用HttpURLConnection实例的InputStream或OutputStream的close()方法以释放请求的网络资源，<li/>
 * <li> 不过这种方式对于持久化连接没用。对于持久化连接，得用disconnect()方法关闭底层连接的socket。<li/>
 *
 * @author  Liaoxb
 * @date 2017/12/18 13:55:55
 */
public class HttpRequest {

    private static Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private static final String  DEFAULT_CHARSET = "utf-8";

    private static final String GET_URL_RE = "/dz/findList";

    /**
     * 设置 Request 请求头域相关信息
     * @param conn HttpURLConnection
     */
    private static void setRequestParams(HttpURLConnection conn){
        // 向被访问的网站提供你所使用的浏览器类型、操作系统及版本、CUP类型、浏览器渲染引擎、浏览器语言、插件等信息的标识
        // 浏览器标准UA格式：浏览器标识（操作系统;加密等级标识;浏览器语言）渲染引擎标识 版本信息
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0");
        // 设置参数内容允许的类型：设置参数内容可允许是 可序列化的java对象
        conn.setRequestProperty("Content-type", "application/x-java-serialized-object");
        conn.setRequestProperty("Content-type", "text/html;charset=utf-8");
        // 设置客户端接收服务器返回数据类型
        conn.setRequestProperty("Accept", "text/html");
        // 表示是否需要持久连接。（HTTP 1.1默认进行持久连接 Keep-Alive）
        conn.setRequestProperty("Connection", "close");
    }

    /**
     * HttpURLConnection Post 请求
     * @param requestUrl 被请求服务器地址
     * @param params 请求参数
     * @return String
     */
    private static String sendPost(String requestUrl, String params){
        try {
            URL url = new URL(requestUrl);
            // HttpURLConnection只能通过URL.openConnection()方法创建具体的实例。
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置请求信息
            setRequestParams(conn);
            // 设置超时
            conn.setConnectTimeout(1000*6);
            conn.setReadTimeout(1000*3);
            conn.setRequestMethod("POST");
            // post 方式必须参数
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 写出参数 格式xx=xx&yy=yy
            if (StringUtils.isNotEmpty(params)){
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.write(params.getBytes("utf-8"));
                out.flush();// 将所有的字节都写入潜在的流中
                out.close();
            }
            // 获取接口返回数据
            InputStream in = conn.getInputStream(); // 执行之前默认调用 conn.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null){
                result.append(line);
            }
            in.close();
            return result.toString();
        } catch (MalformedURLException e) {
            logger.error(e.getMessage()+" requestUrl 转 URL 错误");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HttpURLConnection Get 请求
     * @param requestUrl 被请求的服务器地址
     * @param params 请求参数
     * @return String
     */
    private static String sendGet(String requestUrl, String params){
        try {
            if (StringUtils.isNotEmpty(params)){
                requestUrl = requestUrl + "?" + params;
            }
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            setRequestParams(conn);
            conn.setRequestMethod("GET");// 默认GET
            conn.setConnectTimeout(1000*6);
            conn.setReadTimeout(1000*3);
            conn.setUseCaches(false);
            conn.connect();
            if (conn.getResponseCode()==200){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_CHARSET));
                String line;
                StringBuilder result = new StringBuilder();
                while ((line=bufferedReader.readLine()) != null){
                    result.append(line);
                }
                return result.toString();
            }
        } catch (MalformedURLException e) {
            logger.error(e.getMessage()+" requestUrl 转 URL 错误");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HttpURLConnection 请求
     * @param requestUrl 被请求服务器地址
     * @param params 请求参数
     * @param type 请求类型（POST\GET）
     * @return String
     */
    public static String sendHttpRequest(String requestUrl, String params, String type){
        if (type.toUpperCase().equals("POST")){
            return sendPost(requestUrl, params);
        }else {
            return sendGet(requestUrl, params);
        }
    }

}
