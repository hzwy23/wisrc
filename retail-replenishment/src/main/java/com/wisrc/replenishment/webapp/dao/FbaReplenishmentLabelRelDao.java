package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.entity.FbaReplenishmentLabelRelEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface FbaReplenishmentLabelRelDao {
    /**
     * 添加补货单商品标签信息
     *
     * @param labelRelEntity
     */
    @Insert("insert into replenishment_label_rel (uuid,fba_replenishment_id,label_cd,label_name,delete_status) values (#{uuid},#{fbaReplenishmentId},#{labelCd},#{labelName},#{deleteStatus})")
    void
    saveLabelRel(FbaReplenishmentLabelRelEntity labelRelEntity);

    /**
     * 通过uuid修改补货单商品标签信息 删除标识码状态
     *
     * @param deleteStatus
     * @param uuid
     */
    @Update("update replenishment_label_rel set delete_status=#{deleteStatus} where uuid=#{uuid}")
    void updateLabelStatus(String deleteStatus, String uuid);

    /**
     * 通过补货单ID查询商品标签信息码的id集合 deleteStatus=0
     *
     * @param fbaReplenishmentId
     * @return
     */
    @Select("select label_cd from replenishment_label_rel where fba_replenishment_id=#{fbaReplenishmentId} and delete_status=0")
    List<String> findlabelCdByFbaId(String fbaReplenishmentId);

    /**
     * 通过补货单ID删除商品标签信息
     *
     * @param deleteStatus
     * @param deleteRandom
     * @param fbaReplenishmentId
     */
    @Update("update replenishment_label_rel set delete_status=#{deleteStatus},delete_random=#{deleteRandom} where fba_replenishment_id=#{fbaReplenishmentId}")
    void cancelLabelStatus(@Param("deleteStatus") int deleteStatus, @Param("deleteRandom") String deleteRandom, @Param("fbaReplenishmentId") String fbaReplenishmentId);

    /**
     * 通过补货单id查询补货单商品标签信息集合  deleteStatus=0
     *
     * @param fbaReplenishmentId
     * @return
     */
    @Select("select uuid,label_cd,label_name from replenishment_label_rel where fba_replenishment_id=#{fbaReplenishmentId} and delete_status=0")
    List<FbaReplenishmentLabelRelEntity> findLabelEntity(String fbaReplenishmentId);

    /**
     * 通过uuid移除商品标签信息
     *
     * @param deleteStatus
     * @param deleteRandom
     * @param uuid
     */
    @Update("update replenishment_label_rel set delete_status=#{deleteStatus},delete_random=#{deleteRandom} where uuid=#{uuid}")
    void removeLabel(@Param("deleteStatus") int deleteStatus, @Param("deleteRandom") String deleteRandom, @Param("uuid") String uuid);

    /**
     * 通过商品标签信息码id查询补货单id集合  deleteStatus=0
     *
     * @param labelCd
     * @return
     */
    @Select("select fba_replenishment_id from replenishment_label_rel where label_cd=#{labelCd} and delete_status=0")
    List<String> findFbaIdByLabelCd(String labelCd);


}
