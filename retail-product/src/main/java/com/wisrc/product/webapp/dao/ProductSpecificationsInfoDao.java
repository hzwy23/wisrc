package com.wisrc.product.webapp.dao;

import com.wisrc.product.webapp.entity.ProductSpecificationsInfoEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProductSpecificationsInfoDao {

    @Insert(" INSERT INTO erp_product_specifications_info ( net_weight,sku_id, weight, length, width, height, fba_weight, fba_length, fba_width, fba_height, fba_volume, fba_quantity ) VALUES ( #{netWeight},#{skuId},#{weight},#{length},#{width},#{height},#{fbaWeight},#{fbaLength},#{fbaWidth},#{fbaHeight},#{fbaVolume},#{fbaQuantity}) ")
    void insert(ProductSpecificationsInfoEntity ele);

    @Delete(" delete from erp_product_specifications_info where sku_id = #{skuId}")
    void delete(String skuId);

    @Select(" select sku_id as skuId, weight as weight, length as length, width as width, height as height, fba_weight as fbaWeight, fba_length as fbaLength, fba_width as fbaWidth, fba_height as fbaHeight, fba_volume as fbaVolume, fba_quantity as fbaQuantity from erp_product_specifications_info  where sku_id = #{skuId}")
    ProductSpecificationsInfoEntity findById(String skuId);

    @Update(" update erp_product_specifications_info set net_weight = #{netWeight}, sku_id = #{skuId}, weight = #{weight}, length = #{length}, width = #{width}, height = #{height}, fba_weight = #{fbaWeight} , fba_length = #{fbaLength} , fba_width = #{fbaWidth}, fba_height = #{fbaHeight}, fba_volume = #{fbaVolume} , fba_quantity = #{fbaQuantity}  ,update_time = #{updateTime} where sku_id = #{skuId}")
    void update(ProductSpecificationsInfoEntity ele);

    @Select(" select net_weight as netWeight, sku_id as skuId, weight as weight, length as length, width as width, height as height, fba_weight as fbaWeight, fba_length as fbaLength, fba_width as fbaWidth, fba_height as fbaHeight, fba_volume as fbaVolume, fba_quantity as fbaQuantity ,update_time as updateTime from erp_product_specifications_info  where sku_id = #{skuId}")
    ProductSpecificationsInfoEntity findBySkuId(String skuId);

    @Update(" update erp_product_specifications_info set fba_length = #{fbaLength} , fba_width = #{fbaWidth}, fba_height = #{fbaHeight}, fba_weight = #{fbaWeight}, fba_quantity = #{fbaQuantity} , update_time = #{updateTime}  where sku_id = #{skuId}")
    void updateFba(ProductSpecificationsInfoEntity productSpecificationsInfoEntity);
}
