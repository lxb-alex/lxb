package com.lxb.thread;

/**
 * @Description 测试 线程常用的一些方法：sleep（）、join（）、yield（）、wait（）、notify（）、notifyAll（）
 * @Author Liaoxb
 * @Date 2017/11/23 0023 16:04:04
 */
public class ThreadAndObjectMethod {

    public static void main(String[] args) {

        ThreadClass tc = new ThreadClass();
    }
}

class RunnableClass implements Runnable{

    @Override
    public void run() {

    }
}

class ThreadClass extends Thread{

    public void test(){
    }

}