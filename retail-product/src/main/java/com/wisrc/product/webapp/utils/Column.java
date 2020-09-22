package com.wisrc.product.webapp.utils;

import java.util.HashMap;
import java.util.Map;

public class Column {

    private static Column instance = null;
    private Map<String, String> codes = new HashMap<String, String>();

    public Column() {

        codes.put("updateUser", "更新人");

        codes.put("classifyCd", "分类ID");
        codes.put("classifyNameCh", "分类中文名");
        codes.put("classifyNameEn", "分类英文名");
        codes.put("classifyShortName", "分类简写");
        codes.put("levelCd", "分类等级");
        codes.put("createTime", "创建时间");
        codes.put("createUser", "创建人");
        codes.put("parentCd", "上级分类id");
        codes.put("updateTime", "更新时间");

        codes.put("skuId", "SKU");
        codes.put("customsNumber", "海关编号");
        codes.put("taxRebatePoint", "退税税点");
        codes.put("issuingOffice", "开票单位");
        codes.put("declareNameZh", "申报品名（中文）");
        codes.put("declareNameEn", "申报品名（英文）");
        codes.put("declarePrice", "申报价值");
        codes.put("singleWeight", "单品重量");
        codes.put("declarationElements", "申报要素");
        codes.put("ticketName", "开票品名");

        codes.put("labelCd", "标签编码");
        codes.put("labelList", "标签");
        codes.put("label", "标签");
        codes.put("uuid", "uuid唯一标识符");

        codes.put("skuNameZh", "产品中文名");
        codes.put("statusCd", "产品状态码");
        codes.put("machineFlag", "是否加工");
        codes.put("skuNameEn", "产品英文名");
        codes.put("skuShortName", "产品名称简写");

        codes.put("description", "产品描述");

        codes.put("imageUrl", "图片地址");
        codes.put("imageClassifyCd", "图片分类编码");

        codes.put("dependencySkuId", "添加加工商品");
        codes.put("quantity", "添加加工商品数量");

        codes.put("weight", "单品包装重量");
        codes.put("length", "单品包装长度");
        codes.put("width", "单品包装宽度");
        codes.put("height", "单品包装高度");
        codes.put("fbaWeight", "fba发货装箱重量");
        codes.put("fbaLength", "fba发货装箱长度");
        codes.put("fbaWidth", "fba发货装箱宽度");
        codes.put("fbaHeight", "fba发货装箱高度");
        codes.put("fbaVolume", "fba发货体积重");
        codes.put("fbaQuantity", "fba发货装箱数量");


        codes.put("packLength", "装箱长度");
        codes.put("packHeight", "装箱宽度");
        codes.put("packWidth", "装箱高度");
        codes.put("packQuantity", "体积重");
        codes.put("packWeight", "装箱数量");


        codes.put("originPlace", "原产地");
        codes.put("materials", "材质");
        codes.put("typicalUse", "用途");
        codes.put("brands", "品牌");
        codes.put("model", "型号");

        codes.put("salesStatusCd", "销售状态编码");
        codes.put("salesStatusDesc", "销售状态描述");
        codes.put("safetyStockDays", "安全库存天数");
        codes.put("internationalTransportDays", "国际运输天数");
        codes.put("netWeight", "净重");

        codes.put("typeCd", "类型");
        codes.put("purchaseReferencePrice", "采购参考价");

        codes.put("costPrice", "采购成本价");
        codes.put("packingFlag", "是否需要包材");

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