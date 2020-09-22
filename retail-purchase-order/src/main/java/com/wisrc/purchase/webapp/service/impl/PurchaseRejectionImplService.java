package com.wisrc.purchase.webapp.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.purchase.webapp.dao.PurchaseRejectionDao;
import com.wisrc.purchase.webapp.dto.inspection.GetInspectionDto;
import com.wisrc.purchase.webapp.dto.inspection.GetInspectionProductDto;
import com.wisrc.purchase.webapp.entity.GatherPurchaseRejectionEntity;
import com.wisrc.purchase.webapp.entity.PurchaseRejectionDetailsEntity;
import com.wisrc.purchase.webapp.entity.PurchaseRejectionInfoEntity;
import com.wisrc.purchase.webapp.entity.PurchaseRejectionInfoNewEntity;
import com.wisrc.purchase.webapp.service.*;
import com.wisrc.purchase.webapp.service.externalService.ProductService;
import com.wisrc.purchase.webapp.service.externalService.ProductSkuService;
import com.wisrc.purchase.webapp.utils.Arith;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.utils.Time;
import com.wisrc.purchase.webapp.utils.Toolbox;
import com.wisrc.purchase.webapp.vo.GetPurchaseRejectionNewVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.GatherPurchaseRejectionVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.PurchaseRejectionDetailsVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.PurchaseRejectionInfoVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.RejectionIdPara;
import com.wisrc.purchase.webapp.vo.purchaseRejection.add.AddPurchaseRejectionDetailsVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.add.AddPurchaseRejectionInfoVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.add.AddPurchaseRejectionVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.get.GetPurchaseRejectionVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.set.SetPurchaseRejectionDetailsVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.set.SetPurchaseRejectionInfoVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.set.SetPurchaseRejectionVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;


@Service
public class PurchaseRejectionImplService implements PurchaseRejectionService {

    private final Logger logger = LoggerFactory.getLogger(PurchaseRejectionImplService.class);
    private final String rejectionIdHeader = "PRJ";
    @Autowired
    private PurchaseRejectionDao purchaseRejectionDao;
    @Autowired
    private EmployeeOutsideService employeeOutsideService;
    @Autowired
    private PurchaseEmployeeService purchaseEmployeeService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private InspectionService inspectionService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private SupplierOutsideService supplierOutsideService;

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager",propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result add(@Valid AddPurchaseRejectionVO vo, BindingResult bindingResult, String userId) {
        //校验sku的拒收数量与到货数量
        List<AddPurchaseRejectionDetailsVO> purchaseRejectionDetailsList = vo.getPurchaseRejectionDetailsList();
        String inspectionId = vo.getPurchaseRejectionInfo().getInspectionId();
        Result re = inspectionService.getInspection(inspectionId);

        if (re.getCode() != 200) {
            return new Result(9998, "到货通知单接口无法返回数据，无法确定校验到货数量与拒收数量", vo);
        } else {
            if (purchaseRejectionDetailsList != null || purchaseRejectionDetailsList.size() != 0) {
                GetInspectionDto getInspectionDto = (GetInspectionDto) re.getData();
                List<GetInspectionProductDto> inspectionProduct = getInspectionDto.getInspectionProduct();
                Map<String, Integer> map = new HashMap();
                for (GetInspectionProductDto o : inspectionProduct) {
                    map.put(o.getSkuId(), o.getDeliveryQuantity());
                }
                for (AddPurchaseRejectionDetailsVO o : purchaseRejectionDetailsList) {
                    Integer rejectQuantity = o.getRejectQuantity();
                    Integer deliveryQuantity = map.get(o.getSkuId());
                    if (deliveryQuantity == null) {
                        return new Result(9997, "对应到货单下无此sku，请确定后重填", null);
                    } else {
                        if (rejectQuantity > deliveryQuantity) {
                            return new Result(9997, o.getSkuId() + "的拒收数量大于到货数量,不符合常理，请重填！", null);
                        }
                    }
                }
            }
        }


        //校验VO的参数
        Result result = checkParaAdd(vo, bindingResult);
        if (result.getCode() != 200) {
            return result;
        }
        String time = vo.getPurchaseRejectionInfo().getRejectionDateStr();
        Date date = null;
        try {
            date = DateUtil.parse(time);
        } catch (Exception e) {
            logger.error("拒收日期[rejectionDateStr]格式不符合要求", e);
            return new Result(9999, "拒收日期[rejectionDateStr]格式不符合要求", null);
        }
        Integer year = DateUtil.year(date);
        if (year <= 2000) {
            return new Result(9999, "拒收日期[rejectionDateStr]必须大于2000年", null);
        }

        String rejectionId = Toolbox.randomUUID();
        PurchaseRejectionInfoEntity pRIEntity = new PurchaseRejectionInfoEntity();
        BeanUtils.copyProperties(vo.getPurchaseRejectionInfo(), pRIEntity);
        pRIEntity.setRejectionDate(date);
        pRIEntity.setRejectionId(rejectionId);
        pRIEntity.setDeleteStatus(0);
        pRIEntity.setCreateUser(userId);
        pRIEntity.setModifyUser(userId);
        Timestamp current = Time.getCurrentTimestamp();
        pRIEntity.setCreateTime(current);
        pRIEntity.setModifyTime(current);

        try {
            purchaseRejectionDao.insertPRI(pRIEntity);
        } catch (DuplicateKeyException e) {
            //新增时唯一性约束冲突，并发，提示重试
            return new Result(9999, "后台繁忙，请稍后重试", null);
        }


        // 获取触发器生成的退货单ID
        rejectionId = purchaseRejectionDao.findId(rejectionId);

