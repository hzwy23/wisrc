package com.wisrc.product.webapp.dao;


import com.wisrc.product.webapp.entity.ProductImagesEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductImagesDao {
    @Select(" select uuid as uuid, sku_id as skuId,  image_url as imageUrl, image_classify_cd as imageClassifyCd,uid as uid from erp_product_images where sku_id = #{skuId} order by image_classify_cd asc")
    List<ProductImagesEntity> findBySkuId(String skuId);

    /**
     * c查询出sku的所有的img
     * @param skuId
     * @return
     */
    @Select("select image_url  from erp_product_images where sku_id = #{skuId}")
    List<String> findImgBySkuId(String skuId);
    @Select(" select  image_url as imageUrl, image_classify_cd as imageClassifyCd from erp_product_images where sku_id = #{skuId} order by image_classify_cd asc")
    List<Map> findBySkuIdSimple(String skuId);

    @Select(" select uuid as uuid, sku_id as skuId,  image_url as imageUrl, image_classify_cd as imageClassifyCd from erp_product_images where uuid = #{uuid} ")
    ProductImagesEntity findByUuid(String uuid);

    @Insert(" insert into erp_product_images (uuid, sku_id ,  image_url , image_classify_cd ,create_time,uid) values(#{uuid},  #{skuId},   #{imageUrl}, #{imageClassifyCd}, #{createTime}, #{uid})")
    void insert(ProductImagesEntity productImagesEntity);

    @Delete(" delete from erp_product_images where sku_id = #{skuId}")
    void deleteBySkuId(String skuId);

    @Delete(" delete form erp_product_images where sku_id = #{uuid}")
    void deleteByUuid(String uuid);

    @Update(" update erp_product_images set uuid = #{uuid}, sku_id = #{skuId} ,  image_url = #{imageUrl} , image_classify_cd = #{imageClassifyCd} ,update_time = #{updateTime}  where uuid = #{uuid}")
    void update(ProductImagesEntity productImagesEntity);

    @Select(" select  image_url as imageUrl, image_classify_cd as imageClassifyCd from erp_product_images where sku_id = #{skuId} order by image_classify_cd asc")
    List<Map<String, String>> findBySkuISimple(String skuId);
}
