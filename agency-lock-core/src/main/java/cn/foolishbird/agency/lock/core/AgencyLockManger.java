package cn.foolishbird.agency.lock.core;

/**
 * @author foolish bird
 * @date 2020/07/13
 */
public interface AgencyLockManger {

    /**
     * 根据key获取对应的锁对象
     *
     * @param key
     * @return
     * @throws Exception
     */
    AgencyLock getLock(String key) throws Exception;

    /**
     * 主动释放锁
     *
     * @param key
     * @throws Exception
     */
    void releaseLock(String key) throws Exception;

}
