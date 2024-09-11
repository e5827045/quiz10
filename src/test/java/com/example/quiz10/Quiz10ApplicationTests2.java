package com.example.quiz10;

import mythread.MyThread;
import mythread.MyThread3;
import mythread.SellTicket;
import org.springframework.boot.test.context.SpringBootTest;


class Quiz10ApplicationTests2 {


    public static void main(String[] args) {

//       MyThread my = new MyThread("MT2");
//        Thread t = new Thread(my);
//        t.start();
//        MyThread my1 = new MyThread();
//        MyThread my2 = new MyThread();
//        my1.start();
//        my2.start();

        //同步
//        SellTicket st = new SellTicket();
//        Thread t1 = new Thread(st,"機器A");
//        Thread t2 = new Thread(st,"機器B");
//        t1.start();
//        t2.start();

        //一般類別
//        MyThread my1 = new MyThread("My1");
//        my1.start();
//
//        //匿名類別:在宣告的同時重新定義run方法
//        MyThread my11 = new MyThread("My11") {
//            @Override
//            public void run() {
//                System.out.println("++");
//            }
//        };
//        my11.start();
        long t1 = System.currentTimeMillis();
        MyThread3 my3 = new MyThread3();
            Thread t3 = new Thread(my3);
            t3.start();
            synchronized (t3){
                try {
                    t3.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        System.out.println("sum = " + my3.getSum());
    }


}




