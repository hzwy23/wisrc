package com.wisrc.product.webapp.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 产品申报信息实体类
 */
@Data
@ApiModel
public class ProductDeclareInfoWithSkuVO {
    private String skuId;
    private String customsNumber;
    private double taxRebatePoint;
    private String issuingOffice;
    private String declareNameZh;
    private String declareNameEn;
    private double declarePrice;
    private double singleWeight;
    private String declarationElements;
    private String ticketName;
}
