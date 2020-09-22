package com.wisrc.quality.webapp.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.wisrc.quality.webapp.entity.InspectionItemCheckDetailsEntity;
import com.wisrc.quality.webapp.entity.InspectionItemsInfoEntity;
import com.wisrc.quality.webapp.entity.ProductInspectionInfoEntity;
import com.wisrc.quality.webapp.service.*;
import com.wisrc.quality.webapp.service.externalService.ExternalPurchaseService;
import com.wisrc.quality.webapp.dao.ProductInspectionInfoDao;
import com.wisrc.quality.webapp.utils.Result;
import com.wisrc.quality.webapp.utils.Time;
import com.wisrc.quality.webapp.vo.inspectionApply.InspectionDataVO;
import com.wisrc.quality.webapp.vo.inspectionItemCheckDetails.add.InspectionItemCheckDetailsVO;
import com.wisrc.quality.webapp.vo.inspectionItemsInfoVO.add.AddInspectionItemsInfoVO;
import com.wisrc.quality.webapp.vo.inspectionPersonnelInfo.add.AddInspectionPersonnelInfoVO;
import com.wisrc.quality.webapp.vo.productInspectionInfo.add.AddProductInspectionInfoVO;
import com.wisrc.quality.webapp.vo.productInspectionInfo.get.GetProductInspectionInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProductInspectionInfoImplService implements ProductInspectionInfoService {
    private final Logger logger = LoggerFactory.getLogger(ProductInspectionInfoImplService.class);
    private static final String INSPECTIONCDHEADER = "QC";
    //已完成编码
    private static final int completed_inspectionStatus_cd = 3;

    @Autowired
    private ProductInspectionInfoDao productInspectionInfoDao;
    @Autowired
    private OutArrivalNoticeService outArrivalNoticeService;
    @Autowired
    private OutOrderService outOrderService;
    @Autowired
    private InspectionApplyService inspectionApplyService;
    @Autowired
    private OutEmployeeService outEmployeeService;
    @Autowired
    private CodeAttrService codeAttrService;
    @Autowired
    private ExternalPurchaseService externalPurchaseService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result insert(@Valid AddProductInspectionInfoVO addProductInspectionInfoVO, String userId) {

        Result checkResult = check(addProductInspectionInfoVO);
        if (checkResult.getCode() != 200) {
            return checkResult;
        }

        //时间处理
        Date date;
        try {
            date = DateUtil.parse(addProductInspectionInfoVO.getInspectionDate());
        } catch (Exception e) {
            return new Result(9999, "拒收日期格式不符合要求", null);
        }
        int year = DateUtil.year(date);
        if (year <= 2000) {
            return new Result(9999, "拒收日期[rejectionDateStr]必须大于2000年", null);
        }
        Integer sourceTypeCd = addProductInspectionInfoVO.getSourceTypeCd();
        String purchaseOrderId = addProductInspectionInfoVO.getPurchaseOrderId();
        String sourceDocumentCd = addProductInspectionInfoVO.getSourceDocumentCd();
        if (sourceTypeCd == 2) {
            if (addProductInspectionInfoVO.getArrivalProductId() == null) {
                return new Result(9991, "数据来源为到货通知单时，到货通知单唯一编码[arrivalProductId]为必填参数）", null);
            }
        }
        if (sourceTypeCd == 3) {
            if (!purchaseOrderId.equals(sourceDocumentCd)) {
                return new Result(9999, "单据来源类型为采购订单时，采购订单与来源单据应该都为订单号", null);
            }
        }

        // 一 检验单基础
        ProductInspectionInfoEntity productInspectionInfoEntity = new ProductInspectionInfoEntity();
        BeanUtils.copyProperties(addProductInspectionInfoVO, productInspectionInfoEntity);
        productInspectionInfoEntity.setInspectionDate(date);
        //产品检验单号
        String inspectionCd;
        int yearShort = year - 2000;
        Result result = newInspectionCd(yearShort);
        if (result.getCode() != 200) {
            return result;
        } else {
            inspectionCd = (String) result.getData();
        }
        productInspectionInfoEntity.setInspectionCd(inspectionCd);
        productInspectionInfoEntity.setCreateUser(userId);
        //新增
        insertPIIE(productInspectionInfoEntity);

        // 二 检验单的质检人员
        List<AddInspectionPersonnelInfoVO> personList = addProductInspectionInfoVO.getInspectionPersonList();
        //新增
        insertPersonList(inspectionCd, personList);

        // 三 检验单记录
        List<AddInspectionItemsInfoVO> inspectionItemsList = addProductInspectionInfoVO.getInspectionItemsList();
        //新增
        insertInspectionItemsList(inspectionCd, inspectionItemsList);

        String arrivalProductId = addProductInspectionInfoVO.getArrivalProductId();
        //将产品检验单的状态回写到数据来源处
        chageSource(productInspectionInfoEntity, arrivalProductId);

        return Result.success(productInspectionInfoEntity.getInspectionCd());
    }

    private Result check(AddProductInspectionInfoVO vo) {
        //实际检验数量
        Integer actualInspectionQuantity = vo.getActualInspectionQuantity();
        //合格数量
        Integer qualifiedQuantity = vo.getQualifiedQuantity();
        //不合格数量
        Integer unqualifiedQuantity = vo.getUnqualifiedQuantity();
        //最终判定编码
        Integer finalDetermineCd = vo.getFinalDetermineCd();
        //检验类型编码
        Integer inspectionTypeCd = vo.getInspectionTypeCd();
        //检验状态  3:已完成
        Integer inspectionStatusCd = vo.getInspectionStatusCd();

        if (qualifiedQuantity == null && unqualifiedQuantity == null) {
            //无操作，放行
        } else if (qualifiedQuantity != null && unqualifiedQuantity != null && actualInspectionQuantity != null && (actualInspectionQuantity == qualifiedQuantity + unqualifiedQuantity)) {
            //无操作，放行
        } else {
            if (vo.getFinalDetermineCd() != 2) {
                return new Result(9991, "合格数与不合格数参数错误！请确认后重试。", null);
            }
        }
        //全检 1：抽检，2:全检
        if (inspectionTypeCd != null && inspectionTypeCd == 2) {
            //全检下  合格数加不合格数必须等于本次验货数，且状态改为已经完成
            if (!(qualifiedQuantity != null && unqualifiedQuantity != null && actualInspectionQuantity != null && (actualInspectionQuantity == qualifiedQuantity + unqualifiedQuantity) && inspectionStatusCd != null && inspectionStatusCd == 3)) {
                return new Result(9991, "全检下，合格数加不合格数必须等于本次验货数，且状态改为:已完成！请确认后重试。", null);
            }
        }

        //最终判定不为空
        if (finalDetermineCd != null) {
            if (vo.getFinalDetermineCd() != 2) {
                if (!(qualifiedQuantity != null && unqualifiedQuantity != null && actualInspectionQuantity != null && (actualInspectionQuantity == qualifiedQuantity + unqualifiedQuantity))) {
                    return new Result(9991, "确定了最终判定，合格数加不合格数必须等于本次验货数！请确认后重试。", null);
                }
            }
            if (inspectionStatusCd == null || !inspectionStatusCd.equals(3)) {
                return new Result(9991, "确定了最终判定，检验状态应该为:已完成！请确认后重试。", null);
            }
        }

        return Result.success();
    }


    private void insertPersonList(String inspectionCd, List<AddInspectionPersonnelInfoVO> personList) {
        Map<String, Object> map = new HashMap<>();
        map.put("inspectionCd", inspectionCd);
        for (AddInspectionPersonnelInfoVO o : personList) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            map.put("uuid", uuid);
            map.put("inspectionCd", inspectionCd);
            map.put("employeeId", o.getEmployeeId());
            productInspectionInfoDao.insertIPI(map);
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result update(String inspectionCd, @Valid AddProductInspectionInfoVO addProductInspectionInfoVO) {
        //判断这张单的状态
        //判断是否存在该单号
        LinkedHashMap inspectionInfo = productInspectionInfoDao.getIIByInspectionCd(inspectionCd);
        if (inspectionInfo == null) {
            return new Result(9999, "无该检验单，请确认后更新", null);
        }
        //检验状态
        Integer inspectionStatusCd = (Integer) inspectionInfo.get("inspectionStatusCd");
        if (inspectionStatusCd != null && inspectionStatusCd == completed_inspectionStatus_cd) {
            return new Result(9991, "该检验单已处于已完成状态，无法编辑", null);
        }


        Result checkResult = check(addProductInspectionInfoVO);
        if (checkResult.getCode() != 200) {
            return checkResult;
        }
        //时间处理
        Date date;
        try {
            date = DateUtil.parse(addProductInspectionInfoVO.getInspectionDate());
        } catch (Exception e) {
            return new Result(9999, "拒收日期格式不符合要求", null);
        }

        Integer sourceTypeCd = addProductInspectionInfoVO.getSourceTypeCd();
        String purchaseOrderId = addProductInspectionInfoVO.getPurchaseOrderId();
        String sourceDocumentCd = addProductInspectionInfoVO.getSourceDocumentCd();
        if (sourceTypeCd == 2) {
            if (addProductInspectionInfoVO.getArrivalProductId() == null) {
                return new Result(9991, "数据来源为到货通知单时，到货通知单唯一编码[arrivalProductId]为必填参数）", null);
            }
        }
        if (sourceTypeCd == 3) {
            if (!purchaseOrderId.equals(sourceDocumentCd)) {
                return new Result(9991, "单据来源类型为采购订单时，采购订单与来源单据应该都为订单号", null);
            }
        }

        // 一 检验单基础信息
        ProductInspectionInfoEntity productInspectionInfoEntity = new ProductInspectionInfoEntity();
        BeanUtils.copyProperties(addProductInspectionInfoVO, productInspectionInfoEntity);
        productInspectionInfoEntity.setInspectionDate(date);
        //产品检验单号
        productInspectionInfoEntity.setInspectionCd(inspectionCd);
        updatePIIE(productInspectionInfoEntity);

        //二 质检人员
        //删除上次的质检人员
        productInspectionInfoDao.deleteIPIByInspectionCd(inspectionCd);
        //检验单的质检人员
        List<AddInspectionPersonnelInfoVO> personList = addProductInspectionInfoVO.getInspectionPersonList();
        insertPersonList(inspectionCd, personList);

        // 三 检验单记录与数据
        // 记录与data都是List的元素，数量不确定，唯一性不好确定
        // 干脆更新本次记录与data的前提是先删除上次的记录与data，然后本次新增
        productInspectionInfoDao.deleteDetailsByInspectionCd(inspectionCd);
        productInspectionInfoDao.deleteItemsByInspectionCd(inspectionCd);
        List<AddInspectionItemsInfoVO> inspectionItemsList = addProductInspectionInfoVO.getInspectionItemsList();
        insertInspectionItemsList(inspectionCd, inspectionItemsList);

        String arrivalProductId = addProductInspectionInfoVO.getArrivalProductId();
        chageSource(productInspectionInfoEntity, arrivalProductId);


        return Result.success();
    }

    //将产品检验单的状态回写到数据来源处
    private void chageSource(ProductInspectionInfoEntity entity, String arrivalProductId) {

        if (entity.getInspectionStatusCd() != null && entity.getInspectionStatusCd() == 3) {
            //单sku实际验货总数量，总合格数，总不合格数
            ProductInspectionInfoEntity sumEntity = productInspectionInfoDao.getSUMDetail(entity);
            if (entity.getSourceTypeCd() == 1) {
                //验货申请单
                //修改验货申请单的状态，验货数量，合格数，不合格数
                Map map = new HashMap();
                //验货申请单号
                map.put("inspectionId", entity.getSourceDocumentCd());
                //sku
                map.put("skuId", entity.getSkuId());
                //实际验货数量
                map.put("inspectionQuantity", sumEntity.getActualInspectionQuantity());
                //合格数
                map.put("qualifiedQuantity", sumEntity.getQualifiedQuantity());
                //不合格数
                map.put("unqualifiedQuantity", sumEntity.getUnqualifiedQuantity());
                //实际检验的总数量  如果大于  申请检验数量 就要把验货单状态改为已经完成
                if (sumEntity.getActualInspectionQuantity() >= entity.getApplyInspectionQuantity()) {
                    map.put("statusCd", 3);
                }
                //申请验货数量不准确 就要把验货单状态改为已经完成
                if (entity.getChangeReasonCd() != null && entity.getChangeReasonCd() == 1) {
                    map.put("statusCd", 3);
                }
                productInspectionInfoDao.modifyIADI(map);
            }
            if (entity.getInspectionStatusCd() != null && entity.getSourceTypeCd() == 2) {
                Map map = new HashMap();
                ProductInspectionInfoEntity sumEntity1 = productInspectionInfoDao.getSUMDetailTakeoutfinal2(entity);
                map.put("arrivalProductId", arrivalProductId);
                map.put("inspectionQuantity", sumEntity1.getActualInspectionQuantity());
                map.put("qualifiedQualified", sumEntity1.getQualifiedQuantity());
                map.put("unqualifiedQuantity", sumEntity1.getUnqualifiedQuantity());
                if (entity.getFinalDetermineCd() == null || (entity.getFinalDetermineCd() != null && entity.getFinalDetermineCd() != 2)) {
                    externalPurchaseService.getArrivalProduct(map);
                }

                String arrivalId = entity.getSourceDocumentCd();

                String sku = entity.getSkuId();
                int countActualQuantity = 0;

                //如果是抽检的情况
                if (entity.getInspectionTypeCd() != null && entity.getInspectionTypeCd().equals(1)) {
                    if (entity.getFinalDetermineCd() == null) {
                        entity.setFinalDetermineCd(0);
                    }
                    //如果是特采或允收，改到货通知单sku状态为待入库
                    if ((entity.getFinalDetermineCd().equals(4) || entity.getFinalDetermineCd().equals(1))) {
                        outArrivalNoticeService.changeStatus(arrivalId, sku, 3);
                        //如果是退货则变成已完成
                    } else if (entity.getFinalDetermineCd().equals(3)) {
                        outArrivalNoticeService.changeStatus(arrivalId, sku, 4);
                    }
                } else {
                    List<ProductInspectionInfoEntity> arrivalEntity = productInspectionInfoDao.getByArrivalIdandSku(arrivalId, sku);
                    for (ProductInspectionInfoEntity one : arrivalEntity) {
                        if (one.getFinalDetermineCd() == null || one.getFinalDetermineCd() != 2) {
                            countActualQuantity += one.getActualInspectionQuantity();
                        }
                    }
                    //全检的时候如果验货数量累加等于提货数量时改sku状态为待入库
                    if (countActualQuantity >= entity.getApplyInspectionQuantity()) {
                        Result result = outArrivalNoticeService.changeStatus(arrivalId, sku, 3);
                        System.out.println(result);
                    }
                }
            }
        }
    }


    private void updatePIIE(ProductInspectionInfoEntity productInspectionInfoEntity) {
        if (productInspectionInfoEntity.getSourceTypeCd() != 2) {
            //非到货通知单  无arrivalProductId
            productInspectionInfoEntity.setArrivalProductId(null);
        }
        double temp = 0;
        //外箱长度
        if (productInspectionInfoEntity.getCartonLength() == null) {
            productInspectionInfoEntity.setCartonLength(temp);
        }
        //    //外箱宽度
        if (productInspectionInfoEntity.getCartonWidth() == null) {
            productInspectionInfoEntity.setCartonWidth(temp);
        }
        //    //外箱高度
        if (productInspectionInfoEntity.getCartonHeight() == null) {
            productInspectionInfoEntity.setCartonHeight(temp);
        }
        //    //毛重
        if (productInspectionInfoEntity.getCrossWeight() == null) {
            productInspectionInfoEntity.setCrossWeight(temp);
        }
        productInspectionInfoEntity.setUpdateTime(Time.getCurrentDateTime());
        productInspectionInfoDao.updatePIIE(productInspectionInfoEntity);
    }

    //新增检验记录与他的子数据
    private void insertInspectionItemsList(String inspectionCd, List<AddInspectionItemsInfoVO> inspectionRecordList) {
        InspectionItemsInfoEntity iIIE = new InspectionItemsInfoEntity();
        iIIE.setInspectionCd(inspectionCd);
        for (AddInspectionItemsInfoVO o : inspectionRecordList) {
            //记录
            BeanUtils.copyProperties(o, iIIE);
            iIIE.setItemResultCd(o.getInspectionResultCd());
            String inspectionItemId = UUID.randomUUID().toString().replaceAll("-", "");
            iIIE.setInspectionItemId(inspectionItemId);
            productInspectionInfoDao.insertIIE(iIIE);

            //检验单记录中的项目的集合数据
            List<InspectionItemCheckDetailsVO> dataInfoVOList = o.getItemCheckDetailsList();
            if (dataInfoVOList != null) {
                InspectionItemCheckDetailsEntity iICDE = new InspectionItemCheckDetailsEntity();
                //外键关联
                iICDE.setInspectionItemId(inspectionItemId);
                for (InspectionItemCheckDetailsVO o1 : dataInfoVOList) {
                    BeanUtils.copyProperties(o1, iICDE);
                    String uuidTemp = UUID.randomUUID().toString().replaceAll("-", "");
                    iICDE.setUuid(uuidTemp);
                    productInspectionInfoDao.insertIICDE(iICDE);
                }
            }
        }
    }


    @Override
    public Result getByInspectionCd(String inspectionCd) {
        //产品检验基础信息
        LinkedHashMap<String, Object> inspectionInfo;
        //产品检验人员
        List<LinkedHashMap> personList;
        //检验单项目数据
        List<Map> itemsList;


        inspectionInfo = productInspectionInfoDao.getIIByInspectionCd(inspectionCd);
        if (inspectionInfo != null) {
            personList = productInspectionInfoDao.getPersonListByInspectionCd(inspectionCd);
            itemsList = productInspectionInfoDao.getItemsListByInspectionCd(inspectionCd);
            //查询检验单项目的名称
            List<LinkedHashMap> itemAttList = codeAttrService.getIIA();
            for (Map o : itemsList) {
                for (LinkedHashMap o1 : itemAttList) {
                    if (o.get("itemsCd").equals(o1.get("itemsCd"))) {
                        o.put("itemsDesc", o1.get("itemsDesc"));
                        break;
                    }
                }
            }

            String para = getParaOne(itemsList);
            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("inspectionItemId", para);
            List<LinkedHashMap> dataList = productInspectionInfoDao.getDataList(paraMap);

            //包装VO
            for (Map o : itemsList) {
                String inspectionItemId = (String) o.get("inspectionItemId");
                String temp;
                List<LinkedHashMap> itemCheckDetailsList = new ArrayList<>();
                for (LinkedHashMap o1 : dataList) {
                    temp = (String) o1.get("inspectionItemId");
                    if (temp.equals(inspectionItemId)) {
                        itemCheckDetailsList.add(o1);
                    }
                }
                o.put("itemCheckDetailsList", itemCheckDetailsList);
            }
            inspectionInfo.put("inspectionPersonList", personList);
            inspectionInfo.put("inspectionItemsList", itemsList);

            return Result.success(inspectionInfo);
        } else {
            return new Result(9999, "无该检验单号的检验单", null);
        }


    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result deleteByInspectionCd(String inspectionCd) {
        //判断这张单的状态
        //判断是否存在该单号
        LinkedHashMap inspectionInfo = productInspectionInfoDao.getIIByInspectionCd(inspectionCd);
        if (inspectionInfo == null) {
            //没有这张单，也符合用户删除的意愿
            return Result.success();
        }
        //检验状态
        Integer inspectionStatusCd = (Integer) inspectionInfo.get("inspectionStatusCd");
        if (inspectionStatusCd != null && inspectionStatusCd == completed_inspectionStatus_cd) {
            return new Result(9991, "该检验单已处于已完成状态，无法删除", null);
        }

        //  删除质检人员
        productInspectionInfoDao.deleteIPIByInspectionCd(inspectionCd);
        // 删除检验单项目数据
        productInspectionInfoDao.deleteDetailsByInspectionCd(inspectionCd);
        productInspectionInfoDao.deleteItemsByInspectionCd(inspectionCd);
        // 删除检验单基础信息
        productInspectionInfoDao.deletePIIByInspectionCd(inspectionCd);
        return Result.success();
    }

    @Override
    public Result fuzzyFind(@Valid GetProductInspectionInfoVO vo, Integer pageNum, Integer pageSize) {

        //获取检验单基础信息
        List<ProductInspectionInfoEntity> list = productInspectionInfoDao.fuzzyFind(vo);

        //获取人员
        List<LinkedHashMap> personList = getPersonListByList(list);
        //获取人员id与姓名
        Map employeeMap = null;
        if (personList == null || personList.size() == 0) {
            employeeMap = new HashMap();
        } else {
            try {
                employeeMap = getEmployeeMap(personList);
            } catch (Exception e) {
                return new Result(9999, "调用外部人员查询接口出错", e);
            }
        }


        //验货申请单
        List<InspectionDataVO> inspectionDataList = new ArrayList<>();
        //到货通知单
        List<LinkedHashMap> arrivalDataList = new ArrayList<>();
        //采购订单
        List<LinkedHashMap> orderNeetList = new ArrayList<>();
        Integer sourceTypeCd = vo.getSourceTypeCd();
        //查询外部接口
        try {
            if (sourceTypeCd == null) {
                inspectionDataList = getInspectionDataList(vo);
                arrivalDataList = getArrivalDataList(vo);
                orderNeetList = getOrderNeetList(vo);
            } else if (sourceTypeCd == 1) {
                inspectionDataList = getInspectionDataList(vo);
            } else if (sourceTypeCd == 2) {
                arrivalDataList = getArrivalDataList(vo);
            } else if (sourceTypeCd == 3) {
                orderNeetList = getOrderNeetList(vo);
            } else {
                return new Result(9999, "单据来源类型编码:" + sourceTypeCd + ",属于非法参数，无法为您提供服务，请确认后重试", null);
            }
        } catch (Exception e) {
            logger.info("查询验货申请单，采购订单，到货通知单信息失败，请稍后再试，若长时间未响应请联系管理员", e);
            return new Result(9999, "查询验货申请单，采购订单，到货通知单信息失败，请稍后再试，若长时间未响应请联系管理员", e);
        }

        //检验状态码表
        List<LinkedHashMap> iSAList = codeAttrService.getISA();
        //检验最终判定码表
        List<LinkedHashMap> iFDAList = codeAttrService.getIFDA();
        //单据来源码表
        List<LinkedHashMap> iSTAlist = codeAttrService.getISTA();

        //包装VO
        List<Map> voList = createVO(list, sourceTypeCd, inspectionDataList, arrivalDataList, orderNeetList, personList, employeeMap, iSAList, iFDAList, iSTAlist);

        ////根据前端打包VO分页
        Map<String, Object> map = new HashMap();
        int total = voList.size();
        if (pageSize == null || pageSize < 1) {
            map.put("total", total);
            map.put("pages", 1);
            map.put("productInspectionInfoData", voList);
            return Result.success(map);
        }
        int pages = (total) % (pageSize) > 0 ? (total) / (pageSize) + 1 : (total) / (pageSize);
        int start = (pageNum - 1) * (pageSize);
        int end = (pageNum) * (pageSize) - 1;
        if (end > (total - 1)) {
            end = total - 1;
        }
        List<Map> newList = new ArrayList<>();
        if (start == end) {
            newList.add(voList.get(start));
        } else {
            newList = voList.subList(start, (end + 1));
        }
        map.put("total", total);
        map.put("pages", pages);
        map.put("productInspectionInfoData", newList);
        return Result.success(map);
    }

    @Override
    public Map fuzzyFindTwo(List<String> skuIds, String arrivalId) {
        String skuIdsPara = getParaTwo(skuIds);
        Map map = new LinkedHashMap();
        map.put("sourceDocumentCd", arrivalId);
        map.put("skuId", skuIdsPara);
//      List<LinkedHashMap> list = productInspectionInfoDao.fuzzyFindTwo(map);
//      List<LinkedHashMap> list = productInspectionInfoDao.fuzzyFindThree(map);
        List<LinkedHashMap> list = productInspectionInfoDao.fuzzyFindFour(map);
        Map resultMap = new HashMap();
        for (String o : skuIds) {
            Integer sumQualifiedQualified = 0;
            Integer sunUnqualifiedQualified = 0;
            Integer sumInspectionQuantity = 0;
            for (LinkedHashMap o1 : list) {
                String skuId = (String) o1.get("skuId");
                if (o.equals(skuId)) {
                    Integer inspectionQuantity = (Integer) o1.get("inspectionQuantity");
                    Integer qualifiedQualified = (Integer) o1.get("qualifiedQualified");
                    Integer unqualifiedQualified = (Integer) o1.get("unqualifiedQuantity");
                    Integer finalDetermineCd = (Integer) o1.get("finalDetermineCd");

                    if (finalDetermineCd != null && finalDetermineCd == 2) {
                        inspectionQuantity = 0;
                    }
                    if (qualifiedQualified == null) {
                        qualifiedQualified = 0;
                    }
                    if (unqualifiedQualified == null) {
                        unqualifiedQualified = 0;
                    }
                    sumInspectionQuantity += inspectionQuantity;
                    sumQualifiedQualified += qualifiedQualified;
                    sunUnqualifiedQualified += unqualifiedQualified;
                    o1.put("inspectionQuantity", sumInspectionQuantity);
                    o1.put("qualifiedQualified", sumQualifiedQualified);
                    o1.put("unqualifiedQuantity", sunUnqualifiedQualified);
                    resultMap.put(o, o1);
                }
            }
            sumInspectionQuantity = 0;
            sumQualifiedQualified = 0;
            sunUnqualifiedQualified = 0;
        }
        return resultMap;
    }


    private List<ProductInspectionInfoEntity> fuzzyFindPIList(@Valid GetProductInspectionInfoVO vo, Integer pageNum, Integer pageSize) {
        List<ProductInspectionInfoEntity> resultList;
        if (pageNum == null || pageSize == null || pageNum < 1 || pageSize < 1) {
            resultList = productInspectionInfoDao.fuzzyFind(vo);
        } else {
            PageHelper.startPage(pageNum, pageSize);
            resultList = productInspectionInfoDao.fuzzyFind(vo);
        }
        return resultList;
    }

    private List<Map> createVO(List<ProductInspectionInfoEntity> list, Integer sourceTypeCd, List<InspectionDataVO> inspectionDataList, List<LinkedHashMap> arrivalDataList, List<LinkedHashMap> orderNeetList, List<LinkedHashMap> personList, Map employeeMap, List<LinkedHashMap> iSAList, List<LinkedHashMap> iFDAList, List<LinkedHashMap> iSTAlist) {

        //检验状态码表Map
        Map iSAListMap = new HashMap();
        for (LinkedHashMap o : iSAList) {
            iSAListMap.put(o.get("inspectionStatusCd"), o.get("inspectionStatusDesc"));
        }
        //最终判定码表Map
        Map iFDAMap = new HashMap();
        for (LinkedHashMap o : iFDAList) {
            iFDAMap.put(o.get("finalDetermineCd"), o.get("finalDetermineDesc"));
        }
        //单据来源码表Map
        Map iSTAMap = new HashMap();
        for (LinkedHashMap o : iSTAlist) {
            iSTAMap.put(o.get("sourceTypeCd"), o.get("sourceTypeDesc"));
        }

        List<Map> resultList = new ArrayList<>();
        for (ProductInspectionInfoEntity o : list) {
            Map<String, Object> map = new LinkedHashMap();

            //产品检验单号
            map.put("inspectionCd", o.getInspectionCd());
            //采购订单号
            map.put("purchaseOrderId", o.getPurchaseOrderId());
            //检验状态编码
            map.put("inspectionStatusCd", o.getInspectionStatusCd());
            //检验状态编码
            map.put("inspectionStatusDesc", iSAListMap.get(o.getInspectionStatusCd()));
            //日期
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            map.put("inspectionDate", df.format(o.getInspectionDate()));
            //SKU
            map.put("skuId", o.getSkuId());
            //来源单据类型
            map.put("sourceTypeCd", o.getSourceTypeCd());
            //来源单据单号
            map.put("sourceDocumentCd", o.getSourceDocumentCd());
            //来源单据描述
            map.put("sourceTypeDesc", iSTAMap.get(o.getSourceTypeCd()));
            //本次验货数
            map.put("actualInspectionQuantity", o.getActualInspectionQuantity());
            //最终判定编码
            map.put("finalDetermineCd", o.getFinalDetermineCd());
            //最终判定描述
            map.put("finalDetermineDesc", iFDAMap.get(o.getFinalDetermineCd()));
            //合格数
            map.put("qualifiedQuantity", o.getQualifiedQuantity());
            // 不合格数
            map.put("unqualifiedQuantity", o.getUnqualifiedQuantity());
            //品质消耗数
            map.put("qualityConsumption", o.getQualityConsumption());

            //质检人员
            List<Map> inspectionPersonnelInfoList = new ArrayList<>();
            for (LinkedHashMap o1 : personList) {
                String inspectionCdTemp = (String) o1.get("inspectionCd");
                if (inspectionCdTemp.equals(o.getInspectionCd())) {
                    Map<String, Object> rmap = new HashMap();
                    rmap.put("employeeId", o1.get("employeeId"));
                    rmap.put("employeeName", employeeMap.get(o1.get("employeeId")));
                    inspectionPersonnelInfoList.add(rmap);
                }
            }
            map.put("inspectionPersonList", inspectionPersonnelInfoList);

            //采购订单号（业务逻辑要求不可能为null）
            String purchaseOrderId = o.getPurchaseOrderId();
            //来源单据编码 (业务逻辑要求不可能为null)
            String sourceDocumentCd = o.getSourceDocumentCd();
            //库存sku（业务逻辑要求不可能为null）
            String skuId = o.getSkuId();

            //验货申请单
            if (sourceTypeCd == null || sourceTypeCd == 1) {
                for (InspectionDataVO o1 : inspectionDataList) {
                    String orderIdTemp = o1.getOrderId();
                    String skuIdTemp = o1.getSkuId();
                    String inspectionIdTemp = o1.getInspectionId();
                    if (purchaseOrderId.equals(orderIdTemp)
                            && inspectionIdTemp.equals(sourceDocumentCd)
                            && skuId.equals(skuIdTemp)) {
                        //产品中文名
                        map.put("productNameCN", o1.getProductNameCN());
                        //供应商ID
                        map.put("supplierId", o1.getSupplierId());
                        //供应商名称
                        map.put("supplierName", o1.getSupplierName());
                        //供应商地址 只有验货申请单才有供应商地址
                        map.put("supplierAddr", o1.getSupplierAddr());
                        break;
                    }
                }
            }
            //到货通知单========================
            if (sourceTypeCd == null || sourceTypeCd == 2) {
                for (Map o1 : arrivalDataList) {
                    String orderIdTemp = (String) o1.get("orderId");
                    String arrivalIdTemp = (String) o1.get("arrivalId");
                    String skuIdTemp = (String) o1.get("skuId");
                    if (purchaseOrderId.equals(orderIdTemp)
                            && sourceDocumentCd.equals(arrivalIdTemp)
                            && skuId.equals(skuIdTemp)) {
                        //产品中文名
                        map.put("productNameCN", o1.get("productName"));
                        //供应商ID
                        map.put("supplierId", o1.get("supplierId"));
                        //供应商名称
                        map.put("supplierName", o1.get("supplierName"));
                        if (map.get("supplierAddr") == null) {
                            //供应商地址
                            map.put("supplierAddr", "");
                        }
                        break;
                    }
                }
            }
            //到货通知单========================
            //采购订单订单
            if (sourceTypeCd == null || sourceTypeCd == 3) {
                for (Map o1 : orderNeetList) {
                    String orderIdTemp = (String) o1.get("orderId");
                    String skuIdTemp = (String) o1.get("skuId");
                    if (purchaseOrderId.equals(orderIdTemp)
                            //采购订单的订单号 + 来源单据号（就是订单号） + sku
                            && sourceDocumentCd.equals(orderIdTemp)
                            && skuId.equals(skuIdTemp)) {
                        //产品中文名
                        map.put("productNameCN", o1.get("productNameCN"));
                        //供应商ID
                        map.put("supplierId", o1.get("supplierId"));
                        //供应商名称
                        map.put("supplierName", o1.get("supplierName"));
                        if (map.get("supplierAddr") == null) {
                            //供应商地址
                            map.put("supplierAddr", "");
                        }
                        break;
                    }
                }
            }
            //如果产品中文名与供应商名称为null，说明前端的关键字（产品中文名与供应商名称）模糊查询无结果，该对象不符合要求
            if (map.get("productNameCN") != null || map.get("supplierName") != null) {
                map = solve(map);
                resultList.add(map);
            }
        }

        return resultList;
    }

    //有些假数据（字段，如产品中文名，供应商ID）是不存在与 验货申请单，到货通知单，采购订单关联的，但是也是需要输出前端VO
    private Map<String, Object> solve(Map<String, Object> map) {
        if (map.get("productNameCN") == null) {
            map.put("productNameCN", "");
        }
        if (map.get("supplierId") == null) {
            //供应商ID
            map.put("supplierId", "");
        }
        if (map.get("supplierName") == null) {
            //供应商名称
            map.put("supplierName", "");
        }
        if (map.get("supplierAddr") == null) {
            //供应商地址
            map.put("supplierAddr", "");
        }
        return map;
    }


    private List<LinkedHashMap> getPersonListByList(List<ProductInspectionInfoEntity> list) {
        List<String> inspectionCdList = new ArrayList<>();
        for (ProductInspectionInfoEntity o : list) {
            inspectionCdList.add(o.getInspectionCd());
        }
        String inspectionCdPara = getParaTwo(inspectionCdList);
        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("inspectionCd", inspectionCdPara);
        List<LinkedHashMap> personList = productInspectionInfoDao.getPersonList(paraMap);
        return personList;
    }

    private Map getEmployeeMap(List<LinkedHashMap> personList) throws Exception {
        if (personList == null || personList.size() == 0) {
            return new HashMap();
        } else {
            List<String> employeeIdList = new ArrayList();
            for (LinkedHashMap o : personList) {
                if (o.get("employeeId") != null) {
                    employeeIdList.add((String) o.get("employeeId"));
                }
            }
            if (employeeIdList == null || employeeIdList.size() == 0) {
                return new HashMap();
            } else {
                String[] batchEmployeeId = employeeIdList.toArray(new String[employeeIdList.size()]);
                System.out.println(batchEmployeeId);
                Result employeeNameResult;
                try {
                    employeeNameResult = outEmployeeService.getEmployeeNameBatch(batchEmployeeId);
                } catch (Exception e) {
                    logger.info("调用外部人员查询接口出错", e);
                    throw new Exception("调用外部人员查询接口出错");
                }
                if (employeeNameResult.getCode() == 200) {
                    List<Map> employeeList = (List) employeeNameResult.getData();
                    Map employeeMap = new HashMap();
                    for (Map employee : employeeList) {
                        employeeMap.put(employee.get("employeeId"), employee.get("employeeName"));
                    }
                    return employeeMap;
                } else {
                    logger.info("调用外部人员查询接口出错错误码code:" + employeeNameResult.getCode() + "错误码msg" + employeeNameResult.getMsg());
                    throw new Exception("调用外部人员查询接口出错");
                }
            }
        }
    }

    private List<LinkedHashMap> getArrivalDataList(@Valid GetProductInspectionInfoVO vo) throws Exception {
        Result arrivalResult = getArrivalFuzzy(vo);
        if (arrivalResult.getCode() != 200) {
            throw new Exception("调用外部到货通知单查询接口出错");
        } else {
            LinkedHashMap arrivalMap = (LinkedHashMap) arrivalResult.getData();
            List<LinkedHashMap> arrivalDataList = (List<LinkedHashMap>) arrivalMap.get("inspectionProductList");
            return arrivalDataList;
        }
    }

    private List<InspectionDataVO> getInspectionDataList(@Valid GetProductInspectionInfoVO vo) {

        LinkedHashMap iAmap = inspectionApplyService.findByCond(vo.getPurchaseOrderId(), vo.getKeyWords(), vo.getSkuId());
        List<InspectionDataVO> inspectionDataList = (List<InspectionDataVO>) iAmap.get("inspectionData");
        return inspectionDataList;
    }

    private List<LinkedHashMap> getOrderNeetList(@Valid GetProductInspectionInfoVO vo) throws Exception {
        Result orderResult = getOrderFuzzy(vo);
        if (orderResult.getCode() != 200) {
            throw new Exception("调用外部采购订单查询接口出错");
        } else {
            LinkedHashMap orderMap = (LinkedHashMap) orderResult.getData();
            List<LinkedHashMap> orderNeetList = (List<LinkedHashMap>) orderMap.get("orderNeetList");
            return orderNeetList;
        }
    }

    private Result getOrderFuzzy(GetProductInspectionInfoVO vo) {
        Result orderResult = outOrderService.getOrderFuzzy(
                null,
                null,
                vo.getPurchaseOrderId(),
                vo.getSkuId(),
                vo.getKeyWords());
        return orderResult;
    }


    private Result getArrivalFuzzy(@Valid GetProductInspectionInfoVO vo) {
        Result arrivalResult = outArrivalNoticeService.getArrivalFuzzy(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                vo.getPurchaseOrderId(),
                vo.getSkuId(),
                null,
                vo.getKeyWords());
        return arrivalResult;
    }


    private String getParaOne(List<Map> recordList) {
        String result;
        if (recordList == null || recordList.size() == 0) {
            result = "('')";
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < recordList.size(); i++) {
                Map o = recordList.get(i);
                String inspectionItemId = (String) o.get("inspectionItemId");
                if (i == 0) {
                    sb.append("(" + "'" + inspectionItemId + "'");
                } else {
                    sb.append("," + "'" + inspectionItemId + "'");
                }
                if (i == (recordList.size() - 1)) {
                    sb.append(")");
                }
            }
            result = sb.toString();
        }
        return result;
    }

    private String getParaTwo(List<String> list) {
        String result;
        if (list == null || list.size() == 0) {
            result = "('')";
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                String o = list.get(i);
                if (i == 0) {
                    sb.append("(" + "'" + o + "'");
                } else {
                    sb.append("," + "'" + o + "'");
                }
                if (i == (list.size() - 1)) {
                    sb.append(")");
                }
            }
            result = sb.toString();
        }
        return result;
    }


    private Result newInspectionCd(int yearShort) {
        String inspectionCd = INSPECTIONCDHEADER + yearShort;
        int size = productInspectionInfoDao.getFuzzySize(inspectionCd);
        int idNumber = size + 1;
        if (idNumber >= 99999) {
            return Result.failure(9999, "流水号超出99999，请联系管理员重新设计", null);
        } else {
            String newId = inspectionCd + new DecimalFormat("00000").format(idNumber);
            return Result.success(newId);
        }
    }


    private void insertPIIE(ProductInspectionInfoEntity productInspectionInfoEntity) {
        if (productInspectionInfoEntity.getSourceTypeCd() != 2) {
            //非到货通知单  无arrivalProductId
            productInspectionInfoEntity.setArrivalProductId(null);
        }

        double temp = 0;
        productInspectionInfoEntity.setDeleteStatus(0);
        //外箱长度
        if (productInspectionInfoEntity.getCartonLength() == null) {
            productInspectionInfoEntity.setCartonLength(temp);
        }
        //    //外箱宽度
        if (productInspectionInfoEntity.getCartonWidth() == null) {
            productInspectionInfoEntity.setCartonWidth(temp);
        }
        //    //外箱高度
        if (productInspectionInfoEntity.getCartonHeight() == null) {
            productInspectionInfoEntity.setCartonHeight(temp);
        }
        //    //毛重
        if (productInspectionInfoEntity.getCrossWeight() == null) {
            productInspectionInfoEntity.setCrossWeight(temp);
        }
        String time = Time.getCurrentDateTime();
        productInspectionInfoEntity.setCreateTime(time);
        productInspectionInfoEntity.setUpdateTime(time);
        productInspectionInfoDao.insertPIIE(productInspectionInfoEntity);
    }

    @Override
    public int getInspectionProductInfo(String orderId) {
        return productInspectionInfoDao.getInspectionProductInfo(orderId);
    }
}
