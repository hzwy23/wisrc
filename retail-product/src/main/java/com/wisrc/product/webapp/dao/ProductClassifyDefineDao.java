package com.wisrc.product.webapp.dao;


import com.wisrc.product.webapp.entity.ProductClassifyDefineEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductClassifyDefineDao {

    /**
     * 新增产品分类
     *
     * @param ele ProductClassifyDefineEntity 产品分类定义实体类
     * @return void 添加成功后返回void
     */
    @Insert("insert into erp_product_classify_define(classify_cd, classify_name_ch, classify_name_en, classify_short_name,level_cd,create_time,create_user,parent_cd) values(#{classifyCd},#{classifyNameCh},#{classifyNameEn},#{classifyShortName},#{levelCd},#{createTime},#{createUser},#{parentCd})")
    void insert(ProductClassifyDefineEntity ele);

    /**
     * 查询所有的产品信息
     *
     * @return List<ProductClassifyDefineEntity> 返回所有定义好的产品分类信息
     */
    @Select(" SELECT\n" +
            "  classify_cd AS classifyCd,\n" +
            "  classify_name_ch AS classifyNameCh,\n" +
            "  classify_name_en AS classifyNameEn,\n" +
            "  classify_short_name AS classifyShortName,\n" +
            "  level_cd AS levelCd,\n" +
            "  create_time AS createTime,\n" +
            "  create_user AS createUser,\n" +
            "  parent_cd AS parentCd\n" +
            " FROM\n" +
            "  erp_product_classify_define" +
            "  order by classify_short_name asc ")
    List<ProductClassifyDefineEntity> findAll();

    /**
     * 删除分类信息
     *
     * @param classifyCd 产品分类编码
     */
    @Delete("delete from erp_product_classify_define where classify_cd = #{classifyCd}")
    void delete(String classifyCd);

    @Update("update erp_product_classify_define set classify_name_ch = #{classifyNameCh}, classify_name_en = #{classifyNameEn}, classify_short_name=#{classifyShortName}, level_cd = #{levelCd}, parent_cd = #{parentCd} ,update_time = #{updateTime} where classify_cd = #{classifyCd}")
    void update(ProductClassifyDefineEntity ele);

    /**
     * 通过classifyCd，关联查询获取
     *
     * @param classifyCd
     * @return
     */
    @Select(" SELECT\n" +
            "  a.classify_cd         AS classifyCd,\n" +
            "  a.classify_name_ch    AS classifyNameCh,\n" +
            "  a.classify_name_en    AS classifyNameEn,\n" +
            "  a.classify_short_name AS classifyShortName,\n" +
            "  a.level_cd            AS levelCd,\n" +
            "  a.create_time         AS createTime,\n" +
            "  a.create_user         AS createUser,\n" +
            "  a.parent_cd           AS parentCd,\n" +
            "  b.level_desc        as levelDesc\n" +
            "FROM erp_product_classify_define a\n" +
            "LEFT JOIN erp_product_classify_level_attr b\n" +
            "ON\n" +
            " a.level_cd = b.level_cd WHERE a.classify_cd = #{classifyCd};")
    Map<String, Object> findByClassifyCd(String classifyCd);


    /**
     * 没有关联查询，通过classifyCd获取单表数据
     *
     * @param classifyCd
     * @return
     */
    @Select(" SELECT\n" +
            "  a.classify_cd         AS classifyCd,\n" +
            "  a.classify_name_ch    AS classifyNameCh,\n" +
            "  a.classify_name_en    AS classifyNameEn,\n" +
            "  a.classify_short_name AS classifyShortName,\n" +
            "  a.level_cd            AS levelCd,\n" +
            "  a.create_time         AS createTime,\n" +
            "  a.create_user         AS createUser,\n" +
            "  a.parent_cd           AS parentCd\n" +
            " FROM erp_product_classify_define a\n" +
            "  WHERE a.classify_cd = #{classifyCd};")
    ProductClassifyDefineEntity findByClassifyCdSingel(String classifyCd);

    @Select(" SELECT\n" +
            "  classify_cd AS classifyCd,\n" +
            "  classify_name_ch AS classifyNameCh,\n" +
            "  classify_name_en AS classifyNameEn,\n" +
            "  classify_short_name AS classifyShortName,\n" +
            "  level_cd AS levelCd,\n" +
            "  create_time AS createTime,\n" +
            "  create_user AS createUser,\n" +
            "  parent_cd AS parentCd\n" +
            " FROM\n" +
            "  erp_product_classify_define" +
            " WHERE 1 = 1 \n" +
            " AND classify_name_ch LIKE concat('%',#{classifyNameCh},'%') \n" +
            " AND classify_name_en LIKE concat('%',#{classifyNameEn},'%')\n" +
            " AND classify_short_name LIKE concat('%', #{classifyShortName},'%')\n" +
            " order by create_time desc ")
    List<ProductClassifyDefineEntity> fuzzyQuery(ProductClassifyDefineEntity ele);

    /**
     * 查询单级别下的分类信息
     *
     * @param levelCd
     * @return
     */
    @Select(" SELECT\n" +
            "  classify_cd AS classifyCd,\n" +
            "  classify_name_ch AS classifyNameCh,\n" +
            "  classify_name_en AS classifyNameEn,\n" +
            "  classify_short_name AS classifyShortName,\n" +
            "  level_cd AS levelCd,\n" +
            "  create_time AS createTime,\n" +
            "  create_user AS createUser,\n" +
            "  parent_cd AS parentCd\n" +
            " FROM \n" +
            "  erp_product_classify_define" +
            " WHERE level_cd = #{levelCd} \n" +
            "  order by create_time desc"
    )
    List<ProductClassifyDefineEntity> findLevel(int levelCd);

    /**
     * 查找某个classifyCd下的下一级子类
     *
     * @param classifyCd
     * @return
     */
    @Select(" SELECT\n" +
            "  classify_cd AS classifyCd,\n" +
            "  classify_name_ch AS classifyNameCh,\n" +
            "  classify_name_en AS classifyNameEn,\n" +
            "  classify_short_name AS classifyShortName,\n" +
            "  level_cd AS levelCd,\n" +
            "  create_time AS createTime,\n" +
            "  create_user AS createUser,\n" +
            "  parent_cd AS parentCd\n" +
            " FROM \n" +
            "  erp_product_classify_define" +
            " WHERE parent_cd = #{classifyCd} \n" +
            "  order by create_time desc" +
            " ")
    List<ProductClassifyDefineEntity> findChildren(String classifyCd);

    @Select(" SELECT  count(*) from erp_product_define where classify_cd = #{classifyCd} ")
    int getProduct(String classifyCd);

    /**
     * 检查分类简写的数量（用作判断是否唯一）
     *
     * @param classifyShortName
     * @return
     */
    @Select(" SELECT  count(*) from erp_product_classify_define where classify_short_name = #{classifyShortName} ")
    int checkUnique(String classifyShortName);


    /**
     * 检查同一级别的是否存在相同的名称（只要中文或者英文重复都属于）
     * 如果classifyCd 不为空，则不包含classifyCd所对应的分类
     *
     * @param parentCd       需要检查的分类的父级
     * @param classifyCd     需要检查的分类
     * @param classifyNameCh 需要检查的分类中文名
     * @param classifyNameEn 需要检查的分类英文名
     */
    @Select(" <script>" +
            " SELECT count(*) from erp_product_classify_define " +
            " where parent_cd = #{parentCd} " +
            " <if test = 'classifyCd!=null'>" +
            "           and classify_cd !=#{classifyCd} " +
            " </if>" +
            " and (classify_name_ch=#{classifyNameCh}" +
            " <if test = 'classifyNameEn!=\"\"'>" +
            "      or classify_name_en =#{classifyNameEn}" +
            " </if>" +
            " ) </script>")
    int checkNameUnique(@Param("parentCd") String parentCd,
                        @Param("classifyCd") String classifyCd,
                        @Param("classifyNameCh") String classifyNameCh,
                        @Param("classifyNameEn") String classifyNameEn);

    /**
     * 只更新分类的中文名与英文名
     *
     * @param ele
     */
    @Update("update erp_product_classify_define set classify_name_ch = #{classifyNameCh}, classify_name_en = #{classifyNameEn},update_time = #{updateTime} where classify_cd = #{classifyCd}")
    void updatePart(ProductClassifyDefineEntity ele);
}
