package com.wisrc.basic.dao;

import com.wisrc.basic.entity.VersionAnnouncementEntity;
import com.wisrc.basic.entity.VersionModuleAttrEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VersionAnnouncementDao {

    @Insert("INSERT INTO version_announcement(uuid, version_number, version_module_cd, version_time, version_content) " +
            "value (#{uuid}, #{versionNumber}, #{versionModuleCd}, #{versionTime}, #{versionContent})")
    void add(VersionAnnouncementEntity entity);

    @Select("SELECT uuid, version_number, version_module_cd, version_time, version_content FROM version_announcement ORDER BY version_time DESC")
    List<VersionAnnouncementEntity> findAll();

    @Select("SELECT version_module_cd, version_module_name FROM version_module_attr")
    List<VersionModuleAttrEntity> findModuleAttr();

    @Delete("DELETE FROM version_announcement WHERE version_number = #{versionNumber}")
    void delete(@Param("versionNumber") String versionNumber);

    @Select("SELECT uuid, version_number, version_module_cd, version_time, version_content FROM version_announcement ORDER BY version_number DESC")
    List<VersionAnnouncementEntity> findAllByVersionNumber();
}
