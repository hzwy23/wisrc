package com.wisrc.product.webapp.vo.set;

import lombok.Data;

/**
 * 产品分类实体类
 */
@Data
public class SetProductClassifyDefineVO {
    private String classifyCd;
    private String classifyNameCh;
    private String classifyNameEn;
    private String classifyShortName;
    private Integer levelCd;
    private String parentCd;
}
