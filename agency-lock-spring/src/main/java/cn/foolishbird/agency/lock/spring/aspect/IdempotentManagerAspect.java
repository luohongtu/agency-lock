package cn.foolishbird.agency.lock.spring.aspect;

import cn.foolishbird.agency.lock.core.AgencyLock;
import cn.foolishbird.agency.lock.core.AgencyLockManger;
import cn.foolishbird.agency.lock.core.KeyGenerator;
import cn.foolishbird.agency.lock.core.exception.IdempotentException;
import cn.foolishbird.agency.lock.spring.annotation.Idempotent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author foolishbird
 */
@Aspect
public class IdempotentManagerAspect implements ApplicationContextAware {

    /**
     * spring容器
     */
    private ApplicationContext applicationContext;

    protected AgencyLockManger agencyLockManger;

    public IdempotentManagerAspect(AgencyLockManger agencyLockManger) {
        this.agencyLockManger = agencyLockManger;
    }

    /**
     * 切面方法，让子类重写
     */
    @Pointcut("@annotation(cn.foolishbird.agency.lock.spring.annotation.Idempotent)")
    public void pointcut() {

    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        //用的最多通知的签名
        Signature signature = point.getSignature();
        MethodSignature msg = (MethodSignature) signature;

        //获取注解标注的方法
        Object target = point.getTarget();
        Method method = target.getClass().getMethod(msg.getName(), msg.getParameterTypes());

        Idempotent agencyLock = method.getAnnotation(Idempotent.class);
        String key = agencyLock.key();
        if (StringUtils.isEmpty(key)) {
            key = agencyLock.value();
        }

        String generatorName = agencyLock.keyGenerator();
        if (StringUtils.isEmpty(key) && StringUtils.isEmpty(generatorName)) {
            throw new RuntimeException("key and generatorName can't be empty");
        }

        if (!StringUtils.isEmpty(generatorName)) {
            KeyGenerator<String> generator = applicationContext.getBean(generatorName, KeyGenerator.class);
            if (Objects.isNull(generator)) {
                throw new NullPointerException("KeyGenerator is null");
            }
            key = generator.keyGenerator(key, target, method, point.getArgs());
        }

        Long leaseTime = agencyLock.leaseTime();
        TimeUnit timeUnit = agencyLock.timeUnit();
        AgencyLock lock = agencyLockManger.getLock(key);

        boolean lockFlag = -1 == leaseTime ? lock.tryLock() : lock.tryLock(0L, leaseTime, timeUnit);
        if (!lockFlag) {
            throw new IdempotentException("请不要重复操作，稍等再试");
        }

        // 幂等锁，不主动释放锁，等锁过期自定释放
        return point.proceed(point.getArgs());
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
