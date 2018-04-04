package com.lxb.thread.multi;

/**
 * @Description
 * @Author Liaoxb
 * @Date 18-4-2 16:54:54
 */
public class JiaoTiPrint {

    public static void main(String[] args) {
        Num num = new Num();
//        new Thread(new print_ji(num)).start();
//        new Thread(new print_ou(num)).start();

        for (int i=0; i<6; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new Thread(new print_0(num)).start();
                    new Thread(new print_1(num)).start();
                }
            }, "线程"+i).start();
        }

    }


    private static class print_0 implements Runnable {
        Num num;
        public print_0(Num num){
            this.num = num;
        }

        @Override
        public void run(){
            while (num.i<10){

                synchronized (num){
                    if (!num.flag){
                        try {
                            num.notify();
                            num.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        System.out.println("--" + 1);
                        num.i++;
                        num.flag = false;
                        num.notifyAll();
                    }
                }
            }
        }
    }
    private static class print_1 implements Runnable {
        Num num;
        public print_1(Num num){
            this.num = num;
        }

        @Override
        public void run(){
            while (num.i<10){

                synchronized (num){
                    if (num.flag){
                        try {
                            num.notify();
                            num.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        System.out.println("偶数 " + 0);
                        num.i++;
                        num.flag = true;
                        num.notifyAll();
                    }
                }
            }
        }
    }

    private static class print_ji implements Runnable {
        Num num;
        public print_ji(Num num){
            this.num = num;
        }

        @Override
        public void run(){
            while (num.i<20){

                synchronized (num){
                    if (!num.flag){
                        try {
                            num.notify();
                            num.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        System.out.println("--" + num.i);
                        num.i++;
                        num.flag = false;
                        num.notifyAll();
                    }
                }
            }
        }
    }
    private static class print_ou implements Runnable {
        Num num;
        public print_ou(Num num){
            this.num = num;
        }

        @Override
        public void run(){
            while (num.i<20){

                synchronized (num){
                    if (num.flag){
                        try {
                            num.notify();
                            num.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        System.out.println("偶数 " + num.i);
                        num.i++;
                        num.flag = true;
                        num.notifyAll();
                    }
                }
            }
        }
    }

    private static class Num{
        int i = 0;
        boolean flag = false;
    }
}
