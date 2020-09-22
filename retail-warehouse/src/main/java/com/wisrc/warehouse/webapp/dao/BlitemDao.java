package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.dao.sql.BlitemBasicSQL;
import com.wisrc.warehouse.webapp.entity.BlitemInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlitemDao {

    /**
     * 根据仓库id和盘点时间去查询盘点信息
     *
     * @param blitemId
     * @param warehouseId
     * @param startDate
     * @param endDate
     * @param skuIds
     * @return
     */
    @SelectProvider(type = BlitemBasicSQL.class, method = "search")
    List<BlitemInfoEntity> findAllByCon(@Param("blitemId") String blitemId,
                                        @Param("warehouseId") String warehouseId,
                                        @Param("startDate") String startDate,
                                        @Param("endDate") String endDate,
                                        @Param("skuIds") List<String> skuIds);

    @Insert("insert into blitem_info(blitem_id, warehouse_id, blitem_date, status_cd," +
            " operation_user, operation_date, delete_status, create_time, create_user)" +
            " values (#{blitemId},#{warehouseId},#{blitemDate}" +
            ",#{statusCd},#{operationUser},#{operationDate},#{deleteStatus},#{createTime},#{createUser})")
    void add(BlitemInfoEntity blitemInfoEntity);

    @Select("select blitem_id, warehouse_id, blitem_date, status_cd, operation_user, operation_date, audit_user, audit_date, delete_status, create_time, create_user\n" +
            "from blitem_info\n" +
            "where blitem_id = #{blitemId}\n" +
            " order by create_time desc")
    BlitemInfoEntity findByBlitemId(@Param("blitemId") String blitemId);

    @Update("update blitem_info set status_cd = #{statusCd},audit_user = #{auditUser},audit_date = #{auditDate} \n" +
            "where blitem_id = #{blitemId}")
    void updateStatus(@Param("blitemId") String blitemId,
                      @Param("statusCd") String statusCd,
                      @Param("auditUser") String auditUser,
                      @Param("auditDate") String auditDate);
}
