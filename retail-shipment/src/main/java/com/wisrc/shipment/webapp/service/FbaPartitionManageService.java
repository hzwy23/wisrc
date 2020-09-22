package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.entity.FbaPartitionManageEntity;

import java.util.LinkedHashMap;
import java.util.List;

public interface FbaPartitionManageService {

    LinkedHashMap search(String countryCd, int statusCd);

    LinkedHashMap getAll(int startNum, int pageSize, String countryCd, int statusCd);

    FbaPartitionManageEntity getById(String partitionId);

    FbaPartitionManageEntity getByNameAndCountry(String partitionName, String countryCd);

    void add(FbaPartitionManageEntity entity);

    void update(FbaPartitionManageEntity entity);

    void delete(String partitionId);


}
