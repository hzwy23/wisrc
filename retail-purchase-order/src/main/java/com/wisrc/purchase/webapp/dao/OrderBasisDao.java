package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.dao.sql.OrderBasisSQL;
import com.wisrc.purchase.webapp.entity.OrderAttachmentInfoEntity;
import com.wisrc.purchase.webapp.entity.OrderBasisInfoEntity;
import com.wisrc.purchase.webapp.entity.OrderSkuSupplierEntity;
import com.wisrc.purchase.webapp.vo.OrderNeetVO;
import com.wisrc.purchase.webapp.vo.RemarkVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
@Mapper
public interface OrderBasisDao {
    /**
     * 通过条件查询采购订单基本信息
     *
     * @param orderId
     * @param employeeId
     * @param deliveryTypeCd
     * @param keyword
     * @param tiicketOpenCd
     * @param customsTypeCd
     * @param billDateBegin
     * @param billDateEnd
     * @return
     */
    @SelectProvider(type = OrderBasisSQL.class, method = "findBasisInfo")
    List<OrderBasisInfoEntity> findBasisInfo(@Param("orderId") final String orderId,
                                             @Param("employeeId") final String employeeId,
                                             @Param("deliveryTypeCd") final int deliveryTypeCd,
                                             @Param("keyword") final String keyword,
                                             @Param("tiicketOpenCd") final int tiicketOpenCd,
                                             @Param("customsTypeCd") final int customsTypeCd,
                                             @Param("billDateBegin") final Date billDateBegin,
                                             @Param("billDateEnd") final Date billDateEnd,
                                             @Param("deliveryTimeBegin") final Date deliveryTimeBegin,
                                             @Param("deliveryTimeEnd") final Date deliveryTimeEnd,
                                             @Param("supplierId") final String supplierId,
                                             @Param("supplierIds") final String supplierIds);

    /**
     * 通过条件查询采购订单产品信息列表（外部需求）
     *
     * @return
     */
    @SelectProvider(type = OrderBasisSQL.class, method = "findBasisNeet")
    List<OrderNeetVO> findBasisNeet(@Param("orderId") final String orderId,
                                    @Param("supplierIds") final List supplierIds,
                                    @Param("skuId") final String skuId,
                                    @Param("skuIds") final List skuIds);

    /**
     * 通过条件查询采购订单产品信息列表（外部需求）2
     *
     * @return
     */
    @SelectProvider(type = OrderBasisSQL.class, method = "findBasisNeet2")
    List<OrderNeetVO> findBasisNeet2(@Param("orderId") final String orderId,
                                     @Param("supplierIds") final List supplierIds,
                                     @Param("skuId") final String skuId,
                                     @Param("skuIds") final List skuIds);

    /**
     * 通过id数组查询采购订单基本信息
     */
    @SelectProvider(type = OrderBasisSQL.class, method = "findBasisByIds")
    List<OrderBasisInfoEntity> findBasisByIds(@Param("ids") final String[] ids);

    /**
     * 查询全部采购订单基本信息
     * order_status 订单状态 0表示正常运行  1表示虚拟删除
     */
    @Select("select order_id,bill_date,supplier_id,employee_id,amount_without_tax,amount_with_tax,tiicket_open_cd,customs_type_cd,payment_provision,delivery_type_cd,create_user,create_time,deposit_rate,supplier_people,supplier_phone,supplier_fax,freight,total_amount from order_basis_info where order_status = 0")
    List<OrderBasisInfoEntity> findBasisInfoAll();

    /**
     * 验证订单Id是否存在
     *
     * @return
     */
    @Select("select count(*) from order_basis_info where order_id = #{orderId}")
    int findNumById(@Param("orderId") String orderId);

    /**
     * 通过订单号ID查询订单基础信息
     */
    @Select("select order_id,bill_date,supplier_id,employee_id,amount_without_tax,amount_with_tax,tiicket_open_cd,customs_type_cd,payment_provision,delivery_type_cd,create_user,create_time,deposit_rate,supplier_people,supplier_phone,supplier_fax,freight,total_amount,remark from order_basis_info where order_id = #{orderId}")
    OrderBasisInfoEntity findBasisInfoById(@Param("orderId") String orderId);

    /**
     * 虚拟删除订单信息
     * order_status 订单状态 0表示正常运行  1表示虚拟删除
     */
    @Update("update order_basis_info set order_status = 1 where order_id = #{orderId}")
    void deleteBasisInfo(@Param("orderId") String orderId);

