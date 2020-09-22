package com.wisrc.sales.webapp.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wisrc.sales.webapp.vo.msku.GetMskuIdVO;
import com.wisrc.sales.webapp.vo.msku.MskuVO;
import com.wisrc.sales.webapp.dao.SynchronousSalesEstimateDao;
import com.wisrc.sales.webapp.enity.EstimateApprovalEnity;
import com.wisrc.sales.webapp.enity.EstimateDetailEnity;
import com.wisrc.sales.webapp.enity.EstimateEnity;
import com.wisrc.sales.webapp.enity.RemarkEnity;
import com.wisrc.sales.webapp.helper.HttpAPIService;
import com.wisrc.sales.webapp.helper.HttpResult;
import com.wisrc.sales.webapp.query.GetEstimateQuery;
import com.wisrc.sales.webapp.service.SynchronousSalesEstimateService;
import com.wisrc.sales.webapp.service.externalService.MskuService;
import com.wisrc.sales.webapp.utils.Crypto;
import com.wisrc.sales.webapp.utils.Result;
import com.wisrc.sales.webapp.utils.Time;
import com.wisrc.sales.webapp.utils.Toolbox;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SynchronousSalesEstimateImplService implements SynchronousSalesEstimateService {
    private final Logger logger = LoggerFactory.getLogger(SynchronousSalesEstimateImplService.class);
    //同步接口的url地址
    private static final String basic_url = "http://58.63.40.178:9000/merchandise/sales/forecast";
    //同步接口的凭证
    private static final String license = "4960e1b603004554933cb919b9805ee2";
    //返回值的正确返回码
    private static final int responseCode = 200;
    //默认的用户
    private static final String default_user_id = "admin";

    @Autowired
    private MskuService mskuService;
    @Autowired
    private SynchronousSalesEstimateDao synchronousSalesEstimateDao;

    @Override
    @Transactional(transactionManager = "retailSalesTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result synchronousSingle(String shopSellerId, String msku) {
        try {
            if (shopSellerId == null || msku == null) {
                return new Result(9991, "卖家编号shopSellerId与商品msku不能为空", null);
            }

            HttpAPIService httpAPIService = new HttpAPIService();
            Map<String, Object> paraMap = new HashMap();
            //shopSellerId是我们数据库的卖家id，接口那边是卖家id的字段为shopId  （我们这边数据库shopId是指店铺id）
            String uniqueUrl = basic_url + "?" + "msku=" + msku + "&" + "shopId=" + shopSellerId;
            Map headersMap = new HashMap();
            headersMap.put("license", license);
            HttpResult response;
            try {
                response = httpAPIService.doGet(uniqueUrl, paraMap, headersMap);
            } catch (Exception e) {
                logger.info("销量预估外部接口出错！暂时无法同步", e);
                return new Result(9995, "销量预估外部接口出错！暂时无法同步", e);
            }
            if (response.getCode() != responseCode) {
                logger.info("销量预估外部接口出错！暂时无法同步", response);
                return new Result(response.getCode(), "销量预估外部接口出错！暂时无法同步", response);
            }

            //通过StringEscapeUtils.unescapeJava(str)  清除转义
            String str2 = StringEscapeUtils.unescapeJava(response.getBody());
            JSONArray dateArray = JSONUtil.parseArray(str2);

            if (dateArray.size() == 0) {
                //应产品要求：
                //一般结果为返回一个数组，一般情况下为多个元素。
                //如果返回空数组，请不要删除原来ERP中的数据继续使用
                return Result.success();
            }

            //shopId，msku获取商品id
            GetMskuIdVO getMskuIdVO = makeGetMskuIdVO(dateArray);
            Result mskuResult;
            // todo 暂时使用"admin",因为 整个接口程序功能是同步数据，对数据没有损害，没确定是否增加对用户的权限控制
            String userId = default_user_id;
            try {
                mskuResult = mskuService.shopIdAndMskuToId(userId, getMskuIdVO);
            } catch (Exception e) {
                logger.info("商品外部接口出错！暂时无法同步", e);
                return new Result(response.getCode(), "商品外部接口出错！暂时无法同步", e);
            }
            if (mskuResult.getCode() != 200) {
                logger.info("商品外部接口出错！暂时无法同步", mskuResult);
                return new Result(response.getCode(), "商品外部接口出错！暂时无法同步", mskuResult);
            }
            Map<String, Map<String, Object>> commodityIdMap = (Map<String, Map<String, Object>>) mskuResult.getData();


            ////共用的插入主方法
            //是否插入了
            insertMain(commodityIdMap, dateArray, false, userId);

            //本次至少有一个有效数据插入
//        if (addFlag) {
//            changeUpdateFlagSingel(estimateIdList, commodityIdList);
//        }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    //生成外部接口的VO参数
    private GetMskuIdVO makeGetMskuIdVO(JSONArray dateArray) {
        GetMskuIdVO getMskuIdVO = new GetMskuIdVO();
        List<MskuVO> paraList = new ArrayList<>();
        for (Object o : dateArray) {
            MskuVO mskuVO = new MskuVO();
            JSONObject jSONObject = JSONUtil.parseObj(o);
            mskuVO.setMsku(jSONObject.getStr("msku"));
            mskuVO.setShopOwnerId(jSONObject.getStr("shopId"));
            mskuVO.setUniqueCode(Crypto.join(jSONObject.getStr("shopId"), jSONObject.getStr("msku")));
            paraList.add(mskuVO);
        }
        getMskuIdVO.setNumAndShopVo(paraList);
        return getMskuIdVO;
    }

    // 单独的更新操作
    @Transactional(transactionManager = "retailSalesTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    protected void changeUpdateFlagSingel(List<String> estimateIdList, List<String> commodityIdList) {
        Map<String, Object> map = new HashMap();
        //取本地的使用中的数据
        //（商品唯一码处理值）
        String commodityIds = getIds(commodityIdList);
        map.put("commodityIds", commodityIds);
        map.put("updateFlag", 1);
        //利用update_flag=1和商品码commodityId唯一 找到上次的主键集合
        List<String> lastEstimateIds = synchronousSalesEstimateDao.getLast(map);
        String estimateIdsLast = getIds(lastEstimateIds);
        map.put("estimateIds", estimateIdsLast);
        map.put("updateFlag", 1);
        //删除主表主键为外键的且updateFlag = 1 的数据
        synchronousSalesEstimateDao.deleteEstimateApprovalUpdateFlagSingle(map);
        synchronousSalesEstimateDao.deleteEstimateDetailUpdateFlagSingle(map);
        synchronousSalesEstimateDao.deleteRemarkUpdateFlagSingle(map);
        synchronousSalesEstimateDao.deleteEstimateUpdateFlagSingle(map);
        //将本次更新的（主表主键处理值）且 updateFlag为2的数据改为updateFlag为1
        String estimateIdsNow = getIds(estimateIdList);
        map.put("updateFlag", 2);
        map.put("estimateIds", estimateIdsNow);
        synchronousSalesEstimateDao.updateEstimateUpdateFlagSingle(map);
        synchronousSalesEstimateDao.updateEstimateApprovalUpdateFlagSingle(map);
        synchronousSalesEstimateDao.updateEstimateDetailUpdateFlagSingle(map);
        synchronousSalesEstimateDao.updateRemarkUpdateFlagSingle(map);
    }


    @Override
    public Result regularUpdate() {

        try {
            HttpAPIService httpAPIService = new HttpAPIService();
            Map<String, Object> paraMap = new HashMap();
            Map headersMap = new HashMap();
            headersMap.put("license", license);
            HttpResult response;
            try {
                response = httpAPIService.doGet(basic_url, paraMap, headersMap);
            } catch (Exception e) {
                logger.info("销量预估外部接口出错！暂时无法同步", e);
                return new Result(9995, "销量预估外部接口出错！暂时无法同步", e);
            }
            if (response.getCode() != responseCode) {
                logger.info("销量预估外部接口出错！暂时无法同步", response);
                return new Result(response.getCode(), "销量预估外部接口出错！暂时无法同步", response);
            }
            //通过StringEscapeUtils.unescapeJava(str)  清除转义
            String str2 = StringEscapeUtils.unescapeJava(response.getBody());
            JSONArray dateArray = JSONUtil.parseArray(str2);
            if (dateArray.size() == 0) {
                //应产品要求：
                //一般结果为返回一个数组，一般情况下为多个元素。
                //如果返回空数组，请不要删除原来ERP中的数据继续使用
                return Result.success();
            }

            //shopId，msku获取商品id
            GetMskuIdVO getMskuIdVO = makeGetMskuIdVO(dateArray);
            Result mskuResult;
            // todo 暂时使用"admin",因为 整个接口程序功能是同步数据，对数据没有损害，没确定是否增加对用户的权限控制
            String userId = default_user_id;
            try {
                mskuResult = mskuService.shopIdAndMskuToId(userId, getMskuIdVO);
            } catch (Exception e) {
                logger.info("商品外部接口出错！暂时无法同步", e);
                return new Result(response.getCode(), "商品外部接口出错！暂时无法同步", e);
            }
            if (mskuResult.getCode() != 200) {
                logger.info("商品外部接口出错！暂时无法同步", mskuResult);
                return new Result(response.getCode(), "商品外部接口出错！暂时无法同步", mskuResult);
            }
            Map<String, Map<String, Object>> commodityIdMap = (Map<String, Map<String, Object>>) mskuResult.getData();

            //删除数据库中 update_flag = 2的残留数据，以便开启本次的全量更新
            deleteRemains();
            //共用的插入主方法
            //是否插入了
            insertMain(commodityIdMap, dateArray, true, null);
//        if (addFlag) {
//            changeUpdateFlag();
//        }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    //删除数据库中 update_flag = 2的数据
    private void deleteRemains() {
        synchronousSalesEstimateDao.deleteEstimateApprovalUpdateFlag(2);
        synchronousSalesEstimateDao.deleteEstimateDetailUpdateFlag(2);
        synchronousSalesEstimateDao.deleteRemarkUpdateFlag(2);
        synchronousSalesEstimateDao.deleteEstimateUpdateFlag(2);
    }

    @Transactional(transactionManager = "retailSalesTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    protected void changeUpdateFlag() {
        //删除 updateFlag为1的数据
        synchronousSalesEstimateDao.deleteEstimateApprovalUpdateFlag(1);
        synchronousSalesEstimateDao.deleteEstimateDetailUpdateFlag(1);
        synchronousSalesEstimateDao.deleteRemarkUpdateFlag(1);
        synchronousSalesEstimateDao.deleteEstimateUpdateFlag(1);
        //将updateFlag为2的数据改为updateFlag为1
        synchronousSalesEstimateDao.updateEstimateUpdateFlag(2);
        synchronousSalesEstimateDao.updateEstimateApprovalUpdateFlag(2);
        synchronousSalesEstimateDao.updateEstimateDetailUpdateFlag(2);
        synchronousSalesEstimateDao.updateRemarkUpdateFlag(2);
    }

    private String getIds(List<String> idList) {
        String ids = "";
        for (String estimateId : idList) {
            ids += "'" + estimateId + "'" + ",";
        }
        if (ids.endsWith(",")) {
            int index = ids.lastIndexOf(",");
            ids = ids.substring(0, index);
        }
        ids = "(" + ids + ")";
        return ids;
    }

    //共用的插入主方法
    private void insertMain(Map<String, Map<String, Object>> commodityIdMap, JSONArray dateArray, Boolean insertNew, String userId) throws Exception {
        // 1. 插入基本信息
        EstimateEnity estimateEnity = new EstimateEnity();
        estimateEnity.setUpdateFlag(1);
        String estimateDetailEnityUUID;
        JSONObject jSONObject;
        String uniqueCode;
        String commodityId;
        Map<String, Object> mskuMap;

        // 根据条件查询已存在的预估数据id
        GetEstimateQuery getEstimateQuery = new GetEstimateQuery();
        List<String> queryList = new ArrayList<>();
        for (Object o : dateArray) {
            try {
                jSONObject = JSONUtil.parseObj(o);
                queryList.add((String) commodityIdMap.get(Crypto.join(jSONObject.getStr("shopId"), jSONObject.getStr("msku"))).get("id"));
            } catch (Exception e) {
                continue;
            }
        }
        getEstimateQuery.setCommodityIdList(queryList);
        List<EstimateEnity> estimateList = synchronousSalesEstimateDao.getEstimateEnity(getEstimateQuery);
        Map<String, String> estimateMap = new HashMap();
        List estimateIds = new ArrayList();
        for (EstimateEnity estimateEntity : estimateList) {
            estimateMap.put(estimateEntity.getCommodityId(), estimateEntity.getEstimateId());
            estimateIds.add(estimateEntity.getEstimateId());
        }

        // 获取需进行判断的数据
        Map<String, EstimateApprovalEnity> approvalMap = new HashMap<>();
        Map<String, EstimateDetailEnity> detailMap = new HashMap<>();
        Map<String, RemarkEnity> remarkMap = new HashMap<>();
        if (estimateIds.size() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // 审查数据
            List<EstimateApprovalEnity> approvalList = synchronousSalesEstimateDao.getApprovalByIds(estimateIds);
            for (EstimateApprovalEnity approvalEntity : approvalList) {
                approvalMap.put(approvalEntity.getEstimateId(), approvalEntity);
            }
            // 预估每日详情数据
            List<EstimateDetailEnity> detailList = synchronousSalesEstimateDao.getDetailByIds(estimateIds);
            for (EstimateDetailEnity detailEntity : detailList) {
                detailMap.put(Crypto.join(detailEntity.getEstimateId(), sdf.format(detailEntity.getEstimateDate())), detailEntity);
            }
            // 备注详情数据
            List<RemarkEnity> remarkList = synchronousSalesEstimateDao.getRemarkByIds(estimateIds);
            for (RemarkEnity remarkEntity : remarkList) {
                remarkMap.put(remarkEntity.getEstimateDetailId(), remarkEntity);
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp nowDate = Time.getCurrentTimestamp();
        String nowTime = sdf.format(nowDate);
        for (Object o : dateArray) {
            jSONObject = JSONUtil.parseObj(o);
            uniqueCode = Crypto.join(jSONObject.getStr("shopId"), jSONObject.getStr("msku"));
            mskuMap = commodityIdMap.get(uniqueCode);
            if (mskuMap != null) {
                commodityId = (String) mskuMap.get("id");
                if (commodityId != null) {
                    String estimateId = estimateMap.get(commodityId);
                    String newEstimateId = Toolbox.randomUUID();
                    if (estimateId == null) {
                        if (insertNew) {
                            estimateEnity.setEstimateId(newEstimateId);
                            estimateEnity.setCommodityId(commodityId);
                            //商店的id
                            estimateEnity.setShopId((String) mskuMap.get("shopId"));
                            estimateEnity.setMskuId(jSONObject.getStr("msku"));
                            estimateEnity.setCreateTime(jSONObject.getStr("modifyTime"));
                            estimateEnity.setCreateUser(jSONObject.getStr("modifier"));
                            //店铺的卖家信息
                            estimateEnity.setShopSellerId(jSONObject.getStr("shopId"));
                            estimateEnity.setEffectiveDate(Time.getCurrentTimestamp());
                            synchronousSalesEstimateDao.insertEstimateEnity(estimateEnity);
                        } else {
                            throw new Exception("更新商品不匹配");
                        }
                    }

                    //审核
                    if (estimateId != null) {
                        EstimateApprovalEnity approvalEntity = approvalMap.get(estimateId);
                        if (!jSONObject.getStr("directApprovStatus").toString().equals(approvalEntity.getDirectApprovStatus()) ||
                                !jSONObject.getStr("managerApprovStatus").toString().equals(approvalEntity.getManagerApprovStatus()) ||
                                !jSONObject.getStr("planDepartApprovStatus").toString().equals(approvalEntity.getPlanDepartApprovStatus()) ||
                                !jSONObject.getStr("directApprovRemark").toString().equals(approvalEntity.getDirectApprovRemark()) ||
                                !jSONObject.getStr("managerApprovRemark").toString().equals(approvalEntity.getManagerApprovRemark()) ||
                                !jSONObject.getStr("planDepartApprovRemark").toString().equals(approvalEntity.getPlanDepartApprovRemark())
                        ) {
                            synchronousSalesEstimateDao.updateApprovalExpiration(estimateId, nowTime, userId);
                            saveApproval(estimateId, jSONObject, nowDate);
                        }
                    } else {
                        saveApproval(newEstimateId, jSONObject, nowDate);
                    }

                    //销量预估列表
                    JSONArray detailsArray = jSONObject.getJSONArray("details");
                    JSONObject eleJSONObject;
                    if (detailsArray != null) {
                        for (Object o1 : detailsArray) {
                            eleJSONObject = JSONUtil.parseObj(o1);
                            estimateDetailEnityUUID = Toolbox.randomUUID();
                            if (estimateId != null) {
                                EstimateDetailEnity detailEnity = detailMap.get(Crypto.join(estimateId, eleJSONObject.getStr("dataDt")));
                                if (detailEnity == null) {
                                    saveDetail(estimateId, estimateDetailEnityUUID, eleJSONObject, nowDate);
                                    saveRemark(estimateId, jSONObject, estimateDetailEnityUUID, eleJSONObject, nowDate);
                                } else {
                                    if (!(eleJSONObject.getInt("targetSalesQty") == detailEnity.getEstimateNumber())) {
                                        synchronousSalesEstimateDao.updateDetailExpiration(detailEnity.getUuid(), nowTime);
                                        saveDetail(estimateId, estimateDetailEnityUUID, eleJSONObject, nowDate);
                                    }

                                    RemarkEnity remarkEntity = remarkMap.get(detailEnity.getUuid());
                                    if (remarkEntity == null) {
                                        saveRemark(estimateId, jSONObject, estimateDetailEnityUUID, eleJSONObject, nowDate);
                                    } else {
                                        if (!jSONObject.getStr("modifier").toString().equals(remarkEntity.getEmployeeId()) || !eleJSONObject.getStr("remark").toString().equals(remarkEntity.getRemark())) {
                                            synchronousSalesEstimateDao.updateRemarkExpiration(remarkEntity.getEstimateDetailId(), nowTime);
                                            saveRemark(estimateId, jSONObject, estimateDetailEnityUUID, eleJSONObject, nowDate);
                                        }
                                    }
                                }

                            } else {
                                saveDetail(newEstimateId, estimateDetailEnityUUID, eleJSONObject, nowDate);
                                saveRemark(newEstimateId, jSONObject, estimateDetailEnityUUID, eleJSONObject, nowDate);
                            }
                        }
                    }
                }
            }
        }
    }

    private void saveApproval(String estimateId, JSONObject jSONObject, Timestamp nowDate) {
        // 2. 审核信息
        EstimateApprovalEnity estimateApprovalEnity = new EstimateApprovalEnity();
        estimateApprovalEnity.setUpdateFlag(1);

        estimateApprovalEnity.setUuid(Toolbox.randomUUID());
        estimateApprovalEnity.setEstimateId(estimateId);
        estimateApprovalEnity.setDirectApprovStatus(jSONObject.getStr("directApprovStatus"));
        estimateApprovalEnity.setManagerApprovStatus(jSONObject.getStr("managerApprovStatus"));
        estimateApprovalEnity.setPlanDepartApprovStatus(jSONObject.getStr("planDepartApprovStatus"));
        estimateApprovalEnity.setDirectApprovRemark(jSONObject.getStr("directApprovRemark"));
        estimateApprovalEnity.setManagerApprovRemark(jSONObject.getStr("managerApprovRemark"));
        estimateApprovalEnity.setPlanDepartApprovRemark(jSONObject.getStr("planDepartApprovRemark"));
        estimateApprovalEnity.setEffectiveDate(nowDate);
        synchronousSalesEstimateDao.insertEstimateApprovalEnity(estimateApprovalEnity);
    }

    private void saveDetail(String estimateId, String estimateDetailEnityUUID, JSONObject eleJSONObject, Timestamp nowDate) {
        // 3. 销量预估信息
        EstimateDetailEnity estimateDetailEnity = new EstimateDetailEnity();
        estimateDetailEnity.setUpdateFlag(1);
        estimateDetailEnity.setUuid(estimateDetailEnityUUID);
        estimateDetailEnity.setEstimateId(estimateId);
        estimateDetailEnity.setEstimateNumber(eleJSONObject.getInt("targetSalesQty"));
        estimateDetailEnity.setEstimateDate(DateUtil.parse(eleJSONObject.getStr("dataDt")));
        estimateDetailEnity.setEffectiveDate(nowDate);
        synchronousSalesEstimateDao.insertEstimateDetailEnity(estimateDetailEnity);
    }

    private void saveRemark(String estimateId, JSONObject jSONObject, String estimateDetailEnityUUID, JSONObject eleJSONObject, Timestamp nowDate) {
        //备注
        RemarkEnity remarkEnity = new RemarkEnity();
        remarkEnity.setUpdateFlag(1);
        remarkEnity.setEmployeeId(jSONObject.getStr("modifier"));
        remarkEnity.setEstimateDetailId(estimateDetailEnityUUID);
        remarkEnity.setRemark(eleJSONObject.getStr("remark"));
        remarkEnity.setCreateTime(eleJSONObject.getStr("modifyTime"));
        remarkEnity.setEstimateId(estimateId);
        remarkEnity.setEffectiveDate(nowDate);
        synchronousSalesEstimateDao.insertRemarkEnity(remarkEnity);
    }
}
