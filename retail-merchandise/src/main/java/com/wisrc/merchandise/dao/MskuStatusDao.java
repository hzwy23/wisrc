package com.wisrc.merchandise.dao;

import com.wisrc.merchandise.entity.MskuStatusAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MskuStatusDao {
    @Select("SELECT msku_status_cd AS mskuStatusCd, msku_status_desc AS mskuStatusDesc FROM msku_status_attr")
    List<MskuStatusAttrEntity> getMskuStatus();
}
