package com.wisrc.product.webapp.dao;

import com.wisrc.product.webapp.entity.CustomLabelEntity;
import com.wisrc.product.webapp.entity.NewProductDeclareLabelEntity;
import com.wisrc.product.webapp.entity.ProductDeclareLabelAttrEntity;
import com.wisrc.product.webapp.entity.ProductDeclareLabelEntity;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;

@Mapper
public interface ProductDeclareLabelDao {

    @Update(" update erp_product_declare_label set label = #{label},update_time = #{updateTime} where sku_id = #{skuId} ")
    void update(ProductDeclareLabelEntity ele);

    /**
     * 查询某个产品标签信息
     */
    @Select("select sku_id as skuId, label_cd as labelCd,label AS label, uuid AS uuid from erp_product_declare_label where sku_id = #{skuId} limit 1 ")
    ProductDeclareLabelEntity findBySkuId(String skuId);

    /**
     * 根据skuId，删除某个产品标签信息
     */
    @Delete(" delete from erp_product_declare_label where sku_Id = #{skuId}")
    void deleteBySkuId(String skuId);

    /**
     * 给某个指定的产品添加标签信息
     */
    @Insert("insert into erp_product_declare_label(sku_id, label_cd, uuid) values(#{skuId},#{labelCd},#{uuid})")
    void insert(ProductDeclareLabelEntity ele);


    @Select(" SELECT\n" +
            "  a.sku_id     AS skuId,\n" +
            "  a.label_cd   AS labelCd,\n" +
            "  a.uuid       AS uuid,\n" +
            "  a.label_text       AS labelText,\n" +
            "  b.label_desc AS labelDesc\n" +
            " FROM erp_product_declare_label a,\n" +
            "  erp_product_declare_label_attr b\n" +
            " WHERE a.sku_id = #{skuId} \n" +
            "      AND a.label_cd = b.label_cd\n" +
            " ORDER BY a.label_cd ASC ")
    List<LinkedHashMap> getBySkuId(String skuId);

    @Select(" SELECT\n" +
            "  a.label_cd   AS labelCd,\n" +
            "  a.label_desc AS labelDesc\n" +
            " FROM erp_product_declare_label_attr a\n" +
            " ORDER BY a.label_cd ASC ")
    List<ProductDeclareLabelAttrEntity> getAllLableAttr();

    @Select(" SELECT \n" +
            "  a.label_cd AS labelCd\n" +
            "FROM \n" +
            "  erp_product_declare_label a\n" +
            "WHERE\n" +
            "  a.label_cd is NOT null " +
            "  and a.sku_id = #{skuId}\n" +
            " ORDER BY a.label_cd ASC ")
    List<Integer> getChoseBySkuId(String skuId);


    @Insert(" insert into " +
            " erp_product_declare_label" +
            " (" +
            " sku_id," +
            " label_cd, " +
            " uuid," +
            " label_desc" +
            " ) values" +
            " (" +
            " #{skuId}," +
            " #{labelCd}," +
            " #{uuid}," +
            " #{labelDesc}) ")
    void insertLabel(NewProductDeclareLabelEntity nPDLEntity);

    @Select(" SELECT\n" +
            "  a.uuid as uuid,\n" +
            "  a.sku_id as skuId,\n" +
            "  a.label_cd as labelCd,\n" +
            "  a.label_text as labelText\n" +
            " FROM erp_product_declare_label a\n" +
            " WHERE a.sku_id = #{skuId} " +
            " ORDER BY a.label_cd ASC ")
    List<NewProductDeclareLabelEntity> getNPDLEList(String skuId);

    @Insert(" INSERT INTO erp_product_declare_label\n" +
            "(\n" +
            "  sku_id,\n" +
            "  label_cd,\n" +
            "  uuid,\n" +
            "  label_text,\n" +
            "  label_desc\n" +
            ") VALUES\n" +
            "  (\n" +
            "    #{skuId},\n" +
            "    #{labelCd},\n" +
            "    #{uuid},\n" +
            "    #{labelText},\n" +
            "    #{labelDesc}\n" +
            "  ) ")
    void insertCustomLabel(CustomLabelEntity customLabelEntity);


    /**
     * 基础码表
     *
     * @param skuId
     * @return
     */
    @Select(" SELECT\n" +
            "  a.sku_id     AS skuId,\n" +
            "  a.label_cd   AS labelCd,\n" +
            "  a.uuid       AS uuid,\n" +
            "  a.label      AS label,\n" +
            "  b.label_desc AS labelText,\n" +
            "  b.label_desc AS labelDesc\n" +
            " FROM\n" +
            "  erp_product_declare_label a\n" +
            "  LEFT JOIN erp_product_declare_label_attr b\n" +
            "    ON a.label_cd = b.label_cd\n" +
            " WHERE b.type_cd = 1 " +
            "   AND a.sku_id = #{skuId} " +
            " ORDER BY a.label_cd asc ")
    List<LinkedHashMap> getBasicsLableBySkuId(String skuId);

    /**
     * 自定义标签
     */
    @Select(" SELECT\n" +
            "  a.sku_id     AS skuId,\n" +
            "  a.label_cd   AS labelCd,\n" +
            "  a.uuid       AS uuid,\n" +
            "  a.label      AS label,\n" +
            "  b.label_desc AS labelText,\n" +
            "  b.label_desc AS labelDesc\n" +
            " FROM\n" +
            "  erp_product_declare_label a\n" +
            "  LEFT JOIN erp_product_declare_label_attr b\n" +
            "    ON a.label_cd = b.label_cd\n" +
            " WHERE b.type_cd = 2 " +
            "   AND a.sku_id = #{skuId} " +
            " ORDER BY a.label_cd asc ")
    List<LinkedHashMap> getCustomLableBySkuId(String skuId);

    //labelCd为主键 自增长生成
    @Insert(" INSERT INTO erp_product_declare_label_attr\n" +
            "(\n" +
            "  label_desc,\n" +
            "  type_cd\n" +
            ") VALUES (\n" +
            "  #{labelDesc},\n" +
            "  #{typeCd}\n" +
            ") ")
    //CustomLabelEntity中的labelCd将被自动添加
    @Options(useGeneratedKeys = true, keyProperty = "labelCd")
    void insertCustomLabelEntityInAttr(CustomLabelEntity customLabelEntity);

    @Select(" SELECT\n" +
            "  a.sku_id     AS skuId,\n" +
            "  a.label_cd   AS labelCd,\n" +
            "  a.uuid       AS uuid,\n" +
            "  a.label      AS label,\n" +
            "  b.label_desc AS labelText,\n" +
            "  b.label_desc AS labelDesc,\n" +
            "  b.type_cd AS typeCd\n" +
            " FROM\n" +
            "  erp_product_declare_label a\n" +
            "  LEFT JOIN erp_product_declare_label_attr b\n" +
            "    ON a.label_cd = b.label_cd\n" +
            " WHERE a.sku_id = #{skuId} " +
            " ORDER BY a.label_cd asc ")
    List<LinkedHashMap> newFindBySkuId(String skuId);
}
