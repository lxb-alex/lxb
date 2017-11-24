package com.lxb.thread.multi;

/**
 * @Description 多线程数据共享 卖票类型
 * @Author Liaoxb
 * @Date 2017/11/24 0024 11:01:01
 */
public class TicketingType {

    public static void main(String[] args) {
        // 得到共享数据对象，设定总票数（共享数据）
        SharingObject data = new SharingObject(10);
        // 创建四个线程，将共享数据逐一传递给每个线程。
        for(int i=0;i<2;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (this){
                        while (data.getTickets()>0){
                            data.setTickets(data.getTickets()-1);
                            System.out.println(Thread.currentThread().getName()+" --- "+ data.getTickets());
                        }
                    }
                }
            }, i + " 号窗口").start();
        }

    }
}
// 共享数据对象
class SharingObject{

    private int tickets;

    public SharingObject(int tickets) {
        this.tickets = tickets;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    // 添加synchronized 关键字，保证线程安全
    public synchronized void buyTicket(){
        while (this.tickets>0){
            System.out.println(Thread.currentThread().getName()+" --- "+ this.tickets--);
        }
    }
}
