package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.dao.sql.ExceptOrderSql;
import com.wisrc.order.webapp.vo.ExceptOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface ExceptOrderDao {
    @SelectProvider(type = ExceptOrderSql.class, method = "getExceptOrderByCond")
    List<ExceptOrderVO> getExceptOrderByCond(@Param("orderId") String orderId,
                                             @Param("originalOrderId") String originalOrderId,
                                             @Param("platId") String platId,
                                             @Param("shopId") String shopId,
                                             @Param("commodityId") String commodityId,
                                             @Param("commodityName") String commodityName,
                                             @Param("createStartTime") String createStartTime,
                                             @Param("createEndTime") String createEndTime,
                                             @Param("label") String label);
}
