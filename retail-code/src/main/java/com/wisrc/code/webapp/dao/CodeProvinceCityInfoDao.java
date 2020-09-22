package com.wisrc.code.webapp.dao;

import com.wisrc.code.webapp.entity.AdministrativeDivisionInfoEntity;
import com.wisrc.code.webapp.entity.CodeProvinceCityInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CodeProvinceCityInfoDao {

    @Insert("insert into code_province_info(province_id, country_cd, province_name_en, province_name_cn, create_user, create_time, modify_user, modify_time) values(#{uuid}, #{countryCd}, #{provinceNameEn}, #{provinceNameCn}, #{createUser}, #{createTime}, #{modifyUser}, #{modifyTime})")
    void insertProvince(CodeProvinceCityInfoEntity ele);

    @Insert("insert into code_city_info(city_id, province_id,city_name_cn,city_name_en,create_time,create_user,modify_user,modify_time) values(#{uuid},#{provinceId},#{cityNameCn},#{cityNameEn},#{createTime},#{createUser},#{modifyUser},#{modifyTime})")
    void insertCity(CodeProvinceCityInfoEntity ele);

    @Update("update code_province_info set province_name_en = #{provinceNameEn}, province_name_cn = #{provinceNameCn}, modify_user = #{modifyUser}, modify_time = #{modifyTime} where province_id = #{uuid}")
    void update(CodeProvinceCityInfoEntity ele);

    @Update("update code_city_info set city_name_en = #{cityNameEn}, city_name_cn = #{cityNameCn}, modify_user = #{modifyUser}, modify_time = #{modifyTime} where city_id = #{uuid}")
    void updateCity(CodeProvinceCityInfoEntity ele);

    @Delete("delete from code_province_info where province_id = #{uuid}")
    void deleteProvince(String uuid);

    @Delete("delete from code_city_info where city_id = #{uuid}")
    void deleteCity(String uuid);


    @SelectProvider(type = AreaSQL.class, method = "search")
    List<AdministrativeDivisionInfoEntity> findALl(@Param("keyword") String keyword, @Param("countryCd") String countryCd);

    @Select("select province_id as uuid, country_cd, province_name_en, province_name_cn from code_province_info where country_cd = #{countryCd}")
    List<AdministrativeDivisionInfoEntity> findProvince(String countryCd);

}
