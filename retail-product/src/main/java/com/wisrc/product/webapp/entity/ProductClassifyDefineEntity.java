package com.wisrc.product.webapp.entity;

import lombok.Data;

/**
 * 产品分类实体类
 */
@Data
public class ProductClassifyDefineEntity extends BaseEntity {
    private String classifyCd;
    private String classifyNameCh;
    private String classifyNameEn;
    private String classifyShortName;
    private Integer levelCd;
    private String createTime;
    private String createUser;
    private String parentCd;

    @Override
    public String toString() {
        return "ProductClassifyDefineEntity{" +
                "classifyCd='" + classifyCd + '\'' +
                ", classifyNameCh='" + classifyNameCh + '\'' +
                ", classifyNameEn='" + classifyNameEn + '\'' +
                ", classifyShortName='" + classifyShortName + '\'' +
                ", levelCd=" + levelCd +
                ", createTime='" + createTime + '\'' +
                ", createUser='" + createUser + '\'' +
                ", praentCd='" + parentCd + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        ProductClassifyDefineEntity s = (ProductClassifyDefineEntity) obj;
        return classifyCd.equals(s.classifyCd);
    }

    @Override
    public int hashCode() {
        String in = classifyCd;
        return in.hashCode();
    }
}
