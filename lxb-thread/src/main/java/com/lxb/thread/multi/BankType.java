package com.lxb.thread.multi;

/**
 * @Description 多线程数据共享 银行 存取款类型
 * @Author Liaoxb
 * @Date 2017/11/24 0024 11:32:32
 */
public class BankType {
    public static void main(String[] args) {
        Acount acount = new Acount(100);
        GetATM getATM = new GetATM(acount);
        PushAtm pushAtm = new PushAtm(acount);
        new Thread(getATM, "取款").start();
        new Thread(pushAtm, "存款").start();
        pushAtm.test();
    }
}

// 个人账户信息
class Acount{
    private int money;
    private boolean isGet;


    public Acount(int money) {
        this.money = money;
    }

    public synchronized void getMoney(int money){
        // 此处用while是因为;有可能存款多次余额还是低于取款额度
        // 在等待 存款成功 后唤起线程，重新进入while 判断
        while (this.money<money){
            System.out.println("取款： "+ money + " 余额： "+ this.money + " 账户余额不足，正在等待存款....");
            try {
                this.isGet = false; // 取款失败，需要存款
                notify();// 余额不足，唤醒存款线程
                wait(); // 然后取款线程进入等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.money = this.money - money;
        this.isGet = true;  // 取款成功，不需要存款
        System.out.println("取款： "+ money + " 余额： "+ this.money + " 取款成功");
        notify();   // 唤起存款线程，因不需要再次存款，所以存款线程结束
    }

    public synchronized void pushMoney(int money){
        while (!this.isGet){
            try {
                // 模拟存款耗时
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.money = this.money + money;
            System.out.println("存款： "+ money + " 余额： "+ this.money + " 存款成功");
            // 存款成功，唤起取款线程
            notify();
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
// 外部线程对象类
// 取款
class GetATM implements Runnable {
    Acount acount;

    // 将共享操作对象，通过构造器传递给线程对象
    public GetATM(Acount acount) {
        this.acount = acount;
    }

    @Override
    public void run() {
        int money = (int) (Math.random()*100000);
        acount.getMoney(money);
    }
}

// 外部线程对象类
// 存款
class PushAtm implements Runnable{
    Acount acount;

    // 将共享操作对象，通过构造器传递给线程对象
    public PushAtm(Acount acount) {
        this.acount = acount;
    }

    @Override
    public void run() {
        int money = (int) (Math.random()*100000);
        acount.pushMoney(money);
    }
    public void test (){
        System.out.println("这是一个的实现Runable接口的线程类的普通方法");
    }
}