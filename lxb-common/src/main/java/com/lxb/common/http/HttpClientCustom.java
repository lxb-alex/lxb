package com.lxb.common.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/12/18 0018 16:16:16
 */
public class HttpClientCustom {

    private static final String DEFAULT_CHARSET = "utf-8";

    /**
     * HttpClient POST 请求
     * @param requestUrl 被请求的服务器地址
     * @param paramList 一个键值对实体对象的 List
     * @return String
     */
    public static String post (String requestUrl, List<BasicNameValuePair> paramList){
        // 创建HTTPClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(requestUrl);
        // 构造消息头
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setHeader("Connection", "Close");
        try {
            // 构造消息实体
            // 构造一个表单实体
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(paramList, DEFAULT_CHARSET);
            httpPost.setEntity(encodedFormEntity);
            // 得到返回对象
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            httpResponse.close();
            if (entity != null){
                return EntityUtils.toString(entity, DEFAULT_CHARSET);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try { // 关闭连接，释放资源
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * HTTPClient GET 方式
     * @param requestUrl 被请求的服务器地址
     * @return String
     */
    public static String get (String requestUrl){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(requestUrl);
        // 构造消息头
        httpGet.setHeader("Content-type", "application/json; charset=utf-8");
        httpGet.setHeader("Connection", "Close");
        try {
            // 调用返回 消息对象
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            httpResponse.close();
            if (entity!=null){
                return EntityUtils.toString(entity, DEFAULT_CHARSET);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try { // 关闭连接，释放资源
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * HttpClient  方式上传文件
     * @param file 上传的文件
     * @param ftpUrl 存放文件的服务器的ftp地址
     */
    public static void upload(File file, String ftpUrl){
        // 创建一个httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(ftpUrl);
        // 封装要上传的文件
        FileBody bin = new FileBody(file);
        // 上传注释说明
        StringBody comment = new StringBody("二进制文件上传", ContentType.TEXT_PLAIN);
        HttpEntity requestEntity = MultipartEntityBuilder.create().addPart("bin", bin).addPart("comment",comment).build();
        httpPost.setEntity(requestEntity);
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity responseEntity = httpResponse.getEntity();
            if (responseEntity != null){
                System.out.println( responseEntity.getContentLength());
            }
            EntityUtils.consume(requestEntity);
            httpResponse.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try { // 关闭连接，释放资源
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
