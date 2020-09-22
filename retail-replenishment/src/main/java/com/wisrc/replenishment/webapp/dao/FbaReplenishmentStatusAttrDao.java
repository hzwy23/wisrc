package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.entity.FbaReplenishmentStatusAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface FbaReplenishmentStatusAttrDao {
    /**
     * 查询补货单状态码集合
     *
     * @return
     */
    @Select("select status_cd,status_name from replenishment_status_attr")
    List<FbaReplenishmentStatusAttrEntity> findAll();
}
