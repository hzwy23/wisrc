package com.wisrc.warehouse.webapp.AOP;

import com.google.gson.Gson;
import com.wisrc.warehouse.webapp.service.MskuService;
import com.wisrc.warehouse.webapp.service.externalService.ReplenishmentService;
import com.wisrc.warehouse.webapp.service.externalService.WmsService;
import com.wisrc.warehouse.webapp.vo.syncVO.*;
import com.wisrc.warehouse.webapp.vo.wmsvo.FbaGoodsInfoVO;
import com.wisrc.warehouse.webapp.vo.wmsvo.FbaReplenishmentOutBillSyncVO;
import com.wisrc.warehouse.webapp.vo.wmsvo.PackInfoVo;
import com.wisrc.warehouse.webapp.dao.ProcessDetailDao;
import com.wisrc.warehouse.webapp.dao.ProcessTaskBillDao;
import com.wisrc.warehouse.webapp.entity.ProcessTaskBillEntity;
import com.wisrc.warehouse.webapp.service.ProductService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.utils.Time;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.*;

@Aspect
@Component
public class ProcessTaskBillSyncAOP {
    @Autowired
    private ProcessTaskBillDao processTaskBillDao;
    @Autowired
    private ReplenishmentService replenishmentService;
    @Autowired
    private WmsService wmsService;
    @Autowired
    private MskuService mskuService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProcessDetailDao processDetailDao;

    @Value("${boxInfoUrl:http://49.4.10.118:8790/images/resource/matrix-replenishment}")
    private String boxInfoUrl;

