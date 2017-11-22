import java.math.BigInteger;
import java.util.Date;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/11/22 0022 11:24:24
 */
public class TicketsRunnable implements Runnable{

    private long tickets = Long.MAX_VALUE/200;
    private Long start = null;
    private Long end = null;

    @Override
    public void run() {
        if (tickets==Long.MAX_VALUE/200){
            start = System.currentTimeMillis();
        }
        while (tickets>0){
            tickets = tickets - 2100000000;
            if (tickets%2000000000==0) {
                System.out.print("");
            }
            if (tickets%100>0){
                System.out.println(Thread.currentThread().getName()+" --- "+ tickets);
            }
        }
        if (tickets<=0){
            System.out.println("================================================= user Date  "+ (System.currentTimeMillis() - start));
        }
    }
}

class testRunnable{
    public static void main(String[] args) {
        // Runnable 类对象直接调用run()方法执行的是请求的主线程，并未开启线程
        // Runnable 线程共享类属性
        TicketsRunnable t1 = new TicketsRunnable();
        Thread thread = new Thread(t1);
        Thread thread2 = new Thread(t1);
        Thread thread3 = new Thread(t1);
        Thread thread4 = new Thread(t1);
        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();

        testDemo demo = new testDemo();
        demo.demo();

    }
}

class testDemo{

    private long tickets = Long.MAX_VALUE/200;
    private Long start = null;

    public void demo(){
        if (tickets==Long.MAX_VALUE/200){
            start = System.currentTimeMillis();
        }
        while (tickets>0){
            tickets = tickets - 2100000000;
            if (tickets%2000000000==0) {
                System.out.print("");
            }
            if (tickets%100000>0){
                System.out.print(Thread.currentThread().getName()+" --- "+ tickets);
            }
        }
        if (tickets<=0){

            System.out.println("==================================================== 2  user Date  "+ (System.currentTimeMillis() - start));
        }
    }
}

