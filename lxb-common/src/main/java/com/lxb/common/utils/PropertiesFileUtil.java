package com.lxb.common.utils;

import org.springframework.beans.BeansException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/9/30 15:08:08
 */
public class PropertiesFileUtil {

    // 当打开多个资源文件时，缓存资源文件
    private static HashMap<String, PropertiesFileUtil> configMap = new HashMap<String, PropertiesFileUtil>();
    // 打开文件时间，判断超时使用
    private Date loadTime = null;
    // 资源文件
    private ResourceBundle resourceBundle = null;
    // 默认资源文件名称
    private static final String NAME = "config";
    // 缓存时间
    private static final Integer TIME_OUT = 60 * 1000;

    // 私有构造方法，创建单例
    private PropertiesFileUtil(String name) {
        this.loadTime = new Date();
        this.resourceBundle = ResourceBundle.getBundle(name);
    }

    public static synchronized PropertiesFileUtil getInstance() {
        return getInstance(NAME);
    }

    public static synchronized PropertiesFileUtil getInstance(String name) {
        PropertiesFileUtil conf = configMap.get(name);
        if (null == conf) {
            conf = new PropertiesFileUtil(name);
            configMap.put(name, conf);
        }
        // 判断是否打开的资源文件是否超时1分钟
        if ((new Date().getTime() - conf.getLoadTime().getTime()) > TIME_OUT) {
            conf = new PropertiesFileUtil(name);
            configMap.put(name, conf);
        }
        return conf;
    }

    // 根据key读取value
    public String get(String key) {
        try {
            String value = resourceBundle.getString(key);
            return value;
        }catch (MissingResourceException e) {
            return "";
        }
    }

    // 根据key读取value(整形)
    public Integer getInt(String key) {
        try {
            String value = resourceBundle.getString(key);
            return Integer.parseInt(value);
        }catch (MissingResourceException e) {
            return null;
        }
    }

    // 根据key读取value(布尔)
    public boolean getBool(String key) {
        try {
            String value = resourceBundle.getString(key);
            if ("true".equals(value)) {
                return true;
            }
            return false;
        }catch (MissingResourceException e) {
            return false;
        }
    }

    public Date getLoadTime() {
        return loadTime;
    }


    private static final String CHARSET = "UTF-8";
    /**
     * 	根据key 获取properties中的值<br/>
     * 	通过ResourceBundle方式，这种方式会出现中文乱码
     * 	@param key properties 中的key
     * */
    public static String getValue(String key) throws BeansException {
        // ResourceBundle 不写文件后缀
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        return resourceBundle.getString(key);
    }

    /**
     * 	根据key 获取properties中的值
     * 	通过jdk的Properties方式，可以防止中文乱码
     * 	@param key properties 中的key
     * */
    public static String getPropertiesValue(String key){
        Properties prop = new Properties();
        InputStream in = PropertiesFileUtil.class.getClassLoader().getResourceAsStream ("config.properties");
        try {
            prop.load(new InputStreamReader(in, CHARSET));
            String value = prop.get(key).toString().trim();
            return value;
        } catch (Exception e) {
            System.out.println(e.getMessage()+"获取配置文件信息异常");
            e.printStackTrace();
        }
        return null;
    }

}
