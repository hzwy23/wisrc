package com.wisrc.replenishment.webapp.controller;

import com.wisrc.replenishment.webapp.entity.CustomsInfoEntity;
import com.wisrc.replenishment.webapp.entity.ImproveLogisticsInfoEntity;
import com.wisrc.replenishment.webapp.service.CustomsInfoService;
import com.wisrc.replenishment.webapp.service.ImproveLogisticsInfoService;
import com.wisrc.replenishment.webapp.service.UploadDownloadService;
import com.wisrc.replenishment.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@RestController
@Api(tags = "获取报关资料/物流信息下载路径")
@RequestMapping(value = "/replenishment")
public class DownloadDocController {
    private final Logger logger = LoggerFactory.getLogger(DownloadDocController.class);
    @Autowired
    private CustomsInfoService customsInfoService;
    @Autowired
    private ImproveLogisticsInfoService improveLogisticsInfoService;
    @Autowired
    private UploadDownloadService uploadDownloadService;

    @ApiOperation(value = "获取报关单下载路径")
    @RequestMapping(value = "/download/customsinfo", method = RequestMethod.GET, produces = "application/zip")
    public void downloadCustoms(HttpServletResponse response,
                                @RequestParam(value = "waybillId", required = true) String waybillId,
                                @RequestParam(value = "obsName", required = true) String obsName) throws IOException {
        OutputStream output = response.getOutputStream();
        response.setContentType("application/zip");
        String fineName = URLEncoder.encode("报关单资料.zip", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fineName + "\"");
        ZipOutputStream zout = new ZipOutputStream(output);

        try {

            CustomsInfoEntity entity = customsInfoService.get(waybillId);

            // 如果获取不到物流单对应的附件信息，直接退出
            if (entity == null) {
                zout.flush();
                zout.close();
                return;
            }

            String customsInfoDoc = entity.getCustomsInfoDoc();
            if (customsInfoDoc != null && customsInfoDoc.length() > 0) {
                String fname = customsInfoDoc.substring(customsInfoDoc.lastIndexOf("."), customsInfoDoc.length());
                zout.putNextEntry(new ZipEntry("报关资料" + fname));
                byte[] bytesCustomsInfoDoc = uploadDownloadService.downloadFile(obsName, customsInfoDoc);
                zout.write(bytesCustomsInfoDoc, 0, bytesCustomsInfoDoc.length);
                zout.closeEntry();
            }

            String declarationNumberDoc = entity.getDeclarationNumberDoc();
            if (declarationNumberDoc != null && declarationNumberDoc.length() > 0) {
                String fname = declarationNumberDoc.substring(declarationNumberDoc.lastIndexOf("."), declarationNumberDoc.length());
                zout.putNextEntry(new ZipEntry("申报资料" + fname));
                byte[] bytesNumberDoc = uploadDownloadService.downloadFile(obsName, declarationNumberDoc);
                zout.write(bytesNumberDoc, 0, bytesNumberDoc.length);
                zout.closeEntry();
            }

            String letterReleaseDoc = entity.getLetterReleaseDoc();
            if (letterReleaseDoc != null && letterReleaseDoc.length() > 0) {
                String fname = letterReleaseDoc.substring(letterReleaseDoc.lastIndexOf("."), letterReleaseDoc.length());
                byte[] bytesReleaseDo = uploadDownloadService.downloadFile(obsName, letterReleaseDoc);
                zout.putNextEntry(new ZipEntry("发货信息" + fname));
                zout.write(bytesReleaseDo, 0, bytesReleaseDo.length);
                zout.closeEntry();
            }

            zout.flush();
            zout.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            ((ServletOutputStream) output).println("下载物流报关单资料失败，请联系管理员");
        }

    }

    @ApiOperation(value = "报关资料下载")
    @RequestMapping(value = "/download/customsData/{wayBillId}", method = RequestMethod.GET)
    public Result downloadCustoms(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "wayBillId") String wayBillId) {
        return customsInfoService.customExcel(request, response, wayBillId);
    }


    @ApiOperation(value = "获取物流信息下载路径")
    @RequestMapping(value = "/download/logisticsinfo", method = RequestMethod.GET, produces = "application/zip")
    public void downloadLogistics(HttpServletResponse response,
                                  @RequestParam(value = "waybillId", required = true) String waybillId,
                                  @RequestParam(value = "obsName", required = true) String obsName) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/zip");
        String fineName = URLEncoder.encode("物流信息.zip", "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fineName + "\"");
        ZipOutputStream zout = new ZipOutputStream(outputStream);

        try {
            ImproveLogisticsInfoEntity entity = improveLogisticsInfoService.get(waybillId);
            if (entity == null) {
                zout.flush();
                zout.close();
                return;
            }

            String logisticsSurfaceUrl = entity.getLogisticsSurfaceUrl();
            String examiningReportUrl = entity.getExaminingReportUrl();

            if (logisticsSurfaceUrl != null) {
                byte[] bytesSurfaceUrl = uploadDownloadService.downloadFile(obsName, logisticsSurfaceUrl);
                zout.putNextEntry(new ZipEntry(logisticsSurfaceUrl));
                zout.write(bytesSurfaceUrl, 0, bytesSurfaceUrl.length);
                zout.closeEntry();
            }
            if (examiningReportUrl != null) {
                byte[] bytesReportUrl = uploadDownloadService.downloadFile(obsName, examiningReportUrl);
                zout.putNextEntry(new ZipEntry(examiningReportUrl));
                zout.write(bytesReportUrl, 0, bytesReportUrl.length);
                zout.closeEntry();
            }
            zout.flush();
            zout.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            ((ServletOutputStream) outputStream).println("下载物流信息失败，请联系管理员");
        }
    }
}
