package p79068.util;


public final class Barrier {
    
    private int count;
    
    
    
    public Barrier(int count) {
        this.count = count;
    }
    
    
    
    public synchronized void join() throws InterruptedException {
        if (count > 0)  {
            count--;
            if (count == 0)
                notifyAll();
            else {
                while (count > 0)
                    wait();
            }
        }
    }
    
}