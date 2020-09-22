package com.wisrc.product.webapp.entity;

import lombok.Data;

@Data
public class BaseEntity {
    private String createTime;
    private String updateTime;
    private String createUser;
    private String updateUser;

}
