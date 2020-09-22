package com.wisrc.warehouse.webapp.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.warehouse.webapp.entity.*;
import com.wisrc.warehouse.webapp.service.StockDetailService;
import com.wisrc.warehouse.webapp.dao.ReportLossStatementDao;
import com.wisrc.warehouse.webapp.service.ReportLossStatementService;
import com.wisrc.warehouse.webapp.service.StockService;
import com.wisrc.warehouse.webapp.service.WarehouseManageInfoService;
import com.wisrc.warehouse.webapp.service.externalService.ExternalEmployeeService;
import com.wisrc.warehouse.webapp.service.externalService.ExternalProductService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.utils.ResultCode;
import com.wisrc.warehouse.webapp.utils.Time;
import com.wisrc.warehouse.webapp.utils.UUIDutil;
import com.wisrc.warehouse.webapp.vo.FnSkuStockVO;
import com.wisrc.warehouse.webapp.vo.reportLossStatement.add.AddProductInfoVO;
import com.wisrc.warehouse.webapp.vo.reportLossStatement.add.AddReportLossStatementVO;
import com.wisrc.warehouse.webapp.vo.reportLossStatement.get.GetProductVO;
import com.wisrc.warehouse.webapp.vo.reportLossStatement.set.ReviewVO;
import com.wisrc.warehouse.webapp.vo.reportLossStatement.show.ShowFnSkuStockVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportLossStatementImplService implements ReportLossStatementService {
    //报损单单号 头字母
    private static final String HEADER = "G";
    //最大流水号
    private static final int MAX_SIZE = 999;
    //正常状态码
    private static final int CODE = 200;
    //海外仓
    private static final String TYPE_CD_OVERSEAS = "B";
    private final Logger logger = LoggerFactory.getLogger(ReportLossStatementImplService.class);
    @Autowired
    private ReportLossStatementDao reportLossStatementDao;
    @Autowired
    private ExternalProductService externalProductService;
    @Autowired
    private ExternalEmployeeService externalEmployeeService;
    @Autowired
    private WarehouseManageInfoService warehouseManageInfoService;
    @Autowired
    private StockDetailService stockDetailService;
    @Autowired
    private StockService stockService;

    private static String timeSolve(Object time) throws Exception {
        String timeStr = null;
        if (time == null) {
            timeStr = "";
            return timeStr;
        }
        DateFormat sdfll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            timeStr = sdfll.format(time);
        } catch (Exception e) {
            throw new Exception("日期转换出错");
        }
        return timeStr;
    }

    @Override
    public Result insert(@Valid AddReportLossStatementVO vo, BindingResult bindingResult, String userId) {
        //检验参数
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }

        //做校验
        Result productResult = check(vo);
        if (productResult.getCode() != CODE) {
            return productResult;
        }

        ReportLossStatementEntity rLSEntity = new ReportLossStatementEntity();
        BeanUtils.copyProperties(vo, rLSEntity);
        //生成报损单号
        Result idMakerResult = newIdMaker();
        if (idMakerResult.getCode() != CODE) {
            return idMakerResult;
        }
        rLSEntity.setReportLossStatementId((String) idMakerResult.getData());
        rLSEntity.setApplyPersonId(userId);
        //首次创建状态为待审查
        rLSEntity.setStatusCd(1);
        rLSEntity.setCreateTime(Time.getCurrentTime());
        reportLossStatementDao.insertRLSEntity(rLSEntity);

        List<AddProductInfoVO> productInfoList = vo.getProductInfoList();
        if (productInfoList != null && productInfoList.size() != 0) {
            ProductInfoEntity pIEntity = new ProductInfoEntity();
            for (AddProductInfoVO addProductInfoVO : productInfoList) {
                BeanUtils.copyProperties(addProductInfoVO, pIEntity);
                pIEntity.setReportLossStatementId(rLSEntity.getReportLossStatementId());
                pIEntity.setUuid(UUIDutil.randomUUID());
                //不贴标  不需要fnsku
                if (vo.getLabelFlag() == 0) {
                    pIEntity.setFnSku(null);
                }
                reportLossStatementDao.insertPIEntity(pIEntity);
            }
        }
        return Result.success(rLSEntity.getReportLossStatementId());
    }

    private Result checkSkuExsit(List<AddProductInfoVO> productInfoList) {
        if (productInfoList == null || productInfoList.size() == 0) {
            return new Result(9995, "缺少产品信息，无法为您新建或更新报损单", null);
        }
        Result skuResult;
        try {
            skuResult = externalProductService.getAllSku();
        } catch (Exception e) {
            return new Result(9995, "产品接口出错！请稍后再试后联系管理员", e);
        }
        if (skuResult.getCode() != CODE) {
            return new Result(9995, "产品接口出错！请稍后再试后联系管理员", skuResult);
        }
        List<String> skuList = (List) skuResult.getData();
        boolean flag = false;
        for (AddProductInfoVO oq : productInfoList) {
            for (String ow : skuList) {
                // 可能存在null 如 ["FSDSD",null,"dssadsa"]
                if (ow != null && oq.getSkuId() != null) {
                    if (ow.equals(oq.getSkuId())) {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                return new Result(9991, oq.getSkuId() + "不存在，请确认后重填！", null);
            }
        }
        return Result.success();
    }

    @Override
    public Result getByReportLossStatementId(String reportLossStatementId) {
        Map rLSMap = reportLossStatementDao.getRLSS(reportLossStatementId);
        List<Map> pIList = new ArrayList<>();
        if (rLSMap == null) {
            return Result.success(rLSMap);
        }
        pIList = reportLossStatementDao.getpIList(reportLossStatementId);

        //日期处理
        try {
            rLSMap.put("createTime", timeSolve(rLSMap.get("createTime")));
            rLSMap.put("reviewTime", timeSolve(rLSMap.get("reviewTime")));
        } catch (Exception e) {
            logger.info("日期转换出错！", e);
            return new Result(9991, "日期转换出错！请联系管理员处理", e);
        }

        //状态
        List<ReportLossStatementStatusAttrEntity> statusAttrList = reportLossStatementDao.getStatusAttr();
        Map statusDescMap = new HashMap();
        for (ReportLossStatementStatusAttrEntity o : statusAttrList) {
            statusDescMap.put(o.getStatusCd(), o.getStatusDesc());
        }

        //人员
        List personIdList = new ArrayList();
        personIdList.add(rLSMap.get("applyPersonId"));
        personIdList.add(rLSMap.get("reviewPersonId"));
        Map employeeMap;
        try {
            employeeMap = getEmployeeMap(personIdList);
        } catch (Exception e) {
            logger.info("人员接口发生错误", e);
            return new Result(9995, "人员接口发生错误,无法为您查询到申请人与审核人，请稍后重试或者联系管理员", e);
        }

        //产品
        Map skuMap = new HashMap();
        try {
            List<String> skuIdList = new ArrayList<>();
            for (Map o : pIList) {
                skuIdList.add((String) o.get("skuId"));
            }
            skuMap = getSkuMap(skuIdList);
        } catch (Exception e) {
            return new Result(9995, "调用产品基础信息的模糊查询接口出错！请稍后稍后重试，或者联系管理员", e);
        }
        //产品中文名,图片
        for (Map o : pIList) {
            Map temp = (Map) skuMap.get(o.get("skuId"));
            o.put("skuNameZh", temp.get("skuNameZh"));
            o.put("imageUrl", temp.get("imageUrl"));
        }

        rLSMap.put("applyPersonId", employeeMap.get(rLSMap.get("applyPersonId")));
        rLSMap.put("reviewPersonId", employeeMap.get(rLSMap.get("reviewPersonId")));
        rLSMap.put("statusDesc", statusDescMap.get(rLSMap.get("statusCd")));
        WarehouseManageInfoEntity wMIEntity = reportLossStatementDao.findWarehouseById((String) rLSMap.get("warehouseId"));
        if (wMIEntity == null) {
            rLSMap.put("warehouseName", "没有找到该仓库的名字，可能该仓库不存在，建议工作人员排查");
        } else {
            rLSMap.put("warehouseName", wMIEntity.getWarehouseName());
        }

        rLSMap.put("productInfoList", pIList);
        return Result.success(rLSMap);
    }

    private Map getSkuMap(List<String> skuIdList) throws Exception {
        Map skuMap = new HashMap();
        Map batchSkuId = new HashMap();
        batchSkuId.put("skuIdList", skuIdList);
        Result productResult = externalProductService.getProductInfo(batchSkuId);
        if (productResult.getCode() != CODE) {
            logger.info("调用产品接口出错！请稍后稍后重试，或者联系管理员", productResult);
            throw new Exception("调用产品接口出错！");
        } else {
            JSONArray jSONArray = JSONUtil.parseArray(productResult.getData());
            for (int i = 0; i < jSONArray.size(); i++) {
                Map map = new HashMap();
                JSONObject defineJSONObject = JSONUtil.parseObj(jSONArray.get(i)).getJSONObject("define");
                map.put("skuNameZh", defineJSONObject.getStr("skuNameZh"));
                JSONArray imagesListJSONArray = JSONUtil.parseObj(jSONArray.get(i)).getJSONArray("imagesList");
                for (int j = 0; j < imagesListJSONArray.size(); j++) {
                    int imageClassifyCd = (int) JSONUtil.parseObj(imagesListJSONArray.get(j)).get("imageClassifyCd");
                    //带包装产品图一张
                    if (imageClassifyCd == 1) {
                        map.put("imageUrl", JSONUtil.parseObj(imagesListJSONArray.get(j)).get("imageUrl"));
                        skuMap.put(defineJSONObject.getStr("skuId"), map);
                        break;
                    }
                }
                if (map.get("imageUrl") == null) {
                    map.put("imageUrl", "");
                    skuMap.put(defineJSONObject.getStr("skuId"), map);
                }
            }
        }
        return skuMap;
    }

    private Map getEmployeeMap(List<String> personIdList) throws Exception {
        Map employeeMap = new HashMap();
        Result employeeResult = externalEmployeeService.getEmployeeNameBatch(personIdList);
        if (employeeResult.getCode() != CODE) {
            logger.info("调用查询人员信息接口出错，请稍后重试或者联系管理员", employeeResult);
            throw new Exception("人员接口发生错误");
        }
        List<Map> list = (List<Map>) employeeResult.getData();
        for (Map o : list) {
            employeeMap.put(o.get("employeeId"), o.get("employeeName"));
        }
        return employeeMap;
    }

    @Override
    public Result review(@Valid ReviewVO reviewVO, BindingResult bindingResult, String userId) {
        // todo
        //数据库上锁 安全 界面提示友好，但是性能下降
        //sql语句不执行，数据不会变，但是界面提示不友好
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        ReportLossStatementEntity rLSEntity = new ReportLossStatementEntity();
        rLSEntity.setReportLossStatementId(reviewVO.getReportLossStatementId());
        rLSEntity.setReviewPersonId(userId);
        if (reviewVO.getReviewCd() == 0) {
            //审核同意
            rLSEntity.setStatusCd(2);
            rLSEntity.setDisagreeReason(null);
        } else if (reviewVO.getReviewCd() == 1) {
            //审核不同意
            rLSEntity.setStatusCd(3);
            rLSEntity.setDisagreeReason(reviewVO.getDisagreeReason());
        } else {
            return new Result(9999, "审核编码examineCd非法，请确认后重试！", null);
        }
        Map map = reportLossStatementDao.getRLSS(reviewVO.getReportLossStatementId());
        if (map != null) {
            if ((int) map.get("statusCd") == 2) {
                return new Result(9999, "该报损单号已经处于审核通过状态，无法为您执行本次操作", null);
            }
            if ((int) map.get("statusCd") == 4) {
                return new Result(9999, "该报损单号处于已取消状态，无法为您执行本次操作", null);
            }
        }
        reportLossStatementDao.review(rLSEntity);
        return Result.success();
    }

    @Override
    public Result cancel(String reportLossStatementId, String cancelReason, String userId) {
        // todo
        //数据库上锁 安全 界面提示友好，但是性能下降
        //sql语句不执行，数据不会变，但是界面提示不友好

        ReportLossStatementEntity rLSEntity = new ReportLossStatementEntity();
        rLSEntity.setReportLossStatementId(reportLossStatementId);
        rLSEntity.setCancelReason(cancelReason);
        rLSEntity.setReviewPersonId(userId);
        rLSEntity.setStatusCd(4);
        Map map = reportLossStatementDao.getRLSS(reportLossStatementId);
        if (map != null) {
            Integer statusCd = (Integer) map.get("statusCd");
            if (statusCd == null || statusCd != 1) {
                return new Result(9999, "该报损单号目前所处的状态，不支持取消操作", null);
            }
        }
        reportLossStatementDao.cancel(rLSEntity);
        return Result.success();
    }

    @Override
    public Result fuzzy(Integer statusCd, String createTimeStart, String createTimeEnd, String warehouseId, String keyWords, Integer pageNum, Integer pageSize) {
        String keyWordsSkuPara = null;

        //关键字对产品的模糊查找
        if (keyWords != null) {
            List<String> skuList = new ArrayList<>();
            try {
                Result productResult = externalProductService.getProductSkuInfo(null, null, keyWords);
                if (productResult.getCode() != CODE) {
                    logger.info("调用产品基础信息的模糊查询接口出错！请稍后稍后重试，或者联系管理员", productResult);
                    return new Result(9995, "调用产品基础信息的模糊查询接口出错！请稍后稍后重试，或者联系管理员", productResult);
                } else {
                    JSONObject dataJson = JSONUtil.parseObj(productResult.getData());
                    JSONArray jSONArray = JSONUtil.parseArray(dataJson.get("productData"));
                    JSONObject temp;
                    for (int i = 0; i < jSONArray.size(); i++) {
                        temp = JSONUtil.parseObj(jSONArray.get(i));
                        skuList.add(temp.getStr("sku"));
                    }
                }
            } catch (Exception e) {
                logger.info("调用产品基础信息的模糊查询接口出错！请稍后稍后重试，或者联系管理员", e);
                return new Result(9995, "调用产品基础信息的模糊查询接口出错！请稍后稍后重试，或者联系管理员", e);
            }
            keyWordsSkuPara = makePara(skuList);
        }

        Map map = new HashMap();
        map.put("keyWordsSkuPara", keyWordsSkuPara);
        map.put("keyWords", keyWords);
        List<String> idList = new ArrayList<>();
        //模糊查询报损单中的产品信息  ，并取出产品作为，模糊查询报损单的条件
        List<Map> productList = reportLossStatementDao.fuzzyProduct(map);
        for (Map o : productList) {
            idList.add((String) o.get("reportLossStatementId"));
        }

        String idPara = makePara(idList);
        map.put("createTimeStart", createTimeStart);
        map.put("createTimeEnd", createTimeEnd);
        map.put("warehouseId", warehouseId);
        map.put("statusCd", statusCd);
        map.put("keyWords", keyWords);
        map.put("idPara", idPara);
        List<Map> resultList = new ArrayList<>();
        if (pageNum == null || pageSize == null || pageNum < 1 || pageSize < 1) {
            resultList = reportLossStatementDao.fuzzy(map);
        } else {
            PageHelper.startPage(pageNum, pageSize);
            resultList = reportLossStatementDao.fuzzy(map);
        }
        //获取四状态的报损单数量
        int waitQuantity = reportLossStatementDao.getSizeByStatusCd(1);
        int passQuantity = reportLossStatementDao.getSizeByStatusCd(2);
        int unPassQuantity = reportLossStatementDao.getSizeByStatusCd(3);
        int cancelQuantity = reportLossStatementDao.getSizeByStatusCd(4);
        Map sizeMap = new HashMap();
        sizeMap.put("waitQuantity", waitQuantity);
        sizeMap.put("passQuantity", passQuantity);
        sizeMap.put("unPassQuantity", unPassQuantity);
        sizeMap.put("cancelQuantity", cancelQuantity);

        if (resultList == null || resultList.size() == 0) {
            Map reMap = new HashMap();
            reMap.put("total", 0);
            reMap.put("pages", 1);
            reMap.put("statusQuantity", sizeMap);
            reMap.put("reportLossStatementList", resultList);
            return Result.success(reMap);
        }
        List<String> personIdList = new ArrayList<>();
        for (Map o : resultList) {
            personIdList.add((String) o.get("applyPersonId"));
            personIdList.add((String) o.get("reviewPersonId"));
        }

        //人员
        Map employeeMap;
        try {
            employeeMap = getEmployeeMap(personIdList);
        } catch (Exception e) {
            logger.info("人员接口发生错误", e);
            return new Result(9995, "人员接口发生错误,无法为您查询到申请人与审核人，请稍后重试或者联系管理员", e);
        }


        //状态
        List<ReportLossStatementStatusAttrEntity> statusAttrList = reportLossStatementDao.getStatusAttr();
        Map statusDescMap = new HashMap();
        for (ReportLossStatementStatusAttrEntity o : statusAttrList) {
            statusDescMap.put(o.getStatusCd(), o.getStatusDesc());
        }

        //仓库
        StringBuffer sb = new StringBuffer();
        for (Map o : resultList) {
            sb.append(o.get("warehouseId") + ",");
        }

        List<WarehouseManageInfoEntity> wMIEntityList = warehouseManageInfoService.findInfoList(sb.toString());
        Map warehouseMap = new HashMap();
        for (WarehouseManageInfoEntity o : wMIEntityList) {
            warehouseMap.put(o.getWarehouseId(), o.getWarehouseName());
        }

        //产品
        Map skuMap = new HashMap();
        try {
            List<String> skuIdList = new ArrayList<>();
            for (Map o : productList) {
                skuIdList.add((String) o.get("skuId"));
            }
            skuMap = getSkuMap(skuIdList);
        } catch (Exception e) {
            return new Result(9995, "调用产品基础信息的模糊查询接口出错！请稍后稍后重试，或者联系管理员", e);
        }

        for (Map o : resultList) {
            List<Map> tempList = new ArrayList<>();
            for (Map o1 : productList) {
                if (o1.get("reportLossStatementId").equals(o.get("reportLossStatementId"))) {
                    Map temp = (Map) skuMap.get(o1.get("skuId"));
                    o1.put("skuNameZh", temp.get("skuNameZh"));
                    String imageUrl = (String) temp.get("imageUrl");
                    o1.put("imageUrl", "".endsWith(imageUrl) ? null : imageUrl);
                    tempList.add(o1);
                }
            }

            o.put("statusDesc", statusDescMap.get(o.get("statusCd")));
            o.put("warehouseName", warehouseMap.get(o.get("warehouseId")));
            o.put("applyPersonName", employeeMap.get(o.get("applyPersonId")));
            o.put("reviewPersonName", employeeMap.get(o.get("reviewPersonId")));
            o.put("productInfoList", tempList);
            //日期处理
            try {
                o.put("createTime", timeSolve(o.get("createTime")));
                o.put("reviewTime", timeSolve(o.get("reviewTime")));
            } catch (Exception e) {
                logger.info("日期转换出错！", e);
                return new Result(9991, "日期转换出错！请联系管理员处理", e);
            }
        }


        //包装VO
        Map reMap = new HashMap();
        if (pageNum == null || pageSize == null || pageNum < 1 || pageSize < 1) {
            reMap.put("total", resultList.size());
            reMap.put("pages", 1);
            reMap.put("statusQuantity", sizeMap);
            reMap.put("reportLossStatementList", resultList);
        } else {
            PageInfo pageInfo = new PageInfo(resultList);
            long total = pageInfo.getTotal();
            int pages = pageInfo.getPages();
            reMap.put("total", total);
            reMap.put("pages", pages);
            reMap.put("statusQuantity", sizeMap);
            reMap.put("reportLossStatementList", resultList);
        }

        return Result.success(reMap);
    }

    @Override
    public List<ReportLossStatementStatusAttrEntity> getStatusAttr() {
        List<ReportLossStatementStatusAttrEntity> statusAttrList = reportLossStatementDao.getStatusAttr();
        return statusAttrList;
    }

    @Override
    public Result delete(String reportLossStatementId) {
        try {
            reportLossStatementDao.deletePIE(reportLossStatementId);
            reportLossStatementDao.deleteRLSE(reportLossStatementId);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(ResultCode.DELETE_FAILED);
        }
    }


    @Override
    public Result getProduct(@Valid GetProductVO getProductVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9991, bindingResult.getFieldError().getDefaultMessage(), null);
        }

        String warehouseId = getProductVO.getWarehouseId();
        Integer labelFlag = getProductVO.getLabelFlag();
        String fnSku = getProductVO.getFnSku();
        String skuId = getProductVO.getSkuId();
        //检验仓库
        WarehouseManageInfoEntity wMIEntity = reportLossStatementDao.findWarehouseById(warehouseId);
        if (wMIEntity == null) {
            return new Result(9991, "该仓库不存在，请确认后重新输入", null);
        }
        //贴标时，
        if (labelFlag != null && labelFlag == 1) {
            if (fnSku == null) {
                return new Result(9991, "贴标时，需要传递产品的FnSKU", null);
            }
            //查询产品的库存
            List<FnSkuStockVO> list = stockDetailService.getFnSkuStock(fnSku, warehouseId);
            if (list.size() == 0) {
                return new Result(9995, "该仓库无fnsku编号：" + fnSku + "的产品存在", null);
            }
            //产品属性汇总信息
            Map skuMap = new HashMap();
            try {
                List<String> skuList = new ArrayList<>();
                for (FnSkuStockVO o : list) {
                    skuList.add(o.getSkuId());
                }
                skuMap = getSkuMap(skuList);
            } catch (Exception e) {
                return new Result(9995, "调用产品基础信息的模糊查询接口出错！请稍后稍后重试，或者联系管理员", e);
            }
            //包装VO
            List<ShowFnSkuStockVO> showList = toVOList(list, skuMap);
            return Result.success(showList);
        } else {
            if (skuId == null) {
                return new Result(9991, "缺少skuId参数", null);
            }
            //SKU
            StockEntity entity = stockService.getStockByCond(skuId, warehouseId);
            if (entity == null) {
                return Result.success(new ArrayList<>());
            }
            List<String> skuList = new ArrayList<>();
            skuList.add(entity.getSkuId());
            Map skuMap = new HashMap();
            try {
                skuMap = getSkuMap(skuList);
            } catch (Exception e) {
                return new Result(9995, "调用产品基础信息的模糊查询接口出错！请稍后稍后重试，或者联系管理员", e);
            }
            //包装VO
            List<ShowFnSkuStockVO> showList = toVOList(entity, skuMap);
            return Result.success(showList);
        }
    }

    private Result check(AddReportLossStatementVO vo) {
        String warehouseId = vo.getWarehouseId();
        Integer labelFlag = vo.getLabelFlag();
        List<AddProductInfoVO> productInfoList = vo.getProductInfoList();
        if (productInfoList == null || productInfoList.size() == 0) {
            //无产品清单  不需要验证
            return new Result(9991, "产品清单[productInfoList]不能为空", null);
        }

        //验证仓库
        WarehouseManageInfoEntity wMIEntity = reportLossStatementDao.findWarehouseById(warehouseId);
        if (wMIEntity == null) {
            return new Result(9991, "该仓库不存在，请确认后重新输入", null);
        }
        List<String> skuIdList = new ArrayList();
        for (AddProductInfoVO o : productInfoList) {
            skuIdList.add(o.getSkuId());
        }
        //贴标时，检验sku fnsku ，库存
        if (labelFlag != null && labelFlag == 1) {
            Boolean skuFlag = false;
            Boolean stockNumFlag = false;
            for (AddProductInfoVO o : productInfoList) {
                String fnSku = o.getFnSku();
                String skuId = o.getSkuId();
                int reportedLossAmount = o.getReportedLossAmount();
                if (fnSku == null) {
                    return new Result(9991, "贴标时，fnsku不能为空！", null);
                }
                List<FnSkuStockVO> list = stockDetailService.getFnSkuStock(fnSku, warehouseId);
                if (list.size() == 0) {
                    return new Result(9991, "该仓库无fnSku编号为：" + fnSku + "的产品存在！", null);
                }
                for (FnSkuStockVO o1 : list) {
                    String fnSkuIdTemp = o1.getFnSkuId();
                    String skuIdTemp = o1.getSkuId();
                    int enableStockNum = o1.getEnableStockNum();
                    if (fnSkuIdTemp.equals(fnSku) && skuIdTemp.equals(skuId)) {
                        skuFlag = true;
                        if (reportedLossAmount <= enableStockNum) {
                            stockNumFlag = true;
                        }
                    }
                }
                if (!skuFlag) {
                    return new Result(9991, "该仓库下fnsku:" + fnSku + "与sku：" + skuId + "不匹配，请重新确认", null);
                }
                if (!stockNumFlag) {
                    return new Result(9991, "该" + skuId + "的报损数量超过该仓库的可用库存数量！", null);
                }
            }
        } else {
            //检验sku  ，库存
            List<StockEntity> StockEntityList = stockService.getStockBySku(skuIdList);
            Boolean skuFlag = false;
            Boolean stockNumFlag = false;
            for (AddProductInfoVO o : productInfoList) {
                String skuId = o.getSkuId();
                for (StockEntity o1 : StockEntityList) {
                    String skuTemp = o1.getSkuId();
                    String warehouseIdTemp = o1.getWarehouseId();
                    //校验sku ，库存
                    if (skuId.equals(skuTemp) && warehouseId.equals(warehouseIdTemp)) {
                        skuFlag = true;
                        int enableStockNum = o1.getEnableStockNum();
                        int reportedLossAmount = o.getReportedLossAmount();
                        if (reportedLossAmount <= enableStockNum) {
                            stockNumFlag = true;
                        }
                    }
                }
                if (!skuFlag) {
                    return new Result(9991, "该仓库无sku编号为：" + skuId + "的产品存在！", null);
                }
                if (!stockNumFlag) {
                    return new Result(9991, "该" + skuId + "的报损数量超过该仓库的可用库存数量！", null);
                }
            }
        }
        return Result.success();
    }


    private List<ShowFnSkuStockVO> toVOList(List<FnSkuStockVO> list, Map skuMap) {
        List<ShowFnSkuStockVO> showList = new ArrayList<>();
        for (FnSkuStockVO o : list) {
            ShowFnSkuStockVO showFnSkuStockVO = new ShowFnSkuStockVO();
            BeanUtils.copyProperties(o, showFnSkuStockVO);
            showFnSkuStockVO.setFnSku(o.getFnSkuId());
            showList.add(showFnSkuStockVO);
        }
        //产品中文名,图片
        for (ShowFnSkuStockVO o : showList) {
            Map temp = (Map) skuMap.get(o.getSkuId());
            o.setSkuNameZh((String) temp.get("skuNameZh"));
            o.setImageUrl((String) temp.get("imageUrl"));
        }
        return showList;
    }

    private List<ShowFnSkuStockVO> toVOList(StockEntity entity, Map skuMap) {
        List<ShowFnSkuStockVO> showList = new ArrayList<>();
        ShowFnSkuStockVO showFnSkuStockVO = new ShowFnSkuStockVO();
        showFnSkuStockVO.setFnSku(null);
        BeanUtils.copyProperties(entity, showFnSkuStockVO);
        showList.add(showFnSkuStockVO);
        //产品中文名,图片
        for (ShowFnSkuStockVO o : showList) {
            Map temp = (Map) skuMap.get(o.getSkuId());
            o.setSkuNameZh((String) temp.get("skuNameZh"));
            o.setImageUrl((String) temp.get("imageUrl"));
        }
        return showList;
    }

    private String makePara(List<String> list) {
        String result;
        if (list == null || list.size() == 0) {
            result = "('')";
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                String id = list.get(i);
                if (i == 0) {
                    sb.append("(" + "'" + id + "'");
                } else {
                    sb.append("," + "'" + id + "'");
                }
                if (i == (list.size() - 1)) {
                    sb.append(")");
                }
            }
            result = sb.toString();
        }
        return result;
    }


    private Result newIdMaker() {
        //当前日期字符串，格式：yyyyMMdd
        String today = DateUtil.today().replaceAll("-", "");
        String idTemp = HEADER + today;
        int size = reportLossStatementDao.getFuzzySize(idTemp);
        int idNumber = size + 1;
        if (idNumber >= MAX_SIZE) {
            return Result.failure(9995, "流水号超出" + MAX_SIZE + "，请联系管理员重新设计", null);
        } else {
            String newId = idTemp + new DecimalFormat("000").format(idNumber);
            return Result.success(newId);
        }
    }

}
