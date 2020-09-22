package com.wisrc.code.webapp.dao;

import com.wisrc.code.webapp.entity.CodeCountryInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CodeCountryInfoDao {
    @Select(" select country_cd,country_name,country_english from code_country_info ")
    List<CodeCountryInfoEntity> findAll();

    @Update(" update code_country_info set country_name = #{countryName},country_english = #{countryEnglish},modify_user = #{modifyUser}, modify_time =#{modifyTime}, create_user = #{createUser}, create_time = #{createTime} where country_cd = #{countryCd} ")
    void update(CodeCountryInfoEntity entity);

    @Insert(" insert into code_country_info (country_cd,country_name,country_english, modify_user, modify_time, create_user, create_time) values(#{countryCd},#{countryName},#{countryEnglish}, #{modifyUser}, #{modifyTime}, #{createUser}, #{createTime}) ")
    void insert(CodeCountryInfoEntity entity);

    @Delete(" delete from code_country_info where  country_cd = #{countryCd}  ")
    void delete(String countryCd);

    @Select(" <script>" +
            " SELECT\n" +
            "   country_cd,\n" +
            "   country_name,\n" +
            "   country_english\n" +
            " FROM code_country_info\n" +
            " WHERE" +
            "   1 = 1 \n" +
            "   <if test = 'countryCd!=null'>" +
            "       AND country_cd LIKE concat('%', #{countryCd}, '%')\n" +
            "   </if>" +
            "   <if test = 'countryName!=null'>" +
            "       AND country_name LIKE concat('%', #{countryName}, '%')\n" +
            "   </if>" +
            "   <if test = 'countryEnglish!=null'>" +
            "       AND country_english LIKE concat('%', #{countryEnglish}, '%')\n" +
            "   </if>" +
            " </script>"
    )
    List<CodeCountryInfoEntity> fuzzyQuery(CodeCountryInfoEntity entity);
}
