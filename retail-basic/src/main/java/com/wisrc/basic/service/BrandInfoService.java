package com.wisrc.basic.service;


import com.wisrc.basic.entity.BrandInfoEntity;
import com.wisrc.basic.utils.Result;

public interface BrandInfoService {
    Result insert(BrandInfoEntity entity);

    Result update(BrandInfoEntity entity);

    Result fuzzyFind(Integer pageNum, Integer pageSize, BrandInfoEntity entity);

    Result findById(BrandInfoEntity entity);

    Result restrict(BrandInfoEntity entity);
}
