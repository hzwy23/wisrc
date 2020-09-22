package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.dao.sql.HandmadeOutWarehouseBillSql;
import com.wisrc.warehouse.webapp.vo.SelectOutBillVO;
import com.wisrc.warehouse.webapp.entity.HandmadeOutWarehouseBillEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HandmadeOutWarehouseBillDao {
    @Insert("insert into handmade_out_warehouse_bill (out_bill_id, warehouse_id, out_type_cd, create_user, create_time, sub_warehouse_id) values " +
            "(#{outBillId},#{warehouseId},#{outTypeCd},#{createUser},#{createTime},#{subWarehouseId})")
    void add(HandmadeOutWarehouseBillEntity entity);

    @SelectProvider(type = HandmadeOutWarehouseBillSql.class, method = "getList")
    List<SelectOutBillVO> getList(@Param("outBillId") String outBillId,
                                  @Param("warehouseId") String warehouseId,
                                  @Param("outTypeCd") int outTypeCd,
                                  @Param("startTime") String startTime,
                                  @Param("endTime") String endTime);

    @Select("select out_bill_id, warehouse_id, out_type_cd, create_user, create_time from handmade_out_warehouse_bill where out_bill_id=#{outBillId} ")
    HandmadeOutWarehouseBillEntity getDetail(@Param("outBillId") String outBillId);


}
