package com.wisrc.basic.dao;

import com.wisrc.basic.dao.sql.BasicShipmentEnterpriseSQL;
import com.wisrc.basic.dao.sql.BasicShipmentStatusAttrSQL;
import com.wisrc.basic.dao.sql.BasicShipmentTypeAttrSQL;
import com.wisrc.basic.entity.ShipmentEnterpriseEntity;
import com.wisrc.basic.entity.ShipmentStatusAttrEntity;
import com.wisrc.basic.entity.ShipmentTypeAttrEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShipmentEnterpriseDao {

    @Select("select count(*) from shipment_enterprise where shipment_name = #{shipmentName} and status_cd = 1")
    int checkout(@Param("shipmentName") String shipmentName);

    @Insert("insert into shipment_enterprise(shipment_id,shipment_name,shipment_addr,contact,phone,qq,shipment_type,status_cd,modify_time,modify_user,create_time,create_user,province_name,city_name) values(#{shipmentId},#{shipmentName}," +
            "#{shipmentAddr},#{contact},#{phone},#{qq},#{shipmentType},#{statusCd},#{modifyTime},#{modifyUser},#{createTime},#{createUser},#{provinceName},#{cityName})")
    void add(ShipmentEnterpriseEntity ele);

    @Update("update shipment_enterprise set status_cd = #{statusCd}, modify_user = #{userId}, modify_time = #{currentTime} where shipment_id = #{shipmentId}")
    void changeStatus(@Param("shipmentId") String shipmentId,
                      @Param("statusCd") int statusCd,
                      @Param("userId") String userId,
                      @Param("currentTime") String currentTime);

    @SelectProvider(type = BasicShipmentEnterpriseSQL.class, method = "findByCond")
    List<ShipmentEnterpriseEntity> findByCond(@Param("statusCd") String statusCd,
                                              @Param("keyword") String keyword
    );

    @Update("update shipment_enterprise set shipment_name = #{shipmentName}, shipment_addr = #{shipmentAddr}, contact = #{contact}, phone=#{phone}, qq = #{qq}, shipment_type = #{shipmentType}," +
            "modify_time = #{modifyTime}, modify_user = #{modifyUser}, province_name = #{provinceName}, city_name = #{cityName} where shipment_id = #{shipmentId}")
    void updateById(ShipmentEnterpriseEntity ele);

    /**
     * 分页查询物流商信息
     *
     * @return
     */
    @Select("select shipment_id, shipment_name, shipment_addr, contact, phone, qq,shipment_type, status_cd, modify_time, modify_user, create_time, create_user,country_en,country_name,province_en,province_name,city_en,city_name from shipment_enterprise order by status_cd asc, modify_time desc")
    List<ShipmentEnterpriseEntity> findByPage();

    /**
     * 物流商状态码表信息
     *
     * @return
     */
    @SelectProvider(type = BasicShipmentStatusAttrSQL.class, method = "findStatusAttr")
    List<ShipmentStatusAttrEntity> findStatusAttr(@Param("statusCd") int statusCd);

    /**
     * 物流商类型码表
     *
     * @return
     */
    @SelectProvider(type = BasicShipmentTypeAttrSQL.class, method = "findTypeAttr")
    List<ShipmentTypeAttrEntity> findTypeAttr(@Param("shipmentType") int shipmentType);

    @Select("select shipment_name from shipment_enterprise where shipment_id = #{shipmentId}")
    String findShipmentNameById(String shipmentId);

    @Select("select shipment_id, shipment_name, shipment_addr, contact, phone, qq, shipment_type, status_cd, modify_time, modify_user, create_time, create_user, country_en, " +
            "country_name, province_en, province_name, city_en, city_name from shipment_enterprise where shipment_id=#{id} ")
    ShipmentEnterpriseEntity findShipmentById(@Param("id") String id);

    @SelectProvider(type = BasicShipmentEnterpriseSQL.class, method = "findByListId")
    List<ShipmentEnterpriseEntity> findByListId(@Param("idList") String idList);

    @SelectProvider(type = BasicShipmentEnterpriseSQL.class, method = "findByCond")
    List<ShipmentEnterpriseEntity> findAll(@Param("statusCd") String statusCd, @Param("keyword") String keyword);

    @SelectProvider(type = BasicShipmentEnterpriseSQL.class, method = "findByName")
    List<ShipmentEnterpriseEntity> findByName(@Param("statusCd") int statusCd, @Param("shipmentName") String shipmentName, @Param("contact") String contact);

    @Select("select shipment_id, shipment_name, shipment_addr, contact, phone, qq,shipment_type, status_cd, modify_time, modify_user, create_time, create_user,country_en,country_name,province_en,province_name,city_en,city_name from shipment_enterprise " +
            " where status_cd=1")
    List<ShipmentEnterpriseEntity> findAllEnableShipment();
}