package com.wisrc.product.webapp.service.impl;

import com.wisrc.product.webapp.dao.ProductDeclareLabelAttrDao;
import com.wisrc.product.webapp.entity.ProductDeclareLabelAttrEntity;
import com.wisrc.product.webapp.service.ProductDeclareLabelAttrService;
import com.wisrc.product.webapp.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ProductDeclareLabelAttrImplService implements ProductDeclareLabelAttrService {
    private final Logger logger = LoggerFactory.getLogger(ProductDeclareLabelAttrImplService.class);
    @Autowired
    private ProductDeclareLabelAttrDao dao;

    @Override
    public Result findAll() {
        try {
            List<LinkedHashMap> list = dao.findAll();
            return Result.success(list);
        } catch (Exception e) {
            return new Result(9999, "查询失败！", e);
        }

    }

    @Override
    public Result add(ProductDeclareLabelAttrEntity entity) {
        try {
            dao.add(entity);
            return Result.success();
        } catch (DuplicateKeyException e) {
            return new Result(9999, "该标签已经存在，无法新增", null);
        }
    }

    @Override
    public Result update(ProductDeclareLabelAttrEntity entity) {
        dao.update(entity);
        return Result.success();
    }

    @Override
    public ProductDeclareLabelAttrEntity findByLabelCd(int labelCd) {
        return dao.findByLabelCd(labelCd);

    }

    @Override
    public Result getBasic() {
        List<LinkedHashMap> list = dao.getBasic();
        return Result.success(list);
    }
}
