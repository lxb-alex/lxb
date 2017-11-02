package com.lxb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/10/30 0030 9:15:15
 */
public class TestThread {

    public static void main(String[] args) {
        CustomThread customThread = new CustomThread();
        customThread.setMsg("this is the first thread");
        CustomThread customThread2 = new CustomThread();
        customThread2.setMsg("this is the second thread ");
        CustomThread customThread3 = new CustomThread();
        customThread3.setMsg("this is the three thread");
        CustomThread customThread4 = new CustomThread();
        customThread4.setMsg("this is the foure thread");
        Thread thread = new Thread(customThread);
        thread.start();
        Thread thread2 = new Thread(customThread2);
        thread2.start();
        Thread thread3 = new Thread(customThread3);
        thread3.start();
        Thread thread4 = new Thread(customThread4);
        thread4.start();

        ExecutorService pool = Executors.newCachedThreadPool();
        pool.execute(customThread4);

/*       customClass customClass = new customClass();
       customClass.prints(5);
       customClass customClass2 = new customClass();
       customClass2.prints(3);
       customClass customClass3 = new customClass();
       customClass3.prints(2);
       customClass customClass4 = new customClass();
       customClass4.prints(3);*/

    }
}
