package com.wisrc.product.webapp.vo.productDefineVO.show;

import lombok.Data;

@Data
public class ProductDefineVO {
    private String skuId;
    private String skuNameZh;
    private Integer statusCd;

    private String classifyCd;
    private String createTime;
    private String createUser;
    private String skuNameEn;
    private String skuShortName;
}
