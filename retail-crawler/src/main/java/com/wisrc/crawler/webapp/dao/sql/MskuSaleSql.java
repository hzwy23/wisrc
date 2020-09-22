package com.wisrc.crawler.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class MskuSaleSql {
    public static String getMskuSaleNumCond(@Param("stratTime") String stratTime,
                                            @Param("endTime") String endTime,
                                            @Param("sellerId") String sellerId,
                                            @Param("msku") String msku) {
        return new SQL() {{
            SELECT("seller_id, msku, data_time as dataDt, one_days_ago_sales as lastNum");
            FROM("msku_sale_info");
            if (sellerId != null) {
                WHERE("seller_id=#{sellerId}");
            }
            if (msku != null) {
                WHERE("msku=#{msku}");
            }
            if (stratTime != null) {
                WHERE("data_time >=#{stratTime}");
            }
            if (endTime != null) {
                WHERE("data_time <= #{endTime}");
            }
        }}.toString();

    }
}
