package com.wisrc.shipment.webapp.dao.sql;

import com.wisrc.shipment.webapp.entity.FbaPartitionManageEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class FbaPartitionManageSQL {

    public static String search(@Param("countryCd") final String countryCd,
                                @Param("statusCd") final int statusCd) {
        return new SQL() {{
            SELECT("partition_id, partition_name, country_cd, create_user, create_time, modify_time, modify_user, status_cd");
            FROM("fba_partition_manage");
            if (countryCd != null) {
                WHERE("country_cd = #{countryCd}");
            }

            if (statusCd != 0) {
                WHERE("status_cd = #{statusCd}");
            }
            ORDER_BY("country_cd asc, partition_name asc");
        }}.toString();
    }

    public static String update(FbaPartitionManageEntity entity) {
        return new SQL() {{
            UPDATE("fba_partition_manage");
            if (entity.getCountryCd() != null) {
                SET("country_cd = #{countryCd}");
            }
            if (entity.getPartitionName() != null) {
                SET("partition_name = #{partitionName}");
            }
            if (entity.getModifyUser() != null) {
                SET("modify_user = #{modifyUser}");
            }
            if (entity.getModifyTime() != null) {
                SET("modify_time = #{modifyTime}");
            }
            if (entity.getStatusCd() != 0) {
                SET("status_cd = #{statusCd}");
            }
            WHERE("partition_id = #{partitionId}");
        }}.toString();
    }
}
