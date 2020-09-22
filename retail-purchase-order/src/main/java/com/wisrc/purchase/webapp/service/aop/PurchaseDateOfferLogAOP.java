package com.wisrc.purchase.webapp.service.aop;

import com.wisrc.purchase.webapp.dao.PurchaseDateOfferLogDao;
import com.wisrc.purchase.webapp.entity.PurchaseDateOfferLogEntity;
import com.wisrc.purchase.webapp.entity.SupplierDateOfferEntity;
import com.wisrc.purchase.webapp.service.SupplierDateOfferService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.utils.Time;
import com.wisrc.purchase.webapp.utils.Toolbox;
import com.wisrc.purchase.webapp.vo.supplierDateOffer.SupplierDateOfferVO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class PurchaseDateOfferLogAOP {

    private static final Map<String, String> updateColumns = new HashMap<String, String>() {{
        put("SupplierOfferId", "供应商报价单号");
        put("SkuId", "库存SKU ID");
        put("SupplierId", "供应商编码");
        put("EmployeeId", "采购员");
        put("FirstDelivery", "首次发货");
        put("GeneralDelivery", "普通发货");
        put("Minimum", "最少起订量");
        put("HaulageDays", "运输时间");
        put("DeliveryPart", "交期分解");
        put("DeliveryOptimize", "交期优化方案");
        put("Remark", "备注");
        put("UnitPriceWithoutTax", "不含税单价");
    }};

    @Autowired
    private SupplierDateOfferService supplierDateOfferService;

    @Autowired
    private PurchaseDateOfferLogDao purchaseDateOfferLogDao;

    // 拦截删除
    @Around("execution(public * com.wisrc.purchase.webapp.service.SupplierDateOfferService.upEmployee(..))" + "&&" + "args(supplierDateOfferEntity)")
    public void aopDel(ProceedingJoinPoint pj, SupplierDateOfferEntity supplierDateOfferEntity) {
        SupplierDateOfferVO oldvalue = supplierDateOfferService.findInfoById(supplierDateOfferEntity.getSupplierOfferId());
        try {
            Result result = (Result) pj.proceed();
            if (result != null && result.getCode() == 200) {
                PurchaseDateOfferLogEntity log = new PurchaseDateOfferLogEntity();
                log.setSupplierOfferId(supplierDateOfferEntity.getSupplierOfferId());
                log.setModifyColumn("删除采购报价单");
                log.setNewValue("");
                log.setOldValue("");
                log.setHandleUser(supplierDateOfferEntity.getModifyUser());
                log.setHandleTime(Time.getCurrentTimestamp());
                log.setUuid(Toolbox.randomUUID());
                purchaseDateOfferLogDao.addLogs(log);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    // 拦截更新采购员
    @Around("execution(public * com.wisrc.purchase.webapp.service.SupplierDateOfferService.upEmployee(..))" + "&&" + "args(supplierDateOfferEntity)")
    public void aopUpdateEmployee(ProceedingJoinPoint pj, SupplierDateOfferEntity supplierDateOfferEntity) {
        SupplierDateOfferVO oldvalue = supplierDateOfferService.findInfoById(supplierDateOfferEntity.getSupplierOfferId());
        try {
            Result result = (Result) pj.proceed();
            if (result != null && result.getCode() == 200 && !oldvalue.getEmployeeId().equals(supplierDateOfferEntity.getEmployeeId())) {
                PurchaseDateOfferLogEntity log = new PurchaseDateOfferLogEntity();
                log.setSupplierOfferId(supplierDateOfferEntity.getSupplierOfferId());
                log.setModifyColumn("采购员");
                log.setOldValue(oldvalue.getEmployeeId());
                log.setNewValue(supplierDateOfferEntity.getEmployeeId());
                log.setHandleUser(supplierDateOfferEntity.getModifyUser());
                log.setHandleTime(Time.getCurrentTimestamp());
                log.setUuid(Toolbox.randomUUID());
                purchaseDateOfferLogDao.addLogs(log);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    // 拦截更新状态
    @Around("execution(public * com.wisrc.purchase.webapp.service.SupplierDateOfferService.upStatus(..))" + "&&" + "args(supplierDateOfferEntity)")
    public void aopUpdateStatus(ProceedingJoinPoint pj, SupplierDateOfferEntity supplierDateOfferEntity) {
        SupplierDateOfferVO oldvalue = supplierDateOfferService.findInfoById(supplierDateOfferEntity.getSupplierOfferId());
        try {
            Result result = (Result) pj.proceed();
            if (result != null && result.getCode() == 200 && oldvalue.getStatusCd() != supplierDateOfferEntity.getStatusCd()) {
                PurchaseDateOfferLogEntity log = new PurchaseDateOfferLogEntity();
                log.setSupplierOfferId(supplierDateOfferEntity.getSupplierOfferId());
                log.setModifyColumn("状态");
                log.setOldValue(String.valueOf(oldvalue.getStatusCd()));
                log.setNewValue(String.valueOf(supplierDateOfferEntity.getStatusCd()));
                log.setHandleUser(supplierDateOfferEntity.getModifyUser());
                log.setHandleTime(Time.getCurrentTimestamp());
                log.setUuid(Toolbox.randomUUID());
                purchaseDateOfferLogDao.addLogs(log);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    // 拦截更新日期
    @Around("execution(public * com.wisrc.purchase.webapp.service.SupplierDateOfferService.upDate(..))" + "&&" + "args(supplierDateOfferEntity)")
    public void aopUpdateDate(ProceedingJoinPoint pj, SupplierDateOfferEntity supplierDateOfferEntity) {
        SupplierDateOfferVO oldvalue = supplierDateOfferService.findInfoById(supplierDateOfferEntity.getSupplierOfferId());
        try {
            Result result = (Result) pj.proceed();
            if (result != null && result.getCode() == 200 && oldvalue.getHaulageDays() != supplierDateOfferEntity.getHaulageDays()) {
                PurchaseDateOfferLogEntity log = new PurchaseDateOfferLogEntity();
                log.setSupplierOfferId(supplierDateOfferEntity.getSupplierOfferId());
                log.setModifyColumn("运输时间");
                log.setOldValue(String.valueOf(oldvalue.getHaulageDays()));
                log.setNewValue(String.valueOf(supplierDateOfferEntity.getHaulageDays()));
                log.setHandleUser(supplierDateOfferEntity.getModifyUser());
                log.setHandleTime(Time.getCurrentTimestamp());
                log.setUuid(Toolbox.randomUUID());
                purchaseDateOfferLogDao.addLogs(log);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    // 拦截新增采购报价单
    @Around("execution(public * com.wisrc.purchase.webapp.service.SupplierDateOfferService.insertInfo(..))" + "&&" + "args(supplierDateOfferEntity)")
    public void beforeAop(ProceedingJoinPoint pj, SupplierDateOfferEntity supplierDateOfferEntity) {
        try {
            Result result = (Result) pj.proceed();
            if (result != null && result.getCode() == 200) {
                PurchaseDateOfferLogEntity log = new PurchaseDateOfferLogEntity();
                log.setSupplierOfferId(supplierDateOfferEntity.getSupplierOfferId());
                log.setModifyColumn("新建报价单");
                log.setNewValue("");
                log.setOldValue("");
                log.setHandleUser(supplierDateOfferEntity.getCreateUser());
                log.setHandleTime(Time.getCurrentTimestamp());
                log.setUuid(Toolbox.randomUUID());
                purchaseDateOfferLogDao.addLogs(log);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    // 切入编辑采购报价单
    @Around("execution(public * com.wisrc.purchase.webapp.service.SupplierDateOfferService.updateInfo(..))" + "&&" + "args(supplierDateOfferEntity)")
    public Object beforeUpdateAop(ProceedingJoinPoint pj, SupplierDateOfferEntity supplierDateOfferEntity) {
        // 获取以前的值
        SupplierDateOfferVO oldvalue = supplierDateOfferService.findInfoById(supplierDateOfferEntity.getSupplierOfferId());
        try {
            // 执行编辑方法
            Object object = pj.proceed();
            Result result = (Result) object;
            if (result != null && result.getCode() == 200) {
                // 执行成功，开始对比修改记录
                checkColumn(oldvalue, supplierDateOfferEntity);
            }
            return object;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return Result.failure(435, "更新采购报价信息失败，请联系管理员", supplierDateOfferEntity);
        }
    }

    // 对比字段值是否修改
    private void checkColumn(SupplierDateOfferVO oldValue, SupplierDateOfferEntity newValue) {
        SupplierDateOfferEntity old = new SupplierDateOfferEntity();
        BeanUtils.copyProperties(oldValue, old);

        // 也可以通过反射的方式获取类的属性字段，但是效率不太好，所以改成了直接通过Map配置字段来判断字段值是否改变
        for (String name : updateColumns.keySet()) {
            PurchaseDateOfferLogEntity log = new PurchaseDateOfferLogEntity();
            try {
                Method method = SupplierDateOfferEntity.class.getDeclaredMethod("get" + name);
                Object oldV = method.invoke(old);
                Object newV = method.invoke(newValue);

                if ((oldV == null && newV != null) || (oldV != null && !oldV.equals(newV))) {
                    log.setSupplierOfferId(newValue.getSupplierOfferId());
                    log.setModifyColumn(updateColumns.get(name));
                    log.setNewValue(newV.toString());
                    log.setOldValue(oldV.toString());
                    log.setHandleUser(newValue.getModifyUser());
                    log.setHandleTime(Time.getCurrentTimestamp());
                    log.setUuid(Toolbox.randomUUID());
                    purchaseDateOfferLogDao.addLogs(log);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
