package com.wisrc.product.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ProductDefineSQL {
    private static final String KEYWORD = "keyword";
    private static final String SKUID = "skuId";
    private static final String SKU_NAME = "skuNameZh";
    private static final String CLASSIFY_PARAM = "classifyCdPara";
    private static final String STATUS_CD = "statusCd";
    private static final String IGNORE_SKUID = "ignoreSkuIds";
    private static final String SALES_STATUS_CD = "salesStatusCd";
    private static final String TYPE_CD = "typeCd";

    public static final String search(@Param("mapPara") Map<String, String> mapPara) {
        String keyword = mapPara.containsKey(KEYWORD) ? mapPara.get(KEYWORD) : null;
        String skuId = mapPara.containsKey(SKUID) ? mapPara.get(SKUID) : null;
        String skuName = mapPara.containsKey(SKU_NAME) ? mapPara.get(SKU_NAME) : null;
        String classifyCdPara = mapPara.containsKey(CLASSIFY_PARAM) ? mapPara.get(CLASSIFY_PARAM) : null;
        String statusCd = mapPara.containsKey(STATUS_CD) ? mapPara.get(STATUS_CD) : null;
        String ignoreSkuIds = mapPara.containsKey(IGNORE_SKUID) ? mapPara.get(IGNORE_SKUID) : null;
        String salesStatusCd = mapPara.containsKey(SALES_STATUS_CD) ? mapPara.get(SALES_STATUS_CD) : null;
        String typeCd = mapPara.containsKey(TYPE_CD) ? mapPara.get(TYPE_CD) : null;

        return new SQL() {{
            SELECT("  sku_id                   AS sku,\n" +
                    "  sku_name_zh              AS skuNameZh,\n" +
                    "  status_cd                AS status,\n" +
                    "  classify_cd              AS classifyCd,\n" +
                    "  create_time              AS dataTime,\n" +
                    "  create_user              AS creator,\n" +
                    "  machine_flag             AS machineFlag,\n" +
                    "  sku_name_en              AS skuNameEn,\n" +
                    "  sku_short_name           AS skuShortName,\n" +
                    "  del_flag                 AS delFlag,\n" +
                    "  update_time              AS updateTime,\n" +
                    "  update_user              AS updateUser,\n" +
                    "  type_cd                  AS typeCd,\n" +
                    "  purchase_reference_price AS purchaseReferencePrice,\n" +
                    "  packing_flag             AS packingFlag,\n" +
                    "  cost_price               AS costPrice,\n" +
                    "  classify_name_ch       AS classifyNameCh,\n" +
                    "  type_desc              AS typeDesc,\n" +
                    "  sales_status_cd        as salesStatusCd,\n" +
                    "  sales_status_desc      AS salesStatusDesc ");
            FROM("v_product_details_info_two");
            if (keyword != null && !keyword.isEmpty()) {
                WHERE("(sku_id like concat('%',#{keyword}, '%') or sku_name_zh like concat('%', #{keyword}, '%'))");
            }
            if (skuId != null && !skuId.isEmpty()) {
                WHERE("sku_id like concat('%',#{skuId}, '%')");
            }
            if (skuName != null && !skuName.isEmpty()) {
                WHERE("sku_name_zh like concat('%',#{skuNameZh}, '%')");
            }
            if (classifyCdPara != null && !classifyCdPara.isEmpty()) {
                WHERE("classify_cd in " + classifyCdPara);
            }
            if (statusCd != null && !statusCd.isEmpty()) {
                WHERE("status_cd = #{statusCd}");
            }
            if (ignoreSkuIds != null && !ignoreSkuIds.isEmpty()) {
                WHERE("sku_id not in " + ignoreSkuIds);
            }
            if (salesStatusCd != null && !salesStatusCd.isEmpty()) {
                WHERE("sales_status_cd = #{salesStatusCd}");
            }
            if (typeCd != null && !typeCd.isEmpty()) {
                WHERE("type_cd = #{typeCd}");
            }
            ORDER_BY("status_Cd asc, create_time desc");
        }}.toString();
    }

    public static final String machineExcelData(@Param("mapPara") Map<String, String> mapPara) {
        String keyword = mapPara.containsKey(KEYWORD) ? mapPara.get(KEYWORD) : null;
        String skuId = mapPara.containsKey(SKUID) ? mapPara.get(SKUID) : null;
        String skuName = mapPara.containsKey(SKU_NAME) ? mapPara.get(SKU_NAME) : null;
        String classifyCdPara = mapPara.containsKey(CLASSIFY_PARAM) ? mapPara.get(CLASSIFY_PARAM) : null;
        String statusCd = mapPara.containsKey(STATUS_CD) ? mapPara.get(STATUS_CD) : null;
        String ignoreSkuIds = mapPara.containsKey(IGNORE_SKUID) ? mapPara.get(IGNORE_SKUID) : null;
        String salesStatusCd = mapPara.containsKey(SALES_STATUS_CD) ? mapPara.get(SALES_STATUS_CD) : null;
        String typeCd = mapPara.containsKey(TYPE_CD) ? mapPara.get(TYPE_CD) : null;
        return new SQL() {{
            SELECT("epmi.sku_id AS skuId", "epd.sku_name_zh AS skuNameZh", "dependency_sku_id AS dependencySkuId", "epdd.sku_name_zh AS dependencySkuName", "quantity", "type_desc AS typeDesc");
            FROM("erp_product_machine_info AS epmi");
            LEFT_OUTER_JOIN("erp_product_define AS epd ON epd.sku_id = epmi.sku_id","erp_product_define_type_attr AS epdta ON epdta.type_cd = epmi.type_cd",
                    "erp_product_sales_info AS epsi ON epsi.sku_id = epmi.sku_id", "erp_product_define AS epdd ON epdd.sku_id = epmi.dependency_sku_id");
            if (keyword != null && !keyword.isEmpty()) {
                WHERE("(epmi.sku_id like concat('%',#{keyword}, '%') or epd.sku_name_zh like concat('%', #{keyword}, '%'))");
            }
            if (skuId != null && !skuId.isEmpty()) {
                WHERE("epmi.sku_id like concat('%',#{skuId}, '%')");
            }
            if (skuName != null && !skuName.isEmpty()) {
                WHERE("epd.sku_name_zh like concat('%',#{skuNameZh}, '%')");
            }
            if (classifyCdPara != null && !classifyCdPara.isEmpty()) {
                WHERE("epd.classify_cd in " + classifyCdPara);
            }
            if (statusCd != null && !statusCd.isEmpty()) {
                WHERE("epd.status_cd = #{statusCd}");
            }
            if (ignoreSkuIds != null && !ignoreSkuIds.isEmpty()) {
                WHERE("epmi.sku_id not in " + ignoreSkuIds);
            }
            if (salesStatusCd != null && !salesStatusCd.isEmpty()) {
                WHERE("sales_status_cd = #{salesStatusCd}");
            }
            if (typeCd != null && !typeCd.isEmpty()) {
                WHERE("epmi.type_cd = #{typeCd}");
            }
            ORDER_BY("epd.status_cd asc, epd.create_time desc");
        }}.toString();
    }
}
