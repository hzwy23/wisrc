package com.wisrc.zuul.dto;


import lombok.Data;

@Data
public class SysUserDomainDto {
    private int id;
    private String username;
    private String mobilePhone;
    private Integer status;
    private Integer deleteFlag;
}
