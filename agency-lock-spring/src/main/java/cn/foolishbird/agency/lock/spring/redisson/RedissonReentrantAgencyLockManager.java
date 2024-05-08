package cn.foolishbird.agency.lock.spring.redisson;

import cn.foolishbird.agency.lock.core.AgencyLock;
import cn.foolishbird.agency.lock.core.AgencyLockManger;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @author foolishbird
 */
public class RedissonReentrantAgencyLockManager implements AgencyLockManger {

    private RedissonClient redissonClient;

    public RedissonReentrantAgencyLockManager(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public AgencyLock getLock(String key) throws Exception {
        if (Objects.isNull(redissonClient)) {
            throw new NullPointerException("RedissonClient is null");
        }
        RLock lock = redissonClient.getLock(key);
        return new RedissonReentrantAgencyLock(lock);
    }

    @Override
    public void removeLock(String key) throws Exception {
        // nothting to do
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }

    @Autowired
    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
}
