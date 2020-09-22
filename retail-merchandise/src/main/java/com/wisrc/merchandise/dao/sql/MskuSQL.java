package com.wisrc.merchandise.dao.sql;

import com.wisrc.merchandise.query.*;
import com.wisrc.merchandise.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class MskuSQL {
    public static final int using = 1;
    public static final int stop = 2;
    public static final int delete = 3;

    public static String searchMsku(@Param("platformName") String platformName,
                                    @Param("shopName") String shopName,
                                    @Param("mskuId") String mskuId,
                                    @Param("excludeCommodityIds") String excludeCommodityIds) {
        return new SQL() {{
            SELECT("plat_id, plat_name, plat_site, commodity_id, msku_id, msku_name, sku_id, employee_id, msku_status_cd, shop_id, shop_name, shop_status_cd");
            FROM("v_msku_info");
            if (platformName != null && !platformName.isEmpty()) {
                WHERE("plat_name like concat('%',#{platformName},'%')");
            }
            if (shopName != null && !shopName.isEmpty()) {
                WHERE("shop_name like concat('%',#{shopName},'%')");
            }
            if(mskuId != null && !mskuId.isEmpty()) {
                WHERE("msku_id like concat('%',#{mskuId},'%')");
            }
            if (excludeCommodityIds != null && !excludeCommodityIds.isEmpty()) {
                WHERE("commodity_id not in " + excludeCommodityIds);
            }
        }}.toString();
    }

    public static String getStocks(@Param("ids") String ids) {
        return new SQL() {{
            SELECT("id, shop_id, msku_id, fba_on_warehouse_stock_num, fba_on_way_stock_num, yesterday_sales_num, day_before_yesterday_sales_num, previous_sales_num");
            FROM("msku_stock_sales_info");
            if (ids != null) {
                WHERE("id in" + ids);
            }
        }}.toString();
    }

    public static String getMskuList(GetMskuListQuery getMskuListQuery) {
        return new SQL() {{
            SELECT("mi.*, mie.epitaph, mei.shelf_date, mei.fn_sku_id, mei.asin, mei.delivery_mode, bsi.shop_name");
            FROM("msku_info AS mi");
            LEFT_OUTER_JOIN("msku_info_epitaph AS mie ON mie.id = mi.id", "msku_ext_info AS mei ON mei.id = mi.id", "basic_shop_info AS bsi ON bsi.shop_id = mi.shop_id");
            // 店铺搜索
            if (null != getMskuListQuery.getShopId()) {
                WHERE("mi.shop_id = #{shopId}");
            }
            if (getMskuListQuery.getManager() != null && !getMskuListQuery.getManager().isEmpty()) {
                WHERE("mi.user_id = #{manager}");
            }
            // 小组搜索
            if (getMskuListQuery.getEmployeeIdListStr() != null) {
                WHERE("mi.user_id in" + getMskuListQuery.getEmployeeIdListStr());
            }
            // 销售状态搜索
            if (null != getMskuListQuery.getSalesStatus()) {
                WHERE("sales_status_cd = #{salesStatus}");
            }
            // 商品状态搜索
            if (null != getMskuListQuery.getMskuStatus()) {
                WHERE("msku_status_cd = #{mskuStatus}");
            }
            // 发货方式搜索
            if (null != getMskuListQuery.getDeliveryMode()) {
                WHERE("mei.delivery_mode = #{deliveryMode}");
            }
            // 关键字搜索
            if (null != getMskuListQuery.getFindKey() && !getMskuListQuery.getFindKey().isEmpty()) {
                String findKey = "(msku_id LIKE concat('%', #{findKey}, '%') OR msku_name LIKE concat('%', #{findKey}, '%') OR mei.asin LIKE concat('%', #{findKey}, '%')";
                if (null != getMskuListQuery.getStoreSkuDealted() && getMskuListQuery.getStoreSkuDealted().size() > 0) {
                    findKey += (" OR " + SQLUtil.forUtil("sku_id", getMskuListQuery.getStoreSkuDealted()));
                }
                if (null != getMskuListQuery.getSellerSkuDealted()) {
                    findKey += (" OR " + SQLUtil.forUtil("msku_id", getMskuListQuery.getSellerSkuDealted()));
                }
                findKey += ")";
                WHERE(findKey);
            }
            if (getMskuListQuery.getUserId() != null) {
                String privilege = "(user_Id = '" + getMskuListQuery.getUserId() + "'";
                if (getMskuListQuery.getMskuPrivilege() != null) {
                    if (getMskuListQuery.getMskuPrivilege().size() > 0) {
                        privilege += (" OR " + SQLUtil.forUtil("mi.id", getMskuListQuery.getMskuPrivilege()));
                    }
                }
                privilege += ")";
                WHERE(privilege);
            }
        }}.toString();
    }

    public static String getMskuListOutside(MskuPageOutsideQuery mskuPageOutsideQuery) {
        return new SQL() {{
            SELECT("mi.id, mi.msku_id, msku_name, mi.shop_id, mi.sku_id, mi.user_Id, mei.fn_sku_id, mei.asin, bsi.shop_name, sales_status_cd");
            FROM("msku_info AS mi");
            LEFT_OUTER_JOIN("msku_ext_info AS mei ON mei.id = mi.id", "basic_shop_info AS bsi ON bsi.shop_id = mi.shop_id");
            if (null != mskuPageOutsideQuery.getDoesDelete() && 1 == mskuPageOutsideQuery.getDoesDelete()) {
                WHERE("msku_status_cd <> " + delete);
            }else {
                WHERE("msku_status_cd = " + using);
            }
            if (null != mskuPageOutsideQuery.getShopId()) {
                WHERE("mi.shop_id = #{shopId}");
            }
            if (null != mskuPageOutsideQuery.getMskuId()) {
                WHERE("mi.msku_id = #{mskuId}");
            }
            if (mskuPageOutsideQuery.getManager() != null) {
                WHERE("user_id = #{manager}");
            }
            if (null != mskuPageOutsideQuery.getSalesStatus()) {
                WHERE("mi.sales_status_cd = #{salesStatus}");
            }
            if (null != mskuPageOutsideQuery.getSalesStatusList() && mskuPageOutsideQuery.getSalesStatusList().size() > 0) {
                WHERE(SQLUtil.forUtil("mi.sales_status_cd", mskuPageOutsideQuery.getSalesStatusList()));
            }
            if (null != mskuPageOutsideQuery.getFindKey()) {
                String findKey = "(msku_id LIKE concat('%', #{findKey}, '%') OR msku_name LIKE concat('%', #{findKey}, '%') OR mei.asin LIKE concat('%', #{findKey}, '%')";
                if (null != mskuPageOutsideQuery.getStoreSkuDealted() && mskuPageOutsideQuery.getStoreSkuDealted().size() > 0) {
                    findKey += (" OR " + SQLUtil.forUtil("sku_id", mskuPageOutsideQuery.getStoreSkuDealted()));
                }
                findKey += ")";
                WHERE(findKey);
            }
            if (mskuPageOutsideQuery.getUserId() != null) {
                String privilege = " ( user_Id = '" + mskuPageOutsideQuery.getUserId() + "'";
                if (mskuPageOutsideQuery.getMskuPrivilege() != null) {
                    if (mskuPageOutsideQuery.getMskuPrivilege().size() > 0) {
                        privilege += (" OR " + SQLUtil.forUtil("mi.id", mskuPageOutsideQuery.getMskuPrivilege()));

                    }
                }
                privilege += ")";
                WHERE(privilege);
            }
        }}.toString();
    }

    public static String getMskuId(@Param("shopName") String shopName, @Param("mskuId") String mskuId, @Param("mskuPrivilege") List mskuPrivilege, @Param("user") String user) {
        return new SQL() {{
            SELECT("id");
            FROM("msku_info AS mi");
            LEFT_OUTER_JOIN("basic_shop_info AS bsi ON bsi.shop_id = mi.shop_id");
            if (shopName != null) {
                WHERE("bsi.shop_name LIKE concat('%', #{shopName}, '%')");
            }
            if (mskuId != null) {
                WHERE("mi.msku_id LIKE concat('%', #{mskuId}, '%')");
            }
            if (user != null) {
                String privilege = " ( user_Id = '" + user + "'";
                if (mskuPrivilege != null) {
                    if (mskuPrivilege.size() > 0) {
                        privilege += (" OR " + SQLUtil.forUtil("mi.id", mskuPrivilege));

                    }
                }
                privilege += ")";
                WHERE(privilege);
            }
        }}.toString();
    }

    public static String getIdByNumAndShop(GetIdByNumAndShopQuery getIdByNumAndShopQuery) {
        return new SQL() {{
            SELECT("id", "msku_id" , "mi.shop_id", "shop_owner_id");
            FROM("msku_info AS mi");
            LEFT_OUTER_JOIN("basic_shop_info AS bsi ON bsi.shop_id = mi.shop_id");
            if (getIdByNumAndShopQuery.getUser() != null) {
                String privilege = " (user_Id = '" + getIdByNumAndShopQuery.getUser() + "'";
                if (getIdByNumAndShopQuery.getMskuPrivilege() != null) {
                    if (getIdByNumAndShopQuery.getMskuPrivilege().size() > 0) {
                        privilege += (" OR " + SQLUtil.forUtil("mi.id", getIdByNumAndShopQuery.getMskuPrivilege()));

                    }
                }
                privilege += ")";
                WHERE(privilege);
            }
            String query = "";
            for (int m = 0; m < getIdByNumAndShopQuery.getNumAndShopVo().size(); m++) {
                MskuRelationQuery mskuRelation = getIdByNumAndShopQuery.getNumAndShopVo().get(m);
                if (m > 0) {
                    query += " OR ";
                }
                query += ("(mi.msku_id = '" + mskuRelation.getMsku() + "' AND shop_owner_id = '" + mskuRelation.getShopOwnerId() + "')");
            }
            WHERE(query);
        }}.toString();
    }

    public static String getIdByMskuIdAndName(GetIdByMskuIdAndNameQuery getIdByMskuIdAndNameQuery) {
        return new SQL() {{
            SELECT("id");
            FROM("msku_info");
            if (getIdByMskuIdAndNameQuery.getMskuId() != null) {
                WHERE("msku_id LIKE concat('%', #{mskuId}, '%')");
            }
            if (getIdByMskuIdAndNameQuery.getMskuName() != null) {
                WHERE("msku_name LIKE concat('%',  #{mskuName}, '%')");
            }
            if (getIdByMskuIdAndNameQuery.getUserId() != null) {
                String privilege = " ( user_Id = '" + getIdByMskuIdAndNameQuery.getUserId() + "'";
                if (getIdByMskuIdAndNameQuery.getMskuPrivilege() != null) {
                    if (getIdByMskuIdAndNameQuery.getMskuPrivilege().size() > 0) {
                        privilege += (" OR " + SQLUtil.forUtil("mi.id", getIdByMskuIdAndNameQuery.getMskuPrivilege()));

                    }
                }
                privilege += ")";
                WHERE(privilege);
            }
        }}.toString();
    }

    public static String mskuSearch(MskuSearchQuery mskuSearchQuery) {
        return new SQL() {{
            SELECT("mi.id", "msku_id", "mi.shop_id", "sku_id", "sales_status_cd", "mei.asin", "shop_name");
            FROM("msku_info AS mi");
            LEFT_OUTER_JOIN("msku_ext_info AS mei ON mei.id = mi.id");
            LEFT_OUTER_JOIN("basic_shop_info AS bsi ON bsi.shop_id = mi.shop_id");
            WHERE("msku_status_cd = " + using);
            if (mskuSearchQuery.getAsin() != null) {
                WHERE("mei.asin LIKE concat('%',  #{asin}, '%') ");
            }
            if (mskuSearchQuery.getSkuids() != null) {
                WHERE(SQLUtil.forUtil("sku_id", mskuSearchQuery.getSkuids()));
            }
            if (mskuSearchQuery.getMskuName() != null) {
                WHERE("msku_name LIKE concat('%',  #{mskuName}, '%') ");
            }
            if (mskuSearchQuery.getUserId() != null) {
                String privilege = " ( user_Id = '" + mskuSearchQuery.getUserId() + "'";
                if (mskuSearchQuery.getMskuPrivilege() != null) {
                    if (mskuSearchQuery.getMskuPrivilege().size() > 0) {
                        privilege += (" OR " + SQLUtil.forUtil("mi.id", mskuSearchQuery.getMskuPrivilege()));

                    }
                }
                privilege += ")";
                WHERE(privilege);
            }
        }}.toString();
    }

    public static String getByKeywordQuery(GetByKeywordQuery getByKeywordQuery) {
        return new SQL() {{
            SELECT("mi.id", "msku_id", "mi.shop_id", "sku_id", "sales_status_cd", "mei.asin", "shop_name");
            FROM("msku_info AS mi");
            LEFT_OUTER_JOIN("msku_ext_info AS mei ON mei.id = mi.id");
            LEFT_OUTER_JOIN("basic_shop_info AS bsi ON bsi.shop_id = mi.shop_id");
            WHERE("msku_status_cd = " + using);
            if (getByKeywordQuery.getUserId() != null) {
                String privilege = " ( user_Id = '" + getByKeywordQuery.getUserId() + "'";
                if (getByKeywordQuery.getMskuPrivilege() != null) {
                    if (getByKeywordQuery.getMskuPrivilege().size() > 0) {
                        privilege += (" OR " + SQLUtil.forUtil("mi.id", getByKeywordQuery.getMskuPrivilege()));

                    }
                }
                privilege += ")";
                WHERE(privilege);
            }
            if (getByKeywordQuery.getStoreSkuDealted() != null && getByKeywordQuery.getStoreSkuDealted().size() > 0) {
                WHERE(SQLUtil.forUtil("sku_id", getByKeywordQuery.getStoreSkuDealted()));
            }
            if (getByKeywordQuery.getEmployeeId() != null) {
                WHERE("user_Id = #{employeeId}");
            }
        }}.toString();
    }

    public static String getMskuFBA(GetMskuFBAQuery getMskuFBAQuery) {
        return new SQL() {{
            SELECT("mi.id", "mi.msku_id", "msku_name", "mi.shop_id", "sku_id", "sales_status_cd", "mei.asin", "shop_name", "user_Id", "fba_on_warehouse_stock_num", "fba_on_way_stock_num", "safety_stock_days AS mskuSafetyStockDays");
            FROM("msku_info AS mi");
            LEFT_OUTER_JOIN("msku_ext_info AS mei ON mei.id = mi.id");
            LEFT_OUTER_JOIN("basic_shop_info AS bsi ON bsi.shop_id = mi.shop_id");
            LEFT_OUTER_JOIN("msku_stock_sales_info AS mssi ON mssi.id = mi.id");
            WHERE("msku_status_cd = " + using);
            WHERE("mi.sales_status_cd IN (1,2,3)");
            if (getMskuFBAQuery.getShopId() != null) {
                WHERE("mi.shop_id = #{shopId}");
            }
            if (getMskuFBAQuery.getSalesStatusCd() != null) {
                WHERE("mi.sales_status_cd = #{salesStatusCd}");
            }
            if (getMskuFBAQuery.getDeliveryMode() != null) {
                WHERE("mei.delivery_mode = #{deliveryMode}");
            }
            if (getMskuFBAQuery.getFindKey() != null) {
                String sku = "";
                if (getMskuFBAQuery.getStoreSkuDealted() != null && getMskuFBAQuery.getStoreSkuDealted().size() > 0) {
                    sku = " OR " + SQLUtil.forUtil("sku_id", getMskuFBAQuery.getStoreSkuDealted());
                }
                WHERE("(mi.msku_id LIKE concat('%', #{findKey}, '%') OR msku_name LIKE concat('%', #{findKey}, '%')" + sku + ")");
            }
            if (getMskuFBAQuery.getUserId() != null) {
                String privilege = " ( user_Id = '" + getMskuFBAQuery.getUserId() + "'";
                if (getMskuFBAQuery.getMskuPrivilege() != null) {
                    if (getMskuFBAQuery.getMskuPrivilege().size() > 0) {
                        privilege += (" OR " + SQLUtil.forUtil("mi.id", getMskuFBAQuery.getMskuPrivilege()));

                    }
                }
                privilege += ")";
                WHERE(privilege);
            }
        }}.toString();
    }

    public static String batchGetMskuInfo(@Param("commodityIds") List<String> commodityIds) {
        return new SQL() {
            {
                SELECT("a.msku_id,a.sku_id,b.fn_sku_id");
                FROM("msku_info a LEFT JOIN msku_ext_info b ON a.id = b.id");
                WHERE(SQLUtil.forUtil("a.id",commodityIds));
            }
        }.toString();

    }

    public static String getMskuByEmployee(@Param("userId") String userId) {
        return new SQL() {{
            SELECT("id");
            FROM("msku_info");
            WHERE("msku_status_cd <> " + delete);
            if (userId != null) {
                WHERE("user_Id = #{userId}");
            }
        }}.toString();
    }
}
