package com.wisrc.zuul.utils;

public class ArrayToInArguments {
    public static final String toInArgs(String[] employeeIdList) {
        if (employeeIdList != null && employeeIdList.length > 0) {
            StringBuffer tmp = new StringBuffer("(");
            for (String id : employeeIdList) {
                tmp.append("'").append(id).append("',");
            }
            tmp.setCharAt(tmp.length() - 1, ')');
            if (tmp.length() < 3) {
                return null;
            }
            return tmp.toString();
        }
        return null;
    }
}
