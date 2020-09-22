package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.vo.stockVO.LocalOnWayProductVO;
import com.wisrc.purchase.webapp.vo.stockVO.LocalOnWayTransferVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OnWayStockDao {

    @Select("select T.*, sum(ifnull(d.entry_num,0)) as entry_num from (SELECT a.sku_id, a.order_id,a.quantity,b.delivery_time,b.number FROM order_details_product_info a " +
            "left join order_product_delivery_info b on a.id =b.id)T left join entry_warehouse_info c on T.order_id = c.order_id left join  entry_warehouse_product_info d on c.entry_id = d.entry_id and T.sku_id = d.sku_id  where T.sku_id =#{skuId} group by T.order_id")
    List<LocalOnWayProductVO> get(@Param("skuId") String skuId);

    @Select("SELECT a.arrival_id,B.delivery_time,B.number FROM matrix_purchase.arrival_basis_info a right join (select T.* from (SELECT a.sku_id, a.order_id,a.quantity,b.delivery_time,b.number FROM matrix_purchase.order_details_product_info a " +
            "left join order_product_delivery_info b on a.id =b.id)T left join entry_warehouse_info c on T.order_id = c.order_id left join  entry_warehouse_product_info d on c.entry_id = d.entry_id and T.sku_id = d.sku_id  where T.sku_id =#{skuId} group by T.order_id) B\n" +
            "on a.purchase_order_id = B.order_id ")
    List<LocalOnWayTransferVO> getOnWayTransfer(@Param("skuId") String skuId);
}
