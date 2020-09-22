package com.wisrc.product.webapp.dao;

import com.wisrc.product.webapp.entity.ProductDeclareInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductDeclareInfoDao {

    /**
     * 查询所有的产品申报信息
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
    @Select("select sku_id ,customs_number, tax_rebate_point, ticket_name, issuing_office, declare_name_zh, declare_name_en, declare_price, single_weight, declaration_elements from erp_product_declare_info")
    List<ProductDeclareInfoEntity> findAll();

    /**
     * 新增申报信息，申报信息依赖于产品定义表中的skuId
     */
    @Insert("insert into erp_product_declare_info(origin_place,materials,typical_use,brands,model,sku_id ,customs_number, tax_rebate_point, ticket_name, issuing_office, declare_name_zh, declare_name_en, declare_price, single_weight, declaration_elements) values(#{originPlace},#{materials},#{typicalUse},#{brands},#{model},#{skuId},#{customsNumber},  #{taxRebatePoint},  #{ticketName},  #{issuingOffice},  #{declareNameZh},  #{declareNameEn},  #{declarePrice},  #{singleWeight},  #{declarationElements})")
    void insert(ProductDeclareInfoEntity ele);

    /**
     * 更新指定sku的申报信息
     */
    @Update("update erp_product_declare_info set origin_place = #{originPlace},materials = #{materials},typical_use = #{typicalUse},brands = #{brands},model = #{model}, customs_number = #{customsNumber}, tax_rebate_point = #{taxRebatePoint}, ticket_name = #{ticketName}, issuing_office = #{issuingOffice}, declare_name_zh = #{declareNameZh}, declare_name_en = #{declareNameEn}, declare_price = #{declarePrice}, single_weight = #{singleWeight}, declaration_elements = #{declarationElements},update_time = #{updateTime} where sku_id = #{skuId}")
    void update(ProductDeclareInfoEntity ele);

    /**
     * 删除指定sku的申报信息
     */
    @Delete(" delete from erp_product_declare_info where sku_id = #{skuId}")
    void delete(String skuId);

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

    /**
     * 查询指定sku的申报信息
     */
    @Results({
            @Result(property = "originPlace", column = "origin_place"),
            @Result(property = "materials", column = "materials"),
            @Result(property = "typicalUse", column = "typical_use"),
            @Result(property = "brands", column = "brands"),
            @Result(property = "model", column = "model"),
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
    @Select("select origin_place,materials,typical_use,brands,model, sku_id ,customs_number, tax_rebate_point, ticket_name, issuing_office, declare_name_zh, declare_name_en, declare_price, single_weight, declaration_elements from erp_product_declare_info where sku_id = #{skuId}")
    ProductDeclareInfoEntity findBySkuId(String skuId);
}
