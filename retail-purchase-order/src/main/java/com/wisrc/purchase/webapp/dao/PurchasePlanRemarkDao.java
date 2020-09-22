package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.dao.sql.PurchasePlanRemarkSql;
import com.wisrc.purchase.webapp.entity.PurchasePlanRemarkEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface PurchasePlanRemarkDao {
    @Select("<script>"
            + "SELECT uuid, remark_desc, create_user, create_time FROM purchase_plan_remark WHERE"
            + "<foreach item='uuid' index='index' collection='uuids' open=' uuid IN (' separator=',' close=')'>"
            + "#{uuid}"
            + "</foreach>"
            + "ORDER BY create_time"
            + "</script>"
    )
    List<PurchasePlanRemarkEntity> getRemark(@Param("uuids") List uuids);

    @SelectProvider(type = PurchasePlanRemarkSql.class, method = "deletePlanRemark")
    void deletePlanRemark(@Param("uuids") List uuids);
}
