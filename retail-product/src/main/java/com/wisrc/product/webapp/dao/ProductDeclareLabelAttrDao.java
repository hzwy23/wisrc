package com.wisrc.product.webapp.dao;

import com.wisrc.product.webapp.entity.ProductDeclareLabelAttrEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.LinkedHashMap;
import java.util.List;

@Mapper
public interface ProductDeclareLabelAttrDao {
    @Select(" SELECT\n" +
            "  a.label_cd   AS labelCd,\n" +
            "  a.label_desc AS labelDesc,\n" +
            "  a.type_cd    AS typeCd\n" +
            " FROM erp_product_declare_label_attr a\n" +
            " ORDER BY a.label_cd ASC ")
    List<LinkedHashMap> findAll();

    @Select(" SELECT\n" +
            "  a.label_cd   AS labelCd,\n" +
            "  a.label_desc AS labelDesc,\n" +
            "  a.type_cd AS typeCd\n" +
            " FROM erp_product_declare_label_attr a\n" +
            " where type_cd = 1" +
            " ORDER BY a.label_cd ASC ")
    List<LinkedHashMap> getBasic();

    @Insert(" INSERT INTO erp_product_declare_label_attr (label_cd, label_desc) VALUES (#{labelCd}, #{labelDesc}) ")
    void add(ProductDeclareLabelAttrEntity entity);

    @Update(" UPDATE erp_product_declare_label_attr\n" +
            " SET label_desc = #{labelDesc}  " +
            " WHERE label_cd = #{labelCd} ")
    void update(ProductDeclareLabelAttrEntity entity);

    @Select(" SELECT\n" +
            "  a.label_cd   AS labelCd,\n" +
            "  a.label_desc AS labelDesc\n" +
            " FROM erp_product_declare_label_attr a\n" +
            " where label_cd = #{labelCd} ")
    ProductDeclareLabelAttrEntity findByLabelCd(int labelCd);
}
