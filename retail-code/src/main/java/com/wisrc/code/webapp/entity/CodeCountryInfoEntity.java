package com.wisrc.code.webapp.entity;

import lombok.Data;

@Data
public class CodeCountryInfoEntity {
    private String countryCd;
    private String countryName;
    private String countryEnglish;
    private String modifyUser;
    private String modifyTime;
    private String createUser;
    private String createTime;
}
