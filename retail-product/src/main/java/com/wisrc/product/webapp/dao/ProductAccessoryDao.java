package com.wisrc.product.webapp.dao;

import com.wisrc.product.webapp.entity.ProductAccessoryCdAttrEntity;
import com.wisrc.product.webapp.entity.ProductAccessoryEntity;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;

@Mapper
public interface ProductAccessoryDao {
    @Insert("  INSERT INTO erp_product_accessory\n" +
            " (" +
            "  uuid,\n" +
            "  sku_id,\n" +
            "  accessory_cd,\n" +
            "  accessory_desc\n" +
            " )\n" +
            " VALUES \n" +
            " (" +
            "  #{uuid},\n" +
            "  #{skuId},\n" +
            "  #{accessoryCd},\n" +
            "  #{accessoryDesc}\n" +
            " ) ")
    void insert(ProductAccessoryEntity pAEntity);


    @Delete(" delete from  erp_product_accessory where sku_id = #{skuId}")
    void deleteBySkuId(String skuId);

    @Select(" SELECT\n" +
            "  a.accessory_cd as accessoryCd,\n" +
            "  a.accessory_name as accessoryName\n" +
            " FROM erp_product_accessory_cd_attr a\n" +
            " ORDER BY a.accessory_cd ASC ")
    List<ProductAccessoryCdAttrEntity> getAllAttr();

    @Select(" SELECT\n" +
            "   a.sku_id         AS skuId,\n" +
            "   a.accessory_cd   AS accessoryCd,\n" +
            "   a.accessory_text AS accessoryText\n" +
            " FROM erp_product_accessory a\n" +
            "   where a.sku_id = #{skuId} " +
            " ORDER BY a.accessory_cd ")
    List<ProductAccessoryEntity> findBySkuId(String skuId);

    @Select(" SELECT\n" +
            "  a.sku_id         AS skuId,\n" +
            "  a.accessory_cd   AS accessoryCd,\n" +
            "  b.accessory_name AS accessoryText,\n" +
            "  b.accessory_name AS accessoryDesc,\n" +
            "  b.accessory_name AS accessoryName\n" +
            "FROM erp_product_accessory a\n" +
            "  LEFT JOIN erp_product_accessory_cd_attr b\n" +
            "    ON\n" +
            "      a.accessory_cd = b.accessory_cd\n" +
            "WHERE\n" +
            "  a.sku_id = #{skuId} " +
            " and b.type_cd = 1\n" +
            " ORDER BY a.accessory_cd asc ")
    List<LinkedHashMap> getBySkuId(String skuId);

    @Select(" SELECT\n" +
            "  a.sku_id         AS skuId,\n" +
            "  a.accessory_cd   AS accessoryCd,\n" +
            "  b.accessory_name AS accessoryText,\n" +
            "  b.accessory_name AS accessoryDesc,\n" +
            "  b.accessory_name AS accessoryName\n" +
            "FROM erp_product_accessory a\n" +
            "  LEFT JOIN erp_product_accessory_cd_attr b\n" +
            "    ON\n" +
            "      a.accessory_cd = b.accessory_cd\n" +
            "WHERE\n" +
            "  a.sku_id = #{skuId} " +
            " and b.type_cd = 2\n" +
            " ORDER BY a.accessory_cd asc ")
    List<LinkedHashMap> getCustomBySkuId(String skuId);

    @Select(" SELECT\n" +
            "  accessory_cd   AS accessoryCd,\n" +
            "  accessory_name AS accessoryName,\n" +
            "  accessory_name AS accessoryDesc,\n" +
            "  accessory_name AS accessoryText,\n" +
            "  type_cd        AS typeCd\n" +
            "FROM erp_product_accessory_cd_attr ")
    List<LinkedHashMap> getAll();

    @Select(" SELECT\n" +
            "  accessory_cd   AS accessoryCd,\n" +
            "  accessory_name AS accessoryName,\n" +
            "  type_cd        AS typeCd\n" +
            "FROM erp_product_accessory_cd_attr ")
    List<ProductAccessoryCdAttrEntity> getAttr();

    @Insert(" INSERT INTO erp_product_accessory_cd_attr\n" +
            "(\n" +
            "  accessory_name,\n" +
            "  type_cd\n" +
            ") VALUES (\n" +
            "  #{accessoryDesc},\n" +
            "  #{typeCd}\n" +
            ") ")
    @Options(useGeneratedKeys = true, keyProperty = "accessoryCd")
    void insertCustomAccessory(ProductAccessoryEntity pAEntity);

    @Select(" SELECT\n" +
            "  accessory_cd   AS accessoryCd,\n" +
            "  accessory_name AS accessoryName,\n" +
            "  accessory_name AS accessoryDesc,\n" +
            "  accessory_name AS accessoryText,\n" +
            "  type_cd AS typeCd\n" +
            " FROM erp_product_accessory_cd_attr " +
            " where type_cd = 1 ")
    List<LinkedHashMap> getBasic();

    @Select(" SELECT\n" +
            "  a.sku_id         AS skuId,\n" +
            "  a.accessory_cd   AS accessoryCd,\n" +
            "  b.accessory_name AS accessoryText,\n" +
            "  b.accessory_name AS accessoryDesc,\n" +
            "  b.accessory_name AS accessoryName,\n" +
            "  b.type_cd        AS typeCd\n" +
            "FROM erp_product_accessory a\n" +
            "  LEFT JOIN erp_product_accessory_cd_attr b\n" +
            "    ON\n" +
            "      a.accessory_cd = b.accessory_cd\n" +
            "WHERE\n" +
            "  a.sku_id = #{skuId} " +
            " ORDER BY a.accessory_cd asc ")
    List<LinkedHashMap> getBasicAndCustomBySkuId(String skuId);
}
