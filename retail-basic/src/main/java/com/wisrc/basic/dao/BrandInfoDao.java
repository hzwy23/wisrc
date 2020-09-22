package com.wisrc.basic.dao;

import com.wisrc.basic.entity.BrandInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface BrandInfoDao {

    @Insert(" INSERT INTO brand_info\n" +
            "(" +
            " brand_id,\n" +
            " brand_name,\n" +
            " status_cd,\n" +
            " logo_url,\n" +
            " rel_product_num,\n" +
            " brand_type ,\n" +
            " create_user,\n" +
            " create_time,\n" +
            " modify_user,\n" +
            " modify_time\n" +
            ")\n" +
            "VALUES\n" +
            "  (\n" +
            "    #{brandId},\n" +
            "    #{brandName} ,\n" +
            "    1 ,\n" +
            "    #{logoUrl} ,\n" +
            "    #{relProductNum} ,\n" +
            "    #{brandType} ," +
            "    #{createUser} ,\n" +
            "    #{createTime} ,\n" +
            "    #{modifyUser} ,\n" +
            "    #{modifyTime} \n" +
            "  ) ")
    void insert(BrandInfoEntity entity);


    @Update(" UPDATE \n" +
            "  brand_info a\n" +
            "SET\n" +
            "  brand_name      = #{brandName} ,\n" +
            "  logo_url        = #{logoUrl} ,\n" +
            "  brand_type      = #{brandType}," +
            "  modify_user     = #{modifyUser} ,\n" +
            "  modify_time     = #{modifyTime}   " +
            "  where brand_id = #{brandId}")
    void update(BrandInfoEntity entity);

    @Select(" <script> " +
            " SELECT\n" +
            "  a.brand_type as brandType,\n" +
            "  a.brand_id as brandId,\n" +
            "  a.brand_name as brandName,\n" +
            "  a.status_cd AS statusCd,\n" +
            "  a.logo_url AS logoUrl,\n" +
            "  a.rel_product_num AS relProductNum,\n" +
            "  a.create_user AS createUserId,\n" +
            "  a.create_time AS createTime,\n" +
            "  a.modify_user AS modifyUserId,\n" +
            "  a.modify_time AS modifyTime\n" +
            "FROM brand_info a\n" +
            "WHERE\n" +
            "  1 = 1\n" +
            "  <if test = 'brandName!=null'> \n" +
            "   AND a.brand_name LIKE concat('%',#{brandName}, '%')\n" +
            "  </if>\n" +
            " ORDER BY a.status_cd ASC, a.create_time DESC " +
            "</script>"
    )
    List<Map> fuzzyFind(BrandInfoEntity entity);

    @Select(" SELECT\n" +
            "  a.brand_type as brandType,\n" +
            "  a.brand_id as brandId,\n" +
            "  a.brand_name as brandName,\n" +
            "  a.status_cd AS statusCd,\n" +
            "  a.logo_url AS logoUrl,\n" +
            "  a.rel_product_num AS relProductNum,\n" +
            "  a.create_user AS createUser,\n" +
            "  a.create_time AS createTime,\n" +
            "  a.modify_user AS modifyUser,\n" +
            "  a.modify_time AS modifyTime\n" +
            "FROM brand_info a\n" +
            "WHERE brand_id = #{brandId} ")
    BrandInfoEntity findById(String brandId);

    @Update(" UPDATE \n" +
            "  brand_info \n" +
            "SET\n" +
            "  status_cd      = #{statusCd} \n" +
            "  where brand_id = #{brandId}")
    void restrict(BrandInfoEntity entity);

    @Select(" SELECT\n" +
            "  a.brand_type as brandType,\n" +
            "  a.brand_id as brandId,\n" +
            "  a.brand_name as brandName,\n" +
            "  a.status_cd AS statusCd,\n" +
            "  a.logo_url AS logoUrl,\n" +
            "  a.rel_product_num AS relProductNum,\n" +
            "  a.create_user AS createUserId,\n" +
            "  a.create_time AS createTime,\n" +
            "  a.modify_user AS modifyUserId,\n" +
            "  a.modify_time AS modifyTime\n" +
            "FROM brand_info a\n" +
            "WHERE brand_id = #{brandId} ")
    Map findByIdInMap(String brandId);
}
