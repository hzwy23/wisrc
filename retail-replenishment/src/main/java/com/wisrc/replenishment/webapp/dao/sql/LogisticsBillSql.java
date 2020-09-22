package com.wisrc.replenishment.webapp.dao.sql;

import com.wisrc.replenishment.webapp.query.logisticBill.FindDetailQuery;
import com.wisrc.replenishment.webapp.query.logisticBill.HistoryQuery;
import org.apache.ibatis.jdbc.SQL;

public class LogisticsBillSql {
    public static String findDatail(FindDetailQuery findDetail) {
        return new SQL() {{
            SELECT("shop_id", "msku_id", "declare_unit_price");
            FROM("customs_clearance_product_history");

            String historyWhere = "";
            for (int m = 0; m < findDetail.getHistoryKey().size(); m++) {
                HistoryQuery history = findDetail.getHistoryKey().get(m);
                if (m != 0) {
                    historyWhere += " OR ";
                }
                historyWhere += ("(msku_id = '" + history.getMskuId() + "' AND shop_id = '" + history.getShopId() + "')");
            }
            WHERE(historyWhere);
        }}.toString();
    }
}
