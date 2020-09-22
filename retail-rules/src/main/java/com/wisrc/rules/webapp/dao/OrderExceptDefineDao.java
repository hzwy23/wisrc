package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.entity.OrderExceptDefineEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderExceptDefineDao {
    @Insert("INSERT INTO order_except_define(except_type_cd, except_type_name, cond_column, cond_value, description, status_cd) VALUES(#{exceptTypeCd}, #{exceptTypeName}, #{condColumn}" +
            ", #{condValue}, #{description}, #{statusCd})")
    void saveOrderExcept(OrderExceptDefineEntity orderExceptDefineEntity) throws Exception;

    @Update("UPDATE order_except_define SET except_type_name = #{exceptTypeName}, cond_column = #{condColumn}, cond_value = #{condValue}, description = #{description}, status_cd = #{statusCd} " +
            "WHERE except_type_cd = #{exceptTypeCd} ")
    void editOrderExcept(OrderExceptDefineEntity orderExceptDefineEntity) throws Exception;

    @Select("SELECT except_type_cd, except_type_name, oed.cond_column, cca.cond_column_name, cond_value, description, status_cd FROM order_except_define AS oed " +
            "lEFT JOIN cond_column_attr AS cca ON cca.cond_column = oed.cond_column")
    List<OrderExceptDefineEntity> orderExceptList() throws Exception;

    @Delete("DELETE FROM order_except_define WHERE except_type_cd = #{exceptTypeCd}")
    void deleteOrderExcept(@Param("exceptTypeCd") String exceptTypeCd) throws Exception;

    @Select("SELECT except_type_cd FROM order_except_define ")
    List<String> orderExceptIds() throws Exception;
}
