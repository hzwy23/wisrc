package com.wisrc.replenishment.webapp.aop;

import com.google.gson.Gson;
import com.wisrc.replenishment.webapp.dao.FbaReplenishmentInfoDao;
import com.wisrc.replenishment.webapp.entity.FbaReplenishmentInfoEntity;
import com.wisrc.replenishment.webapp.service.externalService.WarehouseService;
import com.wisrc.replenishment.webapp.service.externalService.WmsService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.utils.Time;
import com.wisrc.replenishment.webapp.vo.wms.FbaCancelReplenInfoVO;
import com.wisrc.replenishment.webapp.vo.wms.ProcessTaskBillEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Aspect
@Component
public class FbaCancelReplenSyncAOP {

    @Autowired
    WarehouseService warehouseService;
    @Autowired
    private WmsService wmsService;
    @Autowired
    private FbaReplenishmentInfoDao fbaReplenishmentInfoDao;

    @AfterReturning(value = "execution(public * com.wisrc.replenishment.webapp.service.FbaReplenishmentInfoService.cancelReplen(..))", returning = "result")
    public void cancelReplenAop(JoinPoint pj, Result result) {
        if (result.getCode() == 200) {
            Gson gson = new Gson();
            Object[] args = pj.getArgs();
            String fbaReplenishmentId = (String) args[0];

            FbaReplenishmentInfoEntity fbaEntity = fbaReplenishmentInfoDao.findById(fbaReplenishmentId);

            int flag = 0;
            if (fbaEntity.getStatusCd() == 0) {
                flag = 1;
            }

            //如果补货单是组装发货
            if (fbaEntity.getStatusCd() == 1 && fbaEntity.getDeliveringTypeCd() == 2) {
                Result processResult = warehouseService.getProcessBillByFbaId(fbaReplenishmentId);
                if (processResult.getCode() == 200 && !(processResult.getData()).equals("")) {
                    List<ProcessTaskBillEntity> processList = (List<ProcessTaskBillEntity>) processResult.getData();
                    flag = 1;
                    for (Object one : processList) {
                        if ((int) ((Map) one).get("statusCd") != 5) {
                            flag = 2;
                            break;
                        }
                    }
                }
            }

            if (flag != 1 && flag != 2) {
                String cancelReason = (String) args[1];
                FbaCancelReplenInfoVO vo = new FbaCancelReplenInfoVO();
                vo.setVoucherCode(fbaReplenishmentId);
                vo.setVoucherType("FBABH");
                vo.setCancelReason(cancelReason);
                vo.setCancelTime(Time.getCurrentDateTime());
                try {
                    Result wmsResult = wmsService.cancelReplen(gson.toJson(vo));
                    System.out.println(wmsResult);
                    if (wmsResult.getCode() != 0) {
                        result.setMsg("FBA补货出库取消错误：" + wmsResult.getMsg());
                    }
                } catch (Exception e) {
                    result.setMsg("wms同步方法错误：" + e.getMessage());
                }
            }
        }
    }
}

