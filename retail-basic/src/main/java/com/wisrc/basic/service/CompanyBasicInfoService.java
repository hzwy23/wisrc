package com.wisrc.basic.service;

import com.wisrc.basic.entity.CompanyBasicInfoEntity;
import com.wisrc.basic.entity.CompanyCustomsInfoEntity;
import com.wisrc.basic.vo.CompanyBasicInfoAllVO;

public interface CompanyBasicInfoService {
    /**
     * 查询公司档案基本信息
     */
    CompanyBasicInfoEntity findBasicInfo();

    /**
     * 查询公司物流报关信息
     */
    CompanyCustomsInfoEntity findCustomsInfo();

    /**
     * 新增公司物流报关信息
     *
     * @param ele
     */
    void addBasicInfo(CompanyBasicInfoEntity ele);

    /**
     * 新增公司物流报关信息
     *
     * @param ele
     */
    void addCustomsInfo(CompanyCustomsInfoEntity ele);

    /**
     * 修改公司物流报关信息
     *
     * @param ele
     */
    void updateBasicInfo(CompanyBasicInfoEntity ele);

    /**
     * 修改公司物流报关信息
     *
     * @param ele
     */
    void updateCustomsInfo(CompanyCustomsInfoEntity ele);

    /**
     * 修改公司档案（包含新增和修改）
     */
    void saveInfo(CompanyBasicInfoAllVO vo);
}
