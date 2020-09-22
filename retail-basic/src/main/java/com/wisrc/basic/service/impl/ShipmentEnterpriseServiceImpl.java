package com.wisrc.basic.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.basic.dao.ShipmentCompanyCodeAttrDao;
import com.wisrc.basic.dao.ShipmentEnterpriseDao;
import com.wisrc.basic.entity.ShipmentCompanyCodeAttrEntity;
import com.wisrc.basic.entity.ShipmentEnterpriseEntity;
import com.wisrc.basic.entity.ShipmentStatusAttrEntity;
import com.wisrc.basic.entity.ShipmentTypeAttrEntity;
import com.wisrc.basic.service.ShipmentEnterpriseService;
import com.wisrc.basic.utils.PageData;
import com.wisrc.basic.utils.Result;
import com.wisrc.basic.utils.Time;
import com.wisrc.basic.vo.ShipmentEnterpriseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ShipmentEnterpriseServiceImpl implements ShipmentEnterpriseService {
    @Autowired
    private ShipmentEnterpriseDao shipmentEnterpriseDao;
    @Autowired
    private ShipmentCompanyCodeAttrDao shipmentCompanyCodeAttrDao;

    @Override
    public void changeStatus(String shipmentId, int statusCd, String userId) {
        shipmentEnterpriseDao.changeStatus(shipmentId, statusCd, userId, Time.getCurrentTime());
    }

    @Override
    public void add(ShipmentEnterpriseEntity ele) {
        ele.setShipmentAddr(ele.getProvinceName() + " " + ele.getCityName());
        if (shipmentEnterpriseDao.checkout(ele.getShipmentName()) == 1) {
            throw new DuplicateKeyException("物流商名称重复");
        }
        shipmentEnterpriseDao.add(ele);
    }

    @Override
    public LinkedHashMap findByCond(int pageNum, int pageSize, String statusCd, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<ShipmentEnterpriseEntity> list = shipmentEnterpriseDao.findByCond(statusCd, keyword);
        PageInfo pageInfo = new PageInfo(list);
        List<ShipmentEnterpriseVO> result = new ArrayList<>();
        for (ShipmentEnterpriseEntity vo : list) {
            result.add(ShipmentEnterpriseVO.toVO(vo));
        }
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "shipmentEnterpriseEntityList", result);
    }

    @Override
    public LinkedHashMap findByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ShipmentEnterpriseEntity> list = shipmentEnterpriseDao.findByPage();
        PageInfo pageInfo = new PageInfo(list);
        List<ShipmentEnterpriseVO> result = new ArrayList<>();
        for (ShipmentEnterpriseEntity vo : list) {
            result.add(ShipmentEnterpriseVO.toVO(vo));
        }
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "shipmentEnterpriseEntityList", result);
    }

    @Override
    public void updateById(ShipmentEnterpriseEntity ele) {
        ele.setShipmentAddr(ele.getProvinceName() + " " + ele.getCityName());
        // 判断物流商是否改名字
        String name = shipmentEnterpriseDao.findShipmentNameById(ele.getShipmentId());
        if (ele.getShipmentName().equals(name)) {
            shipmentEnterpriseDao.updateById(ele);
        } else if (shipmentEnterpriseDao.checkout(ele.getShipmentName()) == 1) {
            // 改了名字
            throw new DuplicateKeyException("物流商名称重复");
        } else {
            shipmentEnterpriseDao.updateById(ele);
        }
    }

    @Override
    public List<ShipmentStatusAttrEntity> findStatusAttr(int statusCd) {
        return shipmentEnterpriseDao.findStatusAttr(statusCd);
    }

    @Override
    public List<ShipmentTypeAttrEntity> findTypeAttr(int shipmentType) {
        return shipmentEnterpriseDao.findTypeAttr(shipmentType);
    }


    @Override
    public ShipmentEnterpriseEntity findShipmentById(String id) {
        return shipmentEnterpriseDao.findShipmentById(id);
    }

    @Override
    public List findByListId(String idList) {
        return shipmentEnterpriseDao.findByListId(idList);
    }

    @Override
    public LinkedHashMap findAll(String statusCd, String keyword) {
        List<ShipmentEnterpriseEntity> list = shipmentEnterpriseDao.findAll(statusCd, keyword);

        return PageData.pack(list.size(), 1, "shipmentEnterpriseEntityList", list);
    }

    @Override
    public LinkedHashMap findByName(int statusCd, String shipmentName, String contact) {
        List<ShipmentEnterpriseEntity> list = shipmentEnterpriseDao.findByName(statusCd, shipmentName, contact);
        return PageData.pack(list.size(), 1, "shipmentEnterpriseEntityList", list);
    }

    @Override
    public LinkedHashMap findAllEnableShipment() {
        List<ShipmentEnterpriseEntity> list = shipmentEnterpriseDao.findAllEnableShipment();
        return PageData.pack(list.size(), 1, "shipmentEnterpriseEntityList", list);
    }

    @Override
    public Result shipmentCodeList() {
        try {
            List<ShipmentCompanyCodeAttrEntity> shipmentCodes = shipmentCompanyCodeAttrDao.shipmentCodeList();
            return Result.success(shipmentCodes);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }
}
