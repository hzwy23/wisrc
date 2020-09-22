package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.dao.sql.FbaPartitionManageSQL;
import com.wisrc.shipment.webapp.entity.FbaPartitionManageEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FbaPartitionManageDao {

    @SelectProvider(type = FbaPartitionManageSQL.class, method = "search")
    List<FbaPartitionManageEntity> search(@Param("countryCd") String countryCd,
                                          @Param("statusCd") int statusCd);

    @Insert("insert into fba_partition_manage(partition_id, partition_name, country_cd, create_user, create_time, modify_user, modify_time, status_cd) " +
            "values (#{partitionId}, #{partitionName}, #{countryCd}, #{createUser}, #{createTime}, #{modifyUser}, #{modifyTime}, #{statusCd})")
    void add(FbaPartitionManageEntity entity);

    @Select("select partition_id, partition_name, country_cd, create_user, create_time, modify_user, modify_time, status_cd from fba_partition_manage order by modify_time desc, country_cd asc, partition_name asc")
    List<FbaPartitionManageEntity> getAll();

    @Select("select partition_id, partition_name, country_cd, create_user, create_time, modify_user, modify_time, status_cd from fba_partition_manage where partition_name = #{partitionName} AND country_cd = #{countryCd}")
    FbaPartitionManageEntity getByNameAndCountry(@Param("partitionName") String partitionName,
                                                 @Param("countryCd") String countryCd);

    @Select("select partition_Name, country_cd, create_user, create_time, modify_user, modify_time, status_cd from fba_partition_manage where partition_id = #{partitionId}")
    FbaPartitionManageEntity getById(@Param("partitionId") String partitionId);

    @UpdateProvider(type = FbaPartitionManageSQL.class, method = "update")
    void update(FbaPartitionManageEntity entity);

    @Delete("delete from fba_partition_manage where partition_id = #{partitionId}")
    void delete(@Param("partitionId") String partitionId);

}
