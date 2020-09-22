package com.wisrc.purchase.webapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisrc.purchase.webapp.dto.purchasePlan.PDFPrintDTO;
import com.wisrc.purchase.webapp.service.ObjectStorageService;
import com.wisrc.purchase.webapp.service.OrderProvisionPdfService;
import com.wisrc.purchase.webapp.utils.FileNameUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;


@Api(tags = "采购订单PDF文件")
@RestController
@RequestMapping(value = "/purchase")
public class OrderProvisionPdfController {
    private final static Logger logger = LoggerFactory.getLogger(OrderProvisionPdfController.class);
    @Autowired
    OrderProvisionPdfService orderProvisionPdfService;
    @Autowired
    private ObjectStorageService objectStorageService;

    /**
     * 导出Pdf
     *
     * @return
     */
    @RequestMapping(value = "/order/export/pdf", method = RequestMethod.GET)
    @ApiOperation(value = "导出采购订单Pdf", notes = "如果参数中存在bank、account、payee 项，将替换条款中的 ${payee} ${bank} ${account}全部项目，如果不传递，将默认替换为空字符  ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payee", value = "收款人", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "bank", value = "开户银行", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "account", value = "银行账号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "orderId", value = "订单号", paramType = "query", dataType = "String", required = true)
    })
    public void exportPdf(HttpServletRequest httpServletRequest, HttpServletResponse response,
                          @RequestParam(value = "orderId") String orderId,
                          @RequestParam(defaultValue = "", required = false) String payee,
                          @RequestParam(defaultValue = "", required = false) String bank,
                          @RequestParam(defaultValue = "", required = false) String account) {

        String content = null;
        try {
            content = orderProvisionPdfService.createPdf(orderId, payee, bank, account);
        } catch (Exception e) {
            logger.error("获取条款错误", e);
            SetErrorMessage(response, "获取条款错误,请稍后再试");
            return;
        }
        byte[] bytes;
        ServletOutputStream outputStream = null;
        try {
            bytes = objectStorageService.renderPDF(new ObjectMapper().writeValueAsString(new PDFPrintDTO(content)));
            response.setContentType("application/pdf");
            response.setCharacterEncoding("UTF-8");
            String exportFileName = FileNameUtil.exportToBrowser("采购订单" + orderId + ".pdf", httpServletRequest);
            response.setHeader("Content-disposition", "inline;filename=\"" + exportFileName + "\"");
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (SocketTimeoutException ex) {
            logger.error("渲染服务连接超时", ex);
            SetErrorMessage(response, "渲染服务连接超时,请稍后再试");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("渲染服务超时", e);
            SetErrorMessage(response, "渲染服务超时,请稍后再试");
        } catch (Exception e) {
            logger.error("渲染服务错误", e);
            SetErrorMessage(response, "渲染服务错误,请稍后再试");
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void SetErrorMessage(HttpServletResponse httpServletResponse, String message) {
        httpServletResponse.setContentType("text/html");
        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        try {
            writer = httpServletResponse.getWriter();
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }
}
