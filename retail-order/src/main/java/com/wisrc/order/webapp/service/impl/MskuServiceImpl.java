package com.wisrc.order.webapp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisrc.order.webapp.dto.msku.GetMskuInfoDTO;
import com.wisrc.order.webapp.dto.msku.MskuInfoDTO;
import com.wisrc.order.webapp.service.MskuOutsideService;
import com.wisrc.order.webapp.service.MskuService;
import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.utils.ServiceUtils;
import com.wisrc.order.webapp.vo.msku.GetByMskuIdAndNameVo;
import com.wisrc.order.webapp.vo.msku.GetMskuListByIdVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MskuServiceImpl implements MskuService {
    @Autowired
    private MskuOutsideService mskuOutsideService;

    @Override
    public Result getSaleStatusSelector() {
        try {
            return mskuOutsideService.getSaleStatusSelector();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result getShopSelector() {
        try {
            return mskuOutsideService.getShopSelector();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result getSalesPlan(String mskuId) {
        try {
            return mskuOutsideService.getSalesPlan(mskuId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Map getMskuInfo(GetMskuListByIdVo getMskuListByIdVo) throws Exception {
        Map result = new HashMap();

        Result getMskuInfo = mskuOutsideService.getMskuInfo(getMskuListByIdVo.getIds());
        if (getMskuInfo.getCode() != 200) {
            throw new Exception(String.valueOf(getMskuInfo.getMsg()));
        }

        ObjectMapper mapper = new ObjectMapper();
        GetMskuInfoDTO mskuListData = ServiceUtils.mapToBean(ServiceUtils.LinkedHashMapToMap(getMskuInfo.getData()), new GetMskuInfoDTO());
        List<MskuInfoDTO> mskuList = mapper.convertValue(mskuListData.getMskuInfoBatch(), new TypeReference<List<MskuInfoDTO>>() {
        });

        for (MskuInfoDTO msku : mskuList) {
            result.put(msku.getId(), msku);
        }

        return result;
    }

    @Override
    public List<String> getIdByMskuIdAndName(GetByMskuIdAndNameVo getByMskuIdAndNameVo) throws Exception {
        Result mskuIdsResult = mskuOutsideService.getIdByMskuIdAndName(getByMskuIdAndNameVo.getMskuId(), getByMskuIdAndNameVo.getMskuName());
        if (mskuIdsResult.getCode() != 200) {
            throw new Exception(mskuIdsResult.getMsg());
        }
        return (List) mskuIdsResult.getData();
    }
}
