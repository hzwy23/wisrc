package com.wisrc.product.webapp.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 数字参数
 */
public class NumberPara {
    private static NumberPara instance = null;
    private Map<String, String> codes = new HashMap<String, String>();

    public NumberPara() {

        codes.put("levelCd", "分类等级");

        codes.put("taxRebatePoint", "退税税点");
        codes.put("declarePrice", "申报价值");
        codes.put("singleWeight", "单品重量");


        codes.put("statusCd", "产品状态码");
        codes.put("machineFlag", "是否加工");


        codes.put("imageClassifyCd", "图片分类编码");

        codes.put("quantity", "添加加工商品数量");

        codes.put("weight", "重量");
        codes.put("length", "长度");
        codes.put("width", "宽度");
        codes.put("height", "高度");
        codes.put("fbaWeight", "装箱重量");
        codes.put("fbaLength", "装箱长度");
        codes.put("fbaWidth", "装箱宽度");
        codes.put("fbaHeight", "装箱高度");
        codes.put("fbaVolume", "体积重");
        codes.put("fbaQuantity", "装箱数量");
    }

    public static String get(String code) {
        if (instance == null) {
            instance = new NumberPara();
        }
        return instance.getMsg(code);
    }

    private String getMsg(String code) {
        return codes.get(code);
    }
}
