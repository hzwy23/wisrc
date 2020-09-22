package com.wisrc.order.webapp.dao.sql;

import com.wisrc.order.webapp.entity.OrderLogisticsInfo;
import com.wisrc.order.webapp.query.GetCountByLogisticsIdQuery;
import com.wisrc.order.webapp.utils.SQLUtil;
import org.apache.ibatis.jdbc.SQL;

public class SaleLogisticsInfoSql {
    public static String getCountByLogisticsId(GetCountByLogisticsIdQuery getCountByLogisticsIdQuery) {
        return new SQL() {{
            SELECT("logistics_id", "COUNT(DISTINCT offer_id, logistics_id) AS count");
            FROM("order_logistics_info");
            if (getCountByLogisticsIdQuery.getLogisticsIds() != null) {
                WHERE(SQLUtil.forUtil("logistics_id", getCountByLogisticsIdQuery.getLogisticsIds()));
            }
            GROUP_BY("logistics_id");
        }}.toString();
    }

    public static String getDistinctOffer(GetCountByLogisticsIdQuery getCountByLogisticsIdQuery) {
        return new SQL() {{
            SELECT_DISTINCT("offer_id", "logistics_id");
            FROM("order_logistics_info");
            if (getCountByLogisticsIdQuery.getLogisticsIds() != null) {
                WHERE(SQLUtil.forUtil("logistics_id", getCountByLogisticsIdQuery.getLogisticsIds()));
            }
            GROUP_BY("offer_id", "logistics_id;");
        }}.toString();
    }

    public static String editSaleLogistics(OrderLogisticsInfo saleLogisticsInfoEntity) {
        return new SQL() {{
            UPDATE("order_logistics_info");
            SET("logistics_cost = #{logisticsCost}");
            WHERE("logistics_id = #{logisticsId}");
            if (saleLogisticsInfoEntity.getOfferId() != null) {
                WHERE("offer_id = #{offerId}");
            }
        }}.toString();
    }
}
