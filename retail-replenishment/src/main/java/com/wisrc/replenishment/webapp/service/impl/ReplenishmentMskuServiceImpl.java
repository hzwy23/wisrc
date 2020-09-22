package com.wisrc.replenishment.webapp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisrc.replenishment.webapp.dto.logisticsPlan.GetLogisticsPlanPageReturnDto;
import com.wisrc.replenishment.webapp.dto.logisticsPlan.GetMskuPageDto;
import com.wisrc.replenishment.webapp.dto.logisticsPlan.LogisticsPlanPageDto;
import com.wisrc.replenishment.webapp.dto.msku.GetMskuPageOutsideDTO;
import com.wisrc.replenishment.webapp.dto.msku.MskuPageOutsideDTO;
import com.wisrc.replenishment.webapp.dto.msku.MskuPlanPageDTO;
import com.wisrc.replenishment.webapp.dto.mskuSalesPlan.GetSalesPlanDTO;
import com.wisrc.replenishment.webapp.service.MskuOutsideService;
import com.wisrc.replenishment.webapp.service.ReplenishmentMskuService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.utils.ServiceUtils;
import com.wisrc.replenishment.webapp.vo.MskuInfoPageOutsideVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplenishmentMskuServiceImpl implements ReplenishmentMskuService {
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
    public LogisticsPlanPageDto getMskuPage(GetMskuPageDto getMskuPageDto, String userId) throws Exception {
        LogisticsPlanPageDto result = new LogisticsPlanPageDto();
        List<GetLogisticsPlanPageReturnDto> logisticsPlanList = new ArrayList<>();
        Result mskuResult;
        ObjectMapper mapper = new ObjectMapper();

        try {
            MskuInfoPageOutsideVo mskuInfoPageOutsideVo = new MskuInfoPageOutsideVo();
            BeanUtils.copyProperties(getMskuPageDto, mskuInfoPageOutsideVo);
            mskuInfoPageOutsideVo.setManager(getMskuPageDto.getEmployeeId());
            mskuInfoPageOutsideVo.setSalesStatus(getMskuPageDto.getSalesStatusCd());
            mskuResult = mskuOutsideService.getMskuPage(mskuInfoPageOutsideVo.getShopId(),
                    mskuInfoPageOutsideVo.getManager(), mskuInfoPageOutsideVo.getSalesStatus(),
                    mskuInfoPageOutsideVo.getFindKey(), mskuInfoPageOutsideVo.getPageNum(),
                    mskuInfoPageOutsideVo.getPageSize(), getMskuPageDto.getSalesStatusList(),
                    mskuInfoPageOutsideVo.getDoesDelete(), userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        if (mskuResult.getCode() != 200) {
            throw new Exception(String.format("商品模块发生错误，数据无法获取,获取状态码%d,信息%s", mskuResult.getCode(), mskuResult.getMsg()));
        }

        MskuPageOutsideDTO mskuListData = ServiceUtils.mapToBean(ServiceUtils.LinkedHashMapToMap(mskuResult.getData()), new MskuPageOutsideDTO());
        List<GetMskuPageOutsideDTO> mskuList = mapper.convertValue(mskuListData.getMskuList(), new TypeReference<List<GetMskuPageOutsideDTO>>() {
        });

        for (GetMskuPageOutsideDTO msku : mskuList) {
            GetLogisticsPlanPageReturnDto logisticsPlan = new GetLogisticsPlanPageReturnDto();
            BeanUtils.copyProperties(msku, logisticsPlan);

            logisticsPlanList.add(logisticsPlan);
        }

        result.setLogisticsPlanList(logisticsPlanList);
        result.setTotal(mskuListData.getTotal());
        result.setPages(mskuListData.getPages());
        return result;
    }

    @Override
    public List<MskuPlanPageDTO> getSalesPlanData(String commodityId) throws Exception {
        Result getSalesPlan;
        ObjectMapper mapper = new ObjectMapper();

        try {
            getSalesPlan = mskuOutsideService.getSalesPlan(commodityId);
        } catch (Exception e) {
            throw e;
        }

        if (getSalesPlan.getCode() != 200) {
            throw new Exception(String.valueOf(getSalesPlan.getData()));
        }

        GetSalesPlanDTO mskuListData = ServiceUtils.mapToBean(ServiceUtils.LinkedHashMapToMap(getSalesPlan.getData()), new GetSalesPlanDTO());
        List<MskuPlanPageDTO> salesPlanList = mapper.convertValue(mskuListData.getSalesPlan(), new TypeReference<List<MskuPlanPageDTO>>() {
        });
        return salesPlanList;
    }
}
