package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.entity.FbaPackUnitAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface FbaPackUnitAttrDao {
    /**
     * 查询补货单商品的单位编码和单位描述
     *
     * @return
     */
    @Select("select unit_cd,unit_desc from fba_pack_unit_attr")
    List<FbaPackUnitAttrEntity> findAll();
}
