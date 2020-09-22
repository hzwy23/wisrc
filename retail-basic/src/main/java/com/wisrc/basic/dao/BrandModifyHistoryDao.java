package com.wisrc.basic.dao;

import com.wisrc.basic.entity.BrandModifyHistoryEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface BrandModifyHistoryDao {
    @Select(" SELECT\n" +
            "  uuid as uuid,\n" +
            "  brand_id as brandId,\n" +
            "  modify_user as modifyUserId,\n" +
            "  modify_time as modifyTime,\n" +
            "  column_name as columnName,\n" +
            "  old_value as oldValue,\n" +
            "  handle_type_cd as handleTypeCd,\n" +
            "  new_value as newValue\n" +
            " FROM brand_modify_history \n" +
            " where brand_id = #{brandId}\n" +
            " ORDER BY modify_time DESC"
    )
    List<Map> findByBrandId(BrandModifyHistoryEntity entity);

    @Insert(" INSERT INTO brand_modify_history\n" +
            "(\n" +
            "  uuid,\n" +
            "  brand_id,\n" +
            "  column_name,\n" +
            "  old_value,\n" +
            "  new_value,\n" +
            "  modify_user,\n" +
            "  handle_type_cd,\n" +
            "  modify_time\n" +
            ")\n" +
            "VALUES\n" +
            "  (\n" +
            "    #{uuid} ,\n" +
            "    #{brandId} ,\n" +
            "    #{columnName} ,\n" +
            "    #{oldValue} ,\n" +
            "    #{newValue} ,\n" +
            "    #{modifyUser} ,\n" +
            "    #{handleTypeCd} ,\n" +
            "    #{modifyTime} \n" +
            "  ) "
    )
    void insert(BrandModifyHistoryEntity entity);

    @Select(" SELECT\n" +
            "  status_cd   AS statusCd,\n" +
            "  status_desc AS statusDesc\n" +
            " FROM brand_status_attr\n ")
    List<Map> getBrandStatus();

    @Select(" SELECT\n" +
            "  brand_type   AS brandType,\n" +
            "  brand_type_desc AS brandTypeDesc\n" +
            " FROM brand_type_attr\n ")
    List<Map> getBrandType();
}
