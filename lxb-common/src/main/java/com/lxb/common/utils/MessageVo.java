package com.lxb.common.utils;

import java.util.List;

/**
 * @Description 返回消息工具类
 * @Author Liaoxb
 * @Date 2017/10/20 10:01:01
 */
public class MessageVo {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    private Integer code;   // 返回执行代码 （200、404..）
    private String status;  // 返回执行后状态 （SUCCESS、ERROR..）
    private String msg;     // 返回执行后消息
    private List<Object> list;// 返回执行后内容
    private Object obj;

    public MessageVo() {
    }

    public MessageVo(Integer code, String status, String msg, List<Object> list) {
        this.code = code;
        this.status = status;
        this.msg = msg;
        this.list = list;
    }

    public MessageVo(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public MessageVo(String status, Object obj) {
        this.status = status;
        this.obj = obj;
    }

    public static MessageVo success(){
        return success("操作成功");
    }
    public static MessageVo success(String msg){
        return new MessageVo(200, "SUCCESS", msg, null);
    }
    public static MessageVo error(){
        return error("操作失败");
    }
    public static MessageVo error(String msg){
        return new MessageVo(0, "ERROR", msg, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MessageVo{");
        sb.append("code=").append(code);
        sb.append(", status='").append(status).append('\'');
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", list=").append(list);
        sb.append(", obj=").append(obj);
        sb.append('}');
        return sb.toString();
    }
}
