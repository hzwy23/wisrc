package com.wisrc.merchandise.service.impl;


import com.wisrc.merchandise.controller.BasicShopInfoController;
import com.wisrc.merchandise.dto.ShopSelectorDTO;
import com.wisrc.merchandise.service.ShopOutsideService;
import com.wisrc.merchandise.utils.Result;
import com.wisrc.merchandise.vo.ShopInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ShopOutsideServiceImpl implements ShopOutsideService {
    @Autowired
    private BasicShopInfoController basicShopInfoController;

    @Override
    public Result getShopSelector() {
        Map result = new HashMap();
        List<ShopSelectorDTO> shopSelector = new ArrayList();
        List<ShopInfoVO> storeInfoList;

        Result shopResult = basicShopInfoController.findAll("1", "999999", null, null, null, null);

        if (shopResult.getCode() != 200) {
            return null;
        }

        try {
            Map shopPage = (Map) shopResult.getData();
            storeInfoList = (List) shopPage.get("storeInfoList");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        for (ShopInfoVO shop : storeInfoList) {
            ShopSelectorDTO shopSelectorDTO = new ShopSelectorDTO();
            shopSelectorDTO.setId(shop.getShopId());
            shopSelectorDTO.setShopName(shop.getStoreName());
            shopSelectorDTO.setSellerId(shop.getSellerNo());
            shopSelector.add(shopSelectorDTO);
        }

        result.put("shopSelector", shopSelector);
        return Result.success(result);
    }

    @Override
    public String getSellerId(String shopId) throws Exception {
        Result getShop = null;
        getShop = basicShopInfoController.findById(shopId);
        ShopInfoVO shop = (ShopInfoVO) getShop.getData();
        if (getShop.getCode() != 200 || shop.getSellerNo() == null) {
            throw new Exception("获取店铺识别编号失败。 " + getShop.getMsg());
        }
        return shop.getSellerNo().toString();
    }
}
