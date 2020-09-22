package com.wisrc.code.webapp.service;

import com.wisrc.code.webapp.entity.CodeProvinceCityInfoEntity;
import com.wisrc.code.webapp.utils.Result;

import java.util.LinkedHashMap;

public interface CodeCountryInfoService {
    /**
     * 查询所有的国家信息
     */
    Result findAll();

    Result update(String countryCd, String countryName, String countryEnglish, String userId);

    Result insert(String countryCd, String countryName, String countryEnglish, String userId);

    Result delete(String countryCd);

    Result fuzzyQuery(String countryCd, String countryName, String countryEnglish, Integer pageNum, Integer pageSize);

    /***********************************************************/
    /**
     * 查询所有的地区信息
     */
    LinkedHashMap getAll(String keyword, String countryCd);

    LinkedHashMap getAllPagging(int pageNum, int pageSize, String keyword, String countryCd);

    Result insert(CodeProvinceCityInfoEntity ele);

    Result update(CodeProvinceCityInfoEntity ele);

    Result deleteProvinceCode(String ele, int typeCd);

    Result getProvince(String countryCd);

}
