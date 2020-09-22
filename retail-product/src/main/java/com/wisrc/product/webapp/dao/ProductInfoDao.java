package com.wisrc.product.webapp.dao;

import com.wisrc.product.webapp.entity.NewProductDeclareLabelEntity;
import com.wisrc.product.webapp.entity.ProductClassifyDefineEntity;
import com.wisrc.product.webapp.entity.ProductDeclareInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductInfoDao {

    /**
     * 查询所有的产品信息
     *
     * @return List<ProductClassifyDefineEntity> 返回所有定义好的产品分类信息
     */
    @Select(" SELECT\n" +
            "  classify_cd AS classifyCd,\n" +
            "  classify_name_ch AS classifyNameCh,\n" +
            "  classify_name_en AS classifyNameEn,\n" +
            "  classify_short_name AS classifyShortName,\n" +
            "  level_cd AS levelCd,\n" +
            "  create_time AS createTime,\n" +
            "  create_user AS createUser,\n" +
            "  parent_cd AS parentCd\n" +
            " FROM\n" +
            "  erp_product_classify_define")
    List<ProductClassifyDefineEntity> findAllProductClassifyDefineEntity();

    /**
     * 查询指定sku的申报信息
     */
    @Results({
            @Result(property = "skuId", column = "sku_id"),
            @Result(property = "customsNumber", column = "customs_number"),
            @Result(property = "taxRebatePoint", column = "tax_rebate_point"),
            @Result(property = "ticketName", column = "ticket_name"),
            @Result(property = "issuingOffice", column = "issuing_office"),
            @Result(property = "declareNameZh", column = "declare_name_zh"),
            @Result(property = "declareNameEn", column = "declare_name_en"),
            @Result(property = "declarePrice", column = "declare_price"),
            @Result(property = "singleWeight", column = "single_weight"),
            @Result(property = "declarationElements", column = "declaration_elements")
    })
    @Select("select sku_id ,customs_number, tax_rebate_point, ticket_name, issuing_office, declare_name_zh, declare_name_en, declare_price, single_weight, declaration_elements from erp_product_declare_info where sku_id = #{skuId}")
    ProductDeclareInfoEntity findById(String skuId);

    @Insert(" insert into erp_product_declare_label(sku_id, label_cd, uuid,label_desc) values(#{skuId},#{labelCd},#{uuid},#{labelDesc}) ")
    void insertLabel(NewProductDeclareLabelEntity nPDLEntity);
}
