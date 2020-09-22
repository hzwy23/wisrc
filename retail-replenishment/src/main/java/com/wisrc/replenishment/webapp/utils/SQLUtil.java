package com.wisrc.replenishment.webapp.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SQLUtil {
    public static String whereIn(String sqlParam, List params) {
        return sqlParam + " IN ( "
                + params.stream().map(p -> "'" + String.valueOf(p) + "'").collect(Collectors.joining(","))
                + (")");
    }

    /**
     * id 转以逗号分隔的字符串
     *
     * @param ids
     * @return
     */
    public static String idsToStr(String[] ids) {
        return Arrays.stream(ids).map(s -> "'" + s + "'").collect(Collectors.joining(","));
    }
}
