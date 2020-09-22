package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.entity.FbaReplenishmentLabelAttrEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface FbaReplenishmentLabelAttrDao {
    /**
     * 查询补货单商品标签的id，名称，颜色的信息码集合 deleteStatus=0
     *
     * @return
     */
    @Select("select label_cd,label_name,label_color from replenishment_label_attr where delete_status = 0")
    List<FbaReplenishmentLabelAttrEntity> findAll();

    /**
     * 通过补货单商品标签的id查询商品标签信息码   deleteStatus=0
     *
     * @param labelCd
     * @return
     */
    @Select("select label_cd,label_name,label_color from replenishment_label_attr where delete_status = 0 and label_cd=#{labelCd}")
    FbaReplenishmentLabelAttrEntity findById(@Param("labelCd") int labelCd);

    /**
     * 通过补货单商品标签的id查询商品标签信息码
     *
     * @param labelCd
     * @return
     */
    @Select("select label_cd,label_name,label_color from replenishment_label_attr where label_cd=#{labelCd}")
    FbaReplenishmentLabelAttrEntity findByLabelCd(@Param("labelCd") int labelCd);

    /**
     * 添加补货单商品标签信息码
     *
     * @param labelAttr
     */
    @Insert("insert into replenishment_label_attr (label_name,label_color,delete_status) values (#{labelName},#{labelColor},#{deleteStatus})")
    void saveLabelAttr(FbaReplenishmentLabelAttrEntity labelAttr);

    /**
     * 修改补货单商品标签信息码
     *
     * @param labelAttr
     */
    @Update("update replenishment_label_attr set label_name=#{labelName},label_color=#{labelColor} where label_cd=#{labelCd}")
    void updateLabelAttr(FbaReplenishmentLabelAttrEntity labelAttr);

    /**
     * 通过补货单商品标签id 修改删除标识码
     *
     * @param deleteStatus
     * @param labelCd
     */
    @Update("update replenishment_label_attr set delete_status=#{deleteStatus} where label_cd=#{labelCd}")
    void deleteLabelAttr(@Param("deleteStatus") int deleteStatus, @Param("labelCd") int labelCd);

}
