package com.wisrc.warehouse.webapp.utils;

import java.util.List;

public class SQLUtil {
    public static String forUtil(String sqlParam, List params) {
        String SQL = sqlParam + " IN (";
        if (params.size() == 0) {
            SQL = sqlParam + " IN ('')";
            return SQL;
        }
        for (int m = 0; m < params.size(); m++) {
            String param = String.valueOf(params.get(m));
            if (m > 0) {
                SQL += (", '" + param + "'");
            } else {
                SQL += ("'" + param + "'");
            }
            if (m == params.size() - 1) {
                SQL += (")");
            }
        }

        return SQL;
    }
}