        List<AddPurchaseRejectionDetailsVO> list = vo.getPurchaseRejectionDetailsList();
        if (list != null) {
            PurchaseRejectionDetailsEntity pRDEntity = new PurchaseRejectionDetailsEntity();
            for (AddPurchaseRejectionDetailsVO o : list) {
                BeanUtils.copyProperties(o, pRDEntity);
                String uuid = Toolbox.randomUUID();
                pRDEntity.setUuid(uuid);
                pRDEntity.setRejectionId(rejectionId);
                //备品数量
                if (pRDEntity.getSpareQuantity() == null) {
                    pRDEntity.setSpareQuantity(0);
                }
                purchaseRejectionDao.insertPRD(pRDEntity);
            }
        }
        return Result.success(rejectionId);
    }


    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager",propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result update(@Valid SetPurchaseRejectionVO vo, BindingResult bindingResult, String userId) {
        //校验sku的拒收数量与到货数量
        List<SetPurchaseRejectionDetailsVO> purchaseRejectionDetailsList = vo.getPurchaseRejectionDetailsList();
        String inspectionId = vo.getPurchaseRejectionInfo().getInspectionId();
        Result re = inspectionService.getInspection(inspectionId);
        if (re.getCode() != 200) {
            return new Result(9998, "到货通知单接口无法返回数据，无法确定校验到货数量与拒收数量", null);
        } else {
            if (purchaseRejectionDetailsList != null || purchaseRejectionDetailsList.size() != 0) {
                GetInspectionDto getInspectionDto = (GetInspectionDto) re.getData();
                List<GetInspectionProductDto> inspectionProduct = getInspectionDto.getInspectionProduct();
                Map<String, Integer> map = new HashMap();
                for (GetInspectionProductDto o : inspectionProduct) {
                    map.put(o.getSkuId(), o.getDeliveryQuantity());
                }
                for (SetPurchaseRejectionDetailsVO o : purchaseRejectionDetailsList) {
                    Integer rejectQuantity = o.getRejectQuantity();
                    Integer deliveryQuantity = map.get(o.getSkuId());
                    if (deliveryQuantity == null) {
                        return new Result(9997, "对应到货单下无此sku，请确定后重填", null);
                    } else {
                        if (rejectQuantity > deliveryQuantity) {
                            return new Result(9997, o.getSkuId() + "的拒收数量大于到货数量,不符合常理，请重填！", null);
                        }
                    }
                }
            }
        }

        //校验VO的参数
        Result result = checkParaUpdate(vo, bindingResult);
        if (result.getCode() != 200) {
            return result;
        }
        PurchaseRejectionInfoEntity pRIEntity = new PurchaseRejectionInfoEntity();
        BeanUtils.copyProperties(vo.getPurchaseRejectionInfo(), pRIEntity);
        String time = vo.getPurchaseRejectionInfo().getRejectionDateStr();
        Date date;

        try {
            date = DateUtil.parse(time);
        } catch (Exception e) {
            logger.error("拒收日期[rejectionDateStr]格式不符合要求", e);
            return new Result(9999, "拒收日期[rejectionDateStr]格式不符合要求", null);
        }

        Integer year = DateUtil.year(date);
        if (year <= 2000) {
            return new Result(9999, "拒收日期[rejectionDateStr]必须大于2000年", null);
        }

        pRIEntity.setRejectionDate(date);
        pRIEntity.setModifyUser(userId);
        pRIEntity.setModifyTime(Time.getCurrentTimestamp());
        purchaseRejectionDao.updatePRI(pRIEntity);

        List<SetPurchaseRejectionDetailsVO> list = vo.getPurchaseRejectionDetailsList();
        //物理删除上次旧数据
        purchaseRejectionDao.deletePRDByRejectionId(pRIEntity.getRejectionId());

        if (list != null) {
            for (SetPurchaseRejectionDetailsVO o : list) {
                PurchaseRejectionDetailsEntity pRDEntity = new PurchaseRejectionDetailsEntity();
                BeanUtils.copyProperties(o, pRDEntity);
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                pRDEntity.setUuid(uuid);
                pRDEntity.setRejectionId(pRIEntity.getRejectionId());
                //备品数量
                if (pRDEntity.getSpareQuantity() == null) {
                    pRDEntity.setSpareQuantity(0);
                }
                purchaseRejectionDao.insertPRD(pRDEntity);
            }
        }
        return Result.success();
    }

    @Override
    public Result changeStatus(String rejectionId, Integer statusCd) {
        purchaseRejectionDao.changeStatus(rejectionId, statusCd, Time.getCurrentDateTime());
        return Result.success();
    }


    @Override
    public Result findById(String rejectionId) {
        PurchaseRejectionInfoEntity pRIEntity = purchaseRejectionDao.getPRI(rejectionId);
        if (pRIEntity == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("purchaseRejectionInfo", new PurchaseRejectionInfoVO());
            map.put("purchaseRejectionDetailsList", new ArrayList<>());
            return Result.success(map);
        } else {

            List<PurchaseRejectionDetailsEntity> list = purchaseRejectionDao.getPRD(rejectionId);

            //调用外部接口 获取额外关联数据
            //人员
            List<String> paraList = new ArrayList<>();
            paraList.add(pRIEntity.getHandleUser());
            Map employeeResult;
            try {
                employeeResult = purchaseEmployeeService.getEmployeeNameMap(paraList);
            } catch (Exception e) {
                logger.error("调用人员管理出现错误", e);
                return new Result(9999, "调用人员管理出现错误！", null);
            }
            PurchaseRejectionInfoVO pRIVO = new PurchaseRejectionInfoVO();
            BeanUtils.copyProperties(pRIEntity, pRIVO);
            pRIVO.setRejectionDateStr(pRIEntity.getRejectionDate());
            pRIVO.setHandleUserName((String) employeeResult.get(pRIEntity.getHandleUser()));


            //供应商
            List<String> supplierIdsList = new ArrayList<>();
            supplierIdsList.add(pRIVO.getSupplierCd());
            Map map;
            try {
                map = supplierService.getSupplierNameMap(supplierIdsList);
            } catch (Exception e) {
                logger.error("调用外部供应商接口出错", e);
                return new Result(9999, "调用外部供应商接口出错！请稍后重试或者联系管理员", e);
            }
            pRIVO.setSupplierName((String) map.get(pRIVO.getSupplierCd()));


            //sku产品
            List<String> skuIdList = new ArrayList();
            for (PurchaseRejectionDetailsEntity o : list) {
                skuIdList.add(o.getSkuId());
            }
            Result productResult = productService.getProductCN(skuIdList);
            if (productResult.getCode() != 200) {
                return new Result(9999, "调用产品管理出现错误！", null);
            }
            Map productNameMap = (Map) productResult.getData();
            List<PurchaseRejectionDetailsVO> reList = poListToVO(list, productNameMap);


            Map<String, Object> reMap = new HashMap<>();
            reMap.put("purchaseRejectionInfo", pRIVO);
            reMap.put("purchaseRejectionDetailsList", reList);
            return Result.success(reMap);
        }
    }

    @Override
    public Result fuzzy(@Valid GetPurchaseRejectionVO vo, BindingResult bindingResult, Integer pageNum, Integer pageSize) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }

        //模糊查询
        List<PurchaseRejectionInfoEntity> list = null;
        try {
            list = getfuzzy(vo, pageNum, pageSize);
        } catch (Exception e) {
            logger.error("模糊查询失败", e);
            return new Result(9999, "模糊查询失败", e);
        }


        List<PurchaseRejectionInfoVO> result = new ArrayList<>();
        if (list.size() > 0) {

            List<String> supplierIdsList = new ArrayList<>();
            for (PurchaseRejectionInfoEntity o : list) {
                supplierIdsList.add(o.getSupplierCd());
            }
            Map map;
            try {
                map = supplierService.getSupplierNameMap(supplierIdsList);
            } catch (Exception e) {
                logger.error("调用外部供应商接口出错", e);
                return new Result(9999, "调用外部供应商接口出错！请稍后重试或者联系管理员", e);
            }

            //调用外部接口 获取额外关联数据
            List<String> paraList = new ArrayList<>();
            for (PurchaseRejectionInfoEntity o : list) {
                paraList.add(o.getHandleUser());
            }
            Map employeeResult;
            try {
                employeeResult = purchaseEmployeeService.getEmployeeNameMap(paraList);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(9999, "调用人员管理出现错误！", null);
            }

            result = new ArrayList<>();
            for (PurchaseRejectionInfoEntity obj : list) {
                PurchaseRejectionInfoVO pRIVO = new PurchaseRejectionInfoVO();
                BeanUtils.copyProperties(obj, pRIVO);
                pRIVO.setSupplierName((String) map.get(pRIVO.getSupplierCd()));
                pRIVO.setRejectionDateStr(obj.getRejectionDate());
                pRIVO.setHandleUserName((String) employeeResult.get(obj.getHandleUser()));
                result.add(pRIVO);
            }
        }

        Map<String, Object> reMap = new HashMap();
        if (pageNum == null || pageSize == null || pageNum < 1 || pageSize < 1) {
            reMap.put("total", list.size());
            reMap.put("pages", 1);
            reMap.put("productData", result);
        } else {
            PageInfo pageInfo = new PageInfo(list);
            long total = pageInfo.getTotal();
            int pages = pageInfo.getPages();
            reMap.put("total", total);
            reMap.put("pages", pages);
            reMap.put("purchaseRejectionInfoList", result);
        }

        return Result.success(reMap);
    }

    private List<PurchaseRejectionInfoEntity> getfuzzy(GetPurchaseRejectionVO vo, Integer pageNum, Integer pageSize) throws Exception {
        PurchaseRejectionInfoEntity pRIEntity = new PurchaseRejectionInfoEntity();
        BeanUtils.copyProperties(vo, pRIEntity);
        //时间处理
        try {
            pRIEntity.setRejectionDateStart(DateUtil.parse(vo.getRejectionDateStartStr()));
            pRIEntity.setRejectionDateEnd(DateUtil.parse(vo.getRejectionDateEndStr()));
        } catch (Exception e) {
            throw new Exception("拒收日期格式不符合要求");
        }

        List<PurchaseRejectionInfoEntity> resultList;
        if (pageNum == null || pageSize == null || pageNum < 1 || pageSize < 1) {
            resultList = purchaseRejectionDao.fuzzy(pRIEntity);
        } else {
            PageHelper.startPage(pageNum, pageSize);
            resultList = purchaseRejectionDao.fuzzy(pRIEntity);
        }
        return resultList;
    }

    @Override
    public Result delete(@Valid RejectionIdPara rejectionIdArray, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        List<String> list = rejectionIdArray.getRejectionIdArray();
        if (list != null) {
            for (String o : list) {
                purchaseRejectionDao.deletePRDByRejectionId(o);
                purchaseRejectionDao.deletePRI(o);
            }
        }
        return Result.success();
    }


    @Override
    public void export(HttpServletResponse response, RejectionIdPara rejectionIdArray) throws IOException {
        List<String> idList = rejectionIdArray.getRejectionIdArray();
        //参数校验
        if (idList == null) {
            doPromptException(response, "采购拒收单号[rejectionIdArray]集合不能为空", null);
            return;
        }
        String idPara = listToString(idList);
        Map papaMap = new HashMap();
        papaMap.put("rejectionId", idPara);

        List<GatherPurchaseRejectionEntity> list = purchaseRejectionDao.getDetail(papaMap);
        if (list.size() == 0) {
            //  输出空excel
            doExport(response, null);
            return;
        } else {


            //补充人员信息
            //调用外部接口 获取额外关联数据
            Map employeeMap;
            List<String> paraList = new ArrayList<>();
            for (GatherPurchaseRejectionEntity o : list) {
                paraList.add(o.getHandleUser());
            }
            try {
                employeeMap = purchaseEmployeeService.getEmployeeNameMap(paraList);
            } catch (Exception e) {
                String exceptionMsg = "调用人员管理出现错误！导出失败";
                logger.error(exceptionMsg, e);
                doPromptException(response, exceptionMsg, e);
                return;
            }

            //补充sku产品信息
            Map productNameMap;
            List<String> skuIdList = new ArrayList();
            for (GatherPurchaseRejectionEntity o : list) {
                skuIdList.add(o.getSkuId());
            }
            try {
                Result productResult = productService.getProductCN(skuIdList);
                if (productResult.getCode() != 200) {
                    String exceptionMsg = "调用产品管理出现错误！导出失败";
                    logger.error(exceptionMsg, productResult.getMsg() + "\n"
                            + productResult.getData()
                    );
                    doPromptException(response, exceptionMsg, productResult.getMsg() + "\n"
                            + productResult.getData());
                    return;
                } else {
                    productNameMap = (Map) productResult.getData();
                }
            } catch (Exception e) {
                String exceptionMsg = "调用产品管理出现错误！导出失败";
                doPromptException(response, exceptionMsg, e);
                return;
            }


            //补充供应商
            List<String> supplierIdsList = new ArrayList<>();
            for (GatherPurchaseRejectionEntity o : list) {
                supplierIdsList.add(o.getSupplierCd());
            }
            Map supplierNameMap;
            try {
                supplierNameMap = supplierService.getSupplierNameMap(supplierIdsList);
            } catch (Exception e) {
                logger.error("调用外部供应商接口出错", e);
                String exceptionMsg = "调用产品管理出现错误！导出失败";
                doPromptException(response, exceptionMsg, e);
                return;
            }

            //包装VO
            List<GatherPurchaseRejectionVO> result = new ArrayList();
            for (GatherPurchaseRejectionEntity obj : list) {
                GatherPurchaseRejectionVO vo = new GatherPurchaseRejectionVO();
                BeanUtils.copyProperties(obj, vo);
                //数值的null转为字符的空白显示
                vo.setRejectionDateStr(obj.getRejectionDate());
                if (obj.getDeleteStatus() == null) {
                    vo.setDeleteStatus("");
                } else {
                    vo.setDeleteStatus(obj.getDeleteStatus() + "");
                }

                if (obj.getSpareQuantity() == null) {
                    vo.setSpareQuantity("");
                } else {
                    vo.setSpareQuantity(obj.getSpareQuantity() + "");
                }

                if (obj.getRejectQuantity() == null) {
                    vo.setRejectQuantity("");
                } else {
                    vo.setRejectQuantity(obj.getRejectQuantity() + "");
                }

                if (obj.getAmountWithoutTax() == null) {
                    vo.setAmountWithoutTax("");
                } else {
                    vo.setAmountWithoutTax(obj.getAmountWithoutTax() + "");
                }

                if (obj.getAmountWithTax() == null) {
                    vo.setAmountWithTax("");
                } else {
                    vo.setAmountWithTax(obj.getAmountWithTax() + "");
                }

                if (obj.getUnitPriceWithoutTax() == null) {
                    vo.setUnitPriceWithoutTax("");
                } else {
                    vo.setUnitPriceWithoutTax(obj.getUnitPriceWithoutTax() + "");
                }

                if (obj.getAmountWithTax() == null) {
                    vo.setAmountWithTax("");
                } else {
                    vo.setAmountWithTax(obj.getAmountWithTax() + "");
                }

                if (obj.getTaxRate() == null) {
                    vo.setTaxRate("");
                } else {
                    vo.setTaxRate(obj.getTaxRate() + "");
                }

                if (obj.getUnitPriceWithTax() == null) {
                    vo.setUnitPriceWithTax("");
                } else {
                    vo.setUnitPriceWithTax(obj.getUnitPriceWithTax() + "");
                }


                vo.setHandleUserName((String) employeeMap.get(obj.getHandleUser()));
                vo.setSkuNameZh((String) productNameMap.get(obj.getSkuId()));
                vo.setSupplierName((String) supplierNameMap.get(obj.getSupplierCd()));

                result.add(vo);
            }
            //输出excel
            doExport(response, result);
        }
    }

    @Override
    public void fuzzyExport(HttpServletResponse response, GetPurchaseRejectionNewVO vo, BindingResult bindingResult, Integer pageNum, Integer pageSize) throws IOException {
        if (bindingResult.hasErrors()) {
            logger.info("模糊查询失败参数不符合规范", bindingResult.getFieldError().getDefaultMessage());
            doPromptException(response, bindingResult.getFieldError().getDefaultMessage(), null);
            return;
        }

        List<PurchaseRejectionInfoEntity> list;
        try {
            list = getNewfuzzy(vo, pageNum, pageSize);
        } catch (Exception e) {
            logger.error("模糊查询失败", e);
            doPromptException(response, "模糊查询失败", e);
            return;
        }

        if (list != null && list.size() != 0) {
            List<String> rejectionIdList = new ArrayList<>();
            for (PurchaseRejectionInfoEntity o : list) {
                rejectionIdList.add(o.getRejectionId());
            }
            RejectionIdPara rejectionIdPara = new RejectionIdPara();
            rejectionIdPara.setRejectionIdArray(rejectionIdList);
            export(response, rejectionIdPara);
            return;
        } else {  //list为null或者数量为0，即为没有数据
            // 输出空excel
            doExport(response, null);
            return;
        }
    }

    private List<PurchaseRejectionInfoEntity> getNewfuzzy(GetPurchaseRejectionNewVO vo, Integer pageNum, Integer pageSize) throws Exception {
        List<PurchaseRejectionInfoEntity> resultList = new ArrayList<>();
        String productName = vo.getProductName();
        String supplierName = vo.getSupplierName();
        List<String> skuIdList = new ArrayList<>();
        List<String> supplierList = new ArrayList<>();
        PurchaseRejectionInfoNewEntity pRIEntity = new PurchaseRejectionInfoNewEntity();
        BeanUtils.copyProperties(vo, pRIEntity);
        //时间处理
        try {
            pRIEntity.setRejectionDateStart(DateUtil.parse(vo.getRejectionDateStartStr()));
            pRIEntity.setRejectionDateEnd(DateUtil.parse(vo.getRejectionDateEndStr()));
            //如果是查询条件中为yyyy-MM-dd的形式，结尾加上一天
            if (vo.getRejectionDateEndStr() != null && vo.getRejectionDateEndStr().matches("\\d{4}-\\d{2}-\\d{2}")) {
                DateTime offsetDay = DateUtil.offsetMillisecond(DateUtil.offsetDay(pRIEntity.getRejectionDateEnd(), 1), -1);
                pRIEntity.setRejectionDateEnd(offsetDay);

            }
        } catch (Exception e) {
            throw new RuntimeException("拒收日期格式不符合要求");
        }
        if (StringUtils.isNotEmpty(productName)) {
            Result proResult = productSkuService.getProductSkuInfo(null, null, productName);
            if (proResult.getCode() != 200) {
                throw new RuntimeException("产品外部接口调用异常");
            }
            Map proResultMap = (Map) proResult.getData();
            List<Map> proMapList = (List<Map>) proResultMap.get("productData");
            if (proMapList == null || proMapList.size() <= 0) {
                return new ArrayList<>();
            }
            for (Map skuMap : proMapList) {
                String skuId = (String) skuMap.get("sku");
                skuIdList.add(skuId);
            }
        }
        if (supplierName != null) {
            try {
                Map map = supplierOutsideService.getSupplierList(supplierName, 1, Integer.MAX_VALUE);
                Integer count = (Integer) map.get("supplierCount");
                if (count.equals(0)) {
                    return new ArrayList<>();
                }
                List<Map> supplierMapList = (List<Map>) map.get("suppliers");
                for (Map supplierMap : supplierMapList) {
                    String supplierId = (String) supplierMap.get("supplierId");
                    supplierList.add(supplierId);
                }
            } catch (Exception e) {
                throw new RuntimeException("供应商接口调用异常");
            }
        }
        String skuIds = "";
        String suppliers = "";
        for (String skuId : skuIdList) {
            skuIds += "'" + skuId + "'" + ",";
        }
        if (skuIds.endsWith(",")) {
            int index = skuIds.lastIndexOf(",");
            skuIds = skuIds.substring(0, index);
        }
        if (StringUtils.isEmpty(skuIds)) {
            skuIds = null;
        }
        for (String supplierId : supplierList) {
            suppliers += "'" + supplierId + "'" + ",";
        }
        if (suppliers.endsWith(",")) {
            int index = suppliers.lastIndexOf(",");
            suppliers = suppliers.substring(0, index);
        }
        if (StringUtils.isEmpty(suppliers)) {
            suppliers = null;
        }
        String rejectBills = "";
        List<String> rejectBillList = null;
        if (productName != null) {
            if (vo.getSkuId() != null) {
                rejectBillList = purchaseRejectionDao.getRejectBillIdAndSkuId(skuIds, vo.getSkuId());
            } else {
                rejectBillList = purchaseRejectionDao.getRejectBillId(skuIds);
            }
            if (rejectBillList == null || rejectBillList.size() <= 0) {
                return new ArrayList<>();
            }
            for (String returnBillId : rejectBillList) {
                rejectBills += "'" + returnBillId + "'" + ",";
            }
            if (rejectBills.endsWith(",")) {
                int index = rejectBills.lastIndexOf(",");
                rejectBills = rejectBills.substring(0, index);
            }
        } else {
            if (vo.getSkuId() != null) {
                rejectBillList = purchaseRejectionDao.getRejectBillIdBySkuId(vo.getSkuId());
                if (rejectBillList == null || resultList.size() <= 0) {
                    return new ArrayList<>();
                }
                for (String returnBillId : rejectBillList) {
                    rejectBills += "'" + returnBillId + "'" + ",";
                }
                if (rejectBills.endsWith(",")) {
                    int index = skuIds.lastIndexOf(",");
                    rejectBills = rejectBills.substring(0, index);
                }
            }
        }
        if (StringUtils.isEmpty(rejectBills)) {
            rejectBills = null;
        }
        pRIEntity.setRejectBills(rejectBills);
        pRIEntity.setSupplierIds(suppliers);
        List<PurchaseRejectionInfoEntity> resultsList;
        if (pageNum == null || pageSize == null || pageNum < 1 || pageSize < 1) {
            resultsList = purchaseRejectionDao.fuzzyNew(pRIEntity);
        } else {
            PageHelper.startPage(pageNum, pageSize);
            resultsList = purchaseRejectionDao.fuzzyNew(pRIEntity);
        }
        return resultsList;
    }

    @Override
    public Result fuzzyNew(GetPurchaseRejectionNewVO vo, BindingResult bindingResult, Integer pageNum, Integer pageSize) {
        List<PurchaseRejectionInfoEntity> resultList = new ArrayList<>();
        String productName = vo.getProductName();
        String supplierName = vo.getSupplierName();
        List<String> skuIdList = new ArrayList<>();
        List<String> supplierList = new ArrayList<>();
        PurchaseRejectionInfoNewEntity pRIEntity = new PurchaseRejectionInfoNewEntity();
        BeanUtils.copyProperties(vo, pRIEntity);
        try {
            pRIEntity.setRejectionDateStart(DateUtil.parse(vo.getRejectionDateStartStr()));
            pRIEntity.setRejectionDateEnd(DateUtil.parse(vo.getRejectionDateEndStr()));
            //如果是查询条件中为yyyy-MM-dd的形式，结尾加上一天
            if (vo.getRejectionDateEndStr() != null && vo.getRejectionDateEndStr().matches("\\d{4}-\\d{2}-\\d{2}")) {
                DateTime offsetDay = DateUtil.offsetMillisecond(DateUtil.offsetDay(pRIEntity.getRejectionDateEnd(), 1), -1);
                pRIEntity.setRejectionDateEnd(offsetDay);

            }
        } catch (Exception e) {
            throw new RuntimeException("拒收日期格式不符合要求");
        }
        if (StringUtils.isNotEmpty(productName)) {
            Result proResult = productSkuService.getProductSkuInfo(null, null, productName);
            if (proResult.getCode() != 200) {
                throw new RuntimeException("产品外部接口调用异常");
            }
            Map proResultMap = (Map) proResult.getData();
            List<Map> proMapList = (List<Map>) proResultMap.get("productData");
            if (proMapList == null || proMapList.size() <= 0) {
                Map reMap = new HashMap();
                reMap.put("total", 0);
                reMap.put("pages", 1);
                reMap.put("purchaseRejectionInfoList", resultList);
                return Result.success(reMap);
            }
            for (Map skuMap : proMapList) {
                String skuId = (String) skuMap.get("sku");
                skuIdList.add(skuId);
            }
        }
        if (supplierName != null) {
            try {
                Map map = supplierOutsideService.getSupplierList(supplierName, 1, Integer.MAX_VALUE);
                Integer count = (Integer) map.get("supplierCount");
                if (count.equals(0)) {
                    Map reMap = new HashMap();
                    reMap.put("total", 0);
                    reMap.put("pages", 1);
                    reMap.put("purchaseRejectionInfoList", resultList);
                    return Result.success(reMap);
                }
                List<Map> supplierMapList = (List<Map>) map.get("suppliers");
                for (Map supplierMap : supplierMapList) {
                    String supplierId = (String) supplierMap.get("supplierId");
                    supplierList.add(supplierId);
                }
            } catch (Exception e) {
                throw new RuntimeException("供应商接口调用异常");
            }
        }
        String skuIds = "";
        String suppliers = "";
        for (String skuId : skuIdList) {
            skuIds += "'" + skuId + "'" + ",";
        }
        if (skuIds.endsWith(",")) {
            int index = skuIds.lastIndexOf(",");
            skuIds = skuIds.substring(0, index);
        }
        if (StringUtils.isEmpty(skuIds)) {
            skuIds = null;
        }
        for (String supplierId : supplierList) {
            suppliers += "'" + supplierId + "'" + ",";
        }
        if (suppliers.endsWith(",")) {
            int index = suppliers.lastIndexOf(",");
            suppliers = suppliers.substring(0, index);
        }
        if (StringUtils.isEmpty(suppliers)) {
            suppliers = null;
        }
        String rejectBills = "";
        List<String> rejectBillList = null;
        if (productName != null) {
            if (vo.getSkuId() != null) {
                rejectBillList = purchaseRejectionDao.getRejectBillIdAndSkuId(skuIds, vo.getSkuId());
            } else {
                rejectBillList = purchaseRejectionDao.getRejectBillId(skuIds);
            }
            if (rejectBillList == null || rejectBillList.size() <= 0) {
                Map reMap = new HashMap();
                reMap.put("total", 0);
                reMap.put("pages", 1);
                reMap.put("purchaseRejectionInfoList", resultList);
                return Result.success(reMap);
            }
            for (String returnBillId : rejectBillList) {
                rejectBills += "'" + returnBillId + "'" + ",";
            }
            if (rejectBills.endsWith(",")) {
                int index = rejectBills.lastIndexOf(",");
                rejectBills = rejectBills.substring(0, index);
            }
        } else {
            if (vo.getSkuId() != null) {
                rejectBillList = purchaseRejectionDao.getRejectBillIdBySkuId(vo.getSkuId());
                if (rejectBillList == null || rejectBillList.size() <= 0) {
                    Map reMap = new HashMap();
                    reMap.put("total", 0);
                    reMap.put("pages", 1);
                    reMap.put("purchaseRejectionInfoList", resultList);
                    return Result.success(reMap);
                }
                for (String returnBillId : rejectBillList) {
                    rejectBills += "'" + returnBillId + "'" + ",";
                }
                if (rejectBills.endsWith(",")) {
                    int index = rejectBills.lastIndexOf(",");
                    rejectBills = rejectBills.substring(0, index);
                }
            }
        }
        if (StringUtils.isEmpty(rejectBills)) {
            rejectBills = null;
        }
        pRIEntity.setRejectBills(rejectBills);
        pRIEntity.setSupplierIds(suppliers);
        List<PurchaseRejectionInfoEntity> list;
        if (pageNum == null || pageSize == null || pageNum < 1 || pageSize < 1) {
            list = purchaseRejectionDao.fuzzyNew(pRIEntity);
        } else {
            PageHelper.startPage(pageNum, pageSize);
            list = purchaseRejectionDao.fuzzyNew(pRIEntity);
        }
        List<PurchaseRejectionInfoVO> result = new ArrayList<>();
        if (list.size() > 0) {

            List<String> supplierIdsList = new ArrayList<>();
            for (PurchaseRejectionInfoEntity o : list) {
                supplierIdsList.add(o.getSupplierCd());
            }
            Map map;
            try {
                map = supplierService.getSupplierNameMap(supplierIdsList);
            } catch (Exception e) {
                logger.error("调用外部供应商接口出错", e);
                return new Result(9999, "调用外部供应商接口出错！请稍后重试或者联系管理员", e);
            }

            //调用外部接口 获取额外关联数据
            List<String> paraList = new ArrayList<>();
            for (PurchaseRejectionInfoEntity o : list) {
                paraList.add(o.getHandleUser());
            }
            Map employeeResult;
            try {
                employeeResult = purchaseEmployeeService.getEmployeeNameMap(paraList);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(9999, "调用人员管理出现错误！", null);
            }

            result = new ArrayList<>();
            for (PurchaseRejectionInfoEntity obj : list) {
                PurchaseRejectionInfoVO pRIVO = new PurchaseRejectionInfoVO();
                BeanUtils.copyProperties(obj, pRIVO);
                pRIVO.setSupplierName((String) map.get(pRIVO.getSupplierCd()));
                pRIVO.setRejectionDateStr(obj.getRejectionDate());
                pRIVO.setHandleUserName((String) employeeResult.get(obj.getHandleUser()));
                result.add(pRIVO);
            }
        }

        Map<String, Object> reMap = new HashMap();
        if (pageNum == null || pageSize == null || pageNum < 1 || pageSize < 1) {
            reMap.put("total", list.size());
            reMap.put("pages", 1);
            reMap.put("productData", result);
        } else {
            PageInfo pageInfo = new PageInfo(list);
            long total = pageInfo.getTotal();
            int pages = pageInfo.getPages();
            reMap.put("total", total);
            reMap.put("pages", pages);
            reMap.put("purchaseRejectionInfoList", result);
        }
        return Result.success(reMap);
    }


    private void doPromptException(HttpServletResponse response, String exceptionMsg, Object e) throws IOException {
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        Result result = new Result(9999, exceptionMsg, e);
        response.getWriter().print(result);
    }

    private void doExport(HttpServletResponse response, List<GatherPurchaseRejectionVO> list) throws IOException {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("采购退货单信息表");
            //新增数据行，并且设置单元格数据
            int rowNum = 1;
            String[] headers = {
                    "采购拒收单号", "日期", "供应商", "采购订单号", "到货通知单", "供应商送货单号", "备注", "处理人",
                    "SKU", "产品中文名", "拒收数量", "备品数", "批次", "不含税单价", "不含税金额", "税率(%)", "含税单价", "含税金额"
            };
            //headers表示excel表中第一行的表头

            HSSFRow row = sheet.createRow(0);
            //在excel表中添加表头

            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            if (null != list) {
                //在表中存放查询到的数据放入对应的列
                for (int i = 0; i < list.size(); i++) {
                    HSSFRow row1 = sheet.createRow(rowNum);
                    GatherPurchaseRejectionVO vo = list.get(i);
                    row1.createCell(0).setCellValue(list.get(i).getRejectionId());
                    row1.createCell(1).setCellValue(list.get(i).getRejectionDateStr());
                    row1.createCell(2).setCellValue(list.get(i).getSupplierName());
                    row1.createCell(3).setCellValue(list.get(i).getOrderId());
                    row1.createCell(4).setCellValue(list.get(i).getInspectionId());
                    row1.createCell(5).setCellValue(list.get(i).getSupplierDeliveryNum());
                    row1.createCell(6).setCellValue(list.get(i).getRemark());
                    row1.createCell(7).setCellValue(list.get(i).getHandleUserName());
                    row1.createCell(8).setCellValue(list.get(i).getSkuId());
                    row1.createCell(9).setCellValue(list.get(i).getSkuNameZh());
                    row1.createCell(10).setCellValue(list.get(i).getRejectQuantity());
                    row1.createCell(11).setCellValue(list.get(i).getSpareQuantity());
                    row1.createCell(12).setCellValue(list.get(i).getBatchNumber());
                    row1.createCell(13).setCellValue(list.get(i).getUnitPriceWithoutTax());
                    row1.createCell(14).setCellValue(list.get(i).getAmountWithoutTax());
                    row1.createCell(15).setCellValue(list.get(i).getTaxRate());
                    row1.createCell(16).setCellValue(list.get(i).getUnitPriceWithTax());
                    row1.createCell(17).setCellValue(list.get(i).getAmountWithTax());
                    rowNum++;
                }
            } else {
                HSSFRow row1 = sheet.createRow(1);
            }

            //
            response.setContentType("application/octet-stream");
            String fileName = "purchaseRejectionInfoTable" + ".xls";
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            //response.addHeader("Content-Disposition", "attachment;filename=" + new String("采购据收单.xls".getBytes(), "iso-8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            doPromptException(response, "导出过程出错！请稍后重试或者联系管理员", e);
        }
    }

    private String listToString(List<String> list) {
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


    private List<PurchaseRejectionDetailsVO> poListToVO(List<PurchaseRejectionDetailsEntity> list, Map productNameMap) {
        List reList = new ArrayList();
        for (PurchaseRejectionDetailsEntity obj : list) {
            PurchaseRejectionDetailsVO pRDVO = new PurchaseRejectionDetailsVO();
            BeanUtils.copyProperties(obj, pRDVO);
            pRDVO.setSkuNameZh((String) productNameMap.get(obj.getSkuId()));
            reList.add(pRDVO);
        }
        return reList;
    }

    private Result checkParaAdd(@Valid AddPurchaseRejectionVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }

        Result result2 = checkParaAdd(vo.getPurchaseRejectionInfo(), bindingResult);
        if (result2.getCode() != 200) {
            return result2;
        }

        List<AddPurchaseRejectionDetailsVO> list = vo.getPurchaseRejectionDetailsList();
        Result result3 = new Result();
        for (AddPurchaseRejectionDetailsVO o : list) {
            result3 = checkParaAdd(o, bindingResult);
            if (result3.getCode() != 200) {
                break;
            }
        }
        return result3;
    }

    private Result checkParaAdd(@Valid AddPurchaseRejectionInfoVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        return Result.success();
    }

    private Result checkParaAdd(@Valid AddPurchaseRejectionDetailsVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }

        //税率(%)
        Double taxRate = vo.getTaxRate();
        if (taxRate != null) {
            if (taxRate > 99.99) {
                return new Result(9999, "税率(%)[taxRate]超过额定数额，请修改", null);
            }
        }

        //不含税单价
        if (vo.getUnitPriceWithoutTax() != null) {
            if (vo.getUnitPriceWithoutTax() > 999999999.99) {
                return new Result(9999, "不含税单价[unitPriceWithoutTax]超过额定数额，请修改", null);
            }
        }
        //含税单价
        if (vo.getUnitPriceWithTax() != null) {
            if (vo.getUnitPriceWithTax() > 999999999.99) {
                return new Result(9999, "含税单价[unitPriceWithTax]超过额定数额，请修改", null);
            }
        }

        //含税单价
        Double unitPriceWithTax = vo.getUnitPriceWithTax();
        //不含税单价
        Double unitPriceWithoutTax = vo.getUnitPriceWithoutTax();
        if (unitPriceWithTax != null) {
            if (taxRate == null) {
                return new Result(9999, "税率(%)[taxRate]缺失，含税单价[unitPriceWithTax]无法计算", null);
            } else {
                if (unitPriceWithoutTax == null) {
                    return new Result(9999, "不含税单价[unitPriceWithoutTax]缺失，含税单价[unitPriceWithTax]无法计算", null);
                } /*else {
                    Double i = Arith.div(taxRate, 100, 2);
                    i = Arith.add(1, i);
                    i = Arith.mul(unitPriceWithoutTax, i);
                    i = Arith.round(i, 2);
                    if (!unitPriceWithTax.equals(i)) {
                        return new Result(9999, "含税单价[unitPriceWithTax]计算不通过，请修改", null);
                    }
                }*/
            }
        }
        if (unitPriceWithoutTax == null) {
            if (taxRate == null) {
                return new Result(9999, "税率(%)[taxRate]缺失,不含税单价[unitPriceWithoutTax]无法计算", null);
            } else {
                if (unitPriceWithTax == null) {
                    return new Result(9999, "含税单价[unitPriceWithTax]缺失，不含税单价[unitPriceWithoutTax]无法计算", null);
                } /*else {
                    Double i = Arith.div(taxRate, 100, 2);
                    i = Arith.add(1, i);
                    i = Arith.div(unitPriceWithTax, i, 2);
                    i = Arith.round(i, 2);
                    if (!unitPriceWithoutTax.equals(i)) {
                        return new Result(9999, "不含税单价[unitPriceWithoutTax]计算不通过，请修改", null);
                    }
                }*/
            }
        }

        if (vo.getUnitPriceWithoutTax() != null && vo.getRejectQuantity() != null) {
            Double amountWithoutTax = Arith.mul(vo.getUnitPriceWithoutTax(), vo.getRejectQuantity());
            if (!vo.getAmountWithoutTax().equals(Arith.round(amountWithoutTax, 2))) {
                return new Result(9999, "不含税金额数值计算不通过，请修改", null);
            }
            vo.setAmountWithoutTax(amountWithoutTax);
        }
        if (vo.getUnitPriceWithTax() != null && vo.getRejectQuantity() != null) {
            Double amountWithTax = Arith.mul(vo.getUnitPriceWithTax(), vo.getRejectQuantity());
            if (!vo.getAmountWithTax().equals(Arith.round(amountWithTax, 2))) {
                return new Result(9999, "含税金额数值计算不通过，请修改", null);
            }
        }
        return Result.success();
    }

    private Result checkParaUpdate(@Valid SetPurchaseRejectionVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }

        Result result2 = checkParaUpdate(vo.getPurchaseRejectionInfo(), bindingResult);
        if (result2.getCode() != 200) {
            return result2;
        }

        List<SetPurchaseRejectionDetailsVO> list = vo.getPurchaseRejectionDetailsList();
        Result result3 = new Result();
        for (SetPurchaseRejectionDetailsVO o : list) {
            result3 = checkParaUpdate(o, bindingResult);
            if (result3.getCode() != 200) {
                break;
            }
        }
        return result3;
    }

    private Result checkParaUpdate(@Valid SetPurchaseRejectionInfoVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        return Result.success();
    }

    private Result checkParaUpdate(@Valid SetPurchaseRejectionDetailsVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }

        //税率(%)
        Double taxRate = vo.getTaxRate();
        if (taxRate != null) {
            if (taxRate > 99.99) {
                return new Result(9999, "税率(%)[taxRate]超过额定数额，请修改", null);
            }
        }

        //不含税单价
        if (vo.getUnitPriceWithoutTax() != null) {
            if (vo.getUnitPriceWithoutTax() > 999999999.99) {
                return new Result(9999, "不含税单价[unitPriceWithoutTax]超过额定数额，请修改", null);
            }
        }
        //含税单价
        if (vo.getUnitPriceWithTax() != null) {
            if (vo.getUnitPriceWithTax() > 999999999.99) {
                return new Result(9999, "含税单价[unitPriceWithTax]超过额定数额，请修改", null);
            }
        }

        //含税单价
        Double unitPriceWithTax = vo.getUnitPriceWithTax();
        //不含税单价
        Double unitPriceWithoutTax = vo.getUnitPriceWithoutTax();
        if (unitPriceWithTax != null) {
            if (taxRate == null) {
                return new Result(9999, "税率(%)[taxRate]缺失，含税单价[unitPriceWithTax]无法计算", null);
            } else {
                if (unitPriceWithoutTax == null) {
                    return new Result(9999, "不含税单价[unitPriceWithoutTax]缺失，含税单价[unitPriceWithTax]无法计算", null);
                } else {
                    Double i = Arith.div(taxRate, 100, 2);
                    i = Arith.add(1, i);
                    i = Arith.mul(unitPriceWithoutTax, i);
                    i = Arith.round(i, 2);
                    Double unitPriceWithTaxTwo = Arith.round(unitPriceWithTax, 2);
                    if (!unitPriceWithTaxTwo.equals(i)) {
                        return new Result(9999, "含税单价[unitPriceWithTax]计算不通过，请修改", null);
                    }
                }
            }
        }
        if (unitPriceWithoutTax == null) {
            if (taxRate == null) {
                return new Result(9999, "税率(%)[taxRate]缺失,不含税单价[unitPriceWithoutTax]无法计算", null);
            } else {
                if (unitPriceWithTax == null) {
                    return new Result(9999, "含税单价[unitPriceWithTax]缺失，不含税单价[unitPriceWithoutTax]无法计算", null);
                } else {
                    Double i = Arith.div(taxRate, 100, 2);
                    i = Arith.add(1, i);
                    i = Arith.div(unitPriceWithTax, i, 2);
                    i = Arith.round(i, 2);
                    Double unitPriceWithoutTaxTwo = Arith.round(unitPriceWithoutTax, 2);
                    if (!unitPriceWithoutTaxTwo.equals(i)) {
                        return new Result(9999, "不含税单价[unitPriceWithoutTax]计算不通过，请修改", null);
                    }
                }
            }
        }

        if (vo.getUnitPriceWithoutTax() != null && vo.getRejectQuantity() != null) {
            Double amountWithoutTax = Arith.mul(vo.getUnitPriceWithoutTax(), vo.getRejectQuantity());
            if (!vo.getAmountWithoutTax().equals(Arith.round(amountWithoutTax, 2))) {
                return new Result(9999, "不含税金额数值计算不通过，请修改", null);
            }
        }
        if (vo.getUnitPriceWithTax() != null && vo.getRejectQuantity() != null) {
            Double amountWithTax = Arith.mul(vo.getUnitPriceWithTax(), vo.getRejectQuantity());
            if (!vo.getAmountWithTax().equals(Arith.round(amountWithTax, 2))) {
                return new Result(9999, "含税金额数值计算不通过，请修改", null);
            }
        }
        return Result.success();
    }

}