    @AfterReturning(value = "execution(public * com.wisrc.warehouse.webapp.service.ProcessTaskBillService.changeStatus(..))")
    public void taskBillSync(JoinPoint jp) {
        Gson gson = new Gson();
        String processTaskId = (String) jp.getArgs()[0];
        String status = (String) jp.getArgs()[1];
        boolean flag = true;
        VirtualEnterBillSyncVO enterBillSyncVO = new VirtualEnterBillSyncVO();
        List<GoodsInfoVO> goodsList = new ArrayList<>();
        GoodsInfoVO goodsInfo = new GoodsInfoVO();
        if ("0".equals(status)) {
            ProcessTaskBillEntity entity = processTaskBillDao.getByProcessTaskId(processTaskId);
            //包装同步加工任务单参数
            List skuList = new ArrayList();
            skuList.add(entity.getProcessLaterSku());
            enterBillSyncVO.setVoucherCode(processTaskId);
            enterBillSyncVO.setVoucherType("JG");
            enterBillSyncVO.setSectionCode(entity.getWarehouseId());
            enterBillSyncVO.setCreateTime(Time.getCurrentTime());
            enterBillSyncVO.setRemark(entity.getRemark());
            goodsInfo.setGoodsCode(entity.getProcessLaterSku());
            String[] ids = new String[skuList.size()];
            skuList.toArray(ids);
            Result proResult = productService.getProductInfo(ids);
            Map map = (Map) proResult.getData();
            goodsInfo.setGoodsName((String) map.get(entity.getProcessLaterSku()));
            goodsInfo.setUnitQuantity(entity.getProcessNum());
            goodsInfo.setPackageQuantity(0);
            goodsInfo.setTotalQuantity(entity.getProcessNum());
            goodsList.add(goodsInfo);
            enterBillSyncVO.setGoodsList(goodsList);
            Result wmsResult = wmsService.virtualEnterBillSync(gson.toJson(enterBillSyncVO));


            //wms同步成功后修改加工任务单状态为已完成
            if (wmsResult.getCode() == 0) {
                processDetailDao.changeStatus(processTaskId, 5);
            }

            if (entity.getFbaReplenishmentId() != null) {
                List<ProcessTaskBillEntity> entityList = processTaskBillDao.getTaskList(entity.getFbaReplenishmentId());
                for (ProcessTaskBillEntity task : entityList) {
                    if (task.getStatusCd() != 5) {
                        flag = false;
                        break;
                    }
                }
                if (StringUtils.isNotEmpty(entity.getFbaReplenishmentId()) && entity.getFbaReplenishmentId().startsWith("P")) {
                    Result result = replenishmentService.getReplenishment(entity.getFbaReplenishmentId());
                    Map mapData = (Map) result.getData();
                    if ((int) ((Map) mapData.get("infoEntity")).get("statusCd") == 5) {
                        flag = false;
                    }
                } else if (StringUtils.isNotEmpty(entity.getFbaReplenishmentId()) && entity.getFbaReplenishmentId().startsWith("A")) {
                    Result transfer = replenishmentService.getTransfer(entity.getFbaReplenishmentId());
                    Map transferMap = (Map) transfer.getData();
                    Map transferBasicInfoEntityMap = (Map) transferMap.get("transferBasicInfoEntity");
                    if ((int) transferBasicInfoEntityMap.get("statusCd") == 7) {
                        flag = false;
                    }
                }
                if (flag) {
                    if (entity.getFbaReplenishmentId().startsWith("P")) {
                        //包装FBA组装发货参数
                        Result result = replenishmentService.getReplenishment(entity.getFbaReplenishmentId());
                        FbaReplenishmentInfoVO infoVO = new FbaReplenishmentInfoVO();
                        FbaReplenishmentOutBillSyncVO wmsFbaVo = new FbaReplenishmentOutBillSyncVO();
                        List<FbaGoodsInfoVO> fbaGoodsList = new ArrayList<>();
                        List<PackInfoVo> boxGaugeList = new ArrayList<>();
                        if (result.getCode() == 200) {
                            Map fbaDataVO = (Map) result.getData();
                            Map fbaData = (Map) fbaDataVO.get("infoEntity");
                            //fba基本信息
                            FbaReplenishmentInfoEntity fbaReplenishmentInfoEntity = new FbaReplenishmentInfoEntity();
                            fbaReplenishmentInfoEntity.setFbaReplenishmentId((String) fbaData.get("fbaReplenishmentId"));
                            fbaReplenishmentInfoEntity.setDeliveringTypeCd((Integer) fbaData.get("deliveringTypeCd"));
                            fbaReplenishmentInfoEntity.setWarehouseId((String) fbaData.get("warehouseId"));
                            fbaReplenishmentInfoEntity.setSubWarehouseId((String) fbaData.get("subWarehouseId"));
                            fbaReplenishmentInfoEntity.setCreateUser((String) fbaData.get("createUser"));
                            fbaReplenishmentInfoEntity.setBatchNumber((String) fbaData.get("batchNumber"));
                            fbaReplenishmentInfoEntity.setAmazonWarehouseAddress((String) fbaData.get("amazonWarehouseAddress"));
                            fbaReplenishmentInfoEntity.setRefercenceId((String) fbaData.get("refercenceId"));
                            fbaReplenishmentInfoEntity.setShopId((String) fbaData.get("shopId"));
                            fbaReplenishmentInfoEntity.setRemark((String) fbaData.get("remark"));
                            fbaReplenishmentInfoEntity.setPackageMark((String) fbaData.get("packageMark"));
                            fbaReplenishmentInfoEntity.setPacklistFile((String) fbaData.get("packlistFile"));
                            infoVO.setInfoEntity(fbaReplenishmentInfoEntity);

                            //fba商品信息
                            List<FbaMskuInfoVO> fbaMskuInfoVOS = new ArrayList<>();
                            List<Map> mskuInfoList = (List<Map>) fbaDataVO.get("mskuInfoList");
                            for (Map map1 : mskuInfoList) {
                                FbaMskuInfoVO fbaMskuInfoVO = new FbaMskuInfoVO();
                                fbaMskuInfoVO.setReplenishmentCommodityId((String) map1.get("replenishmentCommodityId"));
                                fbaMskuInfoVO.setMskuId((String) map1.get("mskuId"));
                                fbaMskuInfoVO.setCommodityId((String) map1.get("commodityId"));
                                fbaMskuInfoVO.setShopId((String) map1.get("shopId"));
                                fbaMskuInfoVO.setShopName((String) map1.get("shopName"));
                                fbaMskuInfoVO.setFbaReplenishmentId((String) map1.get("fbaReplenishmentId"));
                                fbaMskuInfoVO.setModifyUser((String) map1.get("modifyUser"));
                                //fbaMskuInfoVO.setModifyTime((Timestamp) map1.get("modifyTime"));
                                fbaMskuInfoVO.setDeleteStatus((Integer) map1.get("deleteStatus"));
                                fbaMskuInfoVO.setReplenishmentQuantity((Integer) map1.get("replenishmentQuantity"));
                                fbaMskuInfoVO.setPackingNumber((Integer) map1.get("packingNumber"));
                                fbaMskuInfoVO.setDeliveryNumber((Integer) map1.get("deliveryNumber"));
                                fbaMskuInfoVO.setSignInQuantity((Integer) map1.get("signInQuantity"));
                                fbaMskuInfoVO.setFnsku((String) map1.get("fnsku"));
                                fbaMskuInfoVO.setDeclareUnitPrice((Double) map1.get("declareUnitPrice"));
                                fbaMskuInfoVO.setMskuUnitCd((Integer) map1.get("mskuUnitCd"));
                                fbaMskuInfoVO.setDeclarationElements((String) map1.get("declarationElements"));
                                fbaMskuInfoVO.setMskuTypeCd((Integer) map1.get("mskuTypeCd"));

                                //改商品对应的商品装箱规格
                                List<FbaMskuPackQueryVO> fbaMskuPackQueryVOS = new ArrayList<>();
                                List<Map> packMap = (List<Map>) map1.get("mskupackList");
                                for (Map map2 : packMap) {
                                    FbaMskuPackQueryVO fbaMskuPackQueryVO = new FbaMskuPackQueryVO();
                                    fbaMskuPackQueryVO.setUuid((String) map2.get("uuid"));
                                    fbaMskuPackQueryVO.setOuterBoxSpecificationHeight((Integer) map2.get("outerBoxSpecificationHeight"));
                                    fbaMskuPackQueryVO.setOuterBoxSpecificationLen((Integer) map2.get("outerBoxSpecificationLen"));
                                    fbaMskuPackQueryVO.setOuterBoxSpecificationWidth((Integer) map2.get("outerBoxSpecificationWidth"));
                                    fbaMskuPackQueryVO.setPackingQuantity((Integer) map2.get("packingQuantity"));
                                    fbaMskuPackQueryVO.setNumberOfBoxes((Integer) map2.get("numberOfBoxes"));
                                    fbaMskuPackQueryVO.setPackingWeight((Double) map2.get("packingWeight"));
                                    fbaMskuPackQueryVO.setReplenishmentQuantity((Integer) map2.get("replenishmentQuantity"));
                                    fbaMskuPackQueryVO.setDeliveryNumber((Integer) map2.get("deliveryNumber"));
                                    fbaMskuPackQueryVO.setSignInQuantity((Integer) map2.get("signInQuantity"));
                                    fbaMskuPackQueryVO.setIsStandard((Integer) map2.get("isStandard"));
                                    fbaMskuPackQueryVO.setPackingNumber((Integer) map2.get("packingNumber"));
                                    fbaMskuPackQueryVOS.add(fbaMskuPackQueryVO);
                                }
                                fbaMskuInfoVO.setMskupackList(fbaMskuPackQueryVOS);
                                fbaMskuInfoVOS.add(fbaMskuInfoVO);
                            }
                            infoVO.setMskuInfoList(fbaMskuInfoVOS);

                            wmsFbaVo.setVoucherCode(infoVO.getInfoEntity().getFbaReplenishmentId());
                            wmsFbaVo.setVoucherType("FBH");
                            wmsFbaVo.setVoucherCat(String.valueOf(infoVO.getInfoEntity().getDeliveringTypeCd()));
                            wmsFbaVo.setSectionCode(infoVO.getInfoEntity().getSubWarehouseId());
                            wmsFbaVo.setCreateTime(Time.getCurrentTime());
                            wmsFbaVo.setApplicant(infoVO.getInfoEntity().getCreateUser());
                            wmsFbaVo.setReplenishmentBatch(infoVO.getInfoEntity().getBatchNumber());
                            wmsFbaVo.setAddress(infoVO.getInfoEntity().getAmazonWarehouseAddress());
                            wmsFbaVo.setReferenceId(infoVO.getInfoEntity().getRefercenceId());
                            wmsFbaVo.setTargetWhName((String) fbaDataVO.get("shopName"));
                            wmsFbaVo.setRemark(infoVO.getInfoEntity().getRemark());
                            wmsFbaVo.setBoxInfoUrl(boxInfoUrl + infoVO.getInfoEntity().getPackageMark());
                            wmsFbaVo.setBoxGaugeUrl(infoVO.getInfoEntity().getPacklistFile());
                            List<String> commodityIds = new ArrayList<>();
                            Map<String, Object> mskuMap = new HashMap<>();
                            List<FbaMskuInfoVO> mskuInfoVOList = infoVO.getMskuInfoList();
                            for (FbaMskuInfoVO vo : mskuInfoVOList) {
                                commodityIds.add(vo.getCommodityId());
                            }
                            String[] ids2 = commodityIds.toArray(new String[commodityIds.size()]);
                            Result mskuResult = mskuService.getProduct(ids2);
                            LinkedHashMap hashMap = (LinkedHashMap) mskuResult.getData();
                            List skuInfoList = (List<Map>) hashMap.get("mskuInfoBatch");
                            if (skuInfoList != null && skuInfoList.size() > 0) {
                                for (Object object : skuInfoList) {
                                    Map finalMap = (Map) object;
                                    mskuMap.put((String) finalMap.get("id"), finalMap);
                                }
                            }
                            //行号
                            int i = 1;
                            //流水号
                            int numberBoxId = 1;
                            int unitQuantity = 0;
                            for (FbaMskuInfoVO vo : mskuInfoVOList) {
                                FbaGoodsInfoVO fbaGoodsInfoVO = new FbaGoodsInfoVO();
                                Map voMap = (Map) mskuMap.get(vo.getCommodityId());
                                fbaGoodsInfoVO.setLineNum(i);
                                i++;
                                fbaGoodsInfoVO.setGoodsCode((String) voMap.get("skuId"));
                                fbaGoodsInfoVO.setGoodsName((String) voMap.get("productName"));
                                fbaGoodsInfoVO.setFnCode((String) voMap.get("fnSkuId"));
                                fbaGoodsInfoVO.setMsku((String) voMap.get("mskuName"));
                                fbaGoodsInfoVO.setUnitQuantity(vo.getReplenishmentQuantity());
                                fbaGoodsInfoVO.setTotalQuantity(vo.getReplenishmentQuantity());
                                if (vo.getMskupackList().size() == 1) {
                                    int num = vo.getMskupackList().get(0).getNumberOfBoxes();
                                    for (int m = 1; m <= num; m++) {
                                        PackInfoVo packInfoVo = new PackInfoVo();
                                        packInfoVo.setBoxNumber(infoVO.getInfoEntity().getBatchNumber() + "U" + test(numberBoxId));
                                        packInfoVo.setStandard(1);
                                        packInfoVo.setUnit("PCS");
                                        packInfoVo.setPackageCapacity(vo.getMskupackList().get(0).getPackingQuantity());
                                        packInfoVo.setWeight(vo.getMskupackList().get(0).getPackingWeight());
                                        packInfoVo.setSize(vo.getMskupackList().get(0).getOuterBoxSpecificationLen() * 10 + "*" + vo.getMskupackList().get(0).getOuterBoxSpecificationWidth() * 10 + "*" + vo.getMskupackList().get(0).getOuterBoxSpecificationHeight() * 10);
                                        packInfoVo.setPackageQuantity(0);
                                        packInfoVo.setTotalQuantity(vo.getMskupackList().get(0).getPackingQuantity());
                                        //unitQuantity += vo.getMskupackList().get(0).getReplenishmentQuantity();
                                        boxGaugeList.add(packInfoVo);
                                        numberBoxId++;
                                    }
                                } else {
                                    List<FbaMskuPackQueryVO> standardList = new ArrayList<>();
                                    List<FbaMskuPackQueryVO> notStandardList = new ArrayList<>();
                                    for (int j = 0; j < vo.getMskupackList().size(); j++) {
                                        if (vo.getMskupackList().get(j).getIsStandard() == 1) {
                                            standardList.add(vo.getMskupackList().get(j));
                                        } else {
                                            notStandardList.add(vo.getMskupackList().get(j));
                                        }
                                    }
                                    int num = 0;
                                    for (FbaMskuPackQueryVO standardVO : standardList) {
                                        num = standardVO.getNumberOfBoxes();
                                        for (int m = 1; m <= num; m++) {
                                            PackInfoVo packInfoVo = new PackInfoVo();
                                            packInfoVo.setBoxNumber(infoVO.getInfoEntity().getBatchNumber() + "U" + test(numberBoxId));
                                            packInfoVo.setStandard(1);
                                            packInfoVo.setUnit("PCS");
                                            packInfoVo.setPackageCapacity(standardVO.getPackingQuantity());
                                            packInfoVo.setWeight(standardVO.getPackingWeight());
                                            packInfoVo.setSize(standardVO.getOuterBoxSpecificationLen() * 10 + "*" + standardVO.getOuterBoxSpecificationWidth() * 10 + "*" + standardVO.getOuterBoxSpecificationHeight() * 10);
                                            packInfoVo.setPackageQuantity(0);
                                            packInfoVo.setTotalQuantity(standardVO.getPackingQuantity());
                                            boxGaugeList.add(packInfoVo);
                                            numberBoxId++;
                                        }
                                    }
                                    for (FbaMskuPackQueryVO standardVO : notStandardList) {
                                        int box = standardVO.getNumberOfBoxes();
                                        for (int n = num + 1; n <= num + box; n++) {
                                            PackInfoVo packInfoVo = new PackInfoVo();
                                            packInfoVo.setBoxNumber(infoVO.getInfoEntity().getBatchNumber() + "U" + test(numberBoxId));
                                            packInfoVo.setStandard(0);
                                            packInfoVo.setUnit("PCS");
                                            packInfoVo.setPackageCapacity(0);
                                            packInfoVo.setWeight(standardVO.getPackingWeight());
                                            packInfoVo.setSize("0*0*0");
                                            packInfoVo.setPackageQuantity(0);
                                            packInfoVo.setTotalQuantity(0);
                                            boxGaugeList.add(packInfoVo);
                                            numberBoxId++;
                                        }
                                        num += box;
                                    }
                                }
                                //fbaGoodsInfoVO.setUnitQuantity(unitQuantity);
                                fbaGoodsInfoVO.setPackageQuantity(0);
                                //fbaGoodsInfoVO.setTotalQuantity(unitQuantity);
                                fbaGoodsInfoVO.setBoxGaugeList(boxGaugeList);
                                fbaGoodsList.add(fbaGoodsInfoVO);
                            }
                            wmsFbaVo.setGoodsList(fbaGoodsList);
                        }


                        Result wmsFbaResult = wmsService.syncFbaReplenishBill(gson.toJson(wmsFbaVo));
                        System.out.println(wmsResult.getMsg() + wmsFbaResult.getMsg());
                    } else {
                        //调拨单生成的加工任务单
                        Result transfer = replenishmentService.getTransfer(entity.getFbaReplenishmentId());
                        Map transferMap = (Map) transfer.getData();
                        //调拨单基本信息
                        Map transferBasicInfoEntity = (Map) transferMap.get("transferBasicInfoEntity");
                        //调拨单产品详情
                        List<Map> transferOrderDetailsVos = (List<Map>) transferMap.get("transferOrderDetailsVos");
                        TransferOrderSyncVO transferOrderSyncVO = new TransferOrderSyncVO();
                        transferOrderSyncVO.setVoucherCode(entity.getFbaReplenishmentId());
                        transferOrderSyncVO.setVoucherType("DB");
                        transferOrderSyncVO.setSectionCode((String) transferBasicInfoEntity.get("warehouseStartId"));
                        transferOrderSyncVO.setRemark((String) transferBasicInfoEntity.get("remark"));

                        //TODO 准备同步到wms的产品详情
                        List<TransferOrderProductSyncVO> transferOrderProductSyncVOS = new ArrayList<>();
                        int lineNum = 1;
                        //箱号流水
                        int numBoxId = 1;
                        for (Map transferOrderDetailsVo : transferOrderDetailsVos) {
                            TransferOrderProductSyncVO transferOrderProductSyncVO = new TransferOrderProductSyncVO();
                            transferOrderProductSyncVO.setLineNum(String.valueOf(lineNum));
                            transferOrderProductSyncVO.setGoodsCode((String) transferOrderDetailsVo.get("skuId"));
                            String fnSku = (String) transferOrderDetailsVo.get("fnSku");
                            transferOrderProductSyncVO.setFnCode(fnSku);
                            if (StringUtils.isNotEmpty(fnSku)) {
                                Result mskuResult = mskuService.getMskuInfoByFnSkuId(fnSku);
                                Map mskuMap = (Map) mskuResult.getData();
                                transferOrderProductSyncVO.setMsku((String) mskuMap.get("mskuName"));
                            }
                            transferOrderProductSyncVO.setGoodsName((String) transferOrderDetailsVo.get("skuName"));
                            transferOrderProductSyncVO.setUnitQuantity(String.valueOf(transferOrderDetailsVo.get("transferQuantity")));
                            transferOrderProductSyncVO.setPackageQuantity("0");
                            transferOrderProductSyncVO.setTotalQuantity(String.valueOf(transferOrderDetailsVo.get("transferQuantity")));

                            //TODO 准备该产品的装箱信息
                            List<TransferOrderPackSyncVO> transferOrderPackSyncVOS = new ArrayList<>();
                            List<Map> packList = (List<Map>) transferOrderDetailsVo.get("packInfoEntities");
                            //标准箱
                            List<Map> standarPack = new ArrayList<>();
                            //非标准箱
                            List<Map> notStandarPack = new ArrayList<>();
                            for (Map packInfo : packList) {
                                if (packInfo.get("isStandard") == null || (int) packInfo.get("isStandard") == 0) {
                                    notStandarPack.add(packInfo);
                                } else {
                                    standarPack.add(packInfo);
                                }
                            }
                            for (Map pack : standarPack) {
                                //遍历生成标准箱
                                int numOfBoxes = (int) pack.get("numberOfBoxes");
                                for (int i = 0; i < numOfBoxes; i++) {
                                    TransferOrderPackSyncVO transferOrderPackSyncVO = new TransferOrderPackSyncVO();
                                    transferOrderPackSyncVO.setBoxNumber(entity.getFbaReplenishmentId() + test(numBoxId));
                                    transferOrderPackSyncVO.setStandard("1");
                                    transferOrderPackSyncVO.setUnit("PCS");
                                    transferOrderPackSyncVO.setPackageCapacity((Integer) pack.get("packingQuantity"));
                                    transferOrderPackSyncVO.setWeight((Double) pack.get("weight"));
                                    transferOrderPackSyncVO.setSize(String.valueOf(pack.get("outerBoxSpecificationLen")) + "*" + String.valueOf(pack.get("outerBoxSpecificationWidth")) + "*" + String.valueOf(pack.get("outerBoxSpecificationHeight")));
                                    transferOrderPackSyncVO.setTotalQuantity((Integer) pack.get("packingQuantity"));
                                    transferOrderPackSyncVO.setPackageQuantity(0);
                                    transferOrderPackSyncVOS.add(transferOrderPackSyncVO);
                                    numBoxId++;
                                }
                            }
                            for (Map pack : notStandarPack) {
                                int numOfBoxes = (int) pack.get("numberOfBoxes");
                                for (int i = 0; i < numOfBoxes; i++) {
                                    //遍历生成非标准箱
                                    TransferOrderPackSyncVO transferOrderPackSyncVO = new TransferOrderPackSyncVO();
                                    transferOrderPackSyncVO.setBoxNumber(entity.getFbaReplenishmentId() + test(numBoxId));
                                    transferOrderPackSyncVO.setStandard("0");
                                    transferOrderPackSyncVO.setUnit("PCS");
                                    transferOrderPackSyncVO.setPackageCapacity(0);
                                    transferOrderPackSyncVO.setWeight((Double) pack.get("weight"));
                                    transferOrderPackSyncVO.setSize("0*0*0");
                                    transferOrderPackSyncVO.setTotalQuantity(0);
                                    transferOrderPackSyncVO.setPackageQuantity(0);
                                    transferOrderPackSyncVOS.add(transferOrderPackSyncVO);
                                    numBoxId++;
                                }
                            }
                            transferOrderProductSyncVO.setBoxGaugeList(transferOrderPackSyncVOS);
                            transferOrderProductSyncVOS.add(transferOrderProductSyncVO);
                            lineNum++;
                        }
                        transferOrderSyncVO.setGoodsList(transferOrderProductSyncVOS);
                        wmsService.transferOutSync(gson.toJson(transferOrderSyncVO));
                    }
                }
            }
        }
    }

    public String test(int num) {
        DecimalFormat df = new DecimalFormat("000000");
        return df.format(num);
    }
}
