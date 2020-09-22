package com.wisrc.product.webapp.entity;


import lombok.Data;


/**
 * 产品定义信息实体类
 */
@Data
public class ProductDefineEntity extends BaseEntity {
    private String skuId;
    private String skuNameZh;
    private Integer statusCd;
    private String classifyCd;
    private String createTime;
    private String createUser;
    private Integer machineFlag;
    private String skuNameEn;
    private String skuShortName;

    private Integer typeCd;
    private Double purchaseReferencePrice;
    private Double costPrice;
    private Integer packingFlag;

    private String classifyShortName;
    private Integer size;

}
