package com.lxb.thread;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/11/23 0023 15:56:56
 */
public class CustomThreadOne implements Runnable {

    private long tickets = Long.MAX_VALUE/200;
    private Long start = null;
    private Long end = null;

    @Override
    public void run() {
        if (tickets==Long.MAX_VALUE/200){
            start = System.currentTimeMillis();
        }int i = 0;
        while (tickets>0){
            tickets = tickets - 2100000000;
            i++;
            if (tickets%2000000000==0) {
                System.out.print("");
            }
            if (i>=1000){
                i=0;
                Thread.currentThread().getName();
                System.out.println("-");
            }
        }
        if (tickets<=0){
            System.out.println("================================================= user Date  "+ (System.currentTimeMillis() - start));
        }
    }
}

    /** 多线程的执行速度是单一主线程执行速度的N倍 */

class testRunnable{
    public static void main(String[] args) {
        // Runnable 类对象直接调用run()方法执行的是请求的主线程，并未开启线程
        // Runnable 线程共享类属性
        CustomThreadOne cto = new CustomThreadOne();
        Thread thread = new Thread(cto);
        Thread thread2 = new Thread(cto);
        Thread thread3 = new Thread(cto);
        Thread thread4 = new Thread(cto);
        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();

        testDemo demo = new testDemo();
        demo.demo();
        // 由以上两个方法得出结论：多线程工作的速度是单一主线程的速度的N倍

    }
}

class testDemo{

    private long tickets = Long.MAX_VALUE/200;
    private Long start = null;

    public void demo(){
        if (tickets==Long.MAX_VALUE/200){
            start = System.currentTimeMillis();
        }
        int i =0;
        while (tickets>0){
            tickets = tickets - 2100000000;
            i++;
            if (tickets%2000000000==0) {
                System.out.print("");
            }
            if (i>=1000){
                i=0;
                Thread.currentThread().getName();
                System.out.println(".");
            }
        }
        if (tickets<=0){
            System.out.println("");
            System.out.println("==================================================== 2  user Date  "+ (System.currentTimeMillis() - start));
        }
    }
}


