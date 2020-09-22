package com.wisrc.quality.webapp.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface SamplingPlanDao {
    @Select(" select ${columnName} from sample_code_info a where a.start <= #{quantity} and a.end >= #{quantity} ")
    String getCodeOne(Map paraMap);

    @Select(" select ${columnName} from sample_code_info a where where a.start = 500001   ")
    String getCodeTwo(Map paraMap);

    @Select(" select ${dataColumnNameFirst} as Ac,${dataColumnNameSecond} as Re from standard_data_normal where code = #{code} ")
    Map getAcReNormal(Map paraMapTwo);

    @Select(" select ${dataColumnNameFirst} as Ac,${dataColumnNameSecond} as Re from standard_data_strict where code = #{code} ")
    Map getAcReStrict(Map paraMapTwo);

    @Select(" select ${dataColumnNameFirst} as Ac,${dataColumnNameSecond} as Re from standard_data_lax where code = #{code} ")
    Map getAcReLax(Map paraMapTwo);

    @Select(" select sample_quantity as sampleQuantity from standard_data_normal where code = #{code} ")
    Integer getSampleQuantityOne(String code);

    @Select(" select sample_quantity as sampleQuantity from standard_data_strict where code = #{code} ")
    Integer getSampleQuantityTwo(String code);

    @Select(" select sample_quantity as sampleQuantity from standard_data_lax where code = #{code} ")
    Integer getSampleQuantityThree(String code);
}
