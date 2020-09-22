package com.wisrc.merchandise.dao;

import com.wisrc.merchandise.dao.sql.BasicPlatformSQL;
import com.wisrc.merchandise.entity.BasicPlatformInfoEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

@Mapper
public interface BasicPlatformInfoDao {

    @SelectProvider(type = BasicPlatformSQL.class, method = "search")
    List<BasicPlatformInfoEntity> search(@Param("platformName") String platformName,
                                         @Param("statusCd") String statusCd);

    @Select("select fba_warehouse_id from basic_platform_info where plat_id = #{platId}")
    String getFbaWarehouse(@Param("platId") String platId);

    @Results({
            @Result(column = "plat_id", property = "platId"),
            @Result(column = "plat_name", property = "platName"),
            @Result(column = "plat_site", property = "platSite"),
            @Result(column = "api_url", property = "apiUrl"),
            @Result(column = "status_cd", property = "statusCd"),
            @Result(column = "modify_user", property = "modifyUser"),
            @Result(column = "modify_time", property = "modifyTime"),
            @Result(column = "market_place_id", property = "marketPlaceId")
    })
    @Select("select plat_id,plat_name,plat_site,api_url,status_cd,modify_user,modify_time,market_place_id from basic_platform_info where plat_id = #{platId} order by modify_time desc")
    BasicPlatformInfoEntity findById(String platId);

    @Results({
            @Result(column = "plat_id", property = "platId"),
            @Result(column = "plat_name", property = "platName"),
            @Result(column = "plat_site", property = "platSite"),
            @Result(column = "api_url", property = "apiUrl"),
            @Result(column = "status_cd", property = "statusCd"),
            @Result(column = "modify_user", property = "modifyUser"),
            @Result(column = "modify_time", property = "modifyTime"),
            @Result(column = "market_place_id", property = "marketPlaceId")
    })
    @Select("select plat_id,plat_name,plat_site,api_url,status_cd,modify_user,modify_time,market_place_id from basic_platform_info where plat_name = #{platName} and plat_site = #{platSite} order by modify_time desc")
    BasicPlatformInfoEntity find(@Param("platName") String platName, @Param("platSite") String platSite);


    @Select("select plat_id,plat_name,plat_site,api_url,status_cd,modify_user,modify_time,market_place_id from basic_platform_info order by modify_time desc")
    List<BasicPlatformInfoEntity> findAll();

    @Update("update basic_platform_info set plat_name = #{platName}, plat_site = #{platSite}, api_url = #{apiUrl}, status_cd = #{statusCd}, modify_user = #{modifyUser}, modify_time = #{modifyTime},market_place_id = #{marketPlaceId} where plat_id = #{platId}")
    void update(BasicPlatformInfoEntity ele) throws DuplicateKeyException;

    @Update("update basic_platform_info set status_cd = #{statusCd} where plat_id = #{platId}")
    void changeStatus(@Param("platId") String platId, @Param("statusCd") int statusCd);

    @Delete("delete from basic_platform_info where plat_id = #{platId}")
    void delete(String platId);

    @Insert("insert into basic_platform_info(plat_id,plat_name,plat_site,api_url,status_cd,modify_user,modify_time,market_place_id, fba_warehouse_id) values(#{platId},#{platName},#{platSite},#{apiUrl},#{statusCd},#{modifyUser},#{modifyTime},#{marketPlaceId}, #{fbaWarehouseId})")
    void add(BasicPlatformInfoEntity ele);

    @Select("select plat_id, plat_name, plat_site, api_url, status_cd, modify_user, modify_time, market_place_id from basic_platform_info where plat_name = #{platName}")
    List<BasicPlatformInfoEntity> findSiteById(String platName);

    @Select("select plat_id,plat_name,plat_site,api_url,status_cd,modify_user,modify_time,market_place_id from basic_platform_info where plat_id=#{platId}")
    BasicPlatformInfoEntity getSite(String platId);

    @Select("select plat_site from basic_platform_info where plat_name=#{platName}")
    List<String> check(String platName);

    @Select("SELECT market_place_id FROM basic_platform_info AS bpi LEFT JOIN basic_shop_info AS bsi ON bsi.plat_id = bpi.plat_id WHERE shop_id = #{shopId} ")
    String getSellerId(@Param("shopId") String shopId);
}
