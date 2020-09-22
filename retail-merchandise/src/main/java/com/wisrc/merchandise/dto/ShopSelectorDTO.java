package com.wisrc.merchandise.dto;

import lombok.Data;

@Data
public class ShopSelectorDTO {
    private String id;
    private String shopName;
    private String sellerId;

    @Override
    public String toString() {
        return "ShopSelectorDTO{" +
                "id='" + id + '\'' +
                ", shopName='" + shopName + '\'' +
                '}';
    }
}
