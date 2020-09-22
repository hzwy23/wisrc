package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.dao.sql.EntryWarehouseSQL;
import com.wisrc.purchase.webapp.entity.EntryWarehouseProductEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EntryWarehouseProductDao {
    /**
     * 查询入库订单内产品信息
     *
     * @return
     */
    @Select("select uuid,entry_id,sku_id,entry_num,entry_frets,batch,unit_price_without_tax,amount_without_tax,tax_rate,unit_price_with_tax,amount_with_tax,create_time,create_user,delete_status from entry_warehouse_product_info where delete_status=0  and entry_id=#{entryId}")
    List<EntryWarehouseProductEntity> findInfoAll(@Param("entryId") String entryId);

    /**
     * 逻辑删除入库订单内产品信息 （delete_status 0--正常  1--删除）
     *
     * @param uuid
     */
    @Update("update entry_warehouse_product_info set delete_status = 1 where uuid = #{uuid}")
    void updateStatusById(@Param("uuid") String uuid);

    /**
     * 修改入库订单内产品信息
     *
     * @param ele
     */
    @Update("update entry_warehouse_product_info set sku_id=#{skuId},entry_num=#{entryNum},entry_frets=#{entryFrets},batch=#{batch},unit_price_without_tax=#{unitPriceWithoutTax},amount_without_tax=#{amountWithoutTax},tax_rate=#{taxRate},unit_price_with_tax=#{unitPriceWithTax},amount_with_tax=#{amountWithTax} where uuid = #{uuid}")
    void updateInfoById(EntryWarehouseProductEntity ele);

    /**
     * 新增入库订单内产品信息
     *
     * @param ele
     */
    @Insert("insert into entry_warehouse_product_info (uuid,entry_id,sku_id,entry_num,entry_frets,batch,unit_price_without_tax,amount_without_tax,tax_rate,unit_price_with_tax,amount_with_tax,create_time,create_user,delete_status) values (#{uuid},#{entryId},#{skuId},#{entryNum},#{entryFrets},#{batch},#{unitPriceWithoutTax},#{amountWithoutTax},#{taxRate},#{unitPriceWithTax},#{amountWithTax},#{createTime},#{createUser},#{deleteStatus})")
    void addInfo(EntryWarehouseProductEntity ele);

    @Select("select count(*) from entry_warehouse_product_info where uuid = #{uuid}")
    int numById(@Param("uuid") String uuid);

    @Select("SELECT uuid, entry_id, sku_id, entry_num, entry_frets, batch, unit_price_without_tax, amount_without_tax, tax_rate, unit_price_with_tax, amount_with_tax, create_time, create_user, delete_status\n" +
            "FROM entry_warehouse_product_info\n" +
            "WHERE entry_id = #{entryId} AND sku_id = #{skuId}")
    EntryWarehouseProductEntity findEntryProductByEntryIdAndSkuId(@Param("entryId") String entryId,
                                                                  @Param("skuId") String skuId);

    @SelectProvider(type = EntryWarehouseSQL.class, method = "select")
    List<EntryWarehouseProductEntity> findEntryProductByEntryIds(@Param("ids") List<String> ids);
}
