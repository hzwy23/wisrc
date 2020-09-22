package com.wisrc.code.webapp.dao;

import com.wisrc.code.webapp.entity.CodeTemplateConfEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CodeTemplateConfDao {
    @Select("SELECT item_id, item_name, obs_name, addr, version FROM code_template_conf")
    List<CodeTemplateConfEntity> getCodeTemplateConf() throws Exception;

    @Insert("INSERT INTO code_template_conf(item_id, item_name, obs_name, addr, version) VALUES(#{itemId}, #{itemName}, #{obsName}, #{addr}, #{version})")
    void saveCodeTemplateConf(CodeTemplateConfEntity codeTemplateConfEntity) throws Exception;

    @Select("SELECT item_id, item_name, obs_name, addr, version FROM code_template_conf WHERE item_id = #{itemId}")
    CodeTemplateConfEntity getCodeTemplateConfById(@Param("itemId") String itemId) throws Exception;
}
