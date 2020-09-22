package com.wisrc.sales.basic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ConfigEstimateProperties {
    @Value("${estimate.lockDays}")
    private int lockDays;

    public int getLockDays() {
        return lockDays;
    }

    public void setLockDays(int lockDays) {
        this.lockDays = lockDays;
    }
}
