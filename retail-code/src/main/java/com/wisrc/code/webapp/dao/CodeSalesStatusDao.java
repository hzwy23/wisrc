package com.wisrc.code.webapp.dao;

import com.wisrc.code.webapp.entity.CodeSalesStatusEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CodeSalesStatusDao {
    @Select(" select sale_status_cd,sale_status_desc from code_sales_status ")
    List<CodeSalesStatusEntity> findAll();

    @Update(" update code_sales_status set sale_status_desc = #{saleStatusDesc} where sale_status_cd = #{saleStatusCd} ")
    void update(CodeSalesStatusEntity entity);

    @Insert(" insert into code_sales_status (sale_status_cd,sale_status_desc)values(#{saleStatusCd},#{saleStatusDesc}) ")
    void insert(CodeSalesStatusEntity entity);

    @Delete(" delete from code_sales_status where sale_status_cd = #{saleStatusCd} ")
    void delete(Integer saleStatusCd);

    @Select(" select sale_status_cd from code_sales_status WHERE purchase_plan_use = 1")
    List<Integer> purchasePlanStatus();
}
