package com.wisrc.crawler.webapp.dao;

import com.wisrc.crawler.webapp.entity.MskuSaleNumEnity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MskuSaleDao {
    @Insert("insert into msku_sale_info (seller_id, msku, data_time, one_days_ago_sales, two_days_ago_sales, three_days_ago_sales) values " +
            "(#{sellerId},#{msku},#{dataDt},#{lastNum},#{lastTwoNum},#{lastThreeNum})")
    void insert(MskuSaleNumEnity mskuSaleNumEnity);

    @Select("select seller_id, msku, data_time, one_days_ago_sales, two_days_ago_sales, three_days_ago_sales from msku_sale_info where data_time=#{dataTime}")
    List<MskuSaleNumEnity> getAllMskuNumByTime(String dateTime);

    @Delete("delete from msku_sale_info where msku=#{msku} and seller_id=#{sellerId} and data_time=#{dataDt}")
    void delete(@Param("msku") String msku, @Param("sellerId") String sellerId, @Param("dataDt") String dataDt);

}
