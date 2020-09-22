package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.dao.sql.ReturnWarehouseApplySql;
import com.wisrc.shipment.webapp.entity.ReturnWarehouseApplyEnity;
import com.wisrc.shipment.webapp.vo.ProductDetaiVo;
import com.wisrc.shipment.webapp.vo.ReturnWareHouseEnityVo;
import com.wisrc.shipment.webapp.entity.ProductDetail;
import com.wisrc.shipment.webapp.entity.ReceiveWarehouseEnity;
import com.wisrc.shipment.webapp.entity.RemoveOrderEnity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReturnWarehouseApplyDao {
    @Insert("insert into return_warehouse_basis_info (return_apply_id, shop_id, remark, status_cd, reason, create_time, create_user, update_time, update_user, employee_id) " +
            "values (#{returnApplyId}, #{shopId}, #{remark}, #{statusCd}, #{reason}, #{createTime}, #{createUser}, #{updateTime}, #{updateUser}, #{employeeId})")
    void addReturnBasis(ReturnWarehouseApplyEnity returnWarehouseApplyEnity);

    @Insert("insert into return_warehouse_detail_info (uuid, msku_id, commodity_id, sale_price, apply_return_num, return_apply_id) values (#{uuid},#{mskuId},#{commodityId},#{salePrice},#{applyReturnNum},#{returnApplyId})")
    void addProductDetail(ProductDetail productDetail);

    @Insert("insert into return_reason_type_rel (return_type_cd, return_apply_id) values (#{typeCd},#{returnApplyId})")
    void addReasonTypeRel(@Param("typeCd") Integer typeCd, @Param("returnApplyId") String returnApplyId);

    @SelectProvider(type = ReturnWarehouseApplySql.class, method = "findBycond")
    List<ReturnWareHouseEnityVo> findBycond(@Param("shopId") String shopId, @Param("employeeId") String employeeId, @Param("statusCd") int statusCd, @Param("keyword") String keyword, @Param("comodityIds") String comodityIds, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("productName") String productName, @Param("comodityNewIds") String comodityNewIds);

    @Select("select uuid, msku_id, commodity_id, sale_price, apply_return_num, return_apply_id from return_warehouse_detail_info where return_apply_id=#{returnApplyId}")
    List<ProductDetaiVo> getDetail(String returnApplyId);

    @Select("select remove_order_id from remove_order where return_apply_id=#{returnApplyId}")
    List<String> getRemoveOrder(String returnApplyId);

    @Select("select return_apply_id, shop_id, remark, status_cd, reason, create_time, employee_id from return_warehouse_basis_info where return_apply_id=#{returnApplyId}")
    ReturnWareHouseEnityVo getReturnWareHouseById(String returnApplyId);

    @Select("select return_type_name from v_order_reason_rel_code where return_apply_id=#{returnApplyId}")
    List<String> getReturnTypeName(String returnApplyId);

    @Select("select return_status_desc from return_status_code where return_status_cd=#{statusCd}")
    String getStatusDesc(Integer statusCd);

    @Select("select return_apply_id, province_name, city_name, address, zip_code, contact, phone, remark, warehouse_id,sub_warehouse_id from receive_warehouse_info where return_apply_id=#{returnApplyId}")
    ReceiveWarehouseEnity getReceiveWarehouseEnity(String returnApplyId);

    @Update("update return_warehouse_basis_info set shop_id=#{shopId}, remark=#{remark}, update_user=#{updateUser}, update_time=#{updateTime} where return_apply_id=#{returnApplyId}")
    void updateReturnWarehouse(ReturnWarehouseApplyEnity returnWarehouseApplyEnity);

    @Delete("delete from return_reason_type_rel where return_apply_id=#{returnApplyId}")
    void deleteTypeRelById(String returnApplyId);

    @Delete("delete from return_warehouse_detail_info where return_apply_id=#{returnApplyId}")
    void deleteDetailById(String returnApplyId);

    @Select("select return_type_cd from return_reason_type_rel where return_apply_id=#{returnApplyId}")
    List<String> getReturnTypeCdList(String returnApplyId);

    @Update("update return_warehouse_basis_info set status_cd=#{statusCd}, reason=#{reason}, update_user=#{userId}, update_time=now() where return_apply_id=#{returnApplyId}")
    void changeStatusApply(@Param("returnApplyId") String returnApplyId, @Param("reason") String reason, @Param("statusCd") int statusCd, @Param("userId") String userId);

    @Insert("insert into remove_order (remove_order_id, return_apply_id) values (#{orderId}, #{returnApplyId})")
    void addRemoveOrder(@Param("returnApplyId") String returnApplyId, @Param("orderId") String orderId);

    @Insert(" insert into receive_warehouse_info " +
            " (return_apply_id, province_name, city_name, address, zip_code, contact, phone, remark, " +
            "  warehouse_id,sub_warehouse_id, create_user, create_time) values " +
            " (#{returnApplyId}, #{provinceName}, #{cityName}, #{address}, #{zipCode}, #{contact}, #{phone},#{remark}, " +
            " #{warehouseId},#{subWarehouseId}, #{createUser}, #{createTime}) ")
    void addReceiveWarehouse(ReceiveWarehouseEnity receiveWarehouseEnity);

    @Update(" update receive_warehouse_info set province_name=#{provinceName}, city_name=#{cityName}, address=#{address}, " +
            " zip_code=#{zipCode}, contact=#{contact}, phone=#{phone}, remark=#{remark}, warehouse_id=#{warehouseId}, " +
            " sub_warehouse_id=#{subWarehouseId},create_user=#{createUser}, create_time=#{createTime} where return_apply_id=#{returnApplyId} ")
    void updateReceiveWarehouse(ReceiveWarehouseEnity receiveWarehouseEnity);

    @Delete("delete from remove_order where return_apply_id=#{returnApplyId}")
    void deleteRemoveOrder(String returnApplyId);

    @Select("select remove_order_id from remove_order where return_apply_id=#{returnApplyId}")
    List<String> getRemoveOrderIdList(String returnApplyId);

    @Select("select shop_id, remove_order_id from v_return_warehouse_remove_order where status_cd=2 and remove_order_id is not null")
    List<RemoveOrderEnity> getRemoveOrderEnity(int statusCd);

    @Select("select shop_id, remove_order_id from v_return_warehouse_remove_order where return_apply_id=#{returnApplyId}")
    List<RemoveOrderEnity> getShopAndRemoveIds(String returnApplyId);

    @Select("select return_apply_id from remove_order where remove_order_id=#{orderId} limit 0,1")
    String getReturnId(String orderId);

    @Select("select shop_id from return_warehouse_basis_info where return_apply_id=#{returnApplyId}")
    String getShopIdById(String returnApplyId);
}
