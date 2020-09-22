package com.wisrc.product.webapp.entity;

import lombok.Data;

/**
 * 对产品进行维护的历史信息
 */
@Data
public class ProductModifyHistoryEntity {
    private String uuid;
    private String skuId;
    private String modifyUser;
    private String modifyTime;
    private String modifyColumn;
    private String oldValue;
    private String newValue;
    private Integer handleTypeCd;

    @Override
    public String toString() {
        return "ProductModifyHistoryEntity{" +
                "uuid='" + uuid + '\'' +
                ", skuId='" + skuId + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", modifyColumn='" + modifyColumn + '\'' +
                ", oldValue='" + oldValue + '\'' +
                ", newValue='" + newValue + '\'' +
                ", handleTypeCd=" + handleTypeCd +
                '}';
    }

}
