package com.wisrc.product.webapp.vo.productInfo.add;

import lombok.Data;

/**
 * 产品分类实体类
 */
@Data
public class AddProductClassifyDefineVO {
    private String classifyNameCh;
    private String classifyNameEn;
    private String classifyShortName;
    private Integer levelCd;
    private String parentCd;
}
