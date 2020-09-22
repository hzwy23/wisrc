package com.wisrc.quality.webapp.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedHashMap;
import java.util.List;

@Mapper
public interface CodeAttrDao {
    @Select(" select inspection_status_cd as inspectionStatusCd,inspection_status_desc AS inspectionStatusDesc from inspection_status_attr  ")
    List<LinkedHashMap> getISA();

    @Select(" select inspection_method_cd as inspectionMethodCd,inspection_method_desc AS inspectionMethodDesc from inspection_method_attr  ")
    List<LinkedHashMap> getIMA();

    @Select(" select inspection_type_cd AS inspectionTypeCd,inspection_type_desc AS inspectionTypeDesc from inspection_type_attr  ")
    List<LinkedHashMap> getITA();

    @Select(" select inspection_level_cd AS inspectionLevelCd,inspection_levell_desc AS inspectionLevellDesc from inspection_level_attr  ")
    List<LinkedHashMap> getILA();

    @Select(" select sampling_plan_cd AS samplingPlanCd,sampling_plan_desc AS samplingPlanDesc from inspection_sample_plan_attr  ")
    List<LinkedHashMap> getISPA();

    @Select(" select change_reason_cd AS changeReasonCd,change_reason_desc AS changeReasonDesc from inspection_change_reason_attr  ")
    List<LinkedHashMap> getICRA();

    @Select(" select conclusion_cd AS conclusionCd,conclusion_desc AS conclusionDesc from inspection_conclusion_attr  ")
    List<LinkedHashMap> getICA();

    @Select(" select final_determine_cd AS finalDetermineCd,final_determine_desc AS finalDetermineDesc from inspection_final_determination_attr  ")
    List<LinkedHashMap> getIFDA();

    @Select(" select source_type_cd AS sourceTypeCd,source_type_desc AS sourceTypeDesc from inspection_source_type_attr  ")
    List<LinkedHashMap> getISTA();

    @Select(" select items_cd AS itemsCd,items_desc AS itemsDesc ,item_type_cd AS itemTypeCd from inspection_items_attr  ")
    List<LinkedHashMap> getIIA();

    @Select(" select item_result_cd AS itemResultCd,item_result_desc AS itemResultDesc from inspection_items_result_attr  ")
    List<LinkedHashMap> getIIRA();

    @Select(" select judgment_cd AS judgmentCd,judgment_desc AS judgmentDesc from inspection_judgment_attr  ")
    List<LinkedHashMap> getIJA();

}
