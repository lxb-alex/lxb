package com.lxb.thread;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/11/23 0023 16:00:00
 */
public class CustomThread {

    /**
     * 通过Runnable实现线程较于通过Thread方法实现线程的优势
     *  1、避免单继承的局限，一个类可以实现多个接口
     *  2、适合资源的共享
     * */

    public static void main(String[] args) {
        // 测试 Thread Runnable 实现的线程是否共享数据
/*        ThreadDemo t1 = new ThreadDemo();
        ThreadDemo t2 = new ThreadDemo();
        ThreadDemo t3 = new ThreadDemo();
        ThreadDemo t4 = new ThreadDemo();
        t1.start();
        t2.start();
        t3.start();
        t4.start();*/


/*        RunnableDemo rd = new RunnableDemo();
        new Thread(rd).start();
        new Thread(rd).start();
        new Thread(rd).start();
        new Thread(rd).start();*/
/*        // 测试通过Runnable实现的多个线程之间是否线程安全的,
        // 结果不能保证线程安全
        RunnableSalfe rs = new RunnableSalfe();
        new Thread(rs, "一号窗口").start();
        new Thread(rs, "二号窗口").start();
        new Thread(rs, "三号窗口").start();*/

        // 加上 synchronized 同步块，结果：能保证线程安全
 /*       RunnableSalfe_syn rss = new RunnableSalfe_syn();
        new Thread(rss, "一号窗口").start();
        new Thread(rss, "二号窗口").start();
        new Thread(rss, "三号窗口").start();
        new Thread(rss, "四号窗口").start();
        new Thread(rss, "五号窗口").start();
        new Thread(rss, "六号窗口").start();*/

    }


}

    /** 继承 Thread 抽象线程类的类，不能数据共享，即：其他线程不能共享继承Thread的类的属性字段
     * 原因：通过Thread实现线程时，线程和线程所要执行的任务是绑定在一起的（任务实例对象类与线程绑定在一起的），
     * 因此一个任务只能启动一个线程。
     * 不同的线程执行的任务是不相同的，所以没必要也不能让两个线程共享彼此任务中的资源
     * */

class ThreadDemo extends Thread{
        // 1000 张票
        private int tickets = 1000;

        public void run(){
            while (tickets>0){
                if (tickets>0){
                    System.out.println(this.getName()+" --- " + tickets--);
                }
            }
        }
}

/** 通过实现 Runnable 接口实现线程时，能进行数据共享，即：其他线程可以共享实现Runnable的类的属性字段
 * 原因：通过 Runnable 实现线程时，任务实例对象类与线程没有绑定在一起，所以一个任务对象可以启动多个线程。
 * 因为是一个任务对象对应多个线程，所以启动的线程可以共享这一个任务对象
 * */

class RunnableDemo implements Runnable{

    private int tickets = 1000;

    @Override
    public void run() {
        while (tickets>0){
            System.out.println(Thread.currentThread().getName() + " --- " + tickets--);
        }
    }
}
/********************************************  Runnable 线程是否安全 ******************************************/

/**
 * 结果：不能保证线程安全，因此必须要有同步操作
 * */
// 模拟实际售票业务
class RunnableSalfe implements Runnable{
    // 定义10张票
    private  int tickets = 10;
    @Override
    public void run() {
        for( int i = 0 ; i<=10 ; i++){
            try {
                // 模拟网络延迟 200 毫秒，或则是执行任务需要耗时200毫秒
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (tickets>0){
                System.out.println(Thread.currentThread().getName()+" --- "+tickets--);
            }
        }

    }
}
/**
 * 结果：能保证线程安全
 * */
// 模拟实际售票业务
class RunnableSalfe_syn implements Runnable{
    // 定义10张票
    private  int tickets = 500;
    @Override
    public void run() {
        while (tickets>0){
            synchronized (this){
                try {
                    // 模拟网络延迟 200 毫秒，或则是执行任务需要耗时200毫秒
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (tickets>0){
                    System.out.println(Thread.currentThread().getName()+" --- "+tickets--);
                }
            }
        }

    }
}
