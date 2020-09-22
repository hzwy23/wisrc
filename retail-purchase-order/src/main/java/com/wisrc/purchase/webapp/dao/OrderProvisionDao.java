package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.entity.DynamicFieldsAttrEntity;
import com.wisrc.purchase.webapp.entity.OrderProvisionEntity;
import com.wisrc.purchase.webapp.entity.ProvisionMouldEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderProvisionDao {
    /**
     * 查询订单条款信息
     *
     * @param orderId
     * @return
     */
    @Select("select uuid,order_id,provision_url,delete_status,payee,bank,account from order_provision_info where order_id = #{orderId} and delete_status = 0")
    OrderProvisionEntity findOrderProvision(@Param("orderId") String orderId);

    /**
     * 查询条款模板列表
     *
     * @return
     */
    @Select("select uuid,explain_name,mould_url,delete_status,create_time,create_user,modify_user,modify_time from order_provision_mould  where delete_status = 0")
    List<ProvisionMouldEntity> findProvisionMould();

    /**
     * 查询条款模板列表ById
     *
     * @return
     */
    @Select("select uuid,explain_name,mould_url,delete_status,create_time,create_user,modify_user,modify_time from order_provision_mould where uuid=#{uuid}")
    ProvisionMouldEntity findProvisionMouldById(@Param("uuid") String uuid);

    /**
     * 查询在光标位置插入字段码表信息列表
     *
     * @return
     */
    @Select("select dynamic_fields_cd,dynamic_fields_desc from order_dynamic_fields_attr")
    List<DynamicFieldsAttrEntity> findDynamicFieldsAttr();

    /**
     * 新增订单条款信息
     *
     * @param orderProvisionEntity
     */
    @Insert("insert into order_provision_info (uuid,order_id,provision_url,delete_status,payee,bank,account) values (#{uuid},#{orderId},#{provisionUrl},#{deleteStatus},#{payee},#{bank},#{account})")
    void addOrderProvision(OrderProvisionEntity orderProvisionEntity);

    /**
     * 新增条款模板
     *
     * @param provisionMouldEntity
     */
    @Insert("insert into order_provision_mould (uuid,explain_name,mould_url,delete_status,create_time,create_user,modify_user,modify_time) values (#{uuid},#{explainName},#{mouldUrl},#{deleteStatus},#{createTime},#{createUser},#{modifyUser},#{modifyTime})")
    void addProvisionMould(ProvisionMouldEntity provisionMouldEntity);

    @Delete("delete from order_provision_info where uuid = #{uuid}")
    void deleteOrderProvision(@Param("uuid") String uuid);

    @Delete("delete from order_provision_mould where uuid = #{uuid}")
    void deleteProvisionMould(@Param("uuid") String uuid);

    /**
     * 逻辑删除订单条款信息
     *
     * @param
     */
    @Update("update order_provision_info set delete_status=#{deleteStatus} where order_id=#{orderId}")
    void delOrderProvision(OrderProvisionEntity orderProvisionEntity);

    /**
     * 逻辑删除条款模板   modify_user,modify_time
     *
     * @param
     */
    @Update("update order_provision_mould set delete_status=#{deleteStatus},modify_user=#{modifyUser},modify_time=#{modifyTime} where uuid=#{uuid}")
    void delProvisionMould(ProvisionMouldEntity provisionMouldEntity);


    /**
     * 修改订单条款信息
     *
     * @param orderProvisionEntity
     */
    @Update("update order_provision_info set order_id=#{orderId},provision_url=#{provisionUrl},delete_status=#{deleteStatus} where uuid=#{uuid},payee=#{payee},bank=#{payee},account=#{payee}")
    void updateOrderProvision(OrderProvisionEntity orderProvisionEntity);

    /**
     * 修改条款模板
     *
     * @param provisionMouldEntity
     */
    @Update("update order_provision_mould set explain_name=#{explainName},mould_url=#{mouldUrl},delete_status=#{deleteStatus},modify_user=#{modifyUser},modify_time=#{modifyTime} where uuid=#{uuid}")
    void updateProvisionMould(ProvisionMouldEntity provisionMouldEntity);
}
