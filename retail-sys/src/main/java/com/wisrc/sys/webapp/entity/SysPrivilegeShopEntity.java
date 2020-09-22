package com.wisrc.sys.webapp.entity;

import lombok.Data;

@Data
public class SysPrivilegeShopEntity {
    private String uuid;
    private String privilegeCd;
    private String commodityId;
    private String createUser;
    private String createTime;

    @Override
    public String toString() {
        return "SysPrivilegeShopEntity{" +
                "uuid='" + uuid + '\'' +
                ", privilegeCd='" + privilegeCd + '\'' +
                ", commodityId='" + commodityId + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
