package com.lxb.thread.multi;

/**
 * @Description 多线程数据共享 银行 存取款类型
 * @Author Liaoxb
 * @Date 2017/11/24 0024 11:32:32
 */
public class BankType2 {
    public static void main(String[] args) {
        ATM atm = new ATM(100);
        for (int i = 1; i<=4; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BankType2 bankType2 = new BankType2();
                    bankType2.test(atm);
                }
            },"客户 " + i).start();
        }

    }

    public void test(ATM atm){
        GetATM2 getATM = new GetATM2(atm);
        PushAtm2 pushAtm = new PushAtm2(atm);
        new Thread(getATM, Thread.currentThread().getName()+" 取款").start();
        new Thread(pushAtm, Thread.currentThread().getName()+" 存款").start();
        // pushAtm.test();
    }
}



// 个人账户信息
class ATM{
    private int money;

    public ATM(int money) {
        this.money = money;
    }

    public synchronized void getMoney(int money){
        // 此处用while是因为;有可能存款多次余额还是低于取款额度
        // 在等待 存款成功 后唤起线程，重新进入while 判断
        while (this.money<money){
            System.out.println(Thread.currentThread().getName() + money + " 余额： "+ this.money + " 账户余额不足，正在等待存款....");
            try {
                wait(); // 然后取款线程进入等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.money = this.money - money;
        System.out.println(Thread.currentThread().getName()+ money + " 余额： "+ this.money + " 取款成功");
    }

    public synchronized void pushMoney(int money){
        try {
            // 模拟存款耗时
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.money = this.money + money;
        System.out.println(Thread.currentThread().getName()+ money + " 余额： "+ this.money + " 存款成功");
        // 存款成功，唤起取款线程
        notify();
    }
}
// 外部线程对象类
// 取款
class GetATM2 implements Runnable {
    ATM atm;

    // 将共享操作对象，通过构造器传递给线程对象
    public GetATM2(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void run() {
        int money = (int) (Math.random()*100000);
        atm.getMoney(money);
    }
}

// 外部线程对象类
// 存款
class PushAtm2 implements Runnable{
    ATM atm;

    // 将共享操作对象，通过构造器传递给线程对象
    public PushAtm2(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void run() {
        int money = (int) (Math.random()*100000);
        atm.pushMoney(money);
    }
    public void test (){
        System.out.println("这是一个的实现Runable接口的线程类的普通方法");
    }
}