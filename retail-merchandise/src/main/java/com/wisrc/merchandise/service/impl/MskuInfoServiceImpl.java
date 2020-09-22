package com.wisrc.merchandise.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.merchandise.dao.*;
import com.wisrc.merchandise.dto.DeptOperationEmployeeDTO;
import com.wisrc.merchandise.dto.crawler.MskuCrawlerDto;
import com.wisrc.merchandise.dto.msku.*;
import com.wisrc.merchandise.dto.warehouse.FbaOnWayQuantityAndOnStockQuantityDTO;
import com.wisrc.merchandise.entity.*;
import com.wisrc.merchandise.outside.ProductOutside;
import com.wisrc.merchandise.outside.TeamOutside;
import com.wisrc.merchandise.outside.WarehouseService;
import com.wisrc.merchandise.query.*;
import com.wisrc.merchandise.service.*;
import com.wisrc.merchandise.utils.*;
import com.wisrc.merchandise.vo.*;
import com.wisrc.merchandise.vo.mskuStockSalesInfo.MskuStockSalesInfoVO;
import com.wisrc.merchandise.vo.mskuStockSalesInfo.SetBatchMskuStockSalesInfoVO;
import com.wisrc.merchandise.vo.outside.GetIdByNumAndShopVo;
import com.wisrc.merchandise.vo.outside.MskuRelationVo;
import com.wisrc.merchandise.vo.outside.MskuSafetyDayEditVo;
import com.wisrc.merchandise.vo.outside.SkuInfoByMskuAndShopVo;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.*;

import static com.wisrc.merchandise.utils.Crypto.sha;

@Service
public class MskuInfoServiceImpl implements MskuInfoService {

    private final Logger logger = LoggerFactory.getLogger(MskuInfoServiceImpl.class);

    //    @Autowired
//    private AsinOutsideService asinService;
    @Autowired
    private MskuInfoDao mskuInfoDao;
    @Autowired
    private MskuInfoEpitaphDao mskuInfoEpitaphDao;
    @Autowired
    private SalesStatusService salesStatusService;
    @Autowired
    private MskuStatusService mskuStatusService;
    @Autowired
    private ShopOutsideService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MskuExtInfoDao mskuExtInfoDao;
    @Autowired
    private MskuDeliveryModeAttrService mskuDeliveryModeAttrService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CodeService codeService;
    @Autowired
    private BasicPlatformInfoDao basicPlatformInfoDao;
    @Autowired
    private MerchandiseBasicShopInfoDao merchandiseBasicShopInfoDao;
    @Autowired
    private ReplenishmentService replenishmentService;
    @Autowired
    private VMskuInfoDao vMskuInfoDao;
    @Autowired
    private TeamOutside teamOutside;
    @Autowired
    private com.wisrc.merchandise.service.externalservice.ProductService outProductService;
    @Autowired
    private CrawlerService crawlerService;
    @Autowired
    private ProductOutside productOutside;
    @Autowired
    private GenerateCommodityDao generateCommodityDao;
    private WarehouseService warehouseService;

    @Override
    public Result batchGetMsku(List<String> commodityIds) {
        List<wmsMskuInfoVO> mskuInfoEntities = mskuInfoDao.findMskuInfoBatch(commodityIds);
        return Result.success(mskuInfoEntities);
    }

    @Override
    public Map<String, Map> getWarehouseIdAndFnsku(List<Map> mapList) {
        Map<String, Map> resultMap = new HashMap();
        if (mapList == null) {
            return null;
        }
        for (Map map : mapList) {
            String sellerId = (String) map.get("sellerId");
            String fnsku = (String) map.get("fnsku");
            Map<String, String> platMap = mskuInfoDao.getPaltId(sellerId);
            if (platMap == null) {
                continue;
            }
            String commodityId = mskuInfoDao.getByFnsku(fnsku);
            Map<String, String> commodityMap = mskuInfoDao.getCommodityById(commodityId);
            if (commodityMap == null) {
                continue;
            }
            String warehouseId = basicPlatformInfoDao.getFbaWarehouse(platMap.get("platId"));
            Map finalMap = new HashMap();
            finalMap.put("warehouseId", warehouseId);
            finalMap.put("skuId", commodityMap.get("skuId"));
            finalMap.put("shopId", platMap.get("shopId"));
            finalMap.put("fnsku", fnsku);
            resultMap.put(sellerId + fnsku, finalMap);
        }
        return resultMap;
    }

    @Override
    public Result checkFnsku(List<Map> mapList) {
        if (mapList != null) {
            for (Map map : mapList) {
                String mskuId = (String) map.get("mskuId");
                String shopId = (String) map.get("shopId");
                String fnsku = (String) map.get("fnsku");
                String commodityId = mskuInfoDao.getCommodityId(mskuId, shopId);
                String checkFnsku = mskuInfoDao.getFnsku(commodityId);
                if (fnsku != null) {
                    if (!fnsku.equals(checkFnsku)) {
                        return Result.failure(391, "fnsku不同", null);
                    }
                }
            }
        }
        return Result.success();
    }

