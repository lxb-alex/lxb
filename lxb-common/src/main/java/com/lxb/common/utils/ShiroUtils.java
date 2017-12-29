package com.lxb.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.Map;

/**
 * @description
 * @author Liaoxb
 * @date 2017/11/15 0015 11:01:01
 */
public class ShiroUtils {

    public static final String CACHE_CAPTCHA_CODE = "cache_captcha_code";
    public static final String CACHE_LOGIN_USER = "cache_login_user";

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录者实体对象
     */
    public static Object getPrincipal(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Object principal = subject.getPrincipal();
            if (principal != null){
                return principal;
            }
        }catch (UnavailableSecurityManagerException e) {

        }catch (InvalidSessionException e){

        }
        return null;
    }


    /**============================== cache 操作 ================================*/
    /**
     * 获取session
     */
    public static Session getSession(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null){
                session = subject.getSession();
            }
            if (session != null){
                return session;
            }
        }catch (InvalidSessionException e){

        }
        return null;
    }

    public static Object getSessionCache(String key) {
        Object obj = getSession().getAttribute(key);
        return obj;
    }

    public static void setSessionCache(Object key, Object value){
        getSession().setAttribute(key, value);
    }

    public static void removeSessionCache(String key){
        getSession().removeAttribute(key);
    }

    /**============================== 获取当前登录用户（用于操作用户） ================================*/

    public static Map<String, Object> getCurrentLoginUser(){
        Object user = getSessionCache(CACHE_LOGIN_USER);
        if (user!=null){
            Map map = ObjectConvert.objectToMap(user);
            return map;
        }
        return null;
    }
}
