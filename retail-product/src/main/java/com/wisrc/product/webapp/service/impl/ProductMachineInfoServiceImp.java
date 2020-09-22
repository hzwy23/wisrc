package com.wisrc.product.webapp.service.impl;

import com.google.gson.Gson;
import com.wisrc.product.webapp.controller.ProductClassifyDefineController;
import com.wisrc.product.webapp.dao.ProductMachineInfoDao;
import com.wisrc.product.webapp.entity.ProductMachineInfoEntity;
import com.wisrc.product.webapp.service.ProductMachineInfoService;
import com.wisrc.product.webapp.service.ProductModifyHistoryService;
import com.wisrc.product.webapp.service.WarehouseInfoService;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.utils.Time;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ProductMachineInfoServiceImp implements ProductMachineInfoService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(ProductClassifyDefineController.class);
    @Autowired
    private ProductMachineInfoDao productMachineInfoDao;
    @Autowired
    private ProductModifyHistoryService productModifyHistoryService;
    @Autowired
    private WarehouseInfoService warehouseInfoService;

    @Override
    public void insert(ProductMachineInfoEntity ele) {
        String dependencySkuId = ele.getDependencySkuId();
        if (dependencySkuId != null && !dependencySkuId.trim().isEmpty()) {
            String time = Time.getCurrentTime();
            ele.setCreateTime(time);
            String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            ele.setUuid(uuid);
            productMachineInfoDao.insert(ele);
        }

    }

    @Override
    public void delete(String skuId) {
        productMachineInfoDao.delete(skuId);

    }

    @Override
    public List<ProductMachineInfoEntity> findById(String skuId) {
        return productMachineInfoDao.findById(skuId);
    }


    @Override
    public List<ProductMachineInfoEntity> findBySkuId(String skuId) {
        return productMachineInfoDao.findBySkuId(skuId);
    }



    @Override
    public void updateListNewTwo(List<ProductMachineInfoEntity> list, String time, String skuId, String createUser) {
        Map paraMap = new HashMap();
        paraMap.put("skuId", skuId);
        paraMap.put("typeCd", 1);
        List<Map> oldList = productMachineInfoDao.getMachine(paraMap);

        //删除之前的加工信息
        productMachineInfoDao.deleteType(paraMap);
        if (list == null || list.size() == 0) {
            //保存删除删除信息
            for (Map map : oldList) {
                String delValue = "【产品组合】移除：“" + map.get("dependencySkuId") + "”，数量：“" + map.get("quantity") + "”";
                productModifyHistoryService.historyUpdateList(skuId, createUser, map.toString(), "", time, delValue, true);
            }
            return;
        }

        List<Map<String, Object>> newList = new ArrayList<>();
        //重新插入本次加工信息
        ProductMachineInfoEntity entity = null;
        for (int i = 0; i < list.size(); i++) {
            entity = list.get(i);
            entity.setUpdateTime(time);
            String dependencySkuId = entity.getDependencySkuId();
            if (dependencySkuId != null && !dependencySkuId.trim().isEmpty()) {

                insert(entity);
                Map<String, Object> map = new HashMap<>();
                map.put("dependencySkuId", entity.getDependencySkuId());
                map.put("quantity", entity.getQuantity());
                newList.add(map);
            }
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
                String dependencySkuId = (String) newList.get(i).get("dependencySkuId");
                Integer quantity = (Integer) newList.get(i).get("quantity");
                for (int j = 0; j < oldList.size(); j++) {
                    deleteSet.add(oldList.get(j));
                    //dependencySkuId相同，判断数量quantity
                    if (oldList.get(j).get("dependencySkuId").equals(dependencySkuId)) {
                        //说明不是新增的数据
                        flag = false;
                        if (oldList.get(j).get("quantity") != quantity) { //不同为更新
                            Map<String, Map> updateMap = new HashMap();
                            updateMap.put("oldValue", oldList.get(j));
                            updateMap.put("newValue", newList.get(i));
                            updateSet.add(updateMap);
                        }
                        //dependencySkuId，那么可能更新，也可能为原值（不更新），那么就不是删除操作，记录本次的数据
                        //记录被更新的数据
                        tempSet.add(oldList.get(j));
                    }
                }
                if (flag) {
                    insertSet.add(newList.get(i));
                }
            }
        }
        deleteSet.removeAll(tempSet); //旧值中去掉更新（或者原值更新）的，剩下就是删除的

        try {
            if (oldList == null || oldList.size() == 0) {
                //全是新增
                for (Map map : newList) {
                    String addValue = "【产品组合】新增:“" + map.get("dependencySkuId") + "”，数量：“" + map.get("quantity") + "”";
                    productModifyHistoryService.historyUpdateList(skuId, createUser, "", map.toString(), time, addValue, true);
                }
            } else {
                //新增
                for (Map map : insertSet) {
                    String addValue = "【产品组合】新增: “" + map.get("dependencySkuId") + "”，数量：“" + map.get("quantity") + "”";
                    productModifyHistoryService.historyUpdateList(skuId, createUser, "", map.toString(), time, addValue, true);
                }
                //删除
                for (Map map : deleteSet) {
                    String delValue = "【产品组合】移除：“" + map.get("dependencySkuId") + "”，数量：“" + map.get("quantity") + "”";
                    productModifyHistoryService.historyUpdateList(skuId, createUser, map.toString(), "", time, delValue, true);
                }
                //更新
                for (Map<String, Map> map : updateSet) {
                    String updateValue = "【产品组合】" + map.get("oldValue").get("dependencySkuId") + "数量由“" + map.get("oldValue").get("quantity") + "”改为“" + map.get("newValue").get("quantity") + "”";
                    productModifyHistoryService.historyUpdateList(skuId, createUser, map.get("oldValue").toString(), map.get("newValue").toString(), time, updateValue, true);
                }
            }
        } catch (Exception e) {
            logger.error("保存历史纪录出错！", e);
            throw new RuntimeException("保存历史纪录出错");
        }
    }

    //包材数组
    @Override
    public void updateListNewThree(List<ProductMachineInfoEntity> list, String time, String skuId, String createUser) {
        Map paraMap = new HashMap();
        paraMap.put("skuId", skuId);
        paraMap.put("typeCd", 2);
        List<Map> oldList = productMachineInfoDao.getMachine(paraMap);

        //删除之前的加工信息
        productMachineInfoDao.deleteType(paraMap);
        if (list == null || list.size() == 0) {
            //保存删除删除信息
            for (Map map : oldList) {
                String delValue = "【包材组合】移除：“" + map.get("dependencySkuId") + "”，数量：“" + map.get("quantity") + "”";
                productModifyHistoryService.historyUpdateList(skuId, createUser, map.toString(), "", time, delValue, true);
            }
            return;
        }

        List<Map<String, Object>> newList = new ArrayList<>();
        //重新插入本次加工信息
        ProductMachineInfoEntity entity = null;
        for (int i = 0; i < list.size(); i++) {
            entity = list.get(i);
            entity.setUpdateTime(time);
            String dependencySkuId = entity.getDependencySkuId();
            if (dependencySkuId != null && !dependencySkuId.trim().isEmpty()) {

                insert(entity);
                Map<String, Object> map = new HashMap<>();
                map.put("dependencySkuId", entity.getDependencySkuId());
                map.put("quantity", entity.getQuantity());
                newList.add(map);
            }
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
                String dependencySkuId = (String) newList.get(i).get("dependencySkuId");
                Integer quantity = (Integer) newList.get(i).get("quantity");
                for (int j = 0; j < oldList.size(); j++) {
                    deleteSet.add(oldList.get(j));
                    //dependencySkuId相同，判断数量quantity
                    if (oldList.get(j).get("dependencySkuId").equals(dependencySkuId)) {
                        //说明不是新增的数据
                        flag = false;
                        if (oldList.get(j).get("quantity") != quantity) { //不同为更新
                            Map<String, Map> updateMap = new HashMap();
                            updateMap.put("oldValue", oldList.get(j));
                            updateMap.put("newValue", newList.get(i));
                            updateSet.add(updateMap);
                        }
                        //dependencySkuId，那么可能更新，也可能为原值（不更新），那么就不是删除操作，记录本次的数据
                        //记录被更新的数据
                        tempSet.add(oldList.get(j));
                    }
                }
                if (flag) {
                    insertSet.add(newList.get(i));
                }
            }
        }
        deleteSet.removeAll(tempSet); //旧值中去掉更新（或者原值更新）的，剩下就是删除的

        try {
            if (oldList == null || oldList.size() == 0) {
                //全是新增
                for (Map map : newList) {
                    String addValue = "【包材组合】新增:“" + map.get("dependencySkuId") + "”，数量：“" + map.get("quantity") + "”";
                    productModifyHistoryService.historyUpdateList(skuId, createUser, "", map.toString(), time, addValue, true);
                }
            } else {
                //新增
                for (Map map : insertSet) {
                    String addValue = "【包材组合】新增: “" + map.get("dependencySkuId") + "”，数量：“" + map.get("quantity") + "”";
                    productModifyHistoryService.historyUpdateList(skuId, createUser, "", map.toString(), time, addValue, true);
                }
                //删除
                for (Map map : deleteSet) {
                    String delValue = "【包材组合】移除：“" + map.get("dependencySkuId") + "”，数量：“" + map.get("quantity") + "”";
                    productModifyHistoryService.historyUpdateList(skuId, createUser, map.toString(), "", time, delValue, true);
                }
                //更新
                for (Map<String, Map> map : updateSet) {
                    String updateValue = "【包材组合】" + map.get("oldValue").get("dependencySkuId") + "数量由“" + map.get("oldValue").get("quantity") + "”改为“" + map.get("newValue").get("quantity") + "”";
                    productModifyHistoryService.historyUpdateList(skuId, createUser, map.get("oldValue").toString(), map.get("newValue").toString(), time, updateValue, true);
                }
            }
        } catch (Exception e) {
            logger.error("保存历史纪录出错！", e);
            throw new RuntimeException("保存历史纪录出错");
        }
    }

    @Override
    public List<ProductMachineInfoEntity> findPMIEntity(ProductMachineInfoEntity pMIEntity) {
        return productMachineInfoDao.findPMIEntity(pMIEntity);
    }

    @Override
    public Result getPackingMaterial(String skuId) {
        Integer packingFlag = productMachineInfoDao.isNeedPackingMaterial(skuId);
        Map map = new HashMap();
        map.put("packingFlag", packingFlag);
        if (packingFlag == null || packingFlag != 1) {
            map.put("needPackingMaterial", false);
            map.put("packingFlagMsg", "不需要包材");
            map.put("packingMaterialList", new ArrayList<>());
            return Result.success(map);
        }
        map.put("needPackingMaterial", true);
        map.put("packingFlagMsg", "需要包材");
        ProductMachineInfoEntity entity = new ProductMachineInfoEntity();
        entity.setSkuId(skuId);
        entity.setTypeCd(2);
        List<Map> list = productMachineInfoDao.getPackingMaterial(entity);
        map.put("packingMaterialList", list);
        return Result.success(map);
    }

    @Override
    public Integer getTypeOfProdect(String dependencySkuId) {
        return productMachineInfoDao.getTypeOfProdect(dependencySkuId);
    }

    @Override
    public Result checkWarehouseNum(List<Map> mapList) {
        if (mapList == null || mapList.size() <= 0) {
            return Result.failure(390, "参数内容为空", "");
        }
        String warehouseId = null;
        List<Map> list = null;
        List<Map> parameterMapList = new ArrayList<>();
        Gson gson = new Gson();
        List<Map> mapList1 = new ArrayList<>();
        for (Map map : mapList) {
            String skuId = (String) map.get("skuId");
            Integer number = (Integer) map.get("number");
            warehouseId = (String) map.get("warehouseId");
            Integer packingFlag = productMachineInfoDao.isNeedPackingMaterial(skuId);
            if (packingFlag != null && packingFlag == 1) {
                ProductMachineInfoEntity entity = new ProductMachineInfoEntity();
                entity.setSkuId(skuId);
                entity.setTypeCd(2);
                list = productMachineInfoDao.getPackingMaterial(entity);
                Map<String, Integer> checkMap = new HashMap<>();
                Map<String, Integer> compareMap = new HashMap<>();
                Map<String, String> comMap = new HashMap<>();
                for (Map packMap : list) {
                    String depenSku = (String) packMap.get("dependencySkuId");
                    Integer quantity = (Integer) packMap.get("quantity");
                    Integer needQuantity = quantity * number;
                    Map<String, String> parameterMap = new HashMap();
                    parameterMap.put("skuId", depenSku);
                    parameterMap.put("warehouseId", warehouseId);
                    checkMap.put(depenSku + warehouseId, needQuantity);
                    comMap.put(depenSku + warehouseId, skuId);
                    parameterMapList.add(parameterMap);
                    Map<String, Object> materialMap = new HashMap<>();
                    materialMap.put("skuId", depenSku);
                    materialMap.put("useNum", quantity);
                    materialMap.put("needQuantity", needQuantity);
                    materialMap.put("id", depenSku + warehouseId);
                    materialMap.put("enableStockNum", 0);
                    String name = productMachineInfoDao.getSkuName(depenSku);
                    materialMap.put("skuName", name);
                    mapList1.add(materialMap);
                }
                if (checkMap == null || checkMap.size() <= 0) {
                    continue;
                }
                String str = gson.toJson(parameterMapList);
                Result result = warehouseInfoService.getWarehouseStock(str);
                if (result.getCode() != 200) {
                    return result;
                }
                List<Map> wareList = (List<Map>) result.getData();
                if (wareList == null || wareList.size() <= 0) {
                    Map resultMap = new HashMap();
                    resultMap.put("flag", false);
                    resultMap.put("material", mapList1);
                    return Result.failure(391, "库存不足", resultMap);
                }
                for (Map wareMap : wareList) {
                    if (wareMap == null) {
                        Map resultMap = new HashMap();
                        resultMap.put("flag", false);
                        resultMap.put("material", mapList1);
                        return Result.failure(391, "库存不足", resultMap);
                    }
                    String sku = (String) wareMap.get("skuId");
                    String warehouse = (String) wareMap.get("warehouseId");
                    Integer enableStockNum = (Integer) wareMap.get("enableStockNum");
                    if (enableStockNum == null) {
                        enableStockNum = 0;
                    }
                    compareMap.put(sku + warehouse, enableStockNum);
                }
                for (Map map1 : mapList1) {
                    String sku = (String) map1.get("skuId");
                    Integer eableNum = compareMap.get(sku + warehouseId);
                    map1.put("enableStockNum", eableNum);
                }
                for (String id : checkMap.keySet()) {
                    if (compareMap.get(id) == null) {
                        Map resultMap = new HashMap();
                        resultMap.put("flag", false);
                        resultMap.put("skuId", skuId);
                        resultMap.put("material", mapList1);
                        return Result.failure(391, "库存不足", resultMap);
                    }
                    if (checkMap.get(id) > compareMap.get(id)) {
                        Map resultMap = new HashMap();
                        resultMap.put("flag", false);
                        resultMap.put("skuId", skuId);
                        resultMap.put("material", mapList1);
                        return Result.failure(391, "库存不足", resultMap);
                    }
                }
            }
        }
        Map resultMap = new HashMap();
        resultMap.put("flag", true);
        return Result.success(resultMap);
    }

    @Override
    public Map<String, List> getAllPackingMaterial() {
        Map<String,List> map=new HashMap<>();
        List<String> skuIds=productMachineInfoDao.getAllNeedPacking();
        if(skuIds==null||skuIds.size()<=0){
            return null;
        }
        for(String skuId:skuIds){
            ProductMachineInfoEntity entity = new ProductMachineInfoEntity();
            entity.setSkuId(skuId);
            entity.setTypeCd(2);
            List<Map> list = productMachineInfoDao.getPackingMaterial(entity);
            map.put(skuId,list);
        }
        return map;
    }

}
