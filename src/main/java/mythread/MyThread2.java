package mythread;

public class MyThread2 implements Runnable {

    private String name;

    public MyThread2() {
    }


    public String getName() {
        return name;
    }

    public MyThread2(String name) {
        this.name = name;
    }

    @Override
    public synchronized void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//        System.out.println(getName()+"正在執行第"+i+"次");
            System.out.printf("%s 正在執行第 %d次 \n", getName(), i);
        }
    }


}