    @Override
    public Result searchMskuInfo(Integer pageNum, Integer pageSize,
                                 String platformName, String shopName,
                                 String mskuId,
                                 String[] excludeMskuIds) {
        String args = ArrayToInArguments.toInArgs(excludeMskuIds);
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<VMskuEntity> list = vMskuInfoDao.search(platformName, shopName, mskuId, args);
            PageInfo pageInfo = new PageInfo(list);
            LinkedHashMap ret = PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "basisMskuInfo", list);
            return Result.success(ret);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(423, "查询msku信息失败，请联系管理员", excludeMskuIds);
        }
    }


    @Override
    public Result getMskuList(String user,
                              String shopId,
                              String team,
                              String manager,
                              Integer deliveryMode,
                              Integer salesStatus,
                              Integer mskuStatus,
                              String findKey,
                              Integer currentPage,
                              Integer pageSize,
                              String sort) {
        try {
            // 定义变量
            MskuPageDTO results = new MskuPageDTO();
            List<Map<String, Object>> salesStatuses = null;
            List deliverys = new ArrayList();
            List<GetMskuPageDTO> mskuList = new ArrayList();
            List<String> skuIds = new ArrayList<>();
            List<Map> mskuShopArr = new ArrayList<>();
            Map salesStatusMap = new HashMap();
            Map mskuStatusMap = new HashMap();
            Map pictureMap = new HashMap<>();
            Map cnName = null;

            // 定义库存SKU搜索结果
            List storeSkuDealted = null;
            List sellerSkuDealted = null;
            List<MskuPageEntity> mskuFound = new ArrayList<>();
            List<Map<String, Object>> mskuStatuses = null;
            List<Map<String, Object>> teamStatus = new ArrayList<>();
            String prop = null;
            String order = null;
            List Ids = new ArrayList();
            Map deliveryModeMap = new HashMap();


            // 产品信息搜索过滤，根据关键字关联产品筛选商品
            if (findKey != null) {
                try {
                    storeSkuDealted = productService.getFindKeyDealted(findKey);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    return Result.failure(424, "获取库存SKU搜索信息失败，请联系管理员", null);
                }
            }

            // 获取配送方式选择框
            try {
                deliverys = mskuDeliveryModeAttrService.getMskuDeliveryModeAttrList();
            } catch (Exception e) {
                logger.error(e.getMessage());
                return Result.failure(424, "通过配送方式搜索商品信息失败，请联系管理员", null);
            }

            List<String> employeeIdList = new ArrayList<>();
            String employeeIdListStr = null;
            // 分组编号搜索，由于分组信息与员工信息关联，所有，将分组信息搜索转换成员工信息搜索
            if (team != null && !team.isEmpty()) {
                List<LinkedHashMap> teamEmployee = (List<LinkedHashMap>) employeeService.employeeSelector(team);
                if (teamEmployee != null && teamEmployee.size() > 0) {
                    for (LinkedHashMap ele : teamEmployee) {
                        employeeIdList.add(ele.get("employeeId").toString());
                    }
                }
                employeeIdListStr = ArrayToInArguments.toInArgs(employeeIdList);
            }

            try {
                // 根据关键字查询商品并排序
                JSONObject jb = JSONObject.parseObject(sort);
                if (jb.getString("prop") != null && jb.getString("order") != null) {
                    if (jb.getString("prop").equals("updateTime")) {
                        prop = "update_time";
                    }
                    if (jb.getString("order").equals("ascending")) {
                        order = "ASC";
                    } else if (jb.getString("order").equals("descending")) {
                        order = "DESC";
                    }
                }

                if (prop != null && order != null) {
                    PageHelper.orderBy(prop + " " + order);
                }
                if (currentPage != null && pageSize != null) {
                    PageHelper.startPage(currentPage, pageSize);
                }
                GetMskuListQuery getMskuListQuery = new GetMskuListQuery();
                getMskuListQuery.setShopId(shopId);
                getMskuListQuery.setEmployeeIdListStr(employeeIdListStr);
                getMskuListQuery.setManager(manager);
                getMskuListQuery.setDeliveryMode(deliveryMode);
                getMskuListQuery.setSalesStatus(salesStatus);
                getMskuListQuery.setMskuStatus(mskuStatus);
                getMskuListQuery.setFindKey(findKey);
                getMskuListQuery.setStoreSkuDealted(storeSkuDealted);
                getMskuListQuery.setSellerSkuDealted(sellerSkuDealted);
                getMskuListQuery.setProp(prop);
                getMskuListQuery.setOrder(order);

                // 根据当前用户获取访问msku权限
                List mskuPrivilege = null;
                if (!user.equals("admin")) {
                    mskuPrivilege = employeeService.mskuPrivilege(user);
                    getMskuListQuery.setUserId(user);
                }
                getMskuListQuery.setMskuPrivilege(mskuPrivilege);

                mskuFound = mskuInfoDao.getMskuList(getMskuListQuery);

                salesStatusMap = salesStatusService.getSalesStatusMap();
                mskuStatusMap = mskuStatusService.getMskuStatusMap();
                salesStatuses = salesStatusService.mapToList(salesStatusMap);
                mskuStatuses = mskuStatusService.mapToList(mskuStatusMap);
            } catch (Exception e) {
                logger.error(e.getMessage());
                return Result.failure(423, "查询商品信息失败，请联系管理员", null);
            }

            PageInfo pageInfo = new PageInfo(mskuFound);
            long totalNum = pageInfo.getTotal();

            //msku在途在仓实时查询参数数组对象
            List<FbaOnWayQuantityAndOnStockQuantityDTO> fbaOnWayQuantityAndOnStockQuantityQueryList = new ArrayList<>();
            // 获取关联外键参数
            List<String> employeeIds = new ArrayList<>();
            for (MskuPageEntity mskuInfoEntity : mskuFound) {
                skuIds.add(mskuInfoEntity.getSkuId());
                Map mskuShop = new HashMap();
                mskuShop.put(mskuInfoEntity.getMskuId(), mskuInfoEntity.getShopId());
                mskuShopArr.add(mskuShop);
                Ids.add(mskuInfoEntity.getId());
                employeeIds.add(mskuInfoEntity.getUserId());

                //获取所有msku和shopId，fnsku信息
                FbaOnWayQuantityAndOnStockQuantityDTO fbaOnWayQuantityAndOnStockQuantityData
                        = new FbaOnWayQuantityAndOnStockQuantityDTO();
                fbaOnWayQuantityAndOnStockQuantityData.setMskuId(mskuInfoEntity.getMskuId());
                fbaOnWayQuantityAndOnStockQuantityData.setShopId(mskuInfoEntity.getShopId());
                fbaOnWayQuantityAndOnStockQuantityData.setFnSkuId(mskuInfoEntity.getFnsku());
                fbaOnWayQuantityAndOnStockQuantityQueryList.add(fbaOnWayQuantityAndOnStockQuantityData);

            }

            // 获取商品外部信息
            if (Ids.size() > 0) {
                try {
                    // 获取配送方式关系
                    deliveryModeMap = mskuDeliveryModeAttrService.ListToRelation(deliverys);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.failure();
                }
            }

            // 获取商品产品信息
            if (skuIds.size() > 0) {
                pictureMap = getPictureMap(skuIds);

                // 获取产品名称
                try {
                    cnName = productService.getProductCN(skuIds);
                } catch (Exception e) {
                    e.printStackTrace();
                    cnName = null;
                }
            }

            // 获取商品负责人信息
            Map<String, String> employeeName = new HashMap();
            if (employeeIds.size() > 0) {
                try {
                    employeeName = employeeService.getEmployeeNameMap(employeeIds);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            HashMap<String, DeptOperationEmployeeDTO> deptList = new HashMap<>();
            try {
                deptList = employeeService.getEmployees(employeeIds);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //FBA库存与销量
            Map<String, MskuStockSalesInfoEntity> mssifMap = new HashMap();
            String sqlText = toArgs(Ids);
            if (sqlText != null) {
                List<MskuStockSalesInfoEntity> mSSIFList = mskuInfoDao.getMSSIEListById(sqlText);
                for (MskuStockSalesInfoEntity mssif : mSSIFList) {
                    mssifMap.put(mssif.getId(), mssif);
                }
            }

            Map<String, FbaOnWayQuantityAndOnStockQuantityDTO> fbaOnWayQuantityAndOnStockQuantityDataMap = new HashMap<>();
            //查询msku fba在途和在仓数据
            if (!fbaOnWayQuantityAndOnStockQuantityQueryList.isEmpty()) {
                try {
                    Result result = warehouseService.
                            getFbaOnWayQuantityAndOnStockQuantityListByShopIdAndMskuList(fbaOnWayQuantityAndOnStockQuantityQueryList);
                    if (result.getCode() == 200) {
                        List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) result.getData();
//                        转换List到Map,键为msku+shopId+fnsku
                        if (list != null && !list.isEmpty()) {
                            for (LinkedHashMap<String, Object> data : list) {
                                if (data != null) {
                                    String shopIdTemp = (String) data.get("shopId");
                                    String mskuIdTemp = (String) data.get("mskuId");
                                    String fnskuIdTemp = (String) data.get("fnSkuId");
                                    Integer fbaTransportQty = (Integer) data.get("fbaTransportQty");
                                    Integer fbaStockQty = (Integer) data.get("fbaStockQty");
                                    //自定义生成key格式，键为msku+shopId+fnsku
                                    String key = mskuIdTemp + shopIdTemp + fnskuIdTemp;

                                    FbaOnWayQuantityAndOnStockQuantityDTO f = new FbaOnWayQuantityAndOnStockQuantityDTO();
                                    f.setShopId(shopIdTemp);
                                    f.setMskuId(mskuIdTemp);
                                    f.setFnSkuId(fnskuIdTemp);
                                    f.setFbaTransportQuantityRealTime(fbaTransportQty);
                                    f.setFbaStockQuantityRealTime(fbaStockQty);

                                    fbaOnWayQuantityAndOnStockQuantityDataMap.put(key, f);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("获取fba 在途在仓数据错误", e);
                }
            }

            // 封装商品信息
            for (MskuPageEntity mskuInfoEntity : mskuFound) {
                String storeName = null;
                Object picture = null;
                String shelfTime = null;

                if (cnName != null) {
                    if (cnName.get(mskuInfoEntity.getSkuId()) != null) {
                        storeName = String.valueOf(cnName.get(mskuInfoEntity.getSkuId()));
                    }
                }
                if (pictureMap != null) {
                    picture = pictureMap.get(mskuInfoEntity.getSkuId());
                }
                if (mskuInfoEntity.getShelfTime() != null) {
                    shelfTime = Time.formatDate(mskuInfoEntity.getShelfTime());
                }

                GetMskuPageDTO getMskuPageDTO = new GetMskuPageDTO();
                BeanUtils.copyProperties(mskuInfoEntity, getMskuPageDTO);
                getMskuPageDTO.setMsku(mskuInfoEntity.getMskuId());
                getMskuPageDTO.setStoreName(storeName);
                String employee = mskuInfoEntity.getUserId();
                if (deptList.containsKey(employee)) {
                    DeptOperationEmployeeDTO dt = deptList.get(employee);
                    getMskuPageDTO.setTeamCd(dt.getDeptCd());
                    getMskuPageDTO.setTeamName(dt.getDeptName());
                }

                getMskuPageDTO.setEmployeeId(mskuInfoEntity.getUserId());
                getMskuPageDTO.setStoreSku(mskuInfoEntity.getSkuId());
                getMskuPageDTO.setManager(employeeName.get(mskuInfoEntity.getUserId()));
                getMskuPageDTO.setSalesStatus((Map) salesStatusMap.get(mskuInfoEntity.getSalesStatusCd()));
                getMskuPageDTO.setUpdateTime(Time.formatDate(mskuInfoEntity.getUpdateTime()));
                getMskuPageDTO.setMskuStatus((Map) mskuStatusMap.get(mskuInfoEntity.getMskuStatusCd()));
                getMskuPageDTO.setPicture(String.valueOf(picture));
                getMskuPageDTO.setAsin(mskuInfoEntity.getAsin());
                getMskuPageDTO.setFnsku(mskuInfoEntity.getFnsku());
                getMskuPageDTO.setDelivery(String.valueOf(deliveryModeMap.get(mskuInfoEntity.getDeliveryMode())));
                getMskuPageDTO.setShelfTime(shelfTime);

                if (mssifMap.get(mskuInfoEntity.getId()) != null) {
                    getMskuPageDTO.setFbaOnWarehouseStockNum(mssifMap.get(mskuInfoEntity.getId()).getFbaOnWarehouseStockNum());
                    getMskuPageDTO.setYesterdaySalesNum(mssifMap.get(mskuInfoEntity.getId()).getYesterdaySalesNum());
                    getMskuPageDTO.setDayBeforeYesterdaySalesNum(mssifMap.get(mskuInfoEntity.getId()).getDayBeforeYesterdaySalesNum());
                    getMskuPageDTO.setPreviousSalesNum(mssifMap.get(mskuInfoEntity.getId()).getPreviousSalesNum());
                }
                //为fba在仓和在途赋值
                // 这里仅在途取实时数据，在仓取非实时
//                键为msku+shopId+fnsku
                String key = mskuInfoEntity.getMskuId() + mskuInfoEntity.getShopId() + mskuInfoEntity.getFnsku();
                FbaOnWayQuantityAndOnStockQuantityDTO data = fbaOnWayQuantityAndOnStockQuantityDataMap.get(key);
                if (data != null) {
                    //仅赋值实时在途库存
                    getMskuPageDTO.setFbaOnWayStockNum(data.getFbaTransportQuantityRealTime());
                }


                mskuList.add(getMskuPageDTO);
            }

            mskuStatuses.remove(2);

            results.setMskuList(mskuList);
            results.setTotalNum(totalNum);
            results.setSalesStatus(salesStatuses);
            results.setMskuStatus(mskuStatuses);
            results.setTeamList(getTeamList());
            results.setDispatchStatus(deliverys);

            return Result.success(results);
        } catch (
                Exception e)

        {
            e.printStackTrace();
            return Result.failure();
        }

    }

    private String toArgs(List<String> list) {
        StringBuffer sb = new StringBuffer("(");
        for (String ele : list) {
            sb.append("'").append(ele).append("',");
        }
        sb.setCharAt(sb.length() - 1, ')');
        if (sb.length() < 3) {
            return null;
        }
        return sb.toString();
    }

    private String getParaString(List<String> list) {
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

    @Override
    public Result saveMsku(MskuInfoSaveVo mskuInfoSaveVo) {
        Timestamp date = new Timestamp(new Date().getTime());
//        Timestamp time = asinService.getShelfTimestamp(mskuInfoSaveVo.getAsin());
        Timestamp time = null;

        try {
            String id = sha(mskuInfoSaveVo.getMsku().trim(), mskuInfoSaveVo.getShopId());
            MskuPageEntity mskuInfoEntity = mskuInfoDao.getMsku(id);
            if (mskuInfoEntity != null) {
                return new Result(400, "", "MSKU编号重复");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        MskuInfoEntity mskuInfoEntity = new MskuInfoEntity();
        MskuExtInfoEntity mskuExtInfoEntity = new MskuExtInfoEntity();

        String id = sha(mskuInfoSaveVo.getMsku(), mskuInfoSaveVo.getShopId());
        mskuInfoEntity.setId(id);
        mskuInfoEntity.setMskuId(mskuInfoSaveVo.getMsku());
        mskuInfoEntity.setShopId(mskuInfoSaveVo.getShopId());
        mskuInfoEntity.setMskuName(mskuInfoSaveVo.getMskuName());
        mskuInfoEntity.setSkuId(mskuInfoSaveVo.getStoreSkuId());
        mskuInfoEntity.setParentAsin(mskuInfoSaveVo.getParentAsin());
        mskuInfoEntity.setUserId(mskuInfoSaveVo.getManager());
        mskuInfoEntity.setSalesStatusCd(mskuInfoSaveVo.getSalesStatus());
        mskuInfoEntity.setUpdateTime(date);
        mskuInfoEntity.setMskuStatusCd(1);
        mskuInfoEntity.setCommission(mskuInfoSaveVo.getCommission());
        if (mskuInfoSaveVo.getSalesStatus() != 0) {
            mskuInfoEntity.setMskuStatusEditCd(1);
        } else {
            mskuInfoEntity.setMskuStatusEditCd(0);
        }

        mskuExtInfoEntity.setId(id);
        mskuExtInfoEntity.setAsin(mskuInfoSaveVo.getAsin());
        mskuExtInfoEntity.setFnSkuId(mskuInfoSaveVo.getFnsku());
        mskuExtInfoEntity.setShelfDate(time);
       /* int delivery = 2;

        if (mskuInfoSaveVo.getFnsku() != null) {
            delivery = 1;
        }*/
        if (mskuInfoSaveVo.getDeliveryTypeDesc() == null) {
            mskuExtInfoEntity.setDeliveryMode(1);
        } else {
            if (mskuInfoSaveVo.getDeliveryTypeDesc().equals("FBA")) {
                mskuExtInfoEntity.setDeliveryMode(1);
            }
            if (mskuInfoSaveVo.getDeliveryTypeDesc().equals("FBM")) {
                mskuExtInfoEntity.setDeliveryMode(2);
            }
        }
        //fba销售与销量
        MskuStockSalesInfoEntity mskuStockSalesInfoEntity = new MskuStockSalesInfoEntity();
        mskuStockSalesInfoEntity.setId(id);
        mskuStockSalesInfoEntity.setShopId(mskuInfoSaveVo.getShopId());
        mskuStockSalesInfoEntity.setMskuId(mskuInfoSaveVo.getMsku());

        try {
            mskuInfoDao.saveMsku(mskuInfoEntity);
            mskuExtInfoDao.saveMskuExtInfo(mskuExtInfoEntity);
            mskuInfoDao.saveMskuStockSalesInfo(mskuStockSalesInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return new Result(200, "", null);
    }

    @Override
    public Result getMsku(String id) {
        GetMskuDTO getMskuDTO = new GetMskuDTO();

        try {
            MskuPageEntity mskuInfoEntity = mskuInfoDao.getMsku(id);
            String sellerId = shopService.getSellerId(mskuInfoEntity.getShopId());

            BeanUtils.copyProperties(mskuInfoEntity, getMskuDTO);
            getMskuDTO.setStoreSku(mskuInfoEntity.getSkuId());
            getMskuDTO.setEmployee(mskuInfoEntity.getUserId());
            getMskuDTO.setFnsku(mskuInfoEntity.getFnSkuId());
            getMskuDTO.setDelivery(mskuInfoEntity.getDeliveryModeDesc());
            getMskuDTO.setSellerId(sellerId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return new Result(200, "", getMskuDTO);
    }

    @Override
    public Result getMskuOutSide(String id) {
        GetMskuOutsideDTO getMskuOutsideDTO = new GetMskuOutsideDTO();

        try {
            MskuPageEntity mskuInfoEntity = mskuInfoDao.getMsku(id);
            if (mskuInfoEntity == null) {
                return Result.success(getMskuOutsideDTO);
            }

            Map pictureMap = new HashMap();
            List skuId = new ArrayList();
            skuId.add(mskuInfoEntity.getSkuId());
            if (skuId.size() > 0) {
                pictureMap = getPictureMap(skuId);
            }

            // 获取销售状态信息
            Map<Integer, String> salesStatusMap = new HashMap();
            try {
                salesStatusMap = codeService.getSalesStatus();
            } catch (Exception e) {

            }

            String picture = null;
            if (pictureMap.get(mskuInfoEntity.getSkuId()) != null)
                picture = String.valueOf(pictureMap.get(mskuInfoEntity.getSkuId()));

            BeanUtils.copyProperties(mskuInfoEntity, getMskuOutsideDTO);
            getMskuOutsideDTO.setFnsku(mskuInfoEntity.getFnSkuId());
            getMskuOutsideDTO.setStoreSku(mskuInfoEntity.getSkuId());
            getMskuOutsideDTO.setEmployee(mskuInfoEntity.getUserId());
            getMskuOutsideDTO.setShop(idAndName(mskuInfoEntity.getShopId(), mskuInfoEntity.getShopName()));
            getMskuOutsideDTO.setDelivery(idAndName(mskuInfoEntity.getDeliveryMode(), mskuInfoEntity.getDeliveryModeDesc()));
            getMskuOutsideDTO.setSalesStatus(idAndName(mskuInfoEntity.getSalesStatusCd(), salesStatusMap.get(mskuInfoEntity.getSalesStatusCd())));
            getMskuOutsideDTO.setPicture(picture);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return new Result(200, "", getMskuOutsideDTO);
    }

    @Override
    @Transactional(transactionManager = "retailMerchandiseTransactionManager", rollbackFor = Exception.class)
    public Result editMsku(MskuInfoEditVo mskuInfoEditVo) {
        try {
            MskuInfoEntity mskuInfoEntity = new MskuInfoEntity();
            MskuExtInfoEntity mskuExtInfoEntity = new MskuExtInfoEntity();
            Timestamp date = new Timestamp(new Date().getTime());
//            Timestamp time = asinService.getShelfTimestamp(mskuInfoEditVo.getAsin());

            MskuPageEntity mskuEntity = mskuInfoDao.getMsku(mskuInfoEditVo.getId());

            mskuInfoEntity.setId(mskuInfoEditVo.getId());
            mskuInfoEntity.setUserId(mskuInfoEditVo.getManager());
            mskuInfoEntity.setSalesStatusCd(mskuInfoEditVo.getSalesStatus());
            if (mskuInfoEditVo.getParentAsin() != null) mskuInfoEntity.setParentAsin(mskuInfoEditVo.getParentAsin());
            mskuInfoEntity.setMskuName(mskuInfoEditVo.getMskuName());
            mskuInfoEntity.setSkuId(mskuInfoEditVo.getStoreSkuId());
            mskuInfoEntity.setUpdateTime(date);
            mskuInfoEntity.setCommission(mskuInfoEditVo.getCommission());

            mskuExtInfoEntity.setId(mskuInfoEditVo.getId());
            mskuExtInfoEntity.setAsin(mskuInfoEditVo.getAsin());
            mskuExtInfoEntity.setFnSkuId(mskuInfoEditVo.getFnsku());
//            mskuExtInfoEntity.setShelfDate(time);
            int delivery = 2;
            if (mskuInfoEditVo.getFnsku() != null) {
                delivery = 1;
            }
            mskuExtInfoEntity.setDeliveryMode(delivery);

            mskuInfoDao.editMsku(mskuInfoEntity);
            mskuExtInfoDao.editMskuExtInfo(mskuExtInfoEntity);
            if (mskuEntity.getSalesStatusCd() != mskuInfoEditVo.getSalesStatus()) {
                mskuInfoEntity.setMskuStatusEditCd(1);
                mskuInfoDao.editEditCd(mskuInfoEntity);
            }

            return new Result(200, "", null);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }
    }

    @Override
    public Result mskuSwitch(MskuSwitchVo mskuSwitchVo) {
        Map mskuStatusMap = new HashMap();
        Map results = new HashMap();

        MskuInfoEntity mskuInfoEntity = new MskuInfoEntity();
        mskuInfoEntity.setId(mskuSwitchVo.getId());
        if (mskuSwitchVo.getMskuStatus() == 1) {
            mskuInfoEntity.setMskuStatusCd(2);
            mskuStatusMap.put("mskuStatus", 2);
        } else {
            mskuInfoEntity.setMskuStatusCd(1);
            mskuStatusMap.put("mskuStatus", 1);
        }
        try {
            mskuInfoDao.mskuSwitch(mskuInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        mskuStatusMap.put("id", mskuSwitchVo.getId());

        results.put("changeMsku", mskuStatusMap);
        return new Result(200, "", results);
    }

    @Override
    public Result allDelete(IdsBatchVo idsBatchVo) {
        Map results = new HashMap();
        List changeStatus = new ArrayList();
        Map mskuStatus = new HashMap();

        try {
            mskuInfoDao.mskuAllDown(idsBatchVo.getIds());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        for (String id : idsBatchVo.getIds()) {
            mskuStatus.put("id", id);
            mskuStatus.put("mskuStatus", "2");
            changeStatus.add(mskuStatus);
        }
        results.put("changesStatus", changeStatus);
        return new Result(200, "", results);
    }

    @Override
    public Result checkMsku(String msku, String shopId) {
        Map mskuChecked = new HashMap();
        Map<String, Object> results = new HashMap<>();
        List<Map> mskuShops = new ArrayList<>();
        Map<String, String> mskuShop = new HashMap<>();
        mskuShop.put(msku, shopId);
        mskuShops.add(mskuShop);
        String sellerId;
        String delivery = "FBM";
        String marketPlaceId;

        try {
            sellerId = shopService.getSellerId(shopId);
            marketPlaceId = basicPlatformInfoDao.getSellerId(shopId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "查询店铺信息失败，获取参数shopId：" + shopId + "，失败的原因是" + e.getMessage(), "信息有误，请检查后重新提交");
        }

        try {
//            List keys = new ArrayList();
//            keys.add("amazon.mws.url");
//            keys.add("amazon.mws.asin");
//            Map urlMap = codeService.getKey(keys);
//            JSONObject checkMsku = asinService.checkMsku(msku, sellerId, (String) urlMap.get("amazon.mws.url"));
//            JSONArray jsonArr = checkMsku.getJSONArray("data");
//            if (jsonArr != null) {
//                for (int m = 0; m < jsonArr.size(); m++) {
//                    JSONObject json = jsonArr.getJSONObject(m);
//
//                    if (json.getString("asin") == null) {
//                        return new Result(400, "", "信息有误，请检查后重新提交");
//                    }
//
//                    String parentAsin = null;
//                    JSONObject parentJson = asinService.getParentAsin(json.getString("asin"), marketPlaceId, (String) urlMap.get("amazon.mws.asin"));
//                    Map data = parentJson.getJSONObject("data");
//                    for (Object key : data.keySet()) {
//                        parentAsin = (String) data.get(key);
//                        break;
//                    }
//
//                    mskuChecked.put("FnSKU", json.getString("fnsku"));
//                    mskuChecked.put("asin", json.getString("asin"));
//                    if (json.getString("fnsku") != null) delivery = "FBA";
//                    mskuChecked.put("delivery", delivery);
//                    mskuChecked.put("parentAsin", parentAsin);
//                }
//            } else {
//                return new Result(400, "", "信息有误，请检查后重新提交");
//            }

            MskuCrawlerDto mskuCrawlerDto = crawlerService.getMskuCrewler(msku, sellerId);
            mskuChecked.put("FnSKU", mskuCrawlerDto.getFnsku());
            mskuChecked.put("asin", mskuCrawlerDto.getAsin());
            if (mskuCrawlerDto.getFnsku() != null) delivery = "FBA";
            mskuChecked.put("delivery", delivery);
            mskuChecked.put("parentAsin", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "网络延迟，请重新尝试", "asin获取失败：" + e.getMessage());
        }

        results.put("mskuChecked", mskuChecked);
        return new Result(200, "", results);
    }

    @Override
    public Result saveEpitaph(EpitaphSaveVo epitaphSaveVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }

        try {
            MskuInfoEpitaphEntity mskuInfoEpitaph = mskuInfoEpitaphDao.getMskuEpitaphById(epitaphSaveVo.getId());
            if (mskuInfoEpitaph != null) {
                editEpitaph(epitaphSaveVo, bindingResult);
                return Result.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        MskuInfoEpitaphEntity mskuInfoEpitaphEntity = VoToEntity.epitaphVoToEntity(epitaphSaveVo);
        if (!mskuInfoEpitaphEntity.getEpitaph().replace(" ", "").isEmpty()) {
            try {
                mskuInfoEpitaphDao.saveMskuEpitaph(mskuInfoEpitaphEntity);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        } else {
            return new Result(400, "", "墓志铭内容不能为空");
        }

        return new Result(200, "", null);
    }

    @Override
    public Result editEpitaph(EpitaphSaveVo epitaphSaveVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }

        MskuInfoEpitaphEntity mskuInfoEpitaphEntity = VoToEntity.epitaphVoToEntity(epitaphSaveVo);
        if (mskuInfoEpitaphEntity.getEpitaph().replace(" ", "").isEmpty()) {
            try {
                mskuInfoEpitaphDao.deleteMskuEpitaph(mskuInfoEpitaphEntity);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        } else {
            try {
                mskuInfoEpitaphDao.editMskuEpitaph(mskuInfoEpitaphEntity);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        return new Result(200, "", null);
    }

    @Override
    public Result changeManager(IdsBatchVo chargeEditVo) {
        try {
            mskuInfoDao.changeManager(chargeEditVo.getManager(), chargeEditVo.getIds());
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result getMskuListOutside(MskuInfoPageOutsideVo mskuInfoPageOutsideVo, String user) {
        try {
            List storeSkuDealted;
            MskuPageOutsideQuery mskuPageOutsideQuery = new MskuPageOutsideQuery();
            List<MskuPageEntity> mskuFound;
            List skuId = new ArrayList();
            Map cnName = null;
            MskuPageOutsideDTO result = new MskuPageOutsideDTO();
            List<GetMskuPageOutsideDTO> mskuList = new ArrayList<>();
            List employeeIds = new ArrayList();
            Map<String, String> employeeName = new HashMap();
            Map pictureMap = new HashMap<>();

            BeanUtils.copyProperties(mskuInfoPageOutsideVo, mskuPageOutsideQuery);


            // 根据当前用户获取访问msku权限
            List mskuPrivilege = null;
            if (!user.equals("admin")) {
                mskuPrivilege = employeeService.mskuPrivilege(user);
                mskuPageOutsideQuery.setUserId(user);
            }

            mskuPageOutsideQuery.setMskuPrivilege(mskuPrivilege);

            // 根据关键字关联产品筛选商品
            if (mskuInfoPageOutsideVo.getFindKey() != null) {
                try {
                    storeSkuDealted = productService.getFindKeyDealted(mskuInfoPageOutsideVo.getFindKey());
                    mskuPageOutsideQuery.setStoreSkuDealted(storeSkuDealted);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                if (mskuInfoPageOutsideVo.getPageNum() != null && mskuInfoPageOutsideVo.getPageSize() != null) {
                    PageHelper.startPage(mskuInfoPageOutsideVo.getPageNum(), mskuInfoPageOutsideVo.getPageSize());
                }
                PageHelper.orderBy("update_time" + " " + "DESC");
                mskuFound = mskuInfoDao.getMskuListOutside(mskuPageOutsideQuery);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }

            PageInfo pageInfo = new PageInfo(mskuFound);
            result.setTotal(pageInfo.getTotal());
            result.setPages(pageInfo.getPages());

            // 获取关联外键参数
            for (MskuPageEntity mskuInfoEntity : mskuFound) {
                skuId.add(mskuInfoEntity.getSkuId());
                employeeIds.add(mskuInfoEntity.getUserId());
            }

            try {
                employeeName = employeeService.getEmployeeNameMap(employeeIds);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 获取商品产品信息
            if (skuId.size() > 0) {
                // 获取商品图片
                pictureMap = getPictureMap(skuId);

                // 获取产品名称
                try {
                    cnName = productService.getProductCN(skuId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 获取销售状态信息
            Map<Integer, String> salesStatusMap = new HashMap();
            try {
                salesStatusMap = codeService.getSalesStatus();
            } catch (Exception e) {

            }

            // 封装商品信息
            for (MskuPageEntity mskuInfoEntity : mskuFound) {
                String productName = null;
                String picture = null;

                if (cnName != null) {
                    productName = String.valueOf(cnName.get(mskuInfoEntity.getSkuId()));
                }
                if (pictureMap != null) {
                    Object pictureUtl = pictureMap.get(mskuInfoEntity.getSkuId());
                    if (pictureUtl != null) picture = String.valueOf(pictureUtl);
                }

                GetMskuPageOutsideDTO getMskuPageDTO = new GetMskuPageOutsideDTO();
                BeanUtils.copyProperties(mskuInfoEntity, getMskuPageDTO);
                getMskuPageDTO.setProductName(productName);
                getMskuPageDTO.setEmployee(employeeName.get(mskuInfoEntity.getUserId()));
                getMskuPageDTO.setSalesStatus(salesStatusMap.get(mskuInfoEntity.getSalesStatusCd()));
                getMskuPageDTO.setFnsku(mskuInfoEntity.getFnSkuId());
                getMskuPageDTO.setPicture(picture);

                mskuList.add(getMskuPageDTO);
            }

            result.setMskuList(mskuList);
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result getMskuInfo(GetMskuListByIdVo getMskuListByIdVo) {
        GetMskuInfoDTO result = new GetMskuInfoDTO();
        List<MskuInfoDTO> mskuInfoList = new ArrayList<>();
        List<MskuPageEntity> mskuInfoResult;
        Map<String, String> cnName = new HashMap();

        try {
            mskuInfoResult = mskuInfoDao.getMskuByPlan(getMskuListByIdVo.getIds());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        // 获取关联外键参数
        List skuId = new ArrayList();
        List employeeIds = new ArrayList();
        for (MskuPageEntity mskuInfoEntity : mskuInfoResult) {
            skuId.add(mskuInfoEntity.getSkuId());
            employeeIds.add(mskuInfoEntity.getUserId());
        }

        // 获取商品产品信息
        Map pictureMap = new HashMap();
        if (skuId.size() > 0) {
            pictureMap = getPictureMap(skuId);

            // 获取产品名称
            try {
                cnName = productService.getProductCN(skuId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 获取销售状态信息
        Map<Integer, String> salesStatusMap = new HashMap();
        try {
            salesStatusMap = codeService.getSalesStatus();
        } catch (Exception e) {

        }

        // 获取商品负责人信息
        Map<String, String> employeeName = new HashMap();
        if (employeeIds.size() > 0) {
            try {
                employeeName = employeeService.getEmployeeNameMap(employeeIds);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (MskuPageEntity mskuPage : mskuInfoResult) {
            String picture = null;
            if (String.valueOf(pictureMap.get(mskuPage.getSkuId())) != null)
                picture = String.valueOf(pictureMap.get(mskuPage.getSkuId()));

            MskuInfoDTO mskuInfoDTO = new MskuInfoDTO();
            BeanUtils.copyProperties(mskuPage, mskuInfoDTO);
            if (mskuPage.getShelfDate() != null) {
                mskuInfoDTO.setShelfDate(Time.formatDate(mskuPage.getShelfDate()));
            }
            if (mskuPage.getUpdateTime() != null) {
                mskuInfoDTO.setUpdateTime(Time.formatDate(mskuPage.getUpdateTime()));
            }
            mskuInfoDTO.setSalesStatusDesc(salesStatusMap.get(mskuPage.getSalesStatusCd()));
            mskuInfoDTO.setFnSkuId(mskuPage.getFnSkuId());
            mskuInfoDTO.setPicture(picture);
            mskuInfoDTO.setProductName(cnName.get(mskuPage.getSkuId()));
            mskuInfoDTO.setEmployee(employeeName.get(mskuInfoDTO.getUserId()));

            mskuInfoList.add(mskuInfoDTO);
        }

        result.setMskuInfoBatch(mskuInfoList);
        return Result.success(result);
    }

    @Override
    public Result getMskuId(GetMskuIdVo getMskuIdVo, String user) {
        try {
            // 根据当前用户获取访问msku权限
            List mskuPrivilege = null;
            if (!user.equals("admin")) {
                mskuPrivilege = employeeService.mskuPrivilege(user);
            }

            List<String> ids = mskuInfoDao.getMskuId(getMskuIdVo.getShopName(), getMskuIdVo.getMskuId(), mskuPrivilege, user);
            return Result.success(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result getIdByNumAndShop(GetIdByNumAndShopVo getMskuIdVo, String user) {
        try {
            Map IdMap = new HashMap();
            if (getMskuIdVo.getNumAndShopVo().size() == 0) {
                return Result.success(IdMap);
            }

            Map<String, String> uniqueCodeValue = new HashMap();
            for (MskuRelationVo mskuVo : getMskuIdVo.getNumAndShopVo()) {
                uniqueCodeValue.put(Crypto.join(mskuVo.getMsku(), mskuVo.getShopOwnerId()), mskuVo.getUniqueCode());
            }


            GetIdByNumAndShopQuery query = new GetIdByNumAndShopQuery();
            List<MskuRelationQuery> mskuListQuery = new ArrayList<>();
            for (MskuRelationVo mskuVo : getMskuIdVo.getNumAndShopVo()) {
                MskuRelationQuery mskuRelationQuery = new MskuRelationQuery();
                BeanUtils.copyProperties(mskuVo, mskuRelationQuery);

                mskuListQuery.add(mskuRelationQuery);
            }
            // 根据当前用户获取访问msku权限
            List mskuPrivilege = null;
            if (!user.equals("admin")) {
                mskuPrivilege = employeeService.mskuPrivilege(user);
                query.setUser(user);
            }
            query.setNumAndShopVo(mskuListQuery);
            query.setMskuPrivilege(mskuPrivilege);

            List<GetIdByNumAndShop> mskuList = mskuInfoDao.getIdByNumAndShop(query);
            for (GetIdByNumAndShop msku : mskuList) {
                Map mskuReturn = new HashMap();
                mskuReturn.put("id", msku.getId());
                mskuReturn.put("shopId", msku.getShopId());
                if (uniqueCodeValue.get(Crypto.join(msku.getMskuId(), msku.getShopOwnerId())) != null) {
                    IdMap.put(uniqueCodeValue.get(Crypto.join(msku.getMskuId(), msku.getShopOwnerId())), mskuReturn);
                }
            }

            return Result.success(IdMap);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result getIdByMskuIdAndName(GetByMskuIdAndNameVo getByMskuIdAndNameVo, String user) {
        try {


            GetIdByMskuIdAndNameQuery getIdByMskuIdAndNameQuery = new GetIdByMskuIdAndNameQuery();
            BeanUtils.copyProperties(getByMskuIdAndNameVo, getIdByMskuIdAndNameQuery);

            // 根据当前用户获取访问msku权限
            List mskuPrivilege = null;
            if (!user.equals("admin")) {
                mskuPrivilege = employeeService.mskuPrivilege(user);
                getIdByMskuIdAndNameQuery.setUserId(user);
            }
            getIdByMskuIdAndNameQuery.setMskuPrivilege(mskuPrivilege);


            List<String> ids = mskuInfoDao.getIdByMskuIdAndName(getIdByMskuIdAndNameQuery);
            return Result.success(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result mskuSearch(MskuSearchVo mskuSearchVo, String user) {
        try {
            List<MskuSearchDto> result = new ArrayList<>();
            MskuSearchQuery mskuSearchQuery = new MskuSearchQuery();
            BeanUtils.copyProperties(mskuSearchVo, mskuSearchQuery);

            // 根据关键字关联产品筛选商品
            if (mskuSearchVo.getSkuid() != null || mskuSearchVo.getProductName() != null) {
                try {
                    List skuIds = productService.getByIdAndName(mskuSearchVo.getSkuid(), mskuSearchVo.getProductName());
                    if (skuIds.size() == 0) {
                        return Result.success(result);
                    }
                    mskuSearchQuery.setSkuids(skuIds);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.failure();
                }
            }

            // 根据当前用户获取访问msku权限
            List mskuPrivilege = null;
            if (!user.equals("admin")) {
                mskuPrivilege = employeeService.mskuPrivilege(user);
                mskuSearchQuery.setUserId(user);
            }

            mskuSearchQuery.setMskuPrivilege(mskuPrivilege);

            List<mskuRelationEntity> mskuList;
            try {
                mskuList = mskuInfoDao.mskuSearch(mskuSearchQuery);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }

            List skuIds = new ArrayList();
            for (mskuRelationEntity msku : mskuList) {
                skuIds.add(msku.getSkuId());
            }

            // 获取产品名称
            Map<String, String> cnName = new HashMap();
            try {
                cnName = productService.getProductCN(skuIds);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Map<Integer, String> salesStatus = new HashMap<>();
            try {
                salesStatus = codeService.getSalesStatus();
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (mskuRelationEntity msku : mskuList) {
                MskuSearchDto mskuSearchDto = new MskuSearchDto();
                BeanUtils.copyProperties(msku, mskuSearchDto);
                mskuSearchDto.setProductName(cnName.get(msku.getSkuId()));
                mskuSearchDto.setSalesStatus(salesStatus.get(msku.getSalesStatusCd()));
                if (mskuSearchVo.getSalesStatus() == null) {
                    result.add(mskuSearchDto);
                } else {
                    if (mskuSearchVo.getSalesStatus() == msku.getSalesStatusCd()) {
                        result.add(mskuSearchDto);
                    }
                }
            }

            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result mskuFnsku(String fnsku) {
        GetMskuFnskuDto result = new GetMskuFnskuDto();
        List<GetMskuEntity> mskuFnskuList;
        Map<String, String> pictureMap = new HashMap<>();
        Map<String, String> cnName = new HashMap();

        try {
            mskuFnskuList = mskuInfoDao.mskuFnsku(fnsku);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        if (mskuFnskuList.size() > 0) {
            GetMskuEntity getMskuFnsku = mskuFnskuList.get(0);
            Map<Integer, String> salesStatus = new HashMap();
            try {
                salesStatus = codeService.getSalesStatus();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 获取商品产品信息
            List skuIds = new ArrayList();
            skuIds.add(getMskuFnsku.getSkuId());
            pictureMap = getPictureMap(skuIds);

            // 获取产品名称
            try {
                cnName = productService.getProductCN(skuIds);
            } catch (Exception e) {
                e.printStackTrace();
            }

            BeanUtils.copyProperties(getMskuFnsku, result);
            result.setPicture(pictureMap.get(result.getSkuId()));
            result.setProductName(cnName.get(result.getSkuId()));
            result.setFnsku(getMskuFnsku.getFnSkuId());
            result.setSalesStatus(salesStatus.get(getMskuFnsku.getSalesStatusCd()));
        } else {
            return new Result(400, "fnsku数据不存在", "");
        }

        return Result.success(result);
    }

    /**
     * 通过fnsku模糊查询msku
     *
     * @param fnsku
     * @return
     */
    @Override
    public List<GetMskuFnskuDto> mskuByFnSkuLike(String fnsku) {
        List<GetMskuFnskuDto> result = new ArrayList<>();
        if (fnsku == null) {
            return result;
        }

        Map<String, String> pictureMap;
        Map<String, String> cnName;
        List<GetMskuEntity> mskuFnskuList = mskuInfoDao.mskuFnskuLike(fnsku);
        if (mskuFnskuList != null && mskuFnskuList.size() > 0) {
            for (GetMskuEntity mskuEntity : mskuFnskuList) {
                Map<Integer, String> salesStatus;
                GetMskuFnskuDto getMskuFnskuDto = new GetMskuFnskuDto();
                try {
                    salesStatus = codeService.getSalesStatus();
                } catch (Exception e) {
                    logger.error("获取销售状态错误,将状态置空", e);
                    salesStatus = new HashMap();
                }

                // 获取商品产品信息
                List skuIds = new ArrayList();
                skuIds.add(mskuEntity.getSkuId());
                pictureMap = getPictureMap(skuIds);

                // 获取产品名称
                try {
                    cnName = productService.getProductCN(skuIds);
                } catch (Exception e) {
                    logger.error("获取产品名称错误，将名称置空,sku编号为：" + skuIds, e);
                    cnName = new HashMap<>();
                }

                BeanUtils.copyProperties(mskuEntity, getMskuFnskuDto);
                getMskuFnskuDto.setPicture(pictureMap.get(getMskuFnskuDto.getSkuId()));
                getMskuFnskuDto.setProductName(cnName.get(getMskuFnskuDto.getSkuId()));
                getMskuFnskuDto.setFnsku(mskuEntity.getFnSkuId());
                getMskuFnskuDto.setSalesStatus(salesStatus.get(mskuEntity.getSalesStatusCd()));
                result.add(getMskuFnskuDto);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("查询的fnsku为%s,共获取记录%d条", fnsku, result.size());
        }
        return result;

    }

    @Override
    public Result getSkuId(List saleStatusList) {
        try {
            return Result.success(mskuInfoDao.getSkuIdBySalesStatus(saleStatusList));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    @Transactional(transactionManager = "retailMerchandiseTransactionManager", rollbackFor = Exception.class)
    public Result updateSalesStock(@Valid SetBatchMskuStockSalesInfoVO vo) {
        try {
            List<MskuStockSalesInfoVO> mskuStockSalesInfoList = vo.getMskuStockSalesInfoList();
            MskuStockSalesInfoEntity entity = new MskuStockSalesInfoEntity();
            List editMsku = new ArrayList();
            for (MskuStockSalesInfoVO o : mskuStockSalesInfoList) {
                BeanUtils.copyProperties(o, entity);
                mskuInfoDao.updateSalesStock(entity);
                editMsku.add(entity.getId());
            }
            return Result.success();
        } catch (BeansException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }

    }

    @Override
    public Result getSalesStockById(String id) {
        //FBA库存与销量
        MskuStockSalesInfoEntity mSSIEntity = mskuInfoDao.getMSSIE(id);
        return Result.success(mSSIEntity);
    }

    @Override
    public Result updateSaleList(List<MskuSaleNumEnity> mskuSaleNumEnityList) {
        List<BasicShopDetailsInfoEntity> basicShopDetailsInfoEntityList = merchandiseBasicShopInfoDao.search(null, null, null, null);
        Map<String, String> map = new HashMap();
        String date = DateUtil.convertDateToStr(new Date(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        Date modifyTime = DateUtil.convertStrToDate(date, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        for (BasicShopDetailsInfoEntity basicShopDetailsInfoEntity : basicShopDetailsInfoEntityList) {
            map.put(basicShopDetailsInfoEntity.getShopOwnerId(), basicShopDetailsInfoEntity.getShopId());
        }
        if (mskuSaleNumEnityList != null) {
            for (MskuSaleNumEnity mskuSaleNumEnity : mskuSaleNumEnityList) {
                mskuSaleNumEnity.setShopId(map.get(mskuSaleNumEnity.getSellerId()));
                mskuSaleNumEnity.setModifyTime(modifyTime);
                mskuInfoDao.updateShipmentStock(mskuSaleNumEnity);
            }
        }
        return Result.success();
    }

    @Override
    public Result getByKeyword(GetByKeywordVo getByKeywordVo, String user) {
        try {
            List<mskuRelationEntity> mskuList = new ArrayList<>();
            // 根据关键字关联产品筛选商品
            String key = null;

            List storeSkuDealted = null;
            if (getByKeywordVo.getSkuId() != null || getByKeywordVo.getProductName() != null) {
                if (getByKeywordVo.getSkuId() != null) {
                    key = getByKeywordVo.getSkuId();
                } else if (getByKeywordVo.getProductName() != null) {
                    key = getByKeywordVo.getProductName();
                }
                storeSkuDealted = productService.getFindKeyDealted(key);
                if (storeSkuDealted == null || storeSkuDealted.size() == 0) {
                    return Result.success(mskuList);
                }
            }


            GetByKeywordQuery getByKeywordQuery = new GetByKeywordQuery();
            // 根据当前用户获取访问msku权限
            List mskuPrivilege = null;
            if (!user.equals("admin")) {
                mskuPrivilege = employeeService.mskuPrivilege(user);
                getByKeywordQuery.setUserId(user);
            }
            getByKeywordQuery.setStoreSkuDealted(storeSkuDealted);
            getByKeywordQuery.setEmployeeId(getByKeywordVo.getEmployeeId());
            getByKeywordQuery.setMskuPrivilege(mskuPrivilege);


            mskuList = mskuInfoDao.getByKeyword(getByKeywordQuery);
            return Result.success(mskuList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result getMskuFBA(GetMskuFBAVo getMskuFBAVo, String user) {
        try {
            List<GetMskuFBADto> result = new ArrayList<>();

            // 根据关键字关联产品筛选商品
            List storeSkuDealted = null;
            String findKey = getMskuFBAVo.getFindKey();
            if (findKey != null) {
                try {
                    storeSkuDealted = productService.getFindKeyDealted(findKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            GetMskuFBAQuery getMskuFBAQuery = new GetMskuFBAQuery();
            // 根据当前用户获取访问msku权限
            List mskuPrivilege = null;
            BeanUtils.copyProperties(getMskuFBAVo, getMskuFBAQuery);
            if (!user.equals("admin")) {
                mskuPrivilege = employeeService.mskuPrivilege(user);
                getMskuFBAQuery.setUserId(user);
            }

            getMskuFBAQuery.setStoreSkuDealted(storeSkuDealted);
            getMskuFBAQuery.setMskuPrivilege(mskuPrivilege);


            List<GetMskuFBA> mskuFBAList = mskuInfoDao.getMskuFBA(getMskuFBAQuery);

            // 获取关联外键参数
            List<String> employeeIds = new ArrayList<>();
            List<String> skuId = new ArrayList<>();
            for (GetMskuFBA mskuInfoEntity : mskuFBAList) {
                skuId.add(mskuInfoEntity.getSkuId());
                employeeIds.add(mskuInfoEntity.getUserId());
            }

            // 获取商品产品信息
            Map<String, String> cnName = new HashMap();
            Map<String, String> pictureMap = new HashMap();
            Map<String, Map> productSales = new HashMap<>();
            if (skuId.size() > 0) {
                pictureMap = getPictureMap(skuId);

                // 获取产品名称
                try {
                    cnName = productService.getProductCN(skuId);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    productSales = productService.getProductSales(skuId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 获取商品负责人信息
            Map<String, String> employeeName = new HashMap();
            if (employeeIds.size() > 0) {
                try {
                    employeeName = employeeService.getEmployeeNameMap(employeeIds);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 获取销售状态信息
            Map<Integer, String> salesStatusMap = new HashMap();
            try {
                salesStatusMap = codeService.getSalesStatus();
            } catch (Exception e) {

            }

            for (GetMskuFBA mskuFBA : mskuFBAList) {
                GetMskuFBADto getMskuFBADto = new GetMskuFBADto();
                BeanUtils.copyProperties(mskuFBA, getMskuFBADto);
                getMskuFBADto.setProductName(cnName.get(getMskuFBADto.getSkuId()));
                getMskuFBADto.setEmployeeName(employeeName.get(mskuFBA.getUserId()));
                getMskuFBADto.setSalesStatusDesc(salesStatusMap.get(mskuFBA.getSalesStatusCd()));
                getMskuFBADto.setPicture(pictureMap.get(mskuFBA.getSkuId()));
                if (productSales.get(getMskuFBADto.getSkuId()) != null) {
                    getMskuFBADto.setSafetyStockDays((Integer) productSales.get(getMskuFBADto.getSkuId()).get("safetyStockDays"));
                }

                result.add(getMskuFBADto);
            }

            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result updateStockList(List<Map> mapList) {
        List<BasicShopInfoEntity> basicShopInfoEntityList = merchandiseBasicShopInfoDao.findAll();
        String date = DateUtil.convertDateToStr(new Date(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        Date modifyTime = DateUtil.convertStrToDate(date, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        Map<String, String> shopMap = new HashMap<>();
        if (basicShopInfoEntityList != null && basicShopInfoEntityList.size() > 0) {
            for (BasicShopInfoEntity basicShopInfoEntity : basicShopInfoEntityList) {
                shopMap.put(basicShopInfoEntity.getShopOwnerId(), basicShopInfoEntity.getShopId());
            }
        }
        if (mapList != null && mapList.size() > 0) {
            for (Map map : mapList) {
                String sellerId = (String) map.get("sellerId");
                String shopId = shopMap.get(sellerId);
                Integer eableStockNum = (Integer) map.get("eableStockNum");
                Integer unableStockNum = (Integer) map.get("unableStockNum");
                Integer reservedCustomerorders = (Integer) map.get("reservedCustomerorders");
                Integer reservedFcTransfers = (Integer) map.get("reservedFcTransfers");
                Integer reservedFcProcessing = (Integer) map.get("reservedFcProcessing");
                String fnsku = (String) map.get("fnsku");
                String id = mskuInfoDao.getByFnsku(fnsku);
                if (eableStockNum != null && unableStockNum != null && reservedCustomerorders != null && reservedFcTransfers != null && reservedFcProcessing != null) {
                    int stockTotalQuantity = eableStockNum + unableStockNum + reservedCustomerorders + reservedFcTransfers + reservedFcProcessing;
                    int useableQuantity = reservedFcTransfers + reservedFcProcessing + eableStockNum;
                    int unableQuantity = reservedCustomerorders + unableStockNum;
                    mskuInfoDao.updateFbaStockDetail(shopId, id, stockTotalQuantity, useableQuantity, unableQuantity, modifyTime);
                }
            }
        }
        return Result.success();
    }

    @Override
    public List<String> getUnShelve() {
        List<String> list = mskuInfoDao.getUnShelve();
        return list;
    }

    @Override
    public void updateShelveInfo(List<Map> mapList) {
        if (mapList != null && mapList.size() > 0) {
            for (Map map : mapList) {
                String asin = (String) map.get("asin");
                String shelfDate = (String) map.get("shelfDate");
                if (StringUtils.isNotEmpty(shelfDate)) {
                    mskuInfoDao.updateShelveInfo(asin, shelfDate);
                }
            }
        }
    }

    @Override
    @Transactional(transactionManager = "retailMerchandiseTransactionManager", rollbackFor = Exception.class)
    public Result editSafetyDay(List<MskuSafetyDayEditVo> safetyDays) {
        try {
            for (MskuSafetyDayEditVo safetyDay : safetyDays) {
                MskuInfoEntity mskuInfo = new MskuInfoEntity();
                BeanUtils.copyProperties(safetyDay, mskuInfo);
                mskuInfoDao.editSafetyDay(mskuInfo);
            }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }
    }

    @Override
    public Result getMskuByEmployee(String userId) {
        try {
            // 根据当前用户获取访问msku权限
            List mskuPrivilege = new ArrayList();
            if (!userId.equals("admin")) {
                mskuPrivilege = employeeService.mskuPrivilege(userId);
                List IdList = mskuInfoDao.getMskuByEmployee(userId);
                mskuPrivilege.removeAll(IdList);
                mskuPrivilege.addAll(IdList);
            } else {
                mskuPrivilege = null;
            }

            return Result.success(mskuPrivilege);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public String checkFnCode(String mskuId, String shopId) {
        return mskuExtInfoDao.getFnCodeByMskuAndShop(mskuId, shopId);
    }

    @Override
    public Result getSkuInfoByFnSkuId(String fnSkuId) {
        String skuId = mskuExtInfoDao.finSkuIdByFnskuId(fnSkuId);
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isNotEmpty(skuId)) {
            Result skuInfo = outProductService.getSkuInfoBySkuId(skuId);
            Map skuInfoMap = (Map) skuInfo.getData();
            if (skuInfoMap.get("specificationsInfo") != null) {
                Map infoData = (Map) skuInfoMap.get("specificationsInfo");
                result.put("skuId", skuId);
                result.put("len", infoData.get("fbaLength"));
                result.put("width", infoData.get("fbaWidth"));
                result.put("height", infoData.get("fbaHeight"));
                result.put("weight", infoData.get("fbaWeight"));
                result.put("quantity", infoData.get("fbaQuantity"));
            } else {
                Map infoData = (Map) skuInfoMap.get("packingInfo");
                result.put("skuId", skuId);
                result.put("len", infoData.get("packLength"));
                result.put("width", infoData.get("packWidth"));
                result.put("height", infoData.get("packHeight"));
                result.put("weight", infoData.get("packWeight"));
                result.put("quantity", infoData.get("packQuantity"));
            }
            return Result.success(result);
        } else {
            return Result.success();
        }
    }

    public List<LinkedHashMap> getTeamList() {
        try {
            Result result = teamOutside.findAllOperationDept();
            if (result.getCode() == 200) {
                return (List<LinkedHashMap>) result.getData();
            }
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public Map idAndName(Object id, String name) {
        Map returnMap = new HashMap();
        returnMap.put("id", id);
        returnMap.put("name", name);
        return returnMap;
    }

    public Map getPictureMap(List skuIds) {
        Map pictureMap = new HashMap<>();

        try {
            List productPicture = productService.getProductPicture(skuIds);
            for (Object map : productPicture) {
                Map picture = ObjectHandler.LinkedHashMapToMap(map);
                List imageUrl = (List) picture.get("image");
                if (imageUrl.size() > 0) {
                    pictureMap.put(picture.get("skuId"), ObjectHandler.LinkedHashMapToMap(imageUrl.get(0)).get("imageUrl"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pictureMap;
    }

    @Override
    public Result getSkuInfoByShopIdAndMskuId(String shopId, String mskuId) {
        SkuInfoByMskuAndShopVo skuInfoByMskuAndShopVo = mskuExtInfoDao.getMskuInfoByMskuIdAndShopId(mskuId, shopId);
        if (skuInfoByMskuAndShopVo == null) {
            return Result.failure(390, "商品不存在", "");
        }
        Result skuResult = productOutside.getSkuInfoBySkuId(skuInfoByMskuAndShopVo.getSkuId());
        Map skuMap = (Map) skuResult.getData();
        List<Map> imgMap = (List<Map>) skuMap.get("imagesList");
        Map skuDefineMap = (Map) skuMap.get("define");
        Map specificationsInfo = (Map) skuMap.get("specificationsInfo");
        List<Map> skuLableMap = (List<Map>) skuMap.get("declareLabelList");

        //产品图片信息
        List<String> imgs = new ArrayList<>();
        for (Map map : imgMap) {
            imgs.add((String) map.get("imageUrl"));
        }
        skuInfoByMskuAndShopVo.setImgs(imgs);
        //产品装箱信息
        Map packMap = new HashMap();
        packMap.put("fbaWeight", specificationsInfo.get("fbaWeight"));
        packMap.put("fbaLength", specificationsInfo.get("fbaLength"));
        packMap.put("fbaWidth", specificationsInfo.get("fbaWidth"));
        packMap.put("fbaHeight", specificationsInfo.get("fbaHeight"));
        packMap.put("fbaQuantity", specificationsInfo.get("fbaQuantity"));

        //产品标签
        List<String> lables = new ArrayList<>();
        for (Map map : skuLableMap) {
            lables.add((String) map.get("labelText"));
        }
        skuInfoByMskuAndShopVo.setSkuName((String) skuDefineMap.get("skuNameZh"));
        skuInfoByMskuAndShopVo.setSkuPackInfo(packMap);
        skuInfoByMskuAndShopVo.setLables(lables);
        List<String> employees = new ArrayList<>();
        employees.add(skuInfoByMskuAndShopVo.getChargePersonId());
        Map employeeNameMap = employeeService.getEmployeeNameMap(employees);
        skuInfoByMskuAndShopVo.setChargePerson((String) employeeNameMap.get(skuInfoByMskuAndShopVo.getChargePersonId()));
        return Result.success(skuInfoByMskuAndShopVo);
    }

    @Override
    public Result updateCommodity() {
        List<MskuInfoEntity> duplicate = new ArrayList<>();
        List<MskuInfoEntity> rst = mskuInfoDao.findAll();
        for (MskuInfoEntity msku : rst) {
            String id = Crypto.sha(msku.getMskuId(), msku.getShopId());
            try {
                generateCommodityDao.updateCommodity(id, msku.getShopId(), msku.getMskuId());
            } catch (DuplicateKeyException e) {
                duplicate.add(msku);
                e.printStackTrace();
            }
        }
        return Result.success(duplicate);
    }

    public void mskuExcel(String user,
                          String shopId,
                          String team,
                          String manager,
                          Integer deliveryMode,
                          Integer salesStatus,
                          Integer mskuStatus,
                          String findKey,
                          String sort,
                          HttpServletResponse response,
                          HttpServletRequest request) {
        try {
            Result getExcelData = getMskuList(user, shopId, team, manager, deliveryMode, salesStatus, mskuStatus, findKey, 1, 99999, sort);
            if (getExcelData.getCode() != 200) {
                return;
            }

            MskuPageDTO mskuListData = (MskuPageDTO) getExcelData.getData();
            List<GetMskuPageDTO> mskuList = mskuListData.getMskuList();

            Map<String, String> managerMap = new HashMap();
            Map<String, String> teamMap = new HashMap<>();
            if (mskuList.size() > 0 && mskuList.get(0).getManager() == null) {
                List<LinkedHashMap> employeeData = employeeService.employeeSelector(null);
                for (LinkedHashMap employeeLink : employeeData) {
                    managerMap.put((String) employeeLink.get("employeeId"), (String) employeeLink.get("employeeName"));
                    teamMap.put((String) employeeLink.get("employeeId"), (String) employeeLink.get("deptName"));
                }
            }

            List<MskuExcelDto> excelDatas = new ArrayList<>();
            for (GetMskuPageDTO msku : mskuList) {
                MskuExcelDto mskuExcel = new MskuExcelDto();
                mskuExcel.setMsku(msku.getMsku());
                mskuExcel.setMskuName(msku.getMskuName());
                mskuExcel.setStoreSku(msku.getStoreSku());
                mskuExcel.setStoreName(msku.getStoreName());
                mskuExcel.setFnsku(msku.getFnsku());
                mskuExcel.setAsin(msku.getAsin());
                mskuExcel.setShopName(msku.getShopName());
                mskuExcel.setDelivery(msku.getDelivery());
                if (msku.getManager() == null) {
                    mskuExcel.setManager(managerMap.get(msku.getEmployeeId()));
                }else {
                    mskuExcel.setManager(msku.getManager());
                }
                if (msku.getTeamName() == null) {
                    mskuExcel.setTeamName(teamMap.get(msku.getEmployeeId()));
                }else {
                    mskuExcel.setTeamName(msku.getTeamName());
                }
                mskuExcel.setSalesStatus((String) msku.getSalesStatus().get("name"));
                mskuExcel.setShelfTime(msku.getShelfTime());
                mskuExcel.setUpdateTime(msku.getUpdateTime());
                mskuExcel.setCommission(msku.getCommission());

                excelDatas.add(mskuExcel);
            }
            String[] title = {"MSKU", "MSKU名称", "库存SKU", "产品中文名", "FnSKU", "ASIN", "配送方式", "店铺", "小组", "负责人", "销售状态", "上架日期", "更新日期", "佣金率"};
            ExcelTools.exportExcel("msku导出", title, excelDatas, "msku导出", response, request);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
