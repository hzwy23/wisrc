package com.wisrc.shipment.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.shipment.webapp.service.FbaPartitionManageService;
import com.wisrc.shipment.webapp.dao.FbaPartitionManageDao;
import com.wisrc.shipment.webapp.entity.FbaPartitionManageEntity;
import com.wisrc.shipment.webapp.utils.PageData;
import com.wisrc.shipment.webapp.vo.SelectFbaPartitionManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class FbaPartitionManageServiceImpl implements FbaPartitionManageService {

    @Autowired
    private FbaPartitionManageDao fpmd;

    @Override
    public LinkedHashMap search(String countryCd, int statusCd) {
        return PageData.pack(-1, -1, "FbaPartitionManageList", fpmd.search(countryCd, statusCd));
    }

    @Override
    public LinkedHashMap getAll(int startNum, int pageSize, String countryCd, int statusCd) {
        PageHelper.startPage(startNum, pageSize);
        List<FbaPartitionManageEntity> entityList = fpmd.search(countryCd, statusCd);
        List<SelectFbaPartitionManageVO> voList = entityToVo(entityList);
        PageInfo<FbaPartitionManageEntity> info = new PageInfo<>(entityList);
        return PageData.pack(info.getTotal(), info.getPages(), "FbaPartitionManageList", voList);
    }

    @Override
    public FbaPartitionManageEntity getById(String partitionId) {
        return fpmd.getById(partitionId);
    }

    @Override
    public FbaPartitionManageEntity getByNameAndCountry(String partitionName, String countryCd) {
        return fpmd.getByNameAndCountry(partitionName, countryCd);
    }

    @Override
    public void add(FbaPartitionManageEntity entity) {
        fpmd.add(entity);
    }

    @Override
    public void update(FbaPartitionManageEntity entity) {
        fpmd.update(entity);
    }

    @Override
    public void delete(String partitionId) {
        fpmd.delete(partitionId);
    }

    public List<SelectFbaPartitionManageVO> entityToVo(List<FbaPartitionManageEntity> entityList) {
        List<SelectFbaPartitionManageVO> voList = new LinkedList<>();

        for (FbaPartitionManageEntity entity : entityList) {
            System.out.println(entity);
            SelectFbaPartitionManageVO vo = new SelectFbaPartitionManageVO();
            vo.setPartitionId(entity.getPartitionId());
            vo.setPartitionName(entity.getPartitionName());
            vo.setCountryCd(entity.getCountryCd());
            vo.setStatusCd(entity.getStatusCd());
            voList.add(vo);
        }
        return voList;
    }
}
