package com.wisrc.product.webapp.service.aop;

import com.google.gson.Gson;
import com.wisrc.product.webapp.entity.ProductClassifyDefineEntity;
import com.wisrc.product.webapp.service.WmsSyncService;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.vo.wms.ProductInfoVO;
import com.wisrc.product.webapp.vo.wms.ProductPackInfoVO;
import com.wisrc.product.webapp.vo.wms.ProductTypeVO;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class ProductClassifyDefineAOP {
    @Autowired
    private WmsSyncService wmsSyncService;

    // 拦截新增产品类别
    @AfterReturning(value = "execution(public * com.wisrc.product.webapp.service.ProductClassifyDefineService.insert(..))" + "&&" + "args(supplierDateOfferEntity)", returning = "map")
    public void addAop(Map map, ProductClassifyDefineEntity supplierDateOfferEntity) {
        try {
            String errorMsg = (String) map.get("error");
            List<ProductTypeVO> list = new ArrayList<>();
            if ("0".equals(errorMsg)) {
                ProductTypeVO vo = new ProductTypeVO();
                vo.setTypeCode(supplierDateOfferEntity.getClassifyCd());
                vo.setTypeName(supplierDateOfferEntity.getClassifyNameCh());
                list.add(vo);
                Result result1 = wmsSyncService.addProductType(list);
                if (result1.getCode() != 0) {
                    throw new RuntimeException("同步失败:" + result1.getMsg());
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    // 拦截修改产品类别
    @AfterReturning(value = "execution(public * com.wisrc.product.webapp.service.ProductClassifyDefineService.updatePart(..))" + "&&" + "args(supplierDateOfferEntity)", returning = "map")
    public void updateAop(Map map, ProductClassifyDefineEntity supplierDateOfferEntity) {
        try {
            String errorMsg = (String) map.get("error");
            List<ProductTypeVO> list = new ArrayList<>();
            if ("0".equals(errorMsg)) {
                ProductTypeVO vo = new ProductTypeVO();
                vo.setTypeCode(supplierDateOfferEntity.getClassifyCd());
                vo.setTypeName(supplierDateOfferEntity.getClassifyNameCh());
                list.add(vo);
                Result result1 = wmsSyncService.addProductType(list);
                if (result1.getCode() != 0) {
                    throw new RuntimeException("同步失败:" + result1.getMsg());
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    // 拦截新增产品
    @AfterReturning(value = "execution(public * com.wisrc.product.webapp.service.ProductInfoService.newInsert(..))", returning = "obj")
    public void addAop(Result obj) {
        try {
            if (obj.getCode() == 200) {
                List<ProductInfoVO> list = new ArrayList<>();
                ProductInfoVO productInfoVO = (ProductInfoVO) obj.getData();
                List<ProductPackInfoVO> goodsPackList = productInfoVO.getGoodsPackList();
                for (ProductPackInfoVO packInfoVO : goodsPackList) {
                    packInfoVO.setPackName("PCS");
                    packInfoVO.setPackSpec(1);
                    packInfoVO.setIsbase(1);
                    packInfoVO.setWeightUnit("kg");
                }
                list.add(productInfoVO);
                Gson gson = new Gson();
                Result result = wmsSyncService.addProduct(gson.toJson(list));
                if (result.getCode() != 0) {
                    throw new RuntimeException("同步失败:" + result.getMsg());
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    // 拦截修改产品
    @AfterReturning(value = "execution(public * com.wisrc.product.webapp.service.ProductInfoService.updateNew(..))", returning = "obj")
    public void updateAop(Result obj) {
        try {
            if (obj.getCode() == 200) {
                List<ProductInfoVO> list = new ArrayList<>();
                ProductInfoVO productInfoVO = (ProductInfoVO) obj.getData();
                List<ProductPackInfoVO> goodsPackList = productInfoVO.getGoodsPackList();
                for (ProductPackInfoVO packInfoVO : goodsPackList) {
                    packInfoVO.setPackName("PCS");
                    packInfoVO.setPackSpec(1);
                    packInfoVO.setIsbase(1);
                    packInfoVO.setWeightUnit("kg");
                }
                list.add(productInfoVO);
                Gson gson = new Gson();
                Result result = wmsSyncService.addProduct(gson.toJson(list));
                if (result.getCode() != 0) {
                    throw new RuntimeException("同步失败:" + result.getMsg());
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
