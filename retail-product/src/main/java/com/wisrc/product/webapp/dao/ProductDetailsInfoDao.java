package com.wisrc.product.webapp.dao;

import com.wisrc.product.webapp.entity.ProductDetailsInfoEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProductDetailsInfoDao {

    @Results({
            @Result(property = "skuId", column = "sku_id"),
            @Result(property = "description", column = "description")
    })
    @Select("select sku_id, description from erp_product_details_info where sku_id = #{skuId}")
    ProductDetailsInfoEntity findById(String skuId);

    @Update("update erp_product_details_info set description = #{description} ,update_time = #{updateTime} where sku_id = #{skuId}")
    void update(ProductDetailsInfoEntity ele);

    @Delete("delete from erp_product_details_info where sku_id = #{skuId}")
    void delete(String skuId);

    @Insert("insert into erp_product_details_info(sku_id,description) values(#{skuId},#{description})")
    void insert(ProductDetailsInfoEntity ele);

    @Select("select sku_id, description from erp_product_details_info where sku_id = #{skuId}")
    ProductDetailsInfoEntity findBySkuId(String skuId);
}
