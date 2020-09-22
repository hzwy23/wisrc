package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.dao.sql.HandmadeEnterWarehouseBillSql;
import com.wisrc.warehouse.webapp.vo.SelectEnterBillVO;
import com.wisrc.warehouse.webapp.entity.EnterWarehouseListEntity;
import com.wisrc.warehouse.webapp.entity.HandmadeEnterWarehouseBillEntity;
import com.wisrc.warehouse.webapp.entity.HandmadeStatusEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HandmadeEnterWarehouseBillDao {
    @Insert("insert into handmade_enter_warehouse_bill (enter_bill_id, warehouse_id, enter_type_cd, create_user, create_time,sub_warehouse_id,status_cd) values " +
            "(#{enterBillId},#{warehouseId},#{enterTypeCd},#{createUser},#{createTime},#{subWarehouseId},1)")
    void add(HandmadeEnterWarehouseBillEntity billEntity);

    @SelectProvider(type = HandmadeEnterWarehouseBillSql.class, method = "getList")
    List<SelectEnterBillVO> getList(@Param("enterBillId") String enterBillId,
                                    @Param("warehouseId") String warehouseId,
                                    @Param("enterTypeCd") int enterTypeCd,
                                    @Param("startTime") String startTime,
                                    @Param("endTime") String endTime);

    @Select("select enter_bill_id, warehouse_id, enter_type_cd, create_user, create_time from handmade_enter_warehouse_bill where enter_bill_id=#{enterBillId}")
    HandmadeEnterWarehouseBillEntity getDetail(@Param("enterBillId") String enterBillId);

    /**
     * 修改手工入库单状态
     *
     * @param enterBillId
     * @param i
     */
    @Update("UPDATE handmade_enter_warehouse_bill SET status_cd = #{status} WHERE enter_bill_id = #{enterBillId}")
    void updateStatus(@Param("enterBillId") String enterBillId,
                      @Param("status") int i);

    /**
     * 条件查询手工入库单
     *
     * @param warehouseId
     * @param enterTypeCd
     * @param status
     * @param startTime
     * @param endTime
     * @param keyword
     * @return
     */
    @SelectProvider(type = HandmadeEnterWarehouseBillSql.class, method = "findListByCond")
    List<SelectEnterBillVO> findHandmadeListByCond(@Param("warehouseId") String warehouseId,
                                                   @Param("enterTypeCd") Integer enterTypeCd,
                                                   @Param("status") Integer status,
                                                   @Param("startTime") String startTime,
                                                   @Param("endTime") String endTime,
                                                   @Param("keyword") String keyword);

    /**
     * 根据手工入库单号查询出所有该手工入库单下的产品信息
     *
     * @param enterBillId
     * @return
     */
    @Select("SELECT sku_id,sku_name_zh AS  sku_name ,fn_sku_id ,enter_warehouse_num,actual_enter_warehouse_num FROM v_handmade_info WHERE enter_bill_id = #{enterBillId} ")
    List<EnterWarehouseListEntity> findSkuListByHandmadeEnterBillId(@Param("enterBillId") String enterBillId);

    /**
     * 根据手工入库单号查询出相应的手工入库单的基本信息
     *
     * @param enterBillId
     * @return
     */
    @Select("SELECT enter_bill_id,warehouse_id,warehouse_name,enter_type_cd,type_name AS enter_type_name ,status_cd,status_name,create_user,date_format(create_time,'%Y-%m-%d') AS create_time ,date_format(enter_warehouse_time,'%y-%m-%d') AS enter_warehouse_time,cancel_reason\n" +
            "FROM v_handmade_info WHERE enter_bill_id = #{enterBillId} group by enter_bill_id")
    SelectEnterBillVO findHandmadeById(@Param("enterBillId") String enterBillId);

    /**
     * 补充取消原因
     *
     * @param enterBillId
     * @param cancelReason
     */
    @Update("UPDATE handmade_enter_warehouse_bill SET cancel_reason = #{cancelReason} WHERE enter_bill_id = #{enterBillId} ")
    void updateCancelReason(@Param("enterBillId") String enterBillId,
                            @Param("cancelReason") String cancelReason);

    /**
     * 获取手工入库单的所有的状态
     *
     * @return
     */
    @Select("SELECT status_cd,status_name FROM handmade_enter_warehouse_attr")
    List<HandmadeStatusEntity> findAllStatus();

    /**
     * 更新手工入库单的实际入库数量
     *
     * @param enterBillId
     * @param skuId
     * @param actualEnterWarehouseNum
     */
    @Update("UPDATE enter_warehouse_list SET actual_enter_warehouse_num = #{actualEnterWarehouseNum} WHERE enter_bill_id = #{enterBillId} AND  sku_id = #{skuId}")
    void updateDeliveryNum(@Param("enterBillId") String enterBillId,
                           @Param("skuId") String skuId,
                           @Param("actualEnterWarehouseNum") Integer actualEnterWarehouseNum);
}
