package com.wisrc.product.webapp.entity;


import lombok.Data;

/**
 * 产品附带图片信息实体类
 */
@Data
public class ProductImagesEntity extends BaseEntity {
    private String uuid;
    private String skuId;
    private String imageUrl;
    private Integer imageClassifyCd;

    // "uid,配合前端使用，不懂请去问前端")
    private String uid;

    @Override
    public String toString() {
        return "ProductImagesEntity{" +
                "uuid='" + uuid + '\'' +
                ", skuId='" + skuId + '\'' +
                ", imageClassifyCd=" + imageClassifyCd +
                '}';
    }
}
