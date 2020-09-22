package com.wisrc.replenishment.webapp.aop;

import com.google.gson.Gson;
import com.wisrc.replenishment.webapp.dao.TransferDao;
import com.wisrc.replenishment.webapp.entity.TransferBasicInfoEntity;
import com.wisrc.replenishment.webapp.entity.TransferOrderDetailsEntity;
import com.wisrc.replenishment.webapp.entity.TransferOrderPackInfoEntity;
import com.wisrc.replenishment.webapp.service.TransferService;
import com.wisrc.replenishment.webapp.service.externalService.OperationService;
import com.wisrc.replenishment.webapp.service.externalService.ProductService;
import com.wisrc.replenishment.webapp.service.externalService.WarehouseService;
import com.wisrc.replenishment.webapp.service.externalService.WmsService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.utils.Time;
import com.wisrc.replenishment.webapp.vo.transferOut.TransferOrderPackSyncVO;
import com.wisrc.replenishment.webapp.vo.transferOut.TransferOrderProductSyncVO;
import com.wisrc.replenishment.webapp.vo.transferOut.TransferOrderSyncVO;
import com.wisrc.replenishment.webapp.vo.transferorder.TransferInfoReturnVo;
import com.wisrc.replenishment.webapp.vo.transferorder.TransferOrderDetailsVo;
import com.wisrc.replenishment.webapp.vo.wms.AddProcessTaskBillVO;
import com.wisrc.replenishment.webapp.vo.wms.ProcessDetailEntity;
import com.wisrc.replenishment.webapp.vo.wms.ProcessTaskBillEntity;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.*;

@Aspect
@Component
public class TransferOutSyncAOP {

    @Autowired
    Gson gson;
    @Autowired
    private TransferDao transferDao;
    @Autowired
    private OperationService operationService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WmsService wmsService;
    @Autowired
    private TransferService transferService;
    @Autowired
    private WarehouseService warehouseService;

