package mythread;

public class MyThread3 implements Runnable {

    private int sum;

    public int getSum() {
        return sum;
    }

    @Override
    public void run() {
        System.out.println("執行續執行中");
        synchronized (this) {
            for (int i = 1; i <= 10; i++) {
                sum += i;


            }
            notify();
        }

    }


}
