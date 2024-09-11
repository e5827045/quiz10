package mythread;

public class SellTicket implements Runnable{
    private  int tickets = 10;





    @Override
    public  void run() {
        for(int i = 0; i<10; i++){
           //同步區塊:小括號中this範圍指的是後面接續的大括號區塊
            synchronized(this){
                if(tickets>0){
                    try {
                        Thread. sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tickets--;

                    System.out.println(Thread.currentThread().getName()+"票賣出一張，剩餘"+tickets+"張");
                }

            }

        }
    }
}
