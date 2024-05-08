/*
 * Copyright 2012-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.foolish.lock.manager.springboot.config;

import cn.foolishbird.agency.lock.core.AgencyLockManger;
import cn.foolishbird.agency.lock.core.KeyGenerator;
import cn.foolishbird.agency.lock.core.generator.MethodSignatureKeyGenerator;
import cn.foolishbird.agency.lock.core.jdk.ReentrantAgencyLockManager;
import cn.foolishbird.agency.lock.spring.aspect.AgencyLockManagerAspect;
import cn.foolishbird.agency.lock.spring.aspect.IdempotentManagerAspect;
import cn.foolishbird.agency.lock.spring.redisson.RedissonReentrantAgencyLockManager;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author foolishbird
 */
@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties({AgencyLockProperties.class})
public class AgencyLockConfiguration {

    @Bean
    @ConditionalOnMissingBean(KeyGenerator.class)
    public KeyGenerator<String> keyGenerator() {
        return new MethodSignatureKeyGenerator();
    }


    @Bean
    @ConditionalOnProperty(prefix = "agency.config", value = "agencyLockType", havingValue = "jdk")
    public AgencyLockManger reentrantAgencyLockManager() {
        return new ReentrantAgencyLockManager();
    }

    @Bean
    @ConditionalOnProperty(prefix = "agency.config", value = "agencyLockType", havingValue = "redisson")
    public AgencyLockManger redissonReentrantAgencyLockManager(RedissonClient redissonClient) {
        return new RedissonReentrantAgencyLockManager(redissonClient);
    }

    @Bean
    public AgencyLockManagerAspect agencyLockManagerAspect(AgencyLockManger agencyLockManger) {
        return new AgencyLockManagerAspect(agencyLockManger);
    }

    @Bean
    public IdempotentManagerAspect idempotentManagerAspect(AgencyLockManger agencyLockManger) {
        return new IdempotentManagerAspect(agencyLockManger);
    }

}
