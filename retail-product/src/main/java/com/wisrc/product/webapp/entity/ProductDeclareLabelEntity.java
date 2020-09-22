package com.wisrc.product.webapp.entity;


import lombok.Data;

import java.util.List;

/**
 * 产品申报标签信息实体类
 * 用来存储每一个产品的标签信息
 */
@Data
public class ProductDeclareLabelEntity extends BaseEntity {
    private String skuId;
    private String label;
    private String uuid;
    private Integer labelCd;
    private List<String> labelList;
    private String labelText;
    private String labelDesc;
    private Integer typeCd;
}
