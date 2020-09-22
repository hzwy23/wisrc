package com.wisrc.basic.utils;

import java.util.HashMap;
import java.util.Map;

public class Column {

    private static Column instance = null;
    private Map<String, String> codes = new HashMap<String, String>();

    public Column() {
        codes.put("brandId", "品牌编码ID");
        codes.put("brandName", "品牌名字");
        codes.put("statusCd", "状态");
        codes.put("statusDesc", "状态");
        codes.put("logoUrl", "图片地址");
        codes.put("relProductNum", "关联产品数量");
        codes.put("brandType", "品牌类型");
        codes.put("brandTypeDesc", "品牌类型");
        codes.put("modifyUser", "更新人");
        codes.put("modifyTime", "更新时间");
        codes.put("createTime", "创建时间");
        codes.put("createUser", "创建人");
    }

    public static String get(String code) {
        if (instance == null) {
            instance = new Column();
        }
        return instance.getMsg(code);
    }

    private String getMsg(String code) {
        return codes.get(code);
    }

}