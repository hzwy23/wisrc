package com.wisrc.basic.entity;

import lombok.Data;

@Data
public class BrandInfoEntity {
    private String brandId;
    private String brandName;
    private Integer statusCd;
    private String logoUrl;
    private Integer relProductNum;
    private Integer brandType;
    private String modifyUser;
    private String modifyTime;
    private String createTime;
    private String createUser;

}
