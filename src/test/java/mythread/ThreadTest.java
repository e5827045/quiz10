package mythread;

import org.junit.jupiter.api.Test;


public class ThreadTest {


    @Test
    public void test1() {
        MyThread my = new MyThread();
        my.start();
        my.run();


    }

    @Test
    public void test2() {
        MyThread my1 = new MyThread();
        MyThread my2 = new MyThread();
        my1.start();
        my2.start();

    }

    @Test
    public void test3() {

    }
}




