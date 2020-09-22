package com.wisrc.supplier.webapp.service.aop;

import com.wisrc.supplier.webapp.entity.Supplier;
import com.wisrc.supplier.webapp.vo.SupplierInfoVO;
import com.wisrc.supplier.webapp.service.proxy.WmsSyncService;
import com.wisrc.supplier.webapp.utils.Result;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Aspect
@Component
public class SupplierInfoAOP {
    @Autowired
    private WmsSyncService wmsSyncService;
    // 拦截新增产品类别
    @AfterReturning(value = "execution(public * com.wisrc.supplier.webapp.service.SupplierManageService.addSupplierInfo(..)) || execution(public * com.wisrc.supplier.webapp.service.SupplierManageService.setSupplierInfo(..))",returning = "map")
    public void beforeAop(Map map) {
        try {
            boolean flag = (Boolean) map.get("success");
            Supplier supplierInfo = (Supplier)map.get("supplier");
            List<SupplierInfoVO> list = new ArrayList<>();
            if (flag) {
                SupplierInfoVO vo = new SupplierInfoVO();
                vo.setSupplierCode(supplierInfo.getSupplierId());
                vo.setSupplierName(supplierInfo.getSupplierName());
                vo.setAddr(supplierInfo.getSupplierInfo().getProvince()+supplierInfo.getSupplierInfo().getCity()+supplierInfo.getSupplierInfo().getCounty()+supplierInfo.getSupplierInfo().getStreet());
                vo.setRemark(supplierInfo.getSupplierInfo().getPaymentType());
                list.add(vo);
                Result result1 = wmsSyncService.addSupplierInfo(list);
                System.out.println(result1);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
