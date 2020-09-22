package com.wisrc.merchandise.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.merchandise.dao.MerchandiseBasicShopInfoDao;
import com.wisrc.merchandise.entity.BasicPlatformInfoEntity;
import com.wisrc.merchandise.entity.BasicShopDetailsInfoEntity;
import com.wisrc.merchandise.entity.BasicShopInfoEntity;
import com.wisrc.merchandise.service.BasicPlatformInfoService;
import com.wisrc.merchandise.service.BasicShopInfoService;
import com.wisrc.merchandise.utils.PageData;
import com.wisrc.merchandise.utils.Result;
import com.wisrc.merchandise.vo.ShopInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BasicShopInfoServiceImpl implements BasicShopInfoService {

    private final Logger logger = LoggerFactory.getLogger(BasicShopInfoServiceImpl.class);

    @Autowired
    private MerchandiseBasicShopInfoDao merchandiseBasicShopInfoDao;

    @Autowired
    private BasicPlatformInfoService basicPlatformInfoService;

    @Override
    public LinkedHashMap findAll(int startPage, int pageSize) {

        PageHelper.startPage(startPage, pageSize);

        List<BasicShopInfoEntity> shopList = merchandiseBasicShopInfoDao.findAll();

        PageInfo<BasicShopInfoEntity> info = new PageInfo<BasicShopInfoEntity>(shopList);

        List<BasicPlatformInfoEntity> platList = basicPlatformInfoService.findAll();

        List<ShopInfoVO> result = shopToVo(info.getList());

        List<ShopInfoVO> ret = voAddPlatform(result, platList);

        return PageData.pack(info.getTotal(), info.getPages(), "storeInfoList", ret);

    }

    @Override
    public LinkedHashMap findAll() {

        List<BasicShopInfoEntity> shopList = merchandiseBasicShopInfoDao.findAll();

        List<BasicPlatformInfoEntity> platList = basicPlatformInfoService.findAll();

        List<ShopInfoVO> result = shopToVo(shopList);

        List<ShopInfoVO> ret = voAddPlatform(result, platList);

        return PageData.pack(-1, -1, "storeInfoList", ret);

    }

    @Override
    public LinkedHashMap searchAndPage(String platformName, String shopId, String shopName, String statusCd, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        List<BasicShopDetailsInfoEntity> shopList = merchandiseBasicShopInfoDao.search(platformName, shopId, shopName, statusCd);

        PageInfo info = new PageInfo(shopList);

        List<ShopInfoVO> result = shopDetailsToVo(shopList);

        return PageData.pack(info.getTotal(), info.getPages(), "storeInfoList", result);
    }

    @Override
    public LinkedHashMap search(String platformName, String shopId, String shopName, String statusCd) {

        List<BasicShopDetailsInfoEntity> shopList = merchandiseBasicShopInfoDao.search(platformName, shopId, shopName, statusCd);

        List<ShopInfoVO> result = shopDetailsToVo(shopList);

        return PageData.pack(-1, -1, "storeInfoList", result);
    }

    @Override
    public BasicShopInfoEntity findById(String shopId) {
        return merchandiseBasicShopInfoDao.findById(shopId);
    }

    @Override
    public void delete(String shopId) {
        merchandiseBasicShopInfoDao.delete(shopId);
    }

    @Override
    @Transactional(transactionManager = "retailMerchandiseTransactionManager")
    public void deleteList(List<String> list) {
        for (String id : list) {
            merchandiseBasicShopInfoDao.delete(id);
        }
    }

    @Override
    public void update(BasicShopInfoEntity ele) {
        merchandiseBasicShopInfoDao.update(ele);
    }

    @Override
    public void changeStatus(String shopId, int statusCd) {
        merchandiseBasicShopInfoDao.changeStatus(shopId, statusCd);
    }

    @Override
    public Result add(BasicShopInfoEntity ele) {
        try {
            merchandiseBasicShopInfoDao.add(ele);
            return Result.success();
        } catch (DuplicateKeyException e) {
            return Result.failure(423, "卖家编号重复，请重新输入", ele);
        }
    }

    private List<ShopInfoVO> shopToVo(List<BasicShopInfoEntity> shopList) {
        List<ShopInfoVO> result = new LinkedList<>();
        for (BasicShopInfoEntity ele : shopList) {
            ShopInfoVO one = new ShopInfoVO();
            one.setShopId(ele.getShopId());
            one.setStoreName(ele.getShopName());
            one.setAws(ele.getAwsAccessKey());
            one.setKey(ele.getSecurityKey());
            one.setSellerNo(ele.getShopOwnerId());
            one.setStatusCd(ele.getStatusCd());
            one.setUpdateTime(ele.getModifyTime());
            one.setPlatId(ele.getPlatId());
            result.add(one);
        }
        return result;
    }

    private List<ShopInfoVO> shopDetailsToVo(List<BasicShopDetailsInfoEntity> shopList) {
        List<ShopInfoVO> result = new LinkedList<>();
        for (BasicShopDetailsInfoEntity ele : shopList) {
            ShopInfoVO one = new ShopInfoVO();
            one.setShopId(ele.getShopId());
            one.setPlatform(ele.getPlatName());
            one.setSiteName(ele.getPlatSite());
            one.setStoreName(ele.getShopName());
            one.setAws(ele.getAwsAccessKey());
            one.setKey(ele.getSecurityKey());
            one.setSellerNo(ele.getShopOwnerId());
            one.setStatusCd(ele.getStatusCd());
            one.setUpdateTime(ele.getModifyTime());
            one.setPlatId(ele.getPlatId());

            result.add(one);
        }
        return result;
    }

    private List<ShopInfoVO> voAddPlatform(List<ShopInfoVO> vo, List<BasicPlatformInfoEntity> list) {
        Map<String, BasicPlatformInfoEntity> map = listToMap(list);

        for (ShopInfoVO one : vo) {
            if (map.containsKey(one.getPlatId())) {
                BasicPlatformInfoEntity bpie = map.get(one.getPlatId());
                one.setPlatform(bpie.getPlatName());
                one.setSiteName(bpie.getPlatSite());
            }
        }
        return vo;
    }

    private Map<String, BasicPlatformInfoEntity> listToMap(List<BasicPlatformInfoEntity> list) {
        Map<String, BasicPlatformInfoEntity> map = new HashMap<>();
        for (BasicPlatformInfoEntity one : list) {
            map.put(one.getPlatId(), one);
        }
        return map;
    }

    @Override
    public String getWarehouseByShop(String shopId) {
        return merchandiseBasicShopInfoDao.getWarehouseByShop(shopId);
    }
}
