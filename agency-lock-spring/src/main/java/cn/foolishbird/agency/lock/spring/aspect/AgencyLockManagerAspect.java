package cn.foolishbird.agency.lock.spring.aspect;

import cn.foolishbird.agency.lock.core.AgencyLockManger;
import cn.foolishbird.agency.lock.core.KeyGenerator;
import cn.foolishbird.agency.lock.spring.annotation.AgencyLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AgencyLockManagerAspect implements ApplicationContextAware {

    /**
     * spring容器
     */
    private ApplicationContext applicationContext;

    protected AgencyLockManger agencyLockManger;

    public AgencyLockManagerAspect(AgencyLockManger agencyLockManger) {
        this.agencyLockManger = agencyLockManger;
    }

    /**
     * 切面方法，让子类重写
     */
    @Pointcut("@annotation(cn.foolishbird.agency.lock.spring.annotation.AgencyLock)")
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

        AgencyLock agencyLock = method.getAnnotation(AgencyLock.class);
        String key = agencyLock.key();
        if (StringUtils.isEmpty(key)) {
            key = agencyLock.value();
        }

        String keyGeneratorBeanName = agencyLock.keyGenerator();
        if (StringUtils.isEmpty(key) && StringUtils.isEmpty(keyGeneratorBeanName)) {
            throw new RuntimeException("key and keyGeneratorBeanName can't be empty");
        }

        if (!StringUtils.isEmpty(keyGeneratorBeanName)) {
            KeyGenerator<String> generator = applicationContext.getBean(keyGeneratorBeanName, KeyGenerator.class);
            if (Objects.isNull(generator)) {
                throw new NullPointerException("KeyGenerator is null");
            }
            key = generator.keyGenerator(key, target, method, point.getArgs());
        }

        TimeUnit timeUnit = agencyLock.timeUnit();
        cn.foolishbird.agency.lock.core.AgencyLock lock = agencyLockManger.getLock(key);
        if (-1 == agencyLock.leaseTime()) {
            lock.lock();
        } else {
            lock.lock(agencyLock.leaseTime(), timeUnit);
        }

        try {
            return point.proceed(point.getArgs());
        } finally {
            lock.unlock();
            agencyLockManger.removeLock(key);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public AgencyLockManger getAgencyLockManger() {
        return agencyLockManger;
    }

    @Autowired
    public void setAgencyLockManger(AgencyLockManger agencyLockManger) {
        this.agencyLockManger = agencyLockManger;
    }

}
