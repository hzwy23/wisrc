package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.entity.CustomsInfoEntity;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CustomsInfoService {
    /**
     * 报关资料新增--公司报关信息部分
     *
     * @param vo
     */
    void addCustomsInfo(CompanyCustomsInfoVO vo);

    /**
     * 报关资料新增--上传报关单
     *
     * @param vo
     */
    void addCustomsOrder(CustomsOrderVO vo);

    CustomsInfoEntity get(String waybillId);

    SelectDeclareCustomVO getVO(String waybillId);

    CustomsListInfoVO getCustomsMskuInfo(String uuid);

    ImproveSendDataVO getImproveMskuInfo(String uuid);

    String getBatchNumber(String fbaReplenishmentId);

    void updateCustomsInfo(CompanyCustomsInfoVO vo);

    /**
     * 下载报关发票信息
     *
     * @param wayBillId 运单ID
     */
    Result customExcel(HttpServletRequest request, HttpServletResponse response, String wayBillId);
}
