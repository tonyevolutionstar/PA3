package UC.Server;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author miguel
 */
public class ServerSharedRegion {
    
    private final ReentrantLock rl = new ReentrantLock(true);
    private final Condition cEmpty;
    
    public ServerSharedRegion(){
        cEmpty = rl.newCondition();
    }
    
    public void ifThreadIsDoneAndQueueUp()
    {
       rl.lock();
        try {
            cEmpty.signal();
        } finally {
            rl.unlock();
        }
    }
    
    public void threadThatChecksConcurrentWorkingThreads() throws InterruptedException
    {
        rl.lock();
        try {
            cEmpty.await();
        } finally {
            rl.unlock();
        }
    }
    
}
