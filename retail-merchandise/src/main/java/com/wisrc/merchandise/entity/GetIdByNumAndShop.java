package com.wisrc.merchandise.entity;

import lombok.Data;

@Data
public class GetIdByNumAndShop {
    private String id;
    private String mskuId;
    private String shopId;
    private String shopOwnerId;
}
