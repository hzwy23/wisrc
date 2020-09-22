package com.wisrc.product.webapp.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 产品申报标签信息实体类
 * 用来存储每一个产品的标签信息
 */
@Data
@ApiModel
public class ProductDeclareLabeWithSkulVO {
    private List<String> labelList;
    private String skuId;
}
