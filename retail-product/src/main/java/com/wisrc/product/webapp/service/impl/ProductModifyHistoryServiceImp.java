package com.wisrc.product.webapp.service.impl;

import com.wisrc.product.webapp.dao.ProductModifyHistoryDao;
import com.wisrc.product.webapp.entity.ProductModifyHistoryEntity;
import com.wisrc.product.webapp.entity.ProductSalesStatusCdAttr;
import com.wisrc.product.webapp.service.EmployeeService;
import com.wisrc.product.webapp.service.ProductModifyHistoryService;
import com.wisrc.product.webapp.service.ProductService;
import com.wisrc.product.webapp.service.UserService;
import com.wisrc.product.webapp.utils.Column;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.utils.Time;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class ProductModifyHistoryServiceImp implements ProductModifyHistoryService {
    private static final Map typeMap;
    private static final Map statusCdMap;
    private static final Map handleTypeMap;
    //新增类型
    private static int insertTypeCd = 1;
    //更新类型
    private static int updateTypeCd = 2;
    //集合更新类型，便于前端区分
    private static int listUpdateTypeCd = 5;

    static {
        //类型
        Map aMap = new HashMap();
        aMap.put("1", "产品");
        aMap.put("2", "包材");
        //内容设为不可变
        typeMap = Collections.unmodifiableMap(aMap);

        //产品状态
        Map bMap = new HashMap();
        bMap.put("1", "启用");
        bMap.put("2", "禁用");
        statusCdMap = Collections.unmodifiableMap(bMap);

        //操作类型
        Map cMap = new HashMap();
        cMap.put(1, "新增");
        cMap.put(2, "变更");
        handleTypeMap = Collections.unmodifiableMap(cMap);
    }

    private final Logger logger = LoggerFactory.getLogger(ProductModifyHistoryServiceImp.class);
    @Autowired
    private ProductModifyHistoryDao productModifyHistoryDao;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    public void insert(ProductModifyHistoryEntity ele) {
        productModifyHistoryDao.insert(ele);
    }

    @Override
    public Result getHistory(String skuId) {

        if (skuId == null || skuId.trim().isEmpty()) {
            return new Result(9999, "skuId不能为空", null);
        }
        Map<String, String> map = new HashMap<>();
        map.put("skuId", skuId);
        List<Map> list = productModifyHistoryDao.getHistory(map);
        if (list == null || list.size() == 0) {
            return Result.success(list);
        }


        // todo  用户信息 还是 员工？？？
        Map userMap = null;
        try {
            userMap = getUserMap(list);
        } catch (Exception e) {
            logger.info("用户接口发生错误", e);
        }


        //销售状态
        Map salesStatusCdAttrMap = getSalesStatusCdAttrMap();

        List batchList = new ArrayList();
        List<Map> resultList = new ArrayList<Map>();
        Map<String, Object> batch = null;
        //更新时间临时量
        String tempModifyTime = "4B00F9959675925E";
        //根据更新时间分批
        for (int i = 0; i < list.size(); i++) {

            String modifyTime = Time.dateToStr((Timestamp) list.get(i).get("modifyTime"));
            //时间批次不同
            if (!modifyTime.equals(tempModifyTime)) {
                if (i != 0) {
                    resultList.add(batch);
                }
                batch = new HashMap<>();
                batchList = new ArrayList();
                batch.put("modifyTime", modifyTime);
                tempModifyTime = modifyTime;

                String modifyUserId = (String) list.get(i).get("modifyUser");
                batch.put("modifyUserId", modifyUserId);
                if (userMap == null) {
                    //用户接口出错，名字就用ID代替
                    batch.put("modifyUserName", modifyUserId);
                } else {
                    String modifyUserName = (String) userMap.get(list.get(i).get("modifyUser"));
                    if (modifyUserName == null) {
                        batch.put("modifyUserName", modifyUserId);
                    } else {
                        batch.put("modifyUserName", modifyUserName);
                    }
                }


                //判断修改字段是否为null
                if (list.get(i).get("modifyColumn") == null) {
                    list.get(i).put("modifyColumn", "一个未定义的字段，请联系管理员");
                } else {
                    //操作类型
                    int handleTypeCd = (int) list.get(i).get("handleTypeCd");
                    batch.put("handleTypeCd", handleTypeCd);
                    batch.put("handleTypeDesc", handleTypeMap.get(handleTypeCd));
                    if (("类型").equals(list.get(i).get("modifyColumn"))) {
                        list.get(i).put("oldValue", typeMap.get(list.get(i).get("oldValue")));
                        list.get(i).put("newValue", typeMap.get(list.get(i).get("newValue")));
                    }
                    //状态
                    if (("产品状态码").equals(list.get(i).get("modifyColumn"))) {
                        list.get(i).put("modifyColumn", "产品状态");
                        list.get(i).put("oldValue", statusCdMap.get(list.get(i).get("oldValue")));
                        list.get(i).put("newValue", statusCdMap.get(list.get(i).get("newValue")));
                    }
                    //销售状态
                    if (("销售状态编码").equals(list.get(i).get("modifyColumn"))) {
                        list.get(i).put("modifyColumn", "销售状态");
                        list.get(i).put("oldValue", salesStatusCdAttrMap.get(list.get(i).get("oldValue")));
                        list.get(i).put("newValue", salesStatusCdAttrMap.get(list.get(i).get("newValue")));
                    }
                }
                //如果Map中不存在（重复值）录入集合中

                if (checkMap(list.get(i))) {
                    batchList.add(solveMap(list.get(i)));
                }
                batch.put("modifyColumn", batchList);
            } else {  //时间批次相同

                //如果Map中不存在（重复值）录入集合中
                //判断修改字段是否为null
                if (list.get(i).get("modifyColumn") == null) {
                    list.get(i).put("modifyColumn", "一个未定义的字段，请联系管理员");
                } else {
                    //操作类型
                    int handleTypeCd = (int) list.get(i).get("handleTypeCd");
                    batch.put("handleTypeCd", handleTypeCd);
                    batch.put("handleTypeDesc", handleTypeMap.get(handleTypeCd));
                    if (("类型").equals(list.get(i).get("modifyColumn"))) {
                        list.get(i).put("oldValue", typeMap.get(list.get(i).get("oldValue")));
                        list.get(i).put("newValue", typeMap.get(list.get(i).get("newValue")));
                    }
                    //状态
                    if (("产品状态码").equals(list.get(i).get("modifyColumn"))) {
                        list.get(i).put("modifyColumn", "产品状态");
                        list.get(i).put("oldValue", statusCdMap.get(list.get(i).get("oldValue")));
                        list.get(i).put("newValue", statusCdMap.get(list.get(i).get("newValue")));
                    }
                    //销售状态
                    if (("销售状态编码").equals(list.get(i).get("modifyColumn"))) {
                        list.get(i).put("modifyColumn", "销售状态");
                        list.get(i).put("oldValue", salesStatusCdAttrMap.get(list.get(i).get("oldValue")));
                        list.get(i).put("newValue", salesStatusCdAttrMap.get(list.get(i).get("newValue")));
                    }
                }

                if (checkMap(list.get(i))) {
                    batchList.add(solveMap(list.get(i)));
                }
                batch.put("modifyColumn", batchList);
            }
            //如果该记录是最后一条，置入集合中
            if (i == (list.size() - 1)) {
                resultList.add(batch);
            }
        }
        return Result.success(resultList);
    }


    //销售状态
    private Map getSalesStatusCdAttrMap() {
        Map<String, Object> salesStatusCdAttrMap = new HashMap();
        List<ProductSalesStatusCdAttr> salesStatusCdAttrList = productModifyHistoryDao.getSalesAttr();
        for (ProductSalesStatusCdAttr o : salesStatusCdAttrList) {
            salesStatusCdAttrMap.put(o.getSalesStatusCd() + "", o.getSalesStatusDesc());
        }
        return salesStatusCdAttrMap;
    }

    private Map getUserMap(List<Map> list) throws Exception {
        List<String> idList = new ArrayList<>();
        for (Map o : list) {
            idList.add((String) o.get("modifyUser"));
        }
        Map employeeNameMap = userService.getUserMap(idList);
        return employeeNameMap;
    }

    /**
     * 判断map的数据,存在特定值返回boolean值
     *
     * @param map
     * @return
     */
    public boolean checkMap(Map map) {
        if (map.get("modifyColumn").equals("更新时间")) {
            return false;
        }
        if (map.get("modifyColumn").equals("更新人")) {
            return false;
        }
        if (map.get("modifyColumn").equals("uuid唯一标识符")) {
            return false;
        }
        if (map.get("modifyColumn").equals("创建时间")) {
            return false;
        }
        if (map.get("modifyColumn").equals("SKU")) {
            return false;
        }
        return true;
    }

    public Map solveMap(Map map) {
        map.remove("modifyUser");
        map.remove("modifyTime");
        return map;
    }

    @Override
    public void historyInsert(Object object, String createTime) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String, String> map = BeanUtils.describe(object);
        ProductModifyHistoryEntity productModifyHistoryEntity = new ProductModifyHistoryEntity();
        productModifyHistoryEntity.setModifyColumn("新增");
        productModifyHistoryEntity.setModifyTime(createTime);
        productModifyHistoryEntity.setModifyUser(map.get("createUser"));
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        productModifyHistoryEntity.setUuid(uuid);
        productModifyHistoryEntity.setSkuId(map.get("skuId"));
        productModifyHistoryEntity.setHandleTypeCd(insertTypeCd);
        productModifyHistoryDao.insert(productModifyHistoryEntity);
    }

    /**
     * @param oldObj
     * @param newObj
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    @Override
    public void historyUpdate(Object oldObj, Object newObj, String updateTime, String modifyUserId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        Map<String, String> oldMap = BeanUtils.describe(oldObj);
        Map<String, String> newMap = BeanUtils.describe(newObj);
        // entrySet 获取key and value
        for (Map.Entry<String, String> entry : newMap.entrySet()) {
            String key = entry.getKey();
            String oldValue = oldMap.get(key);
            String newVvalue = newMap.get(key);
            if (oldValue == null) {
                oldValue = "";
            }
            if (newVvalue == null) {
                newVvalue = "";
            }
            if (!oldValue.equals(newVvalue)) {
                ProductModifyHistoryEntity productModifyHistoryEntity = new ProductModifyHistoryEntity();
                if (!key.equals("class")) {
                    //字段的中文意义
                    String ChineseMeaning = Column.get(key);
                    //如果没有找到对应中文含义，就直接用原文
                    if (ChineseMeaning == null) {
                        ChineseMeaning = key;
                    }
                    productModifyHistoryEntity.setModifyColumn(ChineseMeaning);
                    productModifyHistoryEntity.getHandleTypeCd();
                    productModifyHistoryEntity.setModifyTime(updateTime);
                    productModifyHistoryEntity.setModifyUser(modifyUserId);
                    productModifyHistoryEntity.setOldValue(oldValue);
                    productModifyHistoryEntity.setNewValue(newVvalue);
                    String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                    productModifyHistoryEntity.setUuid(uuid);
                    productModifyHistoryEntity.setSkuId(newMap.get("skuId"));
                    productModifyHistoryEntity.setHandleTypeCd(updateTypeCd);
                    productModifyHistoryDao.insert(productModifyHistoryEntity);
                }
            }
        }
    }

    @Override
    public void deleteBySkuId(String skuId) {
        productModifyHistoryDao.deleteBySkuId(skuId);
    }

    /**
     * @param skuId
     * @param createUser
     * @param oldValue
     * @param newValue
     * @param time
     * @param column
     * @param b
     */
    @Override
    public void historyUpdateList(String skuId, String createUser, String oldValue, String newValue, String time, String column, boolean b) {
        ProductModifyHistoryEntity entity = new ProductModifyHistoryEntity();
        entity.setSkuId(skuId);
        entity.setModifyUser(createUser);
        entity.setModifyColumn(column);
        entity.setModifyTime(time);
        entity.setOldValue(oldValue);
        entity.setNewValue(newValue);
        if (b != true) {
            //普通更新
            entity.setHandleTypeCd(updateTypeCd);
        } else {
            //集合删除更新
            entity.setHandleTypeCd(listUpdateTypeCd);
        }
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        entity.setUuid(uuid);
        productModifyHistoryDao.insert(entity);
    }


}
