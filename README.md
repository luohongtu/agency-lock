# agency-lock

This open-source project is a proxy lock project that enables dynamic switching to different lock implementations using
the proxy lock approach. It is designed to upgrade from a single-machine lock to a distributed lock, catering to various
application scenarios.

## Getting started

- Download project

  `git clone https://github.com/luohongtu/agency-lock.git`
- Install to the local repository

  `mvn clean install`
- Add agency-lock-spring-boot-starter dependency

```pom
<dependency>
  <groupId>cn.foolishbird</groupId>
  <artifactId>agency-lock-spring-boot-starter</artifactId>
  <version>1.1.0</version>
</exclusions>

```
- Configure the springboot environment
```yml
agency:
  config:
    agencyLockType: "redisson"
```
- The lock key generation policy
```java
@Configuration
public class AgencyLockConfig {

    @Bean("springElGenerator")
    public KeyGenerator springELMethonNameKeyGenerator() {
        return new SpringELMethonNameKeyGenerator();
    }

}
```
- Idempotent locks are used
```java
@Idempotent(key = "#param.phone", keyGenerator = "springElGenerator", leaseTime = 6)
public String login(@Validated @RequestBody PhoneLoginParam param) {
    return "token";
}
```

- AgencyLock locks are used
```java
@AgencyLock(key = "#param.phone", keyGenerator = "springElGenerator", leaseTime = 6)
public String login(@Validated @RequestBody PhoneLoginParam param) {
    return "token";
}
```

- Lock code block
```java
@Autowired
private AgencyLockManger agencyLockManger;

public void test1() throws Exception {
    String key = "demoKey";
    AgencyLock lock = agencyLockManger.getLock(key);
    try {
    lock.lock();
    // service code
    Thread.sleep(3000);
    System.out.println("测试成功");
    } finally {
    lock.unlock();
    agencyLockManger.removeLock(key);
    }
}
```
