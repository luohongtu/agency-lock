package cn.foolishbird.agency.lock.core.jdk;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

import cn.foolishbird.agency.lock.core.AgencyLock;
import cn.foolishbird.agency.lock.core.AgencyLockManger;

/**
 * @author foolish bird
 * @date 2023/1/8
 */
public class ReentrantAgencyLockManager implements AgencyLockManger {

    /**
     * 锁
     */
    private volatile WeakConcurrentMap<String, AgencyLock> lockCache = new WeakConcurrentMap<>();

    @Override
    public AgencyLock getLock(String key) throws Exception {
        if (Objects.isNull(key)) {
            throw new NullPointerException("generatorKey is null");
        }

        AgencyLock lock = lockCache.getIfPresent(key);

        // 初始化锁
        if (null == lock) {
            synchronized (lockCache) {
                lock = new JDKReentrantAgencyLock(new ReentrantLock());
                lockCache.putIfProbablyAbsent(key, lock);
            }
        }
        return lock;
    }

    @Override
    public void releaseLock(String key) throws Exception {

    }
}
