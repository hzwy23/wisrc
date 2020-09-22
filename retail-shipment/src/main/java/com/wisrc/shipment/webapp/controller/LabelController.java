package com.wisrc.shipment.webapp.controller;

import com.wisrc.shipment.webapp.service.LabelService;
import com.wisrc.shipment.webapp.service.UploadDownloadService;
import com.wisrc.shipment.webapp.entity.ChangeLableEnity;
import com.wisrc.shipment.webapp.utils.DateUtil;
import com.wisrc.shipment.webapp.utils.FileNameUtil;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.vo.ChangeLableViewVo;
import com.wisrc.shipment.webapp.vo.ChangeLableVo;
import com.wisrc.shipment.webapp.vo.LabelDetailDealVo;
import com.wisrc.shipment.webapp.vo.WarehouseProductVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;

@Api(tags = "换标管理服务")
@RestController
@RequestMapping(value = "/shipment")
public class LabelController {

    @Autowired
    private LabelService labelService;
    @Autowired
    private UploadDownloadService uploadDownloadService;


    @ApiOperation(value = "安排/修改换标", notes = "安排换标")
    @RequestMapping(value = "/label", method = RequestMethod.POST)
    public Result add(@RequestBody @Validated ChangeLableEnity changeLableEnity, BindingResult result,
                      @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        changeLableEnity.setCreateUser(userId);
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), null);
        }
        try {
            Result returnResult = labelService.insert(changeLableEnity);
        } catch (Exception e) {
            return Result.failure(390, "申请换标失败", e.getMessage());
        }
        return Result.success("安排换标成功");
    }

    @ApiOperation(value = "换标详细", notes = "换标详细")
    @RequestMapping(value = "/label/{changeLabelId}", method = RequestMethod.GET)
    public Result getLabelDetail(@PathVariable("changeLabelId") String changeLabelId,
                                 @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        ChangeLableVo changeLableVoList = null;
        try {
            changeLableVoList = labelService.getLabelDetail(changeLabelId);
        } catch (IllegalStateException e) {
            return Result.failure(390, e.getMessage(), null);
        } catch (Exception e) {
            return Result.failure(390, "查询换标详细失败", e.getMessage());
        }
        return Result.success(changeLableVoList);
    }

    @ApiOperation(value = "取消换标", notes = "取消换标")
    @RequestMapping(value = "/label/cancel", method = RequestMethod.DELETE)
    public Result deleteChangeLabel(@RequestParam("changeLabelId") String changeLabelId, @RequestParam("reason") String reason,
                                    @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            labelService.cancelChangeLabel(changeLabelId, reason, userId);
        } catch (Exception e) {
            return Result.failure(390, "取消换标失败", e.getMessage());
        }
        return Result.success("取消换标成功");
    }

    @ApiOperation(value = "分页模糊查询换标信息", notes = "分页模糊查询换标信息", response = ChangeLableViewVo.class)
    @RequestMapping(value = "/label/list", method = RequestMethod.GET)
    public Result findByCond(@RequestParam(value = "pageNum", required = false) String pageNum,
                             @RequestParam(value = "pageSize", required = false) String pageSize,
                             @RequestParam(value = "startTime", required = false) String startTime,
                             @RequestParam(value = "endTime", required = false) String endTime,
                             @RequestParam(value = "wareHouseId", required = false) String wareHouseId,
                             @RequestParam(value = "statusCd", required = false, defaultValue = "0") int statusCd,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        LinkedHashMap linkedHashMap = null;
        try {
            if (endTime != null) {
                Date date = DateUtil.convertStrToDate(endTime, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
                Date endDate = DateUtil.addDate("dd", 1, date);
                endTime = DateUtil.convertDateToStr(endDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
            }
            linkedHashMap = labelService.findByCond(pageNum, pageSize, startTime, endTime, wareHouseId, statusCd, keyword);
        } catch (Exception e) {
            return Result.failure(390, "模糊查询失败", e.getMessage());
        }
        return Result.success(linkedHashMap);
    }

    @ApiOperation(value = "根据fnsku和仓库获取详细信息", notes = "获取库存和商品信息", response = ChangeLableViewVo.class)
    @RequestMapping(value = "/label/detail", method = RequestMethod.GET)
    public Result getWarehouseAndProduct(@RequestParam(value = "fnsku", required = true) String fnsku,
                                         @RequestParam(value = "warehouseId", required = true) String warehouseId) {
        WarehouseProductVo warehouseAndProduct = null;
        try {
            warehouseAndProduct = labelService.getWarehouseAndProduct(fnsku, warehouseId);
            if (warehouseAndProduct == null) {
                return Result.failure(390, "该fnsku不存在", null);
            }
        } catch (Exception e) {
            return Result.failure(390, "根据fnsku和仓库获取详细信息失败", e.getMessage());
        }
        return Result.success(warehouseAndProduct);
    }

    @ApiOperation(value = "跟进处理换标明细", notes = "跟进处理换标明细", response = ChangeLableViewVo.class)
    @RequestMapping(value = "/label/deal", method = RequestMethod.PUT)
    public Result updateDetail(@RequestBody @Validated LabelDetailDealVo labelDetailDealVo, BindingResult result,
                               @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), null);
        }
        labelDetailDealVo.setModifyUser(userId);
        try {
            if (labelDetailDealVo.getOperationStatusCd() == 1) {
                return Result.failure(390, "请选择跟进处理状态", null);
            }
            labelService.updateDetail(labelDetailDealVo);
        } catch (Exception e) {
            return Result.failure(390, "跟进处理换标明细失败", e.getMessage());
        }
        return Result.success("跟进处理成功");
    }

    @ApiOperation(value = "更新fnsku条码文件")
    @RequestMapping(value = "/label/{changeLabelId}/fnskuCodeFile/{fnskuCodeFileId}", method = RequestMethod.PUT)
    public Result updateFnskuCodeFileId(@PathVariable("changeLabelId") String changeLabelId,
                                        @PathVariable("fnskuCodeFileId") String fnskuCodeFileId) {
        try {
            labelService.updateFnskuCodeFileId(changeLabelId, fnskuCodeFileId);
        } catch (Exception e) {
            return Result.failure(390, "更新fnsku条码文件失败", e.getMessage());
        }
        return Result.success("更新fnsku条码文件成功");
    }

    @ApiOperation(value = "下载fnsku条码文件")
    @RequestMapping(value = "/label/{changeLabelId}/fnskuCodeFile/{fnskuCodeFileId}", method = RequestMethod.GET)
    public Result downloadFnskuCodeFile(HttpServletRequest request, HttpServletResponse response,
                                        @PathVariable("changeLabelId") String changeLabelId,
                                        @PathVariable("fnskuCodeFileId") String fnskuCodeFileId,
                                        @RequestParam("obsName") String obsName) {
        ServletOutputStream os = null;
        try {

            byte[] bytes = uploadDownloadService.downloadFile(obsName, fnskuCodeFileId);
            String extension = FilenameUtils.getExtension(fnskuCodeFileId);
            String exportName = "换标订单" + changeLabelId + "fnsku条码." + extension;
            exportName = FileNameUtil.exportToBrowser(exportName, request);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=\"" + exportName + "\"");
            response.flushBuffer();
            os = response.getOutputStream();
            os.write(bytes);
            os.flush();
        } catch (Exception e) {
            response.setContentType("application/json;charset=UTF-8");
            return Result.failure(390, "下载fnsku条码文件失败", e.getMessage());
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ignored) {
                }
            }
        }
        return Result.failure();
    }
}