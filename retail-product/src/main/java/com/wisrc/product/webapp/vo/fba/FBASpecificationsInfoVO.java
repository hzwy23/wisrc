package com.wisrc.product.webapp.vo.fba;

import lombok.Data;

@Data
public class FBASpecificationsInfoVO {
    private String skuId;
    private double fbaWeight;
    private double fbaLength;
    private double fbaWidth;
    private double fbaHeight;
    private int fbaQuantity;
}
