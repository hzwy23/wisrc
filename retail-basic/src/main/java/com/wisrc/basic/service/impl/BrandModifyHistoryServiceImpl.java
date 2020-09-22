package com.wisrc.basic.service.impl;

import com.wisrc.basic.dao.BrandModifyHistoryDao;
import com.wisrc.basic.entity.BrandInfoEntity;
import com.wisrc.basic.entity.BrandModifyHistoryEntity;
import com.wisrc.basic.service.BrandModifyHistoryService;
import com.wisrc.basic.service.UserService;
import com.wisrc.basic.utils.Column;
import com.wisrc.basic.utils.Result;
import com.wisrc.basic.utils.Time;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class BrandModifyHistoryServiceImpl implements BrandModifyHistoryService {
    private final Logger logger = LoggerFactory.getLogger(BrandModifyHistoryServiceImpl.class);
    @Autowired
    private BrandModifyHistoryDao brandModifyHistoryDao;

    @Autowired
    private UserService userService;

    @Override
    public Result findByBrandId(BrandModifyHistoryEntity entity) {
        List<Map> list = brandModifyHistoryDao.findByBrandId(entity);
        if (list == null || list.size() == 0) {
            return Result.success(list);
        }

        //账户ID与账户name匹配
        Map userMap;
        try {
            userMap = getUserMap(list);
        } catch (Exception e) {
            logger.info("账号接口发生错误", e);
            return new Result(9995, "账号接口发生错误", e);
        }

        //状态值
        Map brandStatusMap = getBrandStatusMap();
        //品牌类型
        Map brandTypeMap = getBrandTypeMap();
        //操作类型
        Map handleTypeMap = new HashMap();
        handleTypeMap.put(1, "新增");
        handleTypeMap.put(2, "变更");

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

                //操作类型
                int handleTypeCd = (int) list.get(i).get("handleTypeCd");
                batch.put("handleTypeCd", handleTypeCd);
                batch.put("handleTypeDesc", handleTypeMap.get(handleTypeCd));
                //用户
                batch.put("modifyUserId", list.get(i).get("modifyUserId"));
                batch.put("modifyUserName", userMap.get(list.get(i).get("modifyUserId")));
                //将 品牌状态值 转为 品牌状态描述
                if (list.get(i).get("columnName").equals("状态")) {
                    list.get(i).put("oldValue", brandStatusMap.get(list.get(i).get("oldValue")));
                    list.get(i).put("newValue", brandStatusMap.get(list.get(i).get("newValue")));
                }
                //将 品牌类型编码 转为 品牌类型描述
                if (list.get(i).get("columnName").equals("品牌类型")) {
                    list.get(i).put("oldValue", brandTypeMap.get(list.get(i).get("oldValue")));
                    list.get(i).put("newValue", brandTypeMap.get(list.get(i).get("newValue")));
                }

                //判断修改字段是否为null
                if (list.get(i).get("columnName") == null) {
                    list.get(i).put("columnName", "一个未定义的字段，请联系管理员");
                }
                if (checkMap(list.get(i))) {
                    batchList.add(solveMap(list.get(i)));
                    batch.put("modify", batchList);
                }
            } else {  //时间批次相同
                //将 品牌状态值 转为 品牌状态描述
                if (list.get(i).get("columnName").equals("状态")) {
                    list.get(i).put("oldValue", brandStatusMap.get(list.get(i).get("oldValue")));
                    list.get(i).put("newValue", brandStatusMap.get(list.get(i).get("newValue")));
                }
                //将 品牌类型编码 转为 品牌类型描述
                if (list.get(i).get("columnName").equals("品牌类型")) {
                    list.get(i).put("oldValue", brandTypeMap.get(list.get(i).get("oldValue")));
                    list.get(i).put("newValue", brandTypeMap.get(list.get(i).get("newValue")));
                }

                //如果Map中不存在（重复值）录入集合中
                //判断修改字段是否为null
                if (list.get(i).get("columnName") == null) {
                    list.get(i).put("columnName", "一个未定义的字段，请联系管理员");
                }
                if (checkMap(list.get(i))) {
                    batchList.add(solveMap(list.get(i)));
                    batch.put("modify", batchList);
                }

            }
            //如果该记录是最后一条，置入集合中
            if (i == (list.size() - 1)) {
                resultList.add(batch);
            }
        }

        return Result.success(resultList);
    }

    private Map getBrandTypeMap() {
        List<Map> list = brandModifyHistoryDao.getBrandType();
        Map resultMap = new HashMap();
        for (Map o : list) {
            resultMap.put(o.get("brandType") + "", o.get("brandTypeDesc"));
        }
        return resultMap;
    }

    private Map getBrandStatusMap() {
        List<Map> list = brandModifyHistoryDao.getBrandStatus();
        Map resultMap = new HashMap();
        for (Map o : list) {
            resultMap.put(o.get("statusCd") + "", o.get("statusDesc"));
        }
        return resultMap;
    }

    private Map getUserMap(List<Map> list) throws Exception {
        List<String> userIdList = new ArrayList<>();
        for (Map o : list) {
            userIdList.add((String) o.get("modifyUserId"));
        }
        Map userMap = userService.getUserMap(userIdList);
        return userMap;
    }

    @Override
    public void historyUpdate(Object oldObj, Object newObj, String updateTime) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        Map<String, String> oldMap = BeanUtils.describe(oldObj);
        Map<String, String> newMap = BeanUtils.describe(newObj);
        // entrySet 获取key and value
        for (Map.Entry<String, String> entry : oldMap.entrySet()) {
            String key = entry.getKey();
            String oldValue = entry.getValue();
            String newVvalue = newMap.get(key);
            if (oldValue == null) {
                oldValue = "";
            }
            if (newVvalue == null) {
                newVvalue = "";
            }
            if (!oldValue.equals(newVvalue)) {
                BrandModifyHistoryEntity entity = new BrandModifyHistoryEntity();
                if (!key.equals("class")) {
                    //字段的中文意义
                    String ChineseMeaning = Column.get(key);
                    //如果没有找到对应中文含义，就直接用原文
                    if (ChineseMeaning == null) {
                        ChineseMeaning = key;
                    }
                    entity.setColumnName(ChineseMeaning);
                    String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                    entity.setUuid(uuid);
                    entity.setBrandId(newMap.get("brandId"));
                    entity.setOldValue(oldValue);
                    entity.setNewValue(newVvalue);
                    entity.setModifyUser(newMap.get("modifyUser"));
                    //缺少 用户名字
                    entity.setModifyTime(updateTime);
                    entity.setHandleTypeCd(2);
                    brandModifyHistoryDao.insert(entity);
                }
            }
        }
    }

    @Override
    public void historyInsert(BrandInfoEntity brandInfoEntity, String createTime) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String, String> map = BeanUtils.describe(brandInfoEntity);
        BrandModifyHistoryEntity entity = new BrandModifyHistoryEntity();
        entity.setColumnName("新增");
        entity.setModifyTime(createTime);
        entity.setModifyUser(map.get("createUser"));
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        entity.setUuid(uuid);
        entity.setBrandId(map.get("brandId"));
        entity.setHandleTypeCd(1);
        brandModifyHistoryDao.insert(entity);
    }

    /**
     * 判断map的数据,存在特定值返回boolean值
     *
     * @param map
     * @return
     */
    public boolean checkMap(Map map) {
        if (map.get("columnName").equals("更新时间")) {
            return false;
        }
        if (map.get("columnName").equals("更新人")) {
            return false;
        }
        if (map.get("columnName").equals("uuid唯一标识符")) {
            return false;
        }
        if (map.get("columnName").equals("创建时间")) {
            return false;
        }
        if (map.get("columnName").equals("SKU")) {
            return false;
        }
        return true;
    }

    public Map solveMap(Map map) {
        map.remove("modifyUser");
        map.remove("modifyTime");
        return map;
    }

}
