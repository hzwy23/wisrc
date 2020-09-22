package com.wisrc.purchase.webapp.dao;


import com.wisrc.purchase.webapp.entity.PurchaseDateOfferLogEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PurchaseDateOfferLogDao {

    @Insert("insert into supplier_date_offer_info_log(uuid, supplier_offer_id, handle_time, handle_user, modify_column, old_value, new_value) values(#{uuid}, #{supplierOfferId}, #{handleTime}, #{handleUser}, #{modifyColumn}, #{oldValue}, #{newValue})")
    void addLogs(PurchaseDateOfferLogEntity ele);

    @Select("select uuid, supplier_offer_id, handle_time, handle_user, modify_column, old_value, new_value from supplier_date_offer_info_log where supplier_offer_id = #{supplierOfferId} order by handle_time desc")
    List<PurchaseDateOfferLogEntity> findById(String supplierOfferId);
}
