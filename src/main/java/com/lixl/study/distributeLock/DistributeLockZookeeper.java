/*
package leven.texous.testmail.distributeLock;

import com.sun.javafx.css.CalculatedValue;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;

import java.util.Collection;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DistributeLockZookeeper implements  Lock{

    private  String lockPath;

    private CuratorFramework zkClient ;

    public DistributeLockZookeeper(String lockPath, CuratorFramework zkClient) {
        this.lockPath = lockPath;
        this.zkClient = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new RetryOneTime(1000));;
    }

    @Override
    public void lock() {
        if (!tryLock()){
            waitForLock();
            lock();//自己掉自己
        }
    }

    private void waitForLock() {
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {//不会阻塞
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        try {
            zkClient.createContainers(lockPath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void unlock() {
        zkClient.delete();
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
*/
