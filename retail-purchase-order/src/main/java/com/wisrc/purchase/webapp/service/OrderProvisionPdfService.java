package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.dto.purchasePlan.PayManInfoDto;
import com.wisrc.purchase.webapp.vo.orderProvision.OrderPdfVO;

public interface OrderProvisionPdfService {
    /**
     * 导出Pdf文件内容
     *
     * @param orderId
     * @param payee
     * @param bank
     * @param account
     * @return
     */
    String createPdf(String orderId, String payee, String bank, String account);

    /**
     * 查询Pdf所需要的动态信息
     *
     * @param orderId
     * @param payManInfoDto
     * @return
     */
    OrderPdfVO findPdfInfo(String orderId, PayManInfoDto payManInfoDto);
}
