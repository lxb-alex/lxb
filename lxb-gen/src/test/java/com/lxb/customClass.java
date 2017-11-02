package com.lxb;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/10/30 0030 9:31:31
 */
public class customClass {

    public void prints(int arg){
        try {
            System.out.println("------ 线程开始 ------");
            for (int i = 0 ; i<arg; i++){
                System.out.println(i);
                Thread.sleep(1000);
            }
            System.out.println("------ 线程结束 ------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
