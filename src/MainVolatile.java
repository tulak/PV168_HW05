/**
 * Created by tulak on 08.04.2014.
 */
public class MainVolatile {

    public static class Counter {
        private volatile Integer num = 0;
        private Integer max = 50;

        public Boolean incrementAndPrint(Integer threadNum) {
            if(num <= max) {
                System.out.println("vlakno "+threadNum+": "+num++);
                return true;
            } else {
                return false;
            }
        }
    }

    public static class CounterThread implements Runnable {
        private Counter counter;
        private Integer threadNum;

        public CounterThread(Counter counter, Integer threadNum) {
            this.counter = counter;
            this.threadNum = threadNum;
        }

        @Override
        public void run(){
            while(counter.incrementAndPrint(threadNum)) {
                double bussyWaitCounter = 0;
                for(int i = 0; i<1_000_000+(Math.random()*100_000); i++) {
                    bussyWaitCounter = Math.random() * 100;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter();
        Thread t1 = new Thread(new CounterThread(c, 1));
        Thread t2 = new Thread(new CounterThread(c, 2));
        Thread t3 = new Thread(new CounterThread(c, 3));
        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }
}
