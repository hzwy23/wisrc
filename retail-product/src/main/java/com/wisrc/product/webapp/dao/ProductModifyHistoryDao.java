package com.wisrc.product.webapp.dao;

import com.wisrc.product.webapp.entity.ProductModifyHistoryEntity;
import com.wisrc.product.webapp.entity.ProductSalesStatusCdAttr;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductModifyHistoryDao {
    @Insert(" INSERT INTO erp_product_modify_history (\n" +
            "  uuid,\n" +
            "  sku_id,\n" +
            "  modify_user,\n" +
            "  modify_time,\n" +
            "  modify_column,\n" +
            "  old_value,\n" +
            "  new_value,\n" +
            "  handle_type_cd,\n" +
            " create_time" +
            " )\n" +
            " VALUES\n" +
            " (\n" +
            "  #{uuid},#{skuId},#{modifyUser},#{modifyTime},#{modifyColumn},#{oldValue},#{newValue},#{handleTypeCd},#{modifyTime})")
    void insert(ProductModifyHistoryEntity productModifyHistoryEntity);

    /**
     * 查询历史纪录
     */
    @Select(" SELECT\n" +
            "  modify_user AS modifyUser,\n" +
            "  modify_time AS modifyTime,\n" +
            "  modify_column AS modifyColumn,\n" +
            "  old_value AS oldValue,\n" +
            "  new_value AS newValue,\n" +
            "  handle_type_cd AS handleTypeCd\n" +
            "  FROM erp_product_modify_history\n" +
            "  WHERE \n" +
            "   sku_id = #{skuId}\n" +
            "  ORDER BY create_time DESC "
    )
    List<Map> getHistory(Map<String, String> map);

    /**
     * 物理删除满足条件skuId的记录
     *
     * @param skuId
     */
    @Delete(" delete from erp_product_modify_history where  sku_id = #{skuId}")
    void deleteBySkuId(String skuId);

    @Select(" select  sales_status_cd as salesStatusCd,sales_status_desc as  salesStatusDesc from erp_product_sales_status_attr ")
    List<ProductSalesStatusCdAttr> getSalesAttr();
}
