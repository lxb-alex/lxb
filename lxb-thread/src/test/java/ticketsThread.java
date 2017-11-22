/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/11/22 0022 11:12:12
 */
public class ticketsThread extends Thread {

    // 100 张票
    private int tickets = 100;

    public void run(){
        while (tickets>0){
            if (tickets>0){
                System.out.println(Thread.currentThread().getName()+"--- " + tickets--);
//                System.out.println(Thread.activeCount());
            }
        }
    }


}

class tickesDemo{
    public static void main(String[] args) {
        // Thread 不能共享类属性
        ticketsThread t1 = new ticketsThread();
        ticketsThread t2 = new ticketsThread();
        ticketsThread t3 = new ticketsThread();
        ticketsThread t4 = new ticketsThread();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

}