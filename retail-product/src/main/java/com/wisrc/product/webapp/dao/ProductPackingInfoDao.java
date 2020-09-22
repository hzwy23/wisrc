package com.wisrc.product.webapp.dao;

import com.wisrc.product.webapp.entity.ProductPackingInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductPackingInfoDao {
    @Insert(" INSERT INTO erp_product_packing_info\n" +
            "(\n" +
            "  sku_id,\n" +
            "  pack_length,\n" +
            "  pack_width,\n" +
            "  pack_height,\n" +
            "  pack_weight,\n" +
            "  pack_quantity\n" +
            ")\n" +
            "VALUES\n" +
            "  (\n" +
            "    #{skuId},\n" +
            "    #{packLength},\n" +
            "    #{packWidth},\n" +
            "    #{packHeight},\n" +
            "    #{packWeight},\n" +
            "    #{packQuantity}\n" +
            "  ) ")
    void insert(ProductPackingInfoEntity pPIEntity);


    @Update(" UPDATE erp_product_packing_info\n" +
            "SET\n" +
            "  pack_length   = #{packLength},\n" +
            "  pack_width    = #{packWidth} ,\n" +
            "  pack_height   = #{packHeight},\n" +
            "  pack_weight   = #{packWeight},\n" +
            "  pack_quantity = #{packQuantity} \n" +
            "WHERE sku_id = #{skuId}")
    void update(ProductPackingInfoEntity pPIEntity);

    @Select(" SELECT\n" +
            "  a.sku_id as skuId,\n" +
            "  a.pack_length as packLength,\n" +
            "  a.pack_width as packWidth,\n" +
            "  a.pack_height as packHeight,\n" +
            "  a.pack_weight as packWeight,\n" +
            "  a.pack_quantity as packQuantity\n" +
            " FROM erp_product_packing_info a" +
            " WHERE sku_id = #{skuId}")
    ProductPackingInfoEntity findBySkuId(String skuId);
}
