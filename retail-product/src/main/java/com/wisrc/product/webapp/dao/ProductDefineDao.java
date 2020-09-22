package com.wisrc.product.webapp.dao;

import com.wisrc.product.webapp.dao.sql.ProductDefineSQL;
import com.wisrc.product.webapp.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductDefineDao {
    @Select(" SELECT\n" +
            "  sku_id                       AS skuId,\n" +
            "  sku_name_zh                  AS skuNameZh,\n" +
            "  sku_name_en                  AS skuNameEn,\n" +
            "  sku_short_name               AS skuShortName,\n" +
            "  status_cd                    AS statusCd,\n" +
            "  classify_cd                  AS classifyCd,\n" +
            "  create_time                  AS createTime,\n" +
            "  create_user                  AS createUser,\n" +
            "  machine_flag                 AS machineFlag,\n" +
            "  packing_flag                 AS packingFlag, \n" +
            "  type_cd                      AS typeCd, \n" +
            "  purchase_reference_price     AS purchaseReferencePrice, \n" +
            "  cost_price                   as cost_price\n" +
            "FROM erp_product_define\n" +
            "ORDER BY create_time DESC ")
    List<ProductDefineEntity> findAll();


    @Delete(" delete from erp_product_define where sku_id = #{skuId} ")
    void delete(String skuId);


    @Update(" UPDATE erp_product_define\n" +
            " SET\n" +
            "  sku_name_zh                  = #{skuNameZh}, \n" +
            "  sku_name_en                  = #{skuNameEn},\n" +
            "  sku_short_name               = #{skuShortName},\n" +
            "  classify_cd                  = #{classifyCd},\n" +
            "  machine_flag                 = #{machineFlag},\n" +
            "  packing_flag                 = #{packingFlag},\n" +
            "  update_time                  = #{updateTime} ,\n" +
            "  status_cd                    = #{statusCd} ,\n" +
            "  type_cd                      = #{typeCd}, \n" +
            "  purchase_reference_price     = #{purchaseReferencePrice} \n" +
            " WHERE sku_id = #{skuId} ")
    void update(ProductDefineEntity ele);

    @Insert(" INSERT INTO erp_product_define\n" +
            "(\n" +
            "  sku_id,\n" +
            "  sku_name_zh,\n" +
            "  status_cd,\n" +
            "  classify_cd,\n" +
            "  create_time,\n" +
            "  create_user,\n" +
            "  machine_flag,\n" +
            "  packing_flag,\n" +
            "  sku_name_en,\n" +
            "  sku_short_name,\n" +
            "  type_cd, \n" +
            "  purchase_reference_price, \n" +
            "  cost_price ,\n" +
            "  classify_short_name ,\n" +
            "  size, random_value \n" +
            ")\n" +
            "VALUES\n" +
            "  (\n" +
            "    #{skuId},\n" +
            "    #{skuNameZh},\n" +
            "    #{statusCd},\n" +
            "    #{classifyCd},\n" +
            "    #{createTime},\n" +
            "    #{createUser},\n" +
            "    #{machineFlag},\n" +
            "    #{packingFlag},\n" +
            "    #{skuNameEn},\n" +
            "    #{skuShortName} ,\n" +
            "    #{typeCd}, \n" +
            "    #{purchaseReferencePrice}, \n" +
            "    #{costPrice} ,\n" +
            "    #{classifyShortName} ,\n" +
            "    #{size}, #{skuId}\n" +
            "  ) ")
    void insert(ProductDefineEntity ele);

    @Select("select sku_id from erp_product_define where random_value = #{randomValue}")
    String getSkuId(@Param("randomValue") String randomValue);

    @Update("update erp_product_define set status_cd = #{statusCd} where sku_id = #{skuId}")
    void changeStatus(Map<String, Object> map);

    @Select(" SELECT\n" +
            "  sku_id                       AS skuId,\n" +
            "  sku_name_zh                  AS skuNameZh,\n" +
            "  sku_name_en                  AS skuNameEn,\n" +
            "  sku_short_name               AS skuShortName,\n" +
            "  status_cd                    AS statusCd,\n" +
            "  classify_cd                  AS classifyCd,\n" +
            "  create_time                  AS createTime,\n" +
            "  create_user                  AS createUser,\n" +
            "  machine_flag                 AS machineFlag,\n" +
            "  packing_flag                 AS packingFlag,\n" +
            "  update_user                  AS updateUser,\n" +
            "  type_cd                      AS typeCd, \n" +
            "  purchase_reference_price     AS purchaseReferencePrice, \n" +
            "  cost_price                   as costPrice \n" +
            "  FROM erp_product_define\n" +
            " WHERE sku_id = #{skuId} order by create_time desc ")
    ProductDefineEntity findBySkuId(String skuId);


    /**
     * （模糊查询）取出同名称简写的skuId的降序集合
     *
     * @param ele
     * @return
     */
    @Select(" select sku_id as skuId from erp_product_define where sku_id LIKE concat('', #{skuId},'%') order by sku_id desc")
    List<ProductDefineEntity> getMaxSku(ProductDefineEntity ele);

    /**
     * 通过skuId 获取中文名
     *
     * @param skuId
     * @return
     */
    @Select(" SELECT\n" +
            "  sku_id AS skuId,\n" +
            "  sku_name_zh AS skuNameZh\n" +
            "  FROM\n" +
            "  erp_product_define\n" +
            " WHERE\n" +
            " sku_id = #{skuId} "
    )
    Map<String, String> getName(String skuId);

    /**
     * 获取skuId
     */
    @Select(" SELECT\n" +
            "  sku_id AS skuId\n" +
            "  FROM\n" +
            "  erp_product_define\n"
    )
    List<String> getAllSKU();

    @Select(" select sku_id AS skuId , image_url as imageUrl, image_classify_cd as imageClassifyCd ,uid as uid from erp_product_images where sku_id in ${condition} order by image_classify_cd asc")
    List<ProductImagesEntity> getImagesList(Map<String, Object> map);

    /**
     * 关联加工表与产品表
     *
     * @return
     */
    @Select(" SELECT\n" +
            "  a.sku_id            AS skuId,\n" +
            "  a.dependency_sku_id AS dependencySkuId,\n" +
            "  a.quantity          AS quantity,\n" +
            "  b.sku_name_zh       AS skuNameZh\n" +
            " FROM erp_product_machine_info a\n" +
            "  LEFT JOIN\n" +
            "  erp_product_define b\n" +
            "    ON b.sku_id = a.dependency_sku_id\n" +
            " WHERE a.type_cd = #{typeCd} \n" +
            "      AND a.sku_id IN ${condition}")
    List<ProductMachineInfoDetailEntity> getMachineList(Map<String, Object> map);


    //    @Select(" <script>" +
//            "   SELECT\n" +
//            "       aa.sku             AS sku,\n" +
//            "       aa.skuNameZh       AS skuNameZh,\n" +
//            "       aa.skuNameEn       AS skuNameEn,\n" +
//            "       aa.statusCd        AS status,\n" +
//            "       aa.createTime      AS dataTime,\n" +
//            "       aa.createUser      AS creator,\n" +
//            "       aa.machineFlag     AS machineFlag,\n" +
//            "       b.classify_name_ch AS classifyNameCh\n" +
//            "   FROM\n" +
//            "       (SELECT\n" +
//            "           a.sku_id       AS sku,\n" +
//            "           a.sku_name_zh  AS skuNameZh,\n" +
//            "           a.sku_name_en  AS skuNameEn,\n" +
//            "           a.status_cd    AS statusCd,\n" +
//            "           a.create_time  AS createTime,\n" +
//            "           a.create_user  AS createUser,\n" +
//            "           a.machine_flag AS machineFlag,\n" +
//            "           a.classify_cd  AS classifyCd\n" +
//            "        FROM\n" +
//            "            erp_product_define a\n" +
//            "        WHERE\n" +
//            "           1 = 1\n" +
//            "           <if test = 'skuId!=null'>" +
//            "           AND a.sku_id LIKE concat('%', #{skuId}, '%')\n" +
//            "           </if>" +
//            "           <if test = 'skuNameZh!=null'>" +
//            "           AND a.sku_name_zh LIKE concat('%', #{skuNameZh}, '%')\n" +
//            "           </if>" +
//            "           <if test = 'classifyCd!=null'>" +
//            "           AND a.classify_cd in ${classifyCdPara}\n" +
//            "           </if>" +
//            "           <if test = 'statusCd!=null'>" +
//            "           AND a.status_cd = #{statusCd}\n" +
//            "           </if>" +
//            "           <if test = 'ignoreSkuIds!=null'>" +
//            "               AND a.sku_id not in" +
//            "                   <foreach item='ignoreSkuId' index='index' " +
//            "                       collection='ignoreSkuIds' open='(' separator=',' close=')'>" +
//            "                       #{ignoreSkuId}" +
//            "                   </foreach>" +
//            "           </if>" +
//            "       ) AS aa\n" +
//            "       LEFT JOIN erp_product_classify_define b ON\n" +
//            "                                            aa.classifyCd = b.classify_cd\n" +
//            "  ORDER BY aa.statusCd ASC, aa.createTime DESC " +
//            "</script>")
    @SelectProvider(type = ProductDefineSQL.class, method = "search")
    List<ProductShowEntity> findFuzzyNew(Map<String, String> mapPara);

    @Update(" UPDATE erp_product_define\n" +
            " SET\n" +
            "  costPrice     = #{costPrice} \n" +
            " WHERE sku_id = #{skuId} ")
    void updateCostPrice(ProductDefineEntity entity);

    @Select(" SELECT\n" +
            "  sku_id                       AS skuId,\n" +
            "  sku_name_zh                  AS skuNameZh,\n" +
            "  sku_name_en                  AS skuNameEn,\n" +
            "  sku_short_name               AS skuShortName,\n" +
            "  status_cd                    AS statusCd,\n" +
            "  classify_cd                  AS classifyCd,\n" +
            "  create_time                  AS createTime,\n" +
            "  create_user                  AS createUser,\n" +
            "  machine_flag                 AS machineFlag,\n" +
            "  packing_flag                 AS packingFlag, \n" +
            "  type_cd                      AS typeCd, \n" +
            "  purchase_reference_price     AS purchaseReferencePrice, \n" +
            "  cost_price                   as cost_price\n" +
            " FROM erp_product_define\n" +
            " where type_cd = #{typeCd}" +
            " ORDER BY create_time DESC ")
    List<ProductDefineEntity> getSkuByTypeCd(Integer typeCd);

    @Select(" SELECT\n" +
            "  sku_id                   AS skuId,\n" +
            "  sku_name_zh              AS skuNameZh,\n" +
            "  status_cd                AS statusCd,\n" +
            "  classify_cd              AS classifyCd,\n" +
            "  create_time              AS createTime,\n" +
            "  create_user              AS createUser,\n" +
            "  machine_flag             AS machineFlag,\n" +
            "  sku_name_en              AS skuNameEn,\n" +
            "  sku_short_name           AS skuShortName,\n" +
            "  del_flag                 AS delFlag,\n" +
            "  update_time              AS updateTime,\n" +
            "  update_user              AS updateUser,\n" +
            "  type_cd                  AS typeCd,\n" +
            "  purchase_reference_price AS purchaseReferencePrice,\n" +
            "  packing_flag             AS packingFlag,\n" +
            "  cost_price               AS costPrice\n" +
            "FROM erp_product_define\n" +
            "WHERE sku_id IN ${skuId} ")
    List<ProductDefineEntity> getDefineInfoBatch(ProductDefineEntity entity);

    @Select(" SELECT count(*)\n" +
            " from erp_product_define where\n" +
            "  sku_id LIKE concat('', #{classifyShortName}, '%') ")
    int getMaxSize(String classifyShortName);

    @SelectProvider(type = ProductDefineSQL.class, method = "machineExcelData")
    List<MachineExcel> machineExcelData(@Param("mapPara") Map<String, String> mapPara);
}
