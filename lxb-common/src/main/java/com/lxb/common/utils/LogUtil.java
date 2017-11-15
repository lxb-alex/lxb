package com.lxb.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/11/15 0015 11:10:10
 */
public class LogUtil {

    public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";

 /*   *//**
     * 保存日志
     *//*
    public static void saveLog(HttpServletRequest request, String title){
        saveLog(request, null, null, title);
    }

    *//**
     * 保存日志
     *//*
    public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title){
        Map userMap = ShiroUtil.getCurrentLoginUser();
        if (userMap != null && userMap.get("id") != null){
            Log log = new Log();
            log.setTitle(title);
            log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
            log.setRemoteAddr(StringUtils.getRemoteAddr(request));
            log.setUserAgent(request.getHeader("user-agent"));
            log.setRequestUri(request.getRequestURI());
            log.setParams(request.getParameterMap());
            log.setMethod(request.getMethod());
            // 异步保存日志
            new SaveLogThread(log, handler, ex).start();
        }
    }

    *//**
     * 保存日志线程
     *//*
    public static class SaveLogThread extends Thread{

        private Log log;
        private Object handler;
        private Exception ex;

        public SaveLogThread(Log log, Object handler, Exception ex){
            super(SaveLogThread.class.getSimpleName());
            this.log = log;
            this.handler = handler;
            this.ex = ex;
        }

        @Override
        public void run() {
            // 获取日志标题
            if (StringUtils.isBlank(log.getTitle())){
                String permission = "";
                if (handler instanceof HandlerMethod){
                    Method m = ((HandlerMethod)handler).getMethod();
                    RequiresPermissions rp = m.getAnnotation(RequiresPermissions.class);
                    permission = (rp != null ? StringUtils.join(rp.value(), ",") : "");
                }
                log.setTitle(getMenuNamePath(log.getRequestUri(), permission));
            }
            // 如果有异常，设置异常信息
            log.setException(Exceptions.getStackTraceAsString(ex));
            // 如果无标题并无异常日志，则不保存信息
            if (StringUtils.isBlank(log.getTitle()) && StringUtils.isBlank(log.getException())){
                return;
            }
            // 保存日志信息
            log.preInsert();
            logDao.insert(log);
        }
    }
*/
}
