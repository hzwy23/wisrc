package com.wisrc.purchase.webapp.utils;

import java.util.List;

public class SQLUtil {
    public static String forUtil(String sqlParam, List params) {
        if (params != null) {
            if (params.size() > 0) {
                String SQL = sqlParam + " IN (";

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
            } else {
                return " 1 = 2 ";
            }
        } else {
            return null;
        }
    }
}
