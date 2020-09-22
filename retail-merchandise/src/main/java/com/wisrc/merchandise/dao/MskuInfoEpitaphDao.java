package com.wisrc.merchandise.dao;

import com.wisrc.merchandise.entity.MskuInfoEpitaphEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MskuInfoEpitaphDao {
    @Select("<script> "
            + "SELECT id, epitaph FROM msku_info_epitaph WHERE 1=1 "
            + "<foreach item='id' index='index' collection='ids' open=' AND id IN (' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
            + "</script>"
    )
    List<MskuInfoEpitaphEntity> getMskuEpitaph(List ids) throws Exception;

    @Select("SELECT id, epitaph FROM msku_info_epitaph WHERE id = #{id} ")
    MskuInfoEpitaphEntity getMskuEpitaphById(String id) throws Exception;

    @Insert("INSERT msku_info_epitaph(id, epitaph) VALUES(#{id}, #{epitaph})")
    void saveMskuEpitaph(MskuInfoEpitaphEntity mskuInfoEpitaphEntity) throws Exception;

    @Update("UPDATE msku_info_epitaph SET epitaph = #{epitaph} WHERE id = #{id}")
    void editMskuEpitaph(MskuInfoEpitaphEntity mskuInfoEpitaphEntity) throws Exception;

    @Delete("DELETE FROM msku_info_epitaph WHERE id = #{id}")
    void deleteMskuEpitaph(MskuInfoEpitaphEntity mskuInfoEpitaphEntity) throws Exception;
}
