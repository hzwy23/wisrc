package com.wisrc.product.webapp.service.impl;

import com.wisrc.product.webapp.dao.ProductDefineDao;
import com.wisrc.product.webapp.dao.ProductImagesDao;
import com.wisrc.product.webapp.entity.ProductImagesEntity;
import com.wisrc.product.webapp.service.ProductImagesService;
import com.wisrc.product.webapp.service.ProductModifyHistoryService;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.utils.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductImagesServiceImp implements ProductImagesService {
    private final Logger logger = LoggerFactory.getLogger(ProductImagesServiceImp.class);
    @Autowired
    private ProductImagesDao productImagesDao;
    @Autowired
    private ProductModifyHistoryService productModifyHistoryService;
    @Autowired
    private ProductDefineDao productDefineDao;
    @Override
    public Map<String, String> insert(ProductImagesEntity entity) {
        Map<String, String> map = new HashMap<>();

        //判断图片地址是否为空！！！
        String imageUrl = entity.getImageUrl();
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            map.put("error", "0");
            map.put("errorMsg", "");
            return map;
        }

        try {
            String time = Time.getCurrentTime();
            entity.setCreateTime(time);
            String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            entity.setUuid(uuid);
            productImagesDao.insert(entity);
            map.put("error", "0");
            map.put("errorMsg", "");
        } catch (Exception e) {
            logger.error("新增产品图片出错！", e);
            map.put("error", "1");
            map.put("errorMsg", "新增产品图片出错！");
            throw new RuntimeException("新增产品图片出错");
        }
        return map;
    }

    @Override
    public List<ProductImagesEntity> findBySkuId(String skuId) {
        return productImagesDao.findBySkuId(skuId);
    }

    @Override
    public void deleteBySkuId(String skuId) {
        productImagesDao.deleteBySkuId(skuId);
    }



    @Override
    public List<Map<String, String>> findBySkuISimple(String skuId) {
        return productImagesDao.findBySkuISimple(skuId);
    }

    @Override
    public void updateListNewTwo(List<ProductImagesEntity> list, String time, String skuId, String createUser) {
        List<Map> oldList = productImagesDao.findBySkuIdSimple(skuId);
        //删除之前的加工信息
        productImagesDao.deleteBySkuId(skuId);
        if (list == null || list.size() == 0) {  //本次更新的图片集合是空或数量为0，说明清空原数据
            for (Map map : oldList) {
                String delValue = "移除" + typeName((Integer) map.get("imageClassifyCd")) + "一张";
                productModifyHistoryService.historyUpdateList(skuId, createUser, map.toString(), "", time, delValue, true);
            }
            return;
        } else {
            List<Map<String, Object>> newList = new ArrayList<>();
            //重新插入本次加工信息
            ProductImagesEntity entity = null;
            for (int i = 0; i < list.size(); i++) {
                entity = list.get(i);
                entity.setUpdateTime(time);

                String imageUrl = entity.getImageUrl();
                if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                    insert(entity);
                    Map<String, Object> map = new HashMap<>();
                    map.put("imageClassifyCd", entity.getImageClassifyCd());
                    map.put("imageUrl", entity.getImageUrl());
                    newList.add(map);
                }
            }
            if (newList.size() == 0) { //这里数量为零，说明本次图片集合全是图片地址为空的数据，也可以视作清除了之前的数据
                for (Map map : oldList) {
                    String delValue = "移除" + typeName((Integer) map.get("imageClassifyCd")) + "一张";
                    productModifyHistoryService.historyUpdateList(skuId, createUser, map.toString(), "", time, delValue, true);
                }
                return;
            }
            //更新集合
            Set<Map<String, Map>> updateSet = new HashSet<>();
            //新增
            Set<Map> insertSet = new HashSet<>();
            //删除
            Set<Map> deleteSet = new HashSet<>();
            //临时记录量
            Set<Map> tempSet = new HashSet<>();
            if (oldList.size() > 0) {
                for (int i = 0; i < newList.size(); i++) {
                    Boolean flag = true;
                    String imageUrl = (String) newList.get(i).get("imageUrl");
                    Integer imageClassifyCd = (Integer) newList.get(i).get("imageClassifyCd");
                    for (int j = 0; j < oldList.size(); j++) {
                        deleteSet.add(oldList.get(j));
                        //imageUrl相同，判断数量imageClassifyCd
                        if (oldList.get(j).get("imageUrl").equals(imageUrl)) {
                            //说明不是新增的数据
                            flag = false;
                            if (oldList.get(j).get("imageClassifyCd") != imageClassifyCd) { //不同为更新
                                Map<String, Map> updateMap = new HashMap();
                                updateMap.put("oldValue", oldList.get(j));
                                updateMap.put("newValue", newList.get(i));
                                updateSet.add(updateMap);
                            }
                            //只要imageUrl相同，那么可能更新也可能为原值更新（不更新），那么就不是删除操作，记录本次的数据
                            tempSet.add(oldList.get(j));
                        }
                    }
                    if (flag) {
                        insertSet.add(newList.get(i));
                    }
                }
            }
            deleteSet.removeAll(tempSet); //旧值中去掉更新（或者原值更新）的，剩下就是删除的

            if (oldList.size() == 0) {
                //全是新增
                for (Map map : newList) {
                    String addValue = "新增" + typeName((Integer) map.get("imageClassifyCd")) + "一张";
                    productModifyHistoryService.historyUpdateList(skuId, createUser, "", map.toString(), time, addValue, true);
                }
            } else {
                //新增
                for (Map map : insertSet) {
                    String addValue = "新增" + typeName((Integer) map.get("imageClassifyCd")) + "一张";
                    productModifyHistoryService.historyUpdateList(skuId, createUser, "", map.toString(), time, addValue, true);
                }
                //删除
                for (Map map : deleteSet) {
                    String delValue = "移除" + typeName((Integer) map.get("imageClassifyCd")) + "一张";
                    productModifyHistoryService.historyUpdateList(skuId, createUser, map.toString(), "", time, delValue, true);
                }
                //更新
                for (Map<String, Map> map : updateSet) {
                    String updateValue = "图片（" + map.get("oldValue").get("imageUrl") + "）的类型由" + typeName((Integer) map.get("oldValue").get("imageClassifyCd")) + "更新为"
                            + typeName((Integer) map.get("newValue").get("imageClassifyCd"));
                    productModifyHistoryService.historyUpdateList(skuId, createUser, map.get("oldValue").toString(), map.get("newValue").toString(), time, updateValue, true);
                }
            }
        }
    }

    @Override
    public Result getAllSkuImgs() {
        Map<String, List<String>> skuIdToImgs = new HashMap<>();
        List<String> allSKU = productDefineDao.getAllSKU();
        for (String skuId : allSKU) {
            List<String> imgBySkuId = productImagesDao.findImgBySkuId(skuId);
            skuIdToImgs.put(skuId,imgBySkuId);
        }
        return Result.success(skuIdToImgs);
    }

    //数字转换类型名
    private String typeName(int i) {
        if (i == 1) {
            return "带包装产品图";
        }
        if (i == 2) {
            return "贴标位示意图";
        } else {
            return "产品图片";
        }
    }

}
