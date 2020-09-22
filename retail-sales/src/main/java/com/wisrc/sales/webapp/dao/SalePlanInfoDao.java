package com.wisrc.sales.webapp.dao;

import com.wisrc.sales.webapp.dao.sql.SalePlanInfoSql;
import com.wisrc.sales.webapp.vo.SelectSalePlanListVO;
import com.wisrc.sales.webapp.vo.SelectTotalInfoVO;
import com.wisrc.sales.webapp.entity.SalePlanInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SalePlanInfoDao {
    @Insert("insert into sale_plan_info (sale_plan_id, director_employee_id, charge_employee_id,  commodity_id, shop_id, msku_id,  create_user, create_time, modify_user, modify_time) values " +
            "(#{salePlanId},#{directorEmployeeId},#{chargeEmployeeId},#{commodityId},#{shopId},#{mskuId},#{createUser},#{createTime},#{modifyUser},#{modifyTime})")
    void add(SalePlanInfoEntity ele);

    @SelectProvider(type = SalePlanInfoSql.class, method = "getAllMsku")
    List<SelectSalePlanListVO> findByPage(@Param("commodityIdList") String commodityIdList);

    @SelectProvider(type = SalePlanInfoSql.class, method = "findByCond")
    List<SelectSalePlanListVO> findByCond(@Param("shopId") String shopId,
                                          @Param("msku") String msku,
                                          @Param("commodityIdListStr") String commodityIdListStr);

    @Select("select sale_plan_id, director_employee_id, charge_employee_id, commodity_id, shop_id, msku_id, create_user, create_time, modify_user, modify_time from sale_plan_info " +
            "where sale_plan_id=#{salePlanId}")
    SalePlanInfoEntity get(@Param("salePlanId") String salePlanId);

    @Update("update sale_plan_info set modify_user=#{modifyUser},modify_time=#{modifyTime} where sale_plan_id=#{salePlanId}")
    void update(SalePlanInfoEntity entity);


    @Select("select sale_plan_id, director_employee_id, charge_employee_id, commodity_id, shop_id, msku_id, create_user, create_time, modify_user, modify_time" +
            " from sale_plan_info where shop_id=#{shopId} and commodity_id=#{commodityId}")
    SalePlanInfoEntity check(@Param("shopId") String shopId,
                             @Param("commodityId") String commodityId);

    @SelectProvider(type = SalePlanInfoSql.class, method = "getTotal")
    List<SelectTotalInfoVO> getTotal(@Param("shopId") String shopId,
                                     @Param("msku") String msku,
                                     @Param("startMonth") String startMonth,
                                     @Param("endMonth") String endMonth,
                                     @Param("commodityIdListStr") String commodityIdListStr);
}
