package com.wisrc.shipment.webapp.service.impl;

import com.wisrc.shipment.webapp.service.EarlyWarningLevelService;
import com.wisrc.shipment.webapp.vo.earlyWarningLevelAttr.BatchEarlyWarningLevelAttrVO;
import com.wisrc.shipment.webapp.vo.earlyWarningLevelAttr.EarlyWarningLevelAttrVO;
import com.wisrc.shipment.webapp.dao.EarlyWarningLevelDao;
import com.wisrc.shipment.webapp.entity.EarlyWarningLevelAttrEntity;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.utils.Time;
import com.wisrc.shipment.webapp.utils.UUIDutil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class EarlyWarningLevelImplService implements EarlyWarningLevelService {
    @Autowired
    private EarlyWarningLevelDao earlyWarningLevelDao;

    @Override
    @Transactional(transactionManager = "retailShipmentTransactionManager")
    public Result update(@Valid BatchEarlyWarningLevelAttrVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        Result checkResult = check(vo);
        if (checkResult.getCode() != 200) {
            return checkResult;
        }

        List<EarlyWarningLevelAttrVO> warningList = vo.getWarningList();
        //特殊删除部分数据
        earlyWarningLevelDao.deleteSpecial();

        EarlyWarningLevelAttrEntity entity = new EarlyWarningLevelAttrEntity();
        for (EarlyWarningLevelAttrVO o : warningList) {
            BeanUtils.copyProperties(o, entity);
            if (entity.getEarlyWarningLevelCd() == null) {
                entity.setEarlyWarningLevelCd(UUIDutil.randomUUID());
                entity.setCreateTime(Time.getCurrentTimestamp());
                try {
                    earlyWarningLevelDao.insert(entity);
                } catch (DuplicateKeyException e) {
                    return new Result(9991, "预警等级名称存在重复！请修改", e);
                }
            } else if (!entity.getEarlyWarningLevelCd().equals("0") && !entity.getEarlyWarningLevelCd().equals("1")) {
                if (entity.getEarlyWarningLevelCd().equals("2")) {
                    earlyWarningLevelDao.update(entity);
                } else {
                    entity.setEarlyWarningLevelCd(UUIDutil.randomUUID());
                    entity.setCreateTime(Time.getCurrentTimestamp());
                    try {
                        earlyWarningLevelDao.insert(entity);
                    } catch (DuplicateKeyException e) {
                        return new Result(9991, "预警等级名称存在重复！请修改", e);
                    }
                }
            }
        }
        return Result.success();
    }

    private Result check(BatchEarlyWarningLevelAttrVO vo) {
        List<EarlyWarningLevelAttrVO> warningList = vo.getWarningList();
        for (EarlyWarningLevelAttrVO o : warningList) {
            if (o.getEarlyWarningLevelCd() == null) {
                if (o.getAgingDays() == null || o.getHeadTransportationDays() == null || o.getInternalProcessingDays() == null || o.getSignedIncomeWarehouseDays() == null) {
                    return new Result(9991, "时效天数(天)计算不通过", null);
                } else if (o.getAgingDays() != o.getHeadTransportationDays() + o.getInternalProcessingDays() + o.getSignedIncomeWarehouseDays()) {
                    return new Result(9991, "时效天数(天)计算不通过", null);
                }
            }
            // earlyWarningLevelCd : "2"  红单预警
            if (o.getEarlyWarningLevelCd() != null && o.getEarlyWarningLevelCd().equals("2")) {
                if (o.getAgingDays() == null || o.getHeadTransportationDays() == null || o.getInternalProcessingDays() == null || o.getSignedIncomeWarehouseDays() == null) {
                    return new Result(9991, "时效天数(天)计算不通过", null);
                } else if (o.getAgingDays() != o.getHeadTransportationDays() + o.getInternalProcessingDays() + o.getSignedIncomeWarehouseDays()) {
                    return new Result(9991, "时效天数(天)计算不通过", null);
                }
            }
        }
        return Result.success();
    }

    @Override
    public Result findAll() {
        List<EarlyWarningLevelAttrEntity> list = earlyWarningLevelDao.findAll();
        List<EarlyWarningLevelAttrVO> voLost = poToVO(list);
        return Result.success(voLost);
    }

    private List<EarlyWarningLevelAttrVO> poToVO(List<EarlyWarningLevelAttrEntity> list) {
        List<EarlyWarningLevelAttrVO> voLost = new ArrayList<>();
        for (EarlyWarningLevelAttrEntity o : list) {
            EarlyWarningLevelAttrVO vo = new EarlyWarningLevelAttrVO();
            BeanUtils.copyProperties(o, vo);
            vo.setCreateTime(o.getCreateTime());
            voLost.add(vo);
        }
        return voLost;
    }
}
