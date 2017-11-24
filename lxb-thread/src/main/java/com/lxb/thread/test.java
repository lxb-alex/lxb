package com.lxb.thread;

/** TicketThreadR.java     * *  @version ： 1.1 *   *  @author  ： 苏若年    <a href="mailto:DennisIT@163.com">发送邮件</a> *     *  @since     ： 1.0        创建时间:    2013-2-24        下午02:29:23 *      *  TODO     :    class TicketThreadR.java is used for ... * */
public class test implements Runnable {
    private int num = 5;            //总共票数设定为5张

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            sale();                    //调用同步方法
        }
    }

    //使用同步方法
    public synchronized void sale() {
        try {
            Thread.sleep(300);    //休息300毫秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (this.num > 0) {                //打印买票信息
            System.out.println(Thread.currentThread().getName() + "买票: " + this.num--);
        }
    }

    public static void main(String[] args) {
        test t = new test();
        new Thread(t, "售票口一").start();
        //线程一
        new Thread(t, "售票口二").start();
        // 线程一
        new Thread(t, "售票口三").start();
        // 线程一
    }
}