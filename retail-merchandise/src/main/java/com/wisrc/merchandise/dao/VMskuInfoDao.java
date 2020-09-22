package com.wisrc.merchandise.dao;

import com.wisrc.merchandise.dao.sql.MskuSQL;
import com.wisrc.merchandise.entity.VMskuEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface VMskuInfoDao {

    @SelectProvider(type = MskuSQL.class, method = "searchMsku")
    List<VMskuEntity> search(@Param("platformName") String platformName,
                             @Param("shopName") String shopName,
                             @Param("mskuId") String mskuId,
                             @Param("excludeCommodityIds") String excludeCommodityIds);
}