    @AfterReturning(value = "execution(public * com.wisrc.replenishment.webapp.service.TransferService.auditTransfer(..))", returning = "result")
    public void transferOutSync(JoinPoint joinPoint, Result result) {
        try {
            if (result.getCode() == 200) {
                TransferBasicInfoEntity basicInfoEntity = (TransferBasicInfoEntity) result.getData();
                String warehouseStartId = basicInfoEntity.getWarehouseStartId();
                //起运仓为本地仓的时候同步调拨单到WMS
                if (warehouseStartId.startsWith("A")) {
                    if ("1".equals(basicInfoEntity.getTransferTypeCd())) {
                        //普通调拨单
                        //写入基础信息
                        TransferOrderSyncVO basicSyncVO = new TransferOrderSyncVO();
                        basicSyncVO.setVoucherCode(basicInfoEntity.getTransferOrderCd());
                        basicSyncVO.setVoucherType("DB");
                        basicSyncVO.setSectionCode(basicInfoEntity.getWarehouseStartId());
                        basicSyncVO.setRemark(basicInfoEntity.getRemark());
                        List<TransferOrderProductSyncVO> productSyncListVO = new LinkedList<>();
                        //查询得到产品信息
                        List<TransferOrderDetailsEntity> productEntityList = transferDao.findTransferDetailsById(basicInfoEntity.getTransferOrderCd());


                        //得到产品名称
                        String[] skuIds = new String[productEntityList.size()];
                        for (int i = 0; i < productEntityList.size(); i++) {
                            skuIds[i] = productEntityList.get(i).getSkuId();
                        }
                        Map<String, String> productMap = new LinkedHashMap<>();
                        Result productResult = productService.getProductInfoById(gson.toJson(skuIds));
                        System.out.println(productResult);
                        if (productResult.getCode() == 200) {
                            List productList = (ArrayList) (productResult.getData());
                            for (Object object : productList) {
                                productMap.put((String) ((Map) object).get("skuId"), (String) ((Map) object).get("skuNameZh"));
                            }
                        }

                        //行号
                        int i = 0;
                        //流水码
                        int waterCode = 1;
                        for (TransferOrderDetailsEntity productEntity : productEntityList) {

                            //如果贴标就把MSKU的名称传过去
                            Map<String, String> mskuInfoMap = new LinkedHashMap<>();
                            if (StringUtils.isNotEmpty(productEntity.getFnSku())) {
                                Result mskuInfoResult = operationService.getMskuInfoByFnCode(productEntity.getFnSku());
                                if (mskuInfoResult.getCode() == 200) {
                                    mskuInfoMap.put((String) ((Map) mskuInfoResult.getData()).get("skuId"), (String) ((Map) mskuInfoResult.getData()).get("mskuName"));
                                }
                            }

                            //写入产品信息
                            TransferOrderProductSyncVO productSyncVO = new TransferOrderProductSyncVO();
                            productSyncVO.setLineNum(i++ + "");
                            productSyncVO.setGoodsCode(productEntity.getSkuId());
                            if (StringUtils.isNotEmpty(productEntity.getFnSku())) {
                                productSyncVO.setFnCode(productEntity.getFnSku());
                                productSyncVO.setMsku(mskuInfoMap.get(productEntity.getSkuId()));
                            }
                            productSyncVO.setGoodsName(productMap.get(productEntity.getSkuId()));
                            productSyncVO.setUnitQuantity(productEntity.getTransferQuantity() + "");
                            productSyncVO.setPackageQuantity("0");
                            productSyncVO.setTotalQuantity(productEntity.getTransferQuantity() + "");
                            productSyncListVO.add(productSyncVO);
                            basicSyncVO.setGoodsList(productSyncListVO);

                            List<TransferOrderPackSyncVO> packSyncListVO = new LinkedList<>();

                            //查询装箱规格信息
                            List<TransferOrderPackInfoEntity> packInfoEntityList = transferDao.findPackInfoByCommodityId(productEntity.getCommodityInfoCd());

                            //将装箱规格重新排序，采取方法是先删除非标准箱的，再加
                            List<TransferOrderPackInfoEntity> newPackInfoEntityList = new LinkedList<>();

                            Iterator<TransferOrderPackInfoEntity> iterator = packInfoEntityList.iterator();

                            if (iterator.hasNext()) {
                                TransferOrderPackInfoEntity packInfoEntity = iterator.next();
                                if (packInfoEntity.getIsStandard() != null && packInfoEntity.getIsStandard() == 0) {
                                    packInfoEntityList.remove(packInfoEntity);
                                    newPackInfoEntityList.add(packInfoEntity);
                                }
                            }
                            packInfoEntityList.addAll(newPackInfoEntityList);

                            for (TransferOrderPackInfoEntity packInfoEntity : packInfoEntityList) {
                                //写入装箱规格和箱号信息
                                int numberOfBoxes = packInfoEntity.getNumberOfBoxes();

                                for (int j = 1; j <= numberOfBoxes; j++) {
                                    TransferOrderPackSyncVO packSyncVO = new TransferOrderPackSyncVO();
                                    String boxNumber = null;

                                    boxNumber = "U" + test(waterCode);
                                    packSyncVO.setBoxNumber(basicInfoEntity.getTransferOrderCd() + boxNumber);
                                    if (packInfoEntity.getIsStandard() == null || packInfoEntity.getIsStandard() == 1) {
                                        packSyncVO.setStandard("1");
                                    } else {
                                        packSyncVO.setStandard("0");
                                    }
                                    packSyncVO.setUnit("PCS");
                                    if (packInfoEntity.getIsStandard() == null || packInfoEntity.getIsStandard() == 1) {
                                        packSyncVO.setPackageCapacity(packInfoEntity.getPackingQuantity());
                                    }
                                    packSyncVO.setWeight(packInfoEntity.getWeight());
                                    packSyncVO.setSize(packInfoEntity.getOuterBoxSpecificationLen() + "*" + packInfoEntity.getOuterBoxSpecificationWidth() + "*" + packInfoEntity.getOuterBoxSpecificationHeight());
                                    if (packInfoEntity.getIsStandard() != null && packInfoEntity.getIsStandard() == 1) {
                                        packSyncVO.setTotalQuantity(packInfoEntity.getPackingQuantity());
                                    }
                                    packSyncListVO.add(packSyncVO);
                                    waterCode++;
                                }
                            }
                            productSyncVO.setBoxGaugeList(packSyncListVO);
                        }
                        Result wmsResult = wmsService.transferOutSync(gson.toJson(basicSyncVO));
                        System.out.println(wmsResult);
                        if (wmsResult.getCode() != 200) {
                            result.setMsg("调拨单同步错误");
                        }
                    } else {
                        //组装调拨单
                        //加工任务单数据
                        AddProcessTaskBillVO addProcessTaskBillVO = new AddProcessTaskBillVO();
                        Result transferResult = transferService.findById(basicInfoEntity.getTransferOrderCd());
                        TransferInfoReturnVo transferInfoReturnVo = (TransferInfoReturnVo) transferResult.getData();

                        for (TransferOrderDetailsVo transferOrderDetailsVo : transferInfoReturnVo.getTransferOrderDetailsVos()) {
                            //判断当前商品是否需要加工
                            List<Object> con = new ArrayList();
                            Map<String, String> entity = new HashMap<>();
                            entity.put("skuId", transferOrderDetailsVo.getSkuId());
                            entity.put("warehouseId", warehouseStartId);
                            con.add(entity);
                            int enableStockNum = 0;
                            Result stockResult = warehouseService.getStockBySkuIdAndWarehouseId(gson.toJson(con));
                            if (stockResult.getCode() == 200) {
                                if (((List<Object>) stockResult.getData()).size() > 0) {
                                    Map data = (Map) ((List<Object>) stockResult.getData()).get(0);
                                    enableStockNum = (int) data.get("enableStockNum");
                                }
                            }
                            int processNum = 0;
                            processNum = transferOrderDetailsVo.getTransferQuantity() - enableStockNum;
                            if (processNum > 0) {
                                //TODO 准备加工任务单基本数据
                                ProcessTaskBillEntity processTaskBillEntity = new ProcessTaskBillEntity();
                                processTaskBillEntity.setFbaReplenishmentId(basicInfoEntity.getTransferOrderCd());
                                processTaskBillEntity.setStatusCd(1);
                                processTaskBillEntity.setProcessDate(Time.getCurrentDateTime());
                                processTaskBillEntity.setProcessLaterSku(transferOrderDetailsVo.getSkuId());
                                processTaskBillEntity.setProcessNum(processNum);
                                processTaskBillEntity.setWarehouseId(warehouseStartId);
                                processTaskBillEntity.setBatch("");
                                processTaskBillEntity.setRemark("系统自动生成");
                                processTaskBillEntity.setCreateUser("admin");
                                //TODO 准备加工任务单的加工原料信息
                                List<ProcessDetailEntity> processDetailEntities = new ArrayList<>();
                                //获取该sku的加工原料信息
                                Result skuMachineInfo = productService.getSkuMachineInfo(transferOrderDetailsVo.getSkuId());
                                List<Object> machineInfoList = (List<Object>) skuMachineInfo.getData();
                                for (Object o : machineInfoList) {
                                    ProcessDetailEntity processDetailEntity = new ProcessDetailEntity();
                                    Map data = (Map) o;
                                    processDetailEntity.setSkuId((String) data.get("dependencySkuId"));
                                    processDetailEntity.setUnitNum((Integer) data.get("quantity"));
                                    processDetailEntity.setTotalAmount(((Integer) data.get("quantity")) * processNum);
                                    processDetailEntity.setWarehouseId(warehouseStartId);
                                    processDetailEntity.setBatch("");
                                    processDetailEntities.add(processDetailEntity);
                                }
                                addProcessTaskBillVO.setEntity(processTaskBillEntity);
                                addProcessTaskBillVO.setEntityList(processDetailEntities);
                                warehouseService.addProcessBill(gson.toJson(addProcessTaskBillVO));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setData(e.getMessage());
        }
    }

    public String test(int num) {
        DecimalFormat df = new DecimalFormat("000000");
        return df.format(num);
    }
}
