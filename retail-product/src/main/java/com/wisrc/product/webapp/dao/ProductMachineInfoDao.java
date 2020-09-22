package com.wisrc.product.webapp.dao;

import com.wisrc.product.webapp.entity.ProductMachineInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMachineInfoDao {
    @Insert(" INSERT INTO erp_product_machine_info (uuid, sku_id, dependency_sku_id, quantity,type_cd) VALUES (#{uuid}, #{skuId}, #{dependencySkuId},#{quantity},#{typeCd})")
    void insert(ProductMachineInfoEntity ele);

    @Delete(" delete from erp_product_machine_info where sku_id = #{skuId}")
    void delete(String skuId);

    @Delete(" delete from erp_product_machine_info where sku_id = #{skuId} and type_cd = #{typeCd}")
    void deleteType(Map paraMap);

    @Select(" select uuid as uuid, sku_id as skuId, dependency_sku_id as dependencySkuId, quantity as quantity from erp_product_machine_info where sku_id = #{skuId} and type_cd = 1")
    List<ProductMachineInfoEntity> findById(String skuId);

    /**
     * 使用uuid更新单条加工信息
     *
     * @param ele
     */
    @Update(" update erp_product_machine_info set  dependency_sku_id = #{dependencySkuId}, quantity = #{quantity} ,update_time = #{updateTime}  where uuid = #{uuid} ")
    void update(ProductMachineInfoEntity ele);


    @Select(" select uuid as uuid, sku_id as skuId, dependency_sku_id as dependencySkuId, quantity as quantity from erp_product_machine_info where sku_id = #{skuId}")
    List<ProductMachineInfoEntity> findBySkuId(String skuId);


    @Select(" select uuid as uuid, sku_id as skuId, dependency_sku_id as dependencySkuId, quantity as quantity from erp_product_machine_info where uuid = #{uuid}")
    ProductMachineInfoEntity findByUuid(String uuid);

    /**
     * 关联加工表与产品表
     *
     * @param skuId
     * @return
     */
    @Select(" SELECT\n" +
            "  uuid,\n" +
            "  dependencySkuId,\n" +
            "  quantity,\n" +
            "  skuNameZh\n" +
            " FROM\n" +
            "  (SELECT\n" +
            "     a.uuid              AS uuid,\n" +
            "     a.sku_id            AS skuId,\n" +
            "     a.dependency_sku_id AS dependencySkuId,\n" +
            "     a.quantity          AS quantity,\n" +
            "     b.sku_name_zh       AS skuNameZh\n" +
            "   FROM erp_product_machine_info a\n" +
            "     LEFT JOIN\n" +
            "     erp_product_define b\n" +
            "       ON b.sku_id = a.dependency_sku_id) AS aa\n" +
            " WHERE skuId = #{skuId};")
    List<Map> findBySkuIdDetail(String skuId);

    @Select(" SELECT\n" +
            "  dependency_sku_id AS dependencySkuId,\n" +
            "  quantity          AS quantity \n" +
            " FROM erp_product_machine_info\n" +
            " WHERE sku_id = #{skuId}\n" +
            " and type_cd  = #{typeCd} ")
    List<Map> getMachine(Map paraMap);

    @Select(" SELECT\n" +
            "  uuid              AS uuid,\n" +
            "  sku_id            AS skuId,\n" +
            "  dependency_sku_id AS dependencySkuId,\n" +
            "  quantity          AS quantity,\n" +
            "  type_cd           AS typeCd\n" +
            " FROM erp_product_machine_info\n" +
            " WHERE\n" +
            "   sku_id = #{skuId}\n" +
            "   and type_cd = #{typeCd} ")
    List<ProductMachineInfoEntity> findPMIEntity(ProductMachineInfoEntity pMIEntity);

    @Select(" SELECT\n" +
            "  packing_flag AS packingFlag \n" +
            "  FROM erp_product_define\n" +
            " WHERE sku_id = #{skuId}  ")
    Integer isNeedPackingMaterial(String skuId);

    @Select(" SELECT\n" +
            "  type_cd AS typeCd \n" +
            "  FROM erp_product_define\n" +
            " WHERE sku_id = #{skuId}  ")
    Integer getTypeOfProdect(String skuId);

    @Select(" SELECT\n" +
            "  a.dependency_sku_id AS dependencySkuId,\n" +
            "  a.quantity          AS quantity,\n" +
            "  b.sku_name_zh       AS skuNameZh\n" +
            "FROM erp_product_machine_info a\n" +
            "  LEFT JOIN erp_product_define b\n" +
            "    ON a.dependency_sku_id = b.sku_id\n" +
            "WHERE a.sku_id = #{skuId} \n" +
            "      AND a.type_cd = #{typeCd} ")
    List<Map> getPackingMaterial(ProductMachineInfoEntity entity);

    @Select("select sku_name_zh from erp_product_define where sku_id=#{depenSku}")
    String getSkuName(String depenSku);

    @Select("select sku_id from erp_product_define where packing_flag=1")
    List<String> getAllNeedPacking();
}
