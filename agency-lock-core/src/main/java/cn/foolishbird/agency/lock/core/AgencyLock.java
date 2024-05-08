package cn.foolishbird.agency.lock.core;

import java.util.concurrent.TimeUnit;

/**
 * 业务锁抽象接口
 *
 * @author foolish bird
 * @version 1.0
 * @date 2020-01-24
 */
public interface AgencyLock {

    /**
     * 尝试获取一次锁
     *
     * @return
     */
    boolean tryLock();

    /**
     * 尝试获取一次锁， 并设置锁自动释放时间
     *
     * @param waitTime  获取锁等待时长
     * @param leaseTime 锁自动释放最大时间
     * @param timeUnit  时间单位
     * @return
     */
    boolean tryLock(long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException;

    /**
     * 加锁
     */
    void lock();

    /**
     * 枷锁
     *
     * @param leaseTime 过期时间
     * @param timeUnit  过期时间单元
     */
    void lock(long leaseTime, TimeUnit timeUnit);

    /**
     * 解锁
     */
    void unlock();
}
