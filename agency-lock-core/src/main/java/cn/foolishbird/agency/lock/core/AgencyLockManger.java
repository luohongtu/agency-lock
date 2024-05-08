package cn.foolishbird.agency.lock.core;

/**
 * @author foolishbird
 */
public interface AgencyLockManger {

    /**
     * obtains the proxy lock based on the lock key
     *
     * @param key Lock key
     * @return AgencyLock  AgencyLock
     * @throws Exception Failure to obtain the lock reported an error
     */
    AgencyLock getLock(String key) throws Exception;

    /**
     * Remove a lock from the lock manager
     *
     * @param key Lock key
     * @throws Exception Failure to remove the lock reported an Exception
     */
    void removeLock(String key) throws Exception;

}
