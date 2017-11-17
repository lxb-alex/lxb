package com.lxb.common.utils;

import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.security.Principal;
import java.util.Map;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/11/15 0015 11:01:01
 */
public class ShiroUtil {

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
//			subject.logout();
        }catch (InvalidSessionException e){

        }
        return null;
    }

    public static Object getSession(String key) {
        Object obj = getSession().getAttribute(key);
        return obj;
    }

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal)subject.getPrincipal();
            if (principal != null){
                return principal;
            }
//			subject.logout();
        }catch (UnavailableSecurityManagerException e) {

        }catch (InvalidSessionException e){

        }
        return null;
    }

    /**============================== cache 操作 ================================*/

    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
        Object obj = getCacheMap().get(key);
        return obj==null?defaultValue:obj;
    }

    public static void putCache(String key, Object value) {
        getCacheMap().put(key, value);
    }

    public static void removeCache(String key){
        getCacheMap().remove(key);

    }

    public static Map<String, Object> getCacheMap(){
        Map<String, Object> map = Maps.newHashMap();
        Principal principal = (Principal) getSubject().getPrincipal();
//        return principal != null ? principal.getCacheMap():map;
return null;
    }

    /**============================== 获取当前登录用户（用于操作用户） ================================*/

    public static Map<String, Object> getCurrentLoginUser(){
        Object user = getSession(Constant.SESSION_LOGIN_USER);
        if (user!=null){
            Map map = ObjectConvert.objectToMap(user);
            return map;
        }
        return null;
    }
}
