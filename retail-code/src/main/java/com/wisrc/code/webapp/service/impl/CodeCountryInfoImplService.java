package com.wisrc.code.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.code.webapp.dao.CodeCountryInfoDao;
import com.wisrc.code.webapp.dao.CodeProvinceCityInfoDao;
import com.wisrc.code.webapp.entity.AdministrativeDivisionInfoEntity;
import com.wisrc.code.webapp.entity.CodeCountryInfoEntity;
import com.wisrc.code.webapp.entity.CodeProvinceCityInfoEntity;
import com.wisrc.code.webapp.service.CodeCountryInfoService;
import com.wisrc.code.webapp.utils.PageData;
import com.wisrc.code.webapp.utils.Result;
import com.wisrc.code.webapp.utils.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CodeCountryInfoImplService implements CodeCountryInfoService {
    private final Logger logger = LoggerFactory.getLogger(CodeCountryInfoImplService.class);
    @Autowired
    private CodeCountryInfoDao codeCountryInfoDao;
    @Autowired
    private CodeProvinceCityInfoDao codeProvinceCityInfoDao;

    @Override
    public Result findAll() {
        List<CodeCountryInfoEntity> list = codeCountryInfoDao.findAll();
        return Result.success(list);
    }

    @Override
    public Result update(String countryCd, String countryName, String countryEnglish, String userId) {
        if (countryName == null || countryName.trim().isEmpty()) {
            return new Result(9997, "国家中文名countryName不合法", null);
        }

        CodeCountryInfoEntity entity = new CodeCountryInfoEntity();
        entity.setCountryCd(countryCd);
        entity.setCountryName(countryName);
        entity.setCountryEnglish(countryEnglish);
        String time = Time.getCurrentDateTime();
        entity.setModifyTime(time);
        entity.setModifyUser(userId);
        codeCountryInfoDao.update(entity);
        return Result.success();
    }

    @Override

    public Result insert(String countryCd, String countryName, String countryEnglish, String userId) {
        if (countryCd == null || countryCd.trim().isEmpty()) {
            return new Result(9997, "国家编码countryCd不合法", null);
        }
        if (countryName == null || countryName.trim().isEmpty()) {
            return new Result(9997, "国家中文名countryName不合法", null);
        }

        CodeCountryInfoEntity entity = new CodeCountryInfoEntity();
        entity.setCountryCd(countryCd);
        entity.setCountryName(countryName);
        entity.setCountryEnglish(countryEnglish);
        String time = Time.getCurrentDateTime();
        entity.setCreateTime(time);
        entity.setModifyTime(time);
        entity.setModifyUser(userId);
        entity.setCreateUser(userId);
        try {
            codeCountryInfoDao.insert(entity);
            return Result.success();
        } catch (DuplicateKeyException e) {
            return new Result(9999, "该状态码已存在！无法新建", entity);
        }
    }

    @Override
    public Result delete(String countryCd) {
        codeCountryInfoDao.delete(countryCd);
        return Result.success();
    }

    @Override
    public Result fuzzyQuery(String countryCd, String countryName, String countryEnglish, Integer pageNum, Integer pageSize) {
        List<CodeCountryInfoEntity> resultList;
        CodeCountryInfoEntity entity = new CodeCountryInfoEntity();
        entity.setCountryCd(countryCd);
        entity.setCountryName(countryName);
        entity.setCountryEnglish(countryEnglish);
        if (pageNum == null || pageSize == null || pageNum < 1 || pageSize < 1) {
            resultList = codeCountryInfoDao.fuzzyQuery(entity);
        } else {
            PageHelper.startPage(pageNum, pageSize);
            resultList = codeCountryInfoDao.fuzzyQuery(entity);
        }
        PageInfo pageInfo = new PageInfo(resultList);
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        Map<String, Object> map = new HashMap();
        map.put("total", total);
        map.put("pages", pages);
        map.put("codeCountryInfoList", resultList);
        return Result.success(map);
    }

    @Override
    public LinkedHashMap getAll(String keyword, String countryCd) {
        List<AdministrativeDivisionInfoEntity> list = codeProvinceCityInfoDao.findALl(keyword, countryCd);
        return PageData.pack(-1, -1, "areaList", list);
    }

    @Override
    public LinkedHashMap getAllPagging(int pageNum, int pageSize, String keyword, String countryCd) {
        PageHelper.startPage(pageNum, pageSize);
        List<AdministrativeDivisionInfoEntity> list = codeProvinceCityInfoDao.findALl(keyword, countryCd);
        PageInfo pageInfo = new PageInfo(list);
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "areaList", list);
    }


    /**
     * 新增省市
     */
    @Override
    public Result insert(CodeProvinceCityInfoEntity ele) {
        // 新增省份
        if (ele.getTypeCd() == 1) {
            codeProvinceCityInfoDao.insertProvince(ele);
        } else {
            codeProvinceCityInfoDao.insertCity(ele);
        }
        return Result.success();
    }

    @Override
    public Result update(CodeProvinceCityInfoEntity ele) {
        if (ele.getTypeCd() == 1) {
            codeProvinceCityInfoDao.update(ele);
        } else {
            codeProvinceCityInfoDao.updateCity(ele);
        }
        return Result.success();
    }

    /**
     * 如果这个待删除的省份下边有城市，则不允许删除这个省份
     */
    @Override
    public Result deleteProvinceCode(String ele, int typeCd) {
        if (typeCd == 1) {
            codeProvinceCityInfoDao.deleteProvince(ele);
        } else {
            codeProvinceCityInfoDao.deleteCity(ele);
        }
        return Result.success();
    }

    @Override
    public Result getProvince(String countryCd) {
        return Result.success(codeProvinceCityInfoDao.findProvince(countryCd));
    }
}
