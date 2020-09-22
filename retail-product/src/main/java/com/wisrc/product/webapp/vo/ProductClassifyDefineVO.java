package com.wisrc.product.webapp.vo;

import lombok.Data;

/**
 * 产品分类实体类
 */
@Data
public class ProductClassifyDefineVO {
    private String classifyCd;
    private String classifyNameCh;
    private String classifyNameEn;
    private String classifyShortName;
    private Integer levelCd;
    private String parentCd;
}
