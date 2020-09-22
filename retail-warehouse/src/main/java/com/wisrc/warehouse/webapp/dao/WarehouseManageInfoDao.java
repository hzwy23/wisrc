package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.dao.sql.WarehouseManageInfoSQL;
import com.wisrc.warehouse.webapp.entity.WarehouseManageInfoEntity;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface WarehouseManageInfoDao {
    @SelectProvider(type = WarehouseManageInfoSQL.class, method = "search")
    List<WarehouseManageInfoEntity> search(@Param("statusCd") int statusCd,
                                           @Param("typeCd") String typeCd,
                                           @Param("keyWord") String keyWord);


    // 查询所有的仓库信息
    @Select("select warehouse_id,warehouse_name,type_cd,status_cd,create_user,create_time,country_cd, province_name,city_name,zip_code, details_addr, sub_warehouse_support, remark, warehouse_contact, warehouse_phone, modify_user, modify_time from warehouse_basis_info")
    List<WarehouseManageInfoEntity> findAll();

    //编辑仓库信息
    @Update("update warehouse_basis_info set warehouse_name = #{warehouseName}, modify_user = #{modifyUser}, modify_time=#{modifyTime}, country_cd = #{countryCd}, province_name = #{provinceName}, city_name = #{cityName}, zip_code = #{zipCode}, details_addr = #{detailsAddr}, warehouse_contact = #{warehouseContact}, warehouse_phone = #{warehousePhone}, remark = #{remark} where warehouse_id = #{warehouseId}")
    void changeName(WarehouseManageInfoEntity ele);

    // 改变仓库状态
    @Update("update warehouse_basis_info set status_cd = #{statusCd},modify_user=#{modifyUser},modify_time=#{modifyTime} where warehouse_id = #{warehouseId}")
    void changeStatus(@Param("warehouseId") String warehouseId, @Param("statusCd") int statusCd, @Param("modifyUser") String modifyUser, @Param("modifyTime") Timestamp modifyTime);


    @Insert("insert into warehouse_basis_info(warehouse_id,warehouse_name,type_cd,status_cd,create_user,create_time,country_cd, province_name,city_name,zip_code, details_addr, sub_warehouse_support, remark, warehouse_contact, warehouse_phone, modify_user, modify_time, random_value) values(#{warehouseId},#{warehouseName},#{typeCd},#{statusCd},#{createUser},#{createTime},#{countryCd}, #{provinceName},#{cityName}, #{zipCode}, #{detailsAddr}, #{subWarehouseSupport}, #{remark}, #{warehouseContact}, #{warehousePhone}, #{modifyUser}, #{modifyTime}, #{warehouseId})")
    void add(WarehouseManageInfoEntity ele);

    @Select("select warehouse_id from warehouse_basis_info where random_value = #{randomValue}")
    String findIdByRandomValue(String randomValue);

    @Update("update warehouse_basis_info set warehouse_id = #{newWarehouseId} where warehouse_id = #{warehouseId}")
    void updateWarehouseId(@Param("warehouseId") String warehouseId, @Param("newWarehouseId") String newWarehouseId);

    /**
     * 通过仓库ID查询仓库信息
     *
     * @param warehouseId
     * @return
     */
    @Select("select warehouse_id,warehouse_name,type_cd,status_cd,create_user,create_time,country_cd, province_name,city_name,zip_code, details_addr, sub_warehouse_support, remark, warehouse_contact, warehouse_phone, modify_user, modify_time from warehouse_basis_info where warehouse_id = #{warehouseId}")
    WarehouseManageInfoEntity findById(@Param("warehouseId") String warehouseId);

    /**
     * 通过仓库ID数组查询仓库信息
     *
     * @param idList id  数组
     * @return
     */
    @SelectProvider(type = WarehouseManageInfoSQL.class, method = "find")
    List<WarehouseManageInfoEntity> findByIdList(@Param("idList") String idList);

    @Select("select warehouse_id,warehouse_name,type_cd,status_cd,create_user,create_time,country_cd, province_name,city_name,zip_code, details_addr, sub_warehouse_support, remark, warehouse_contact, warehouse_phone," +
            "  modify_user, modify_time from warehouse_basis_info where status_cd=1 and type_cd != 'F'")
    List<WarehouseManageInfoEntity> findAllNotFba();
}
