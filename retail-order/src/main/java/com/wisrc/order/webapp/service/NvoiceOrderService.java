package com.wisrc.order.webapp.service;

import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.vo.saleNvoice.SaleNvoicePageVo;
import com.wisrc.order.webapp.vo.saleNvoice.SaleNvoiceSaveVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NvoiceOrderService {
    Result saleNvoicePage(SaleNvoicePageVo saleNvoicePageVo);

    Result invoiceStatus();

    Result getSaleNvoice(String invoiceNumber);

    Result excelModel();

    Result excelHandle(MultipartFile freightExcel);

    Result saveSaleNvoice(SaleNvoiceSaveVo saleNvoiceSaveVo, String userId);

    Result nvoiceOrderByIds(List ids);

    Result saveNvoiceOrderByIds(List<String> ids, String userId);
}
