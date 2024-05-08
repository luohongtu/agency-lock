package cn.foolishbird.agency.lock.spring;

/**
 * Support for lock implementation enumeration
 *
 * @author foolish bird
 */
public enum AgencyLockType {

    JDK("jdk", "jdk implements proxy lock"),
    REDISSON("redisson", "redisson implements proxy lock");

    private final String type;

    private final String DESC;

    AgencyLockType(String type, String DESC) {
        this.type = type;
        this.DESC = DESC;
    }

    public String getType() {
        return type;
    }

    public String getDESC() {
        return DESC;
    }
}
