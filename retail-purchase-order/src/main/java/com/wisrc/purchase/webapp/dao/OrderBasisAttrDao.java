package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.entity.CustomsTypeAtrEntity;
import com.wisrc.purchase.webapp.entity.DeliveryTypeAttrEntity;
import com.wisrc.purchase.webapp.entity.TiicketOpenAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface OrderBasisAttrDao {
    /**
     * 查询报关类型码表
     */
    @Select("SELECT customs_type_cd,customs_type_desc FROM order_customs_type_attr")
    List<CustomsTypeAtrEntity> cusomsAttr();

    /**
     * 查询交货状态码表
     */
    @Select("SELECT delivery_type_cd,delivery_type_desc FROM order_delivery_type_attr")
    List<DeliveryTypeAttrEntity> deliveryAttr();

    /**
     * 查询是否开票码表
     */
    @Select("SELECT tiicket_open_cd,tiicket_open_desc FROM order_tiicket_open_attr")
    List<TiicketOpenAttrEntity> tiicketAttr();
}
