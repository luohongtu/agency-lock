package cn.foolish.lock.manager.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author foolish bird
 * @date 2023/1/9
 */
@ConfigurationProperties(prefix = "agency.config")
public class AgencyLockProperties {

    /**
     * 代理锁类型
     */
    private String agencyLockType;

    public String getAgencyLockType() {
        return agencyLockType;
    }

    public void setAgencyLockType(String agencyLockType) {
        this.agencyLockType = agencyLockType;
    }

}
