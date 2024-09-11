package mythread;

public class MyThread  extends Thread{

    @Override
    public synchronized void run() {
        for(int i = 1; i<= 10; i++){


//        System.out.println(getName()+"正在執行第"+i+"次");
        System.out.printf("%s 正在執行第 %d次 \n",getName(),i);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public MyThread(String name) {
        super(name);
    }

    public MyThread() {
        super();
    }
}
