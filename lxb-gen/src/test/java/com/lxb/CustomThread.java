package com.lxb;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/10/30 0030 9:13:13
 */
public class CustomThread implements Runnable{

    public String msg = "first thread";

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
/*        System.out.println(msg);
        try {
            Thread.sleep(1000);
            System.out.println("---------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        customClass customClass2 = new customClass();
        customClass2.prints(3);
    }
}
