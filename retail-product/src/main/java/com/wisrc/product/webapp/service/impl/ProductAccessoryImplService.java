package com.wisrc.product.webapp.service.impl;

import com.wisrc.product.webapp.dao.ProductAccessoryDao;
import com.wisrc.product.webapp.entity.ProductAccessoryCdAttrEntity;
import com.wisrc.product.webapp.entity.ProductAccessoryEntity;
import com.wisrc.product.webapp.service.ProductAccessoryService;
import com.wisrc.product.webapp.service.ProductModifyHistoryService;
import com.wisrc.product.webapp.utils.MD5;
import com.wisrc.product.webapp.vo.productAccessory.set.SetProductAccessoryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ProductAccessoryImplService implements ProductAccessoryService {
    private final Logger logger = LoggerFactory.getLogger(ProductAccessoryImplService.class);

    @Autowired
    private ProductAccessoryDao productAccessoryDao;
    @Autowired
    private ProductModifyHistoryService productModifyHistoryService;

    @Override
    public void insert(ProductAccessoryEntity pAEntity) {
        //不是自定义标签，并且没有编号，属于无效数据
        if (!pAEntity.getTypeCd().equals(2) && pAEntity.getAccessoryCd() == null) {
            return;
        }
        if (pAEntity.getTypeCd().equals(2)) {
            //先去配件码表新增码表，利用自增长获取唯一编码
            productAccessoryDao.insertCustomAccessory(pAEntity);
            System.out.println(pAEntity.getAccessoryCd());
        }
        //uuid主键
        String uuid = MD5.encrypt32(MD5.encrypt32(pAEntity.getSkuId()) + MD5.encrypt32(pAEntity.getAccessoryCd() + ""));
        pAEntity.setUuid(uuid);
        try {
            productAccessoryDao.insert(pAEntity);
        } catch (DuplicateKeyException e) {
            //重复新增不做处理 （同一skuId与accessoryCd 的新增不做处理）
        }
    }

    @Override
    public void update(List<SetProductAccessoryVO> accessoryVOList, String time, String skuId, String userId) {
        //上次的旧值
        //sql 按 label_cd ASC 排序
        //基础配件
        List<LinkedHashMap> oldBasicList = getBySkuId(skuId);
        //获取自定义配件
        List<LinkedHashMap> oldCustomList = getCustomBySkuId(skuId);

        String oldAccessoryNameValue = oldAccessoryNameValue(oldBasicList, oldCustomList);

        String newAccessoryNameValue = "";

        //删除旧的数据
        productAccessoryDao.deleteBySkuId(skuId);

        //重新插入 配件
        ProductAccessoryEntity pAEntity = new ProductAccessoryEntity();
        pAEntity.setSkuId(skuId);
        if (accessoryVOList != null) {
            for (SetProductAccessoryVO o : accessoryVOList) {
                BeanUtils.copyProperties(o, pAEntity);
                insert(pAEntity);
            }
        }

        //将上次的值的描述拼接组合
        newAccessoryNameValue = newAccessoryNameValue(accessoryVOList);

        //==============添加历史纪录start
        if (!oldAccessoryNameValue.equals(newAccessoryNameValue)) {
            try {
                String column = "包含配件";
                productModifyHistoryService.historyUpdateList(skuId, userId, oldAccessoryNameValue, newAccessoryNameValue, time, column, false);
            } catch (Exception e) {
                logger.error("保存历史纪录出错！", e);
                throw new RuntimeException("保存历史纪录出错");
            }
        }
        //==============添加历史纪录end

    }

    @Override
    public List<ProductAccessoryCdAttrEntity> getAttr() {
        return productAccessoryDao.getAttr();
    }

    @Override
    public List<ProductAccessoryEntity> findBySkuId(String skuId) {
        List<ProductAccessoryEntity> list = productAccessoryDao.findBySkuId(skuId);
        return list;
    }

    @Override
    public List<LinkedHashMap> getCustomBySkuId(String skuId) {
        List<LinkedHashMap> result = productAccessoryDao.getCustomBySkuId(skuId);
        return result;
    }

    @Override
    public List<LinkedHashMap> getBySkuId(String skuId) {
        return productAccessoryDao.getBySkuId(skuId);
    }


    @Override
    public List<LinkedHashMap> getBasicAndCustomBySkuId(String skuId) {
        List<LinkedHashMap> customAccessoryList1 = productAccessoryDao.getBasicAndCustomBySkuId(skuId);
        return customAccessoryList1;
    }

    @Override
    public List<LinkedHashMap> getAll() {
        List<LinkedHashMap> list = productAccessoryDao.getAll();
        return list;
    }

    @Override
    public List<LinkedHashMap> getBasic() {
        List<LinkedHashMap> list = productAccessoryDao.getBasic();
        return list;
    }


    private String newAccessoryNameValue(List<SetProductAccessoryVO> accessoryVOList) {
        StringBuffer sb = new StringBuffer();
        if (accessoryVOList != null) {
            for (SetProductAccessoryVO o : accessoryVOList) {
                sb.append(o.getAccessoryDesc() + ",");
            }
        }
        //去除最后一个的逗号 ","
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


    private String oldAccessoryNameValue(List<LinkedHashMap> oldBasicList, List<LinkedHashMap> oldCustomList) {
        StringBuffer sb = new StringBuffer();
        if (oldBasicList != null) {
            for (LinkedHashMap o : oldBasicList) {
                sb.append(o.get("accessoryName") + ",");
            }
        }
        if (oldCustomList != null) {
            for (LinkedHashMap o : oldCustomList) {
                sb.append(o.get("accessoryDesc") + ",");
            }
        }
        //去除最后一个的逗号 ","
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
