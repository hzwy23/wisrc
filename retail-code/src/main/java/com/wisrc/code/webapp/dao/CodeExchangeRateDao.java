package com.wisrc.code.webapp.dao;

import com.wisrc.code.webapp.entity.CodeCurrencyInfoEntity;
import com.wisrc.code.webapp.entity.CodeExchangeRateEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CodeExchangeRateDao {
    @Insert(" INSERT INTO code_exchange_rate\n" +
            "(\n" +
            "  uuid,\n" +
            "  as_of_date,\n" +
            "  iso_currency_cd,\n" +
            "  target_currency_cd,\n" +
            "  middle_price,\n" +
            "  create_time,\n" +
            "  create_user,\n" +
            "  modify_time,\n" +
            "  modify_user\n" +
            ")\n" +
            "VALUES\n" +
            "  (\n" +
            "    #{uuid},\n" +
            "    #{asOfDate},\n" +
            "    #{isoCurrencyCd},\n" +
            "    #{targetCurrencyCd},\n" +
            "    #{middlePrice},\n" +
            "    #{createTime},\n" +
            "    #{createUser},\n" +
            "    #{modifyTime},\n" +
            "    #{modifyUser}\n" +
            "  ) ")
    void insert(CodeExchangeRateEntity entity);

    @Update(" UPDATE code_exchange_rate\n" +
            " SET\n" +
            "  as_of_date         = #{asOfDate},\n" +
            "  iso_currency_cd    = #{isoCurrencyCd},\n" +
            "  target_currency_cd = #{targetCurrencyCd},\n" +
            "  middle_price       = #{middlePrice},\n" +
            "  modify_time        = #{modifyTime},\n" +
            "  modify_user        = #{modifyUser}  " +
            " where uuid          = #{uuid} "
    )
    void update(CodeExchangeRateEntity entity);

    @Select(" <script>" +
            " SELECT\n" +
            "  uuid               AS uuid,\n" +
            "  as_of_date         AS asOfDate,\n" +
            "  iso_currency_cd    AS isoCurrencyCd,\n" +
            "  target_currency_cd AS targetCurrencyCd,\n" +
            "  middle_price       AS middlePrice,\n" +
            "  create_time        AS createTime,\n" +
            "  create_user        AS createUser,\n" +
            "  modify_time        AS modifyTime,\n" +
            "  modify_user        AS modifyUser\n" +
            " FROM code_exchange_rate " +
            " where     1 = 1" +
            "           <if test = 'asOfDate!=null'>" +
            "           AND as_of_date = #{asOfDate}\n" +
            "           </if>" +
            "           <if test = 'isoCurrencyCd!=null'>" +
            "           AND iso_currency_cd  = #{isoCurrencyCd}\n" +
            "           </if>" +
            "           <if test = 'targetCurrencyCd!=null'>" +
            "           AND target_currency_cd = #{targetCurrencyCd}\n" +
            "           </if>" +
            " order by modify_time desc" +
            " </script>" +
            "")
    List<Map> find(CodeExchangeRateEntity entity);

    @Select(" select  iso_currency_cd,iso_currency_english,iso_currency_name,status_cd from code_currency_info")
    List<CodeCurrencyInfoEntity> getCurrencyInfo();

    @Select(" SELECT\n" +
            "  uuid               AS uuid,\n" +
            "  as_of_date         AS asOfDate,\n" +
            "  iso_currency_cd    AS isoCurrencyCd,\n" +
            "  target_currency_cd AS targetCurrencyCd,\n" +
            "  middle_price       AS middlePrice,\n" +
            "  create_time        AS createTime,\n" +
            "  create_user        AS createUser,\n" +
            "  modify_time        AS modifyTime,\n" +
            "  modify_user        AS modifyUser\n" +
            " FROM code_exchange_rate " +
            " where uuid = #{uuid}")
    Map getByUuid(String uuid);

    @Delete(" DELETE FROM code_exchange_rate\n" +
            " WHERE uuid = #{uuid}  "
    )
    void deleteByUuid(String map);

}
