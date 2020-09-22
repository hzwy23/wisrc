package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.dao.FbaMskuInfoDao;
import com.wisrc.replenishment.webapp.dao.FbaMskuPackInfoDao;
import com.wisrc.replenishment.webapp.entity.FbaMskuInfoEntity;
import com.wisrc.replenishment.webapp.entity.VReplenishmentMskuEntity;
import com.wisrc.replenishment.webapp.service.FbaMskuInfoService;
import com.wisrc.replenishment.webapp.service.MskuInfoService;
import com.wisrc.replenishment.webapp.service.ProductService;
import com.wisrc.replenishment.webapp.utils.ArrayToInArguments;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.FbaMskuInfoVO;
import com.wisrc.replenishment.webapp.vo.FbaMskuPackQueryVO;
import com.wisrc.replenishment.webapp.vo.FbaNewMskuInfoVO;
import com.wisrc.replenishment.webapp.vo.MskuInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class FbaMskuInfoServiceImpl implements FbaMskuInfoService {

    @Autowired
    private FbaMskuInfoDao mskuInfoDao;
    @Autowired
    private MskuInfoService mskuInfoService;
    @Autowired
    private ProductService productService;
    @Autowired
    private FbaMskuPackInfoDao fbaMskuPackInfoDao;

    @Override
    public void saveMskuInfo(FbaMskuInfoEntity mskuInfoEntity) {
        mskuInfoDao.saveMskuInfo(mskuInfoEntity);
    }

    @Override
    public void cancelMskuInfo(FbaMskuInfoVO mskuInfoVO) {
        mskuInfoDao.cancelMskuInfo(mskuInfoVO);
    }

    @Override
    public List<FbaMskuInfoVO> findMskuByReplenId(String fbaReplenishmentId, Map mskuList, Map productMap) {
        List<FbaMskuInfoVO> list = mskuInfoDao.findMskuByReplenId(fbaReplenishmentId);

        for (FbaMskuInfoVO fbaMskuInfo : list) {
            List<FbaMskuPackQueryVO> fbaPackList = fbaMskuPackInfoDao.findByFbaCommId(fbaMskuInfo.getReplenishmentCommodityId());
            if (fbaPackList != null && fbaPackList.size() > 0) {
                for (FbaMskuPackQueryVO fbaMskuPackQueryVO : fbaPackList) {
                    fbaMskuPackQueryVO.setSignInQuantity(fbaMskuInfo.getSignInQuantity());
                }
            }
            fbaMskuInfo.setMskupackList(fbaPackList);
            MskuInfoVO mskuInfo = (MskuInfoVO) mskuList.get(fbaMskuInfo.getCommodityId());
            if (mskuInfo != null) {
                Map map = (Map) productMap.get(mskuInfo.getStoreSku());
                mskuInfo.setStoreLabel((List<Map<String, Object>>) (map).get("declareLabelList"));
                mskuInfo.setStoreName((String) map.get("skuNameZh"));
                fbaMskuInfo.setShopName(mskuInfo.getShopName());
                fbaMskuInfo.setMskuInfoVO(mskuInfo);
            } else {
                log.warn("商品信息不存在，{}", mskuInfo);
            }
        }
        return list;
    }

    @Override
    public List<FbaMskuInfoVO> findMskuByReplenId(String fbaReplenishmentId) {
        Map labelMap = new HashMap();
        Result lableResult = productService.getProductLabelAttr("admin");
        if (lableResult.getCode() == 200) {
            List lableList = (List) lableResult.getData();
            for (Object object : lableList) {
                Map lable = (Map) object;
                labelMap.put(lable.get("labelCd"), lable.get("labelDesc"));
            }
        }
        List<FbaMskuInfoVO> list = mskuInfoDao.findMskuByReplenId(fbaReplenishmentId);
        for (FbaMskuInfoVO fbaMskuInfo : list) {
            List<FbaMskuPackQueryVO> fbaPackList = fbaMskuPackInfoDao.findByFbaCommId(fbaMskuInfo.getReplenishmentCommodityId());
            fbaMskuInfo.setMskupackList(fbaPackList);
            Result map = mskuInfoService.getMskuInfo(fbaMskuInfo.getCommodityId());
            MskuInfoVO mskuInfo = new MskuInfoVO();
            String shopName = "";
            String shopId = fbaMskuInfo.getShopId();
            Result shopResult = mskuInfoService.getShopName(shopId);
            if (200 == shopResult.getCode()) {
                LinkedHashMap data = (LinkedHashMap) shopResult.getData();
                if (data != null && !"".equals(data)) {
                    shopName = (String) data.get("storeName");
                }
            }
            if (200 == map.getCode()) {
                LinkedHashMap data = (LinkedHashMap) map.getData();
                if (data != null && !"".equals(data)) {
                    String mskuId = (String) data.get("mskuId");
                    String storeSku = (String) data.get("storeSku");
                    Result prodDetailResult = productService.getProdDetails(storeSku);
                    if (prodDetailResult.getCode() == 200) {
                        HashMap prodDetail = (HashMap) prodDetailResult.getData();
                        List<Map<String, Object>> labelList = (List<Map<String, Object>>) prodDetail.get("declareLabelList");
                        for (Map laMap : labelList) {
                            Set<String> labelSet = laMap.keySet();
                            for (String labelKey : labelSet) {
                                if ("labelCd".equals(labelKey)) {
                                    laMap.put("labelText", labelMap.get(laMap.get(labelKey)));
                                }
                            }
                        }
                        mskuInfo.setStoreLabel(labelList);
                        Map detail = (Map) prodDetail.get("define");
                        String storeName = (String) detail.get("skuNameZh");
                        mskuInfo.setStoreName(storeName);

                        mskuInfo.setDeclareNameZh((String) ((Map) prodDetail.get("declareInfo")).get("declareNameZh"));
                        mskuInfo.setDeclareNameEn((String) ((Map) prodDetail.get("declareInfo")).get("declareNameEn"));
                        mskuInfo.setCustomsNumber((String) ((Map) prodDetail.get("declareInfo")).get("customsNumber"));
                    }

                    String mskuName = (String) data.get("mskuName");
                    String FnSKU = (String) data.get("fnsku");
                    String ASIN = (String) data.get("asin");
                    String salesStatus = "";
                    LinkedHashMap salesStatusMap = (LinkedHashMap) data.get("salesStatus");
                    salesStatus = (String) salesStatusMap.get("name");
                    String manager = (String) data.get("employee");
                    //LinkedHashMap shopMap = (LinkedHashMap) data.get("shop");
                    //shopName = (String) shopMap.get("name");
                    String picture = (String) data.get("picture");
                    mskuInfo.setMsku(mskuId);
                    mskuInfo.setStoreSku(storeSku);
                    mskuInfo.setMskuName(mskuName);
                    mskuInfo.setFnSKU(FnSKU);
                    mskuInfo.setASIN(ASIN);
                    mskuInfo.setSalesStatus(salesStatus);
                    mskuInfo.setManager(manager);
                    mskuInfo.setPicture(picture);
                }
            }
            fbaMskuInfo.setShopName(shopName);
            fbaMskuInfo.setMskuInfoVO(mskuInfo);
        }
        return list;
    }


    @Override
    public List<FbaMskuInfoEntity> getMskuInfo(String[] fbaIds) {
        List<FbaMskuInfoEntity> mskulist = mskuInfoDao.getMskuInfo(fbaIds);
        return mskulist;
    }

    @Override
    public List<FbaNewMskuInfoVO> findMskuByReplenishmentId(String fbaReplenishmentId) {
        List<FbaNewMskuInfoVO> list = mskuInfoDao.findMskuByFbaId(fbaReplenishmentId);
//        for (FbaNewMskuInfoVO fbaMskuInfo : list) {
//            List<FbaMskuPackQueryVO> mskuPackInfoList = fbaMskuPackInfoDao.findByFbaCommId(fbaMskuInfo.getReplenishmentCommodityId());
//            fbaMskuInfo.setFbaMskuPackUpdateVOList(mskuPackInfoList);
//            Result map = mskuInfoService.getMskuInfo(fbaMskuInfo.getCommodityId());
//            MskuInfoVO mskuInfo = new MskuInfoVO();
//            String shopName = "";
//            String shopId = fbaMskuInfo.getShopId();
//            Result shopResult = mskuInfoService.getShopName(shopId);
//            if (200 == shopResult.getCode()) {
//                LinkedHashMap data = (LinkedHashMap) shopResult.getData();
//                if (data != null && !"".equals(data)){
//                    shopName = (String) data.get("storeName");
//                }
//            }
//            if (200 == map.getCode()) {
//                LinkedHashMap data = (LinkedHashMap) map.getData();
//                if (data != null && !"".equals(data)) {
//                    String mskuId = (String) data.get("mskuId");
//                    String storeSku = (String) data.get("storeSku");
//                    Result prodDetailResult = productService.getProdDetails(storeSku);
//                    if (prodDetailResult.getCode() == 200){
//                        HashMap prodDetail = (HashMap) prodDetailResult.getData();
//                        mskuInfo.setStoreLabel((List<Map<String,Object>>) prodDetail.get("declareLabelList"));
//                        Map detail = (Map) prodDetail.get("define");
//                        String storeName = (String) detail.get("skuNameZh");
//                        mskuInfo.setStoreName(storeName);
//
//                        mskuInfo.setDeclareNameZh((String) ((Map)prodDetail.get("declareInfo")).get("declareNameZh"));
//                        mskuInfo.setDeclareNameEn((String) ((Map)prodDetail.get("declareInfo")).get("declareNameEn"));
//                        mskuInfo.setCustomsNumber((String) ((Map)prodDetail.get("declareInfo")).get("customsNumber"));
//                    }
//
//                    String mskuName = (String) data.get("mskuName");
//                    String FnSKU = (String) data.get("fnsku");
//                    String ASIN = (String) data.get("asin");
//                    String salesStatus = "";
//                    LinkedHashMap salesStatusMap = (LinkedHashMap) data.get("salesStatus");
//                    salesStatus = (String) salesStatusMap.get("name");
//                    String manager = (String) data.get("employee");
//                    LinkedHashMap shopMap = (LinkedHashMap) data.get("shop");
//                    shopName = (String) shopMap.get("name");
//                    String picture = (String) data.get("picture");
//                    mskuInfo.setMsku(mskuId);
//                    mskuInfo.setStoreSku(storeSku);
//                    mskuInfo.setMskuName(mskuName);
//                    mskuInfo.setFnSKU(FnSKU);
//                    mskuInfo.setASIN(ASIN);
//                    mskuInfo.setSalesStatus(salesStatus);
//                    mskuInfo.setManager(manager);
//                    mskuInfo.setPicture(picture);
//                }
//            }
//            fbaMskuInfo.setShopName(shopName);
//            fbaMskuInfo.setMskuInfoVO(mskuInfo);
//        }
        return list;
    }


    @Override
    public int getFbaOnWayNum(String mskuId) {
        int sum = 0;
        try {
            List<FbaMskuInfoEntity> fbaMskuInfoEntityList = mskuInfoDao.getFbaIdByMskuId(mskuId);
            for (int i = 0; i < fbaMskuInfoEntityList.size(); i++) {
                String fbaReplenishmentId = fbaMskuInfoEntityList.get(i).getFbaReplenishmentId();
                if (mskuInfoDao.getSignStatusByFbaId(fbaReplenishmentId) == 3) {
                    int deliveryNumber = fbaMskuInfoEntityList.get(i).getDeliveryNumber();
                    int signQuantity = fbaMskuInfoEntityList.get(i).getSignQuantity();
                    sum = deliveryNumber - signQuantity;
                    sum++;
                }
            }
        } catch (Exception e) {
            Result.failure();
        }
        return sum;
    }

    @Override
    public List<VReplenishmentMskuEntity> findUnderway(String[] commodityIdList) {
        String commodityIdListStr = ArrayToInArguments.toInArgs(commodityIdList);
        return mskuInfoDao.getMskuUnderway(commodityIdListStr);
    }

}
