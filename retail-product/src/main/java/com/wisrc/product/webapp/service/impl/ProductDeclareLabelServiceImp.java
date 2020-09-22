package com.wisrc.product.webapp.service.impl;

import com.wisrc.product.webapp.dao.ProductDeclareLabelDao;
import com.wisrc.product.webapp.entity.CustomLabelEntity;
import com.wisrc.product.webapp.entity.NewProductDeclareLabelEntity;
import com.wisrc.product.webapp.entity.ProductDeclareLabelAttrEntity;
import com.wisrc.product.webapp.service.ProductDeclareLabelService;
import com.wisrc.product.webapp.service.ProductModifyHistoryService;
import com.wisrc.product.webapp.utils.MD5;
import com.wisrc.product.webapp.utils.UUIDutil;
import com.wisrc.product.webapp.vo.declareLabel.set.SetDeclareLabelVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductDeclareLabelServiceImp implements ProductDeclareLabelService {
    private final Logger logger = LoggerFactory.getLogger(ProductDeclareLabelServiceImp.class);
    @Autowired
    private ProductDeclareLabelDao productDeclareLabelDao;
    @Autowired
    private ProductModifyHistoryService productModifyHistoryService;

    @Override
    public void deleteBySkuId(String skuId) {
        productDeclareLabelDao.deleteBySkuId(skuId);
    }



    @Override
    public Map<String, Object> getBySkuId(String skuId) {
        List<ProductDeclareLabelAttrEntity> lableAttrList = getAllLableAttr();
        Map<Integer, String> lableAttrMap = new LinkedHashMap<>();
        for (ProductDeclareLabelAttrEntity o : lableAttrList) {
            lableAttrMap.put(o.getLabelCd(), o.getLabelDesc());
        }
        List<Integer> choseList = choseLabelList(skuId);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("label", lableAttrMap);
        result.put("chose", choseList);
        return result;
    }

    @Override
    public List<ProductDeclareLabelAttrEntity> getAllLableAttr() {
        List<ProductDeclareLabelAttrEntity> lableAttrList = productDeclareLabelDao.getAllLableAttr();
        return lableAttrList;
    }


    @Override
    public void updateLabelList(List<SetDeclareLabelVO> declareLabelList, String time, String skuId, String userId) {
        //上次的旧值
        //sql 按 label_cd ASC 排序
        //获取基础标签
        List<LinkedHashMap> oldBasicsLableList = getBasicsLableBySkuId(skuId);
        //获取自定义标签
        List<LinkedHashMap> oldCustomLableList = getCustomLableBySkuId(skuId);

        String oldDescValue = oldDescValue(oldBasicsLableList, oldCustomLableList);
        String newDescValue = "";

        //删除旧的数据
        productDeclareLabelDao.deleteBySkuId(skuId);

        //重新新增本次的标签
        if (declareLabelList != null) {
            NewProductDeclareLabelEntity nPDLEntity = new NewProductDeclareLabelEntity();
            nPDLEntity.setSkuId(skuId);
            CustomLabelEntity customLabelEntity = new CustomLabelEntity();
            customLabelEntity.setSkuId(skuId);
            for (SetDeclareLabelVO o : declareLabelList) {
                if (o.getTypeCd() != null && o.getTypeCd().equals(1)) {
                    BeanUtils.copyProperties(o, nPDLEntity);
                    insertLabel(nPDLEntity);
                } else if (o.getTypeCd() != null && o.getTypeCd().equals(2)) {
                    BeanUtils.copyProperties(o, customLabelEntity);
                    insertCustomLabel(customLabelEntity);
                }

            }
        }

        //本次的新值 将本次标签集合的描述组合拼接作为新值存入历史表
        //将本次的标签描述拼接组合
        newDescValue = newDescValue(declareLabelList);

        if (!oldDescValue.equals(newDescValue)) {
            try {
                String column = "特性标签";
                productModifyHistoryService.historyUpdateList(skuId, userId, oldDescValue, newDescValue, time, column, false);
            } catch (Exception e) {
                logger.error("保存历史纪录出错！", e);
                throw new RuntimeException("保存历史纪录出错");
            }
        }

    }

    @Override
    public List<LinkedHashMap> getBasicsLableBySkuId(String skuId) {
        return productDeclareLabelDao.getBasicsLableBySkuId(skuId);
    }

    @Override
    public List<LinkedHashMap> getCustomLableBySkuId(String skuId) {
        return productDeclareLabelDao.getCustomLableBySkuId(skuId);
    }

    @Override
    public List<LinkedHashMap> newFindBySkuId(String skuId) {
        List<LinkedHashMap> customLabelList = productDeclareLabelDao.newFindBySkuId(skuId);
        return customLabelList;
    }


    @Override
    public void insertLabel(NewProductDeclareLabelEntity nPDLEntity) {
        //如果labelCd 为null
        if (nPDLEntity.getLabelCd() == null) {
            return;
        } else {
            //uuid主键
            String uuid = MD5.encrypt32(MD5.encrypt32(nPDLEntity.getSkuId()) + MD5.encrypt32(nPDLEntity.getLabelCd() + ""));
            nPDLEntity.setUuid(uuid);
            try {
                productDeclareLabelDao.insertLabel(nPDLEntity);
            } catch (DuplicateKeyException e) {
                //重复新增不做处理 （同一skuId与LabelCd的新增不做处理）
            }
        }
    }

    @Override
    public List<NewProductDeclareLabelEntity> getNPDLEList(String skuId) {
        List<NewProductDeclareLabelEntity> list = productDeclareLabelDao.getNPDLEList(skuId);
        return list;
    }

    @Override
    public void insertCustomLabel(CustomLabelEntity customLabelEntity) {
        //先去标签码表新增码表，利用自增长获取唯一编码
        customLabelEntity.setLabelCd(null);
        productDeclareLabelDao.insertCustomLabelEntityInAttr(customLabelEntity);
        //uuid主键
        customLabelEntity.setUuid(UUIDutil.randomUUID());
        try {
            productDeclareLabelDao.insertCustomLabel(customLabelEntity);
        } catch (DuplicateKeyException e) {
            //重复新增不做处理 （同一skuId与LabelCd的新增不做处理）
        }
    }


    @Override
    public List<Integer> choseLabelList(String skuId) {
        List<Integer> choseList = productDeclareLabelDao.getChoseBySkuId(skuId);
        return choseList;
    }


    private String oldDescValue(List<LinkedHashMap> oldBasicsLableList, List<LinkedHashMap> oldCustomLableList) {
        StringBuffer sb = new StringBuffer();
        for (LinkedHashMap o : oldBasicsLableList) {
            sb.append(o.get("labelDesc") + ",");
        }
        for (LinkedHashMap o : oldCustomLableList) {
            sb.append(o.get("labelDesc") + ",");
        }
        //去除最后一个的逗号 ","
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


    private String newDescValue(List<SetDeclareLabelVO> declareLabelList) {
        StringBuffer sb = new StringBuffer();
        if (declareLabelList != null) {
            for (SetDeclareLabelVO o : declareLabelList) {
                sb.append(o.getLabelDesc() + ",");
            }
        }
        //去除最后一个的逗号 ","
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
//==============  标签新改 END ====================================================================================================================================================


}