    /**
     * 修改订单基本信息
     *
     * @param ele
     */
    @Update("update order_basis_info set bill_date = #{billDate},supplier_id = #{supplierId},employee_id = #{employeeId},amount_without_tax = #{amountWithoutTax},amount_with_tax = #{amountWithTax},tiicket_open_cd = #{tiicketOpenCd},customs_type_cd = #{customsTypeCd},payment_provision = #{paymentProvision}," +
            "delivery_type_cd = #{deliveryTypeCd},create_user = #{createUser},create_time = #{createTime},supplier_people = #{supplierPeople},supplier_phone = #{supplierPhone},supplier_fax = #{supplierFax},freight = #{freight},deposit_rate = #{depositRate},total_amount=#{totalAmount}, remark = #{remark} where order_id = #{orderId}")
    void updateBasisInfo(OrderBasisInfoEntity ele);

    /**
     * 修改订单状态
     */
    @Update("update order_basis_info set delivery_type_cd = #{deliveryTypeCd} where order_id = #{orderId}")
    void updateOrderStatus(@Param("orderId") String orderId, @Param("deliveryTypeCd") int deliveryTypeCd);

    /**
     * 新增订单基本信息
     *
     * @param ele
     */
    @Insert("insert into order_basis_info (order_id,bill_date,supplier_id,employee_id,amount_without_tax,amount_with_tax,tiicket_open_cd,customs_type_cd,payment_provision,delivery_type_cd,create_user,create_time,order_status,supplier_people,supplier_phone,supplier_fax,freight,deposit_rate,total_amount,remark )" +
            "values(#{orderId},#{billDate},#{supplierId},#{employeeId},#{amountWithoutTax},#{amountWithTax},#{tiicketOpenCd},#{customsTypeCd},#{paymentProvision},#{deliveryTypeCd},#{createUser},#{createTime},#{orderStatus},#{supplierPeople},#{supplierPhone},#{supplierFax},#{freight},#{depositRate},#{totalAmount},#{remark})")
    void addBasisInfo(OrderBasisInfoEntity ele);

    /**
     * 新增订单附件信息
     */
    @Insert("insert into order_attachment_info (uuid,order_id,attachment_url) values (#{uuid},#{orderId},#{attachmentUrl})")
    void addOrderAttachment(OrderAttachmentInfoEntity ele);

    /**
     * 删除订单附件信息
     *
     * @param orderId
     */
    @Delete("delete from order_attachment_info where order_id = #{orderId}")
    void delOrderAttachment(@Param("orderId") String orderId);

    /**
     * 通过订单ID查询此订单相关附件
     *
     * @param orderId
     * @return
     */
    @Select("select uuid,order_id,attachment_url from order_attachment_info where order_id = #{orderId}")
    List<OrderAttachmentInfoEntity> findAttachmentInfo(@Param("orderId") String orderId);

    /**
     * 查询数据库订单号最大的Id
     *
     * @return
     */
    @Select("select max(order_id) from order_basis_info")
    String findOrderId();

    /**
     * 查询数据库订单号最大的Id
     *
     * @return
     */
    @Select("select order_id from order_basis_info")
    List<String> findAll();

    /**
     * 根据库存sku批量查询最近一次采购订单的供应商
     */
    @Select("select sku_id,supplier_id,max(create_time) from v_order_sku_supplier where sku_id =#{skuId}")
    OrderSkuSupplierEntity findSupplier(@Param("skuId") String skuId);


    @Select("SELECT count(purchase_order_id) FROM arrival_basis_info where purchase_order_id = #{orderId}")
    int getArrivalBasisInfo(@Param("orderId") String orderId);

    @Select("SELECT count(order_Id) FROM entry_warehouse_info where order_id = #{orderId}")
    int getEntryWarehouseInfo(@Param("orderId") String orderId);

    @Select("SELECT count(order_Id) FROM purchase_rejection_info where order_id = #{orderId}")
    int getPurchaseRejectionInfo(@Param("orderId") String orderId);

    @Select("SELECT count(order_Id) FROM purchase_return_info where order_id=#{orderId}")
    int getPurchaseReturnInfo(@Param("orderId") String orderId);

    @Update("update order_basis_info set delivery_type_cd=#{statusCd} where order_id=#{orderId} and delivery_type_cd!=4")
    void changeStatus(@Param("statusCd") int statusCd, @Param("orderId") String orderId);

    @Update("update order_basis_info set remark=#{remark} where order_id=#{orderId}")
    void updateRemark(RemarkVo remarkVo);
}
