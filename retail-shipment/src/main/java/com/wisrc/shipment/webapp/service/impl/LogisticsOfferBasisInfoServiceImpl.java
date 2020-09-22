package com.wisrc.shipment.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.shipment.webapp.dao.*;
import com.wisrc.shipment.webapp.entity.*;
import com.wisrc.shipment.webapp.entity.exception.MyException;
import com.wisrc.shipment.webapp.service.LogisticsOfferBasisInfoService;
import com.wisrc.shipment.webapp.service.ShipMentService;
import com.wisrc.shipment.webapp.service.ShipmentListService;
import com.wisrc.shipment.webapp.service.externalService.ReplenishmentService;
import com.wisrc.shipment.webapp.utils.PageData;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.utils.Time;
import com.wisrc.shipment.webapp.vo.DestinationEnityVo;
import com.wisrc.shipment.webapp.vo.LogisticsOfferVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogisticsOfferBasisInfoServiceImpl implements LogisticsOfferBasisInfoService {
    @Autowired
    private LogisticsOfferBasisInfoDao logisticsOfferBasisInfoDao;
    @Autowired
    private LogisticsOfferEffectiveDao logisticsOfferEffectiveDao;
    @Autowired
    private LogisticsOfferHistoryInfoDao logisticsOfferHistoryInfoDao;
    @Autowired
    private LogisticsAllowLabelRelDao logisticsAllowLabelRelDao;
    @Autowired
    private LogisticsOfferDestinationDao logisticsOfferDestinationDao;
    @Autowired
    private LittlePackOfferDetailsDao littlePackOfferDetailsDao;
    @Autowired
    private LogisticsChargeTypeAttrDao logisticsChargeTypeAttrDao;
    @Autowired
    private ShipMentService shipMentService;
    @Autowired
    private ShipmentListService shipmentListService;
    @Autowired
    private ReplenishmentService replenishmentService;


    @Override
    public LinkedHashMap findByCond(String pageNum, String pageSize, int channelTypeCd, String labelCd, int deleteStatus, String keyword, int offerTypeCd, String zipcode) {
        List<LogisticsOfferBasisInfoEntity> basisList = null;
        //查询物流商名称记录中包含keyword的
        String shipmentIdsForSearch = null;
        if (keyword != null) {
            Result shipMentListResult = shipMentService.getByName(keyword);
            if (shipMentListResult.getCode() == 200) {
                List<String> shipmentIds = new ArrayList<>();
                Map data = (Map) shipMentListResult.getData();
                List<Map> shipmentEnterpriseEntityList = (List<Map>) data.get("shipmentEnterpriseEntityList");
                if (shipmentEnterpriseEntityList != null && shipmentEnterpriseEntityList.size() != 0) {
                    for (Map shipmentEnterpriseEntity : shipmentEnterpriseEntityList) {
                        String shipmentId = (String) shipmentEnterpriseEntity.get("shipmentId");
                        shipmentIds.add(shipmentId);
                    }
                    shipmentIdsForSearch = shipmentIds.stream().map(s -> "'" + s + "'").collect(Collectors.joining(","));
                }
            }
        }
        if (pageNum != null && pageSize != null && zipcode == null) {
            int num = Integer.valueOf(pageNum);
            int size = Integer.valueOf(pageSize);
            PageHelper.startPage(num, size);
            basisList = logisticsOfferBasisInfoDao.findByCond(channelTypeCd, deleteStatus, keyword, offerTypeCd, labelCd, shipmentIdsForSearch);
        } else {
            basisList = logisticsOfferBasisInfoDao.findByCond(channelTypeCd, deleteStatus, keyword, offerTypeCd, labelCd, shipmentIdsForSearch);
        }
        Map map = new HashMap<>();
        List<String> shipmentIds = new ArrayList();
        if (basisList != null) {
            for (LogisticsOfferBasisInfoEntity basis : basisList) {
                List<LogisticsOfferEffectiveEntity> effeList = logisticsOfferEffectiveDao.get(basis.getOfferId(), basis.getDeleteStatus());
                basis.setEffectiveList(effeList);
                if (basis.getOfferTypeCd() == 1) {
                    List<LogisticsOfferHistoryInfoEntity> historyList = logisticsOfferHistoryInfoDao.get(basis.getOfferId(), basis.getDeleteStatus());
                    String chargeName = logisticsChargeTypeAttrDao.findChargeNameById(basis.getChargeTypeCd());
                    basis.setChargeTypeName(chargeName);
                    basis.setHistoryList(historyList);
                }
                List<LogisticsOfferDestinationEnity> destList = logisticsOfferDestinationDao.findByCond(basis.getOfferId(), basis.getDeleteStatus());
                for (LogisticsOfferDestinationEnity dest : destList) {
                    if (offerTypeCd == 1) {
                        String destName = logisticsOfferDestinationDao.findDestNameById(dest.getDestinationCd());
                        dest.setDestinationName(destName);
                    }
                    if (offerTypeCd == 2) {
                        String destName = logisticsOfferDestinationDao.findCounNameById(dest.getDestinationCd());
                        dest.setDestinationName(destName);
                    }
                }
                if (basis.getOfferTypeCd() == 2) {
                    List<LittlePackOfferDetailsEntity> littlePackList = littlePackOfferDetailsDao.findByCond(basis.getOfferId(), basis.getDeleteStatus());
                    basis.setLittlePacketList(littlePackList);
                }
           /* for(LogisticsAllowLabelRelEntity label:labelList){
                Map  map = getLabelName(label.getLabelCd());

                //label.setLabelName(labelName);
            }*/
                List<LogisticsAllowLabelRelEntity> labelList = logisticsAllowLabelRelDao.findByCond(0, basis.getOfferId(), basis.getDeleteStatus());
                basis.setDestinationList(destList);
                basis.setLabelList(labelList);
                if (!shipmentIds.contains(basis.getShipmentId())) {
                    shipmentIds.add(basis.getShipmentId());
                }
            }
        }
        PageInfo pageInfo = new PageInfo(basisList);
        StringBuilder ids = new StringBuilder();
        for (String shipmentId : shipmentIds) {
            ids.append(shipmentId + ",");
        }
//      通过上诉拼接的ids批量查询对应的物流商名称
        Result shipResult = shipMentService.getProducts(ids.toString());
        if (shipResult.getCode() == 200) {
            List objects = (List) shipResult.getData();
            Map<String, String> shipMentMap = new HashMap();
            for (Object shipMent : objects) {
                Map shipMap = (Map) shipMent;
                String shipMentId = (String) shipMap.get("shipmentId");
                String shipmentName = (String) shipMap.get("shipmentName");
                shipMentMap.put(shipMentId, shipmentName);
            }
            for (LogisticsOfferBasisInfoEntity basis : basisList) {
                basis.setShipMentName(shipMentMap.get(basis.getShipmentId()));
            }
        }
        if (pageNum != null && pageSize != null) {
            return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "logisticsOfferBasisInfoList", basisList);
        }
        return PageData.pack(-1, -1, "logisticsOfferBasisInfoList", basisList);
    }

    @Override
    public LogisticsOfferBasisInfoEntity get(String offerId) {
        LogisticsOfferBasisInfoEntity ele = logisticsOfferBasisInfoDao.get(offerId);
        int deleStatus = 0;
        if (ele != null) {
            deleStatus = ele.getDeleteStatus();
        }
        if (ele == null) {
            return null;
        }
        List<LogisticsOfferEffectiveEntity> effeList = logisticsOfferEffectiveDao.get(offerId, deleStatus);
        List<LogisticsOfferHistoryInfoEntity> historyList = logisticsOfferHistoryInfoDao.get(offerId, deleStatus);
        List<LogisticsAllowLabelRelEntity> labelList = logisticsAllowLabelRelDao.get(offerId, deleStatus);
        List<LogisticsOfferDestinationEnity> destList = logisticsOfferDestinationDao.findByCond(offerId, deleStatus);
        List<LittlePackOfferDetailsEntity> littleList = littlePackOfferDetailsDao.findByCond(offerId, deleStatus);
        ele.setDestinationList(destList);
        ele.setEffectiveList(effeList);
        ele.setHistoryList(historyList);
        ele.setLabelList(labelList);
        ele.setLittlePacketList(littleList);
//      查询物流商名称
        Result result = shipMentService.getProduct(ele.getShipmentId());
        Map resultMap = (Map) result.getData();
        String chargeName = logisticsChargeTypeAttrDao.findChargeNameById(ele.getChargeTypeCd());
        ele.setChargeTypeName(chargeName);
        if (resultMap != null) {
            String shipMentName = (String) resultMap.get("shipmentName");
            ele.setShipMentName(shipMentName);
        }
        return ele;
    }

    @Override
    public void delete(String offerId, String userId) {
        String modifyTime = Time.getCurrentDateTime();
        logisticsOfferBasisInfoDao.delete(offerId, userId, modifyTime);
        logisticsOfferEffectiveDao.delete(offerId);
        logisticsOfferHistoryInfoDao.delete(offerId, userId, modifyTime);
        logisticsAllowLabelRelDao.delete(offerId);
        logisticsOfferDestinationDao.delete(offerId);
        littlePackOfferDetailsDao.deleteLittlePack(offerId);
    }

    @Override
    public List<LogisticsOfferBasisInfoEntity> findByChannelName(String channelName) {
        return logisticsOfferBasisInfoDao.findByChannelName(channelName);
    }


    @Override
    public void deleteByshipMentId(String shipmentId, String userId) {
        List<String> basisListIds = logisticsOfferBasisInfoDao.findByshipmentId(shipmentId);
        String time = Time.getCurrentDateTime();
        for (String offerId : basisListIds) {
            logisticsOfferBasisInfoDao.deleteByOfferId(offerId);
            logisticsOfferEffectiveDao.delete(offerId);
            logisticsOfferHistoryInfoDao.delete(offerId, userId, time);
            logisticsAllowLabelRelDao.delete(offerId);
            logisticsOfferDestinationDao.delete(offerId);
            littlePackOfferDetailsDao.deleteLittlePack(offerId);
        }
    }

    @Override
    public void addFba(LogicOfferBasisInfoFbaEnity ele) {
        String channelName = ele.getChannelName();
        String shipMentId = ele.getShipmentId();
        List<LogisticsOfferDestinationEnity> destList = ele.getDestinationList();
        List<String> list = new ArrayList();
        for (LogisticsOfferDestinationEnity enity : destList) {
            list.add(enity.getDestinationCd());
        }
        List<String> offerIds = logisticsOfferBasisInfoDao.getByNameAndShipId(channelName, shipMentId, ele.getOfferTypeCd());
        for (String offerId : offerIds) {
            List<String> destIds = logisticsOfferDestinationDao.getDestIdByOfferId(offerId);
            if (destIds == null) {
                continue;
            }
            if (list.size() == destIds.size() && list.containsAll(destIds)) {
                throw new MyException(390, "重复", null);
            }
        }
        logisticsOfferBasisInfoDao.addFba(ele);
    }

    @Override
    public void addLittle(LogicOfferBasisInfoLittilEnity ele) {
        String channelName = ele.getChannelName();
        String shipMentId = ele.getShipmentId();
        List<LogisticsOfferDestinationEnity> destList = ele.getDestinationList();
        List<String> list = new ArrayList();
        for (LogisticsOfferDestinationEnity enity : destList) {
            list.add(enity.getDestinationCd());
        }
        List<String> offerIds = logisticsOfferBasisInfoDao.getByNameAndShipId(channelName, shipMentId, ele.getOfferTypeCd());
        for (String offerId : offerIds) {
            List<String> destIds = logisticsOfferDestinationDao.getDestIdByOfferId(offerId);
            if (destIds == null) {
                continue;
            }
            if (list.size() == destIds.size() && list.containsAll(destIds)) {
                throw new MyException(390, "重复", null);
            }
        }
        logisticsOfferBasisInfoDao.addLittle(ele);
    }

    @Override
    public void updateFbaByFbaId(LogicOfferBasisInfoFbaEnity ele) {
        String channelName = ele.getChannelName();
        String shipMentId = ele.getShipmentId();
        List<LogisticsOfferDestinationEnity> destList = ele.getDestinationList();
        List<String> list = new ArrayList();
        for (LogisticsOfferDestinationEnity enity : destList) {
            list.add(enity.getDestinationCd());
        }
        List<String> offerIds = logisticsOfferBasisInfoDao.getByNameAndShipId(channelName, shipMentId, ele.getOfferTypeCd());
        for (String offerId : offerIds) {
            List<String> destIds = logisticsOfferDestinationDao.getDestIdByOfferId(offerId);
            if (destIds == null) {
                continue;
            }
            if (list.size() == destIds.size() && list.containsAll(destIds)) {
                throw new MyException(390, "重复", null);
            }
        }
        logisticsOfferBasisInfoDao.updateFba(ele);
    }

    @Override
    public void updateLittleById(LogicOfferBasisInfoLittilEnity ele) {
        String channelName = ele.getChannelName();
        String shipMentId = ele.getShipmentId();
        List<LogisticsOfferDestinationEnity> destList = ele.getDestinationList();
        List<String> list = new ArrayList();
        for (LogisticsOfferDestinationEnity enity : destList) {
            list.add(enity.getDestinationCd());
        }
        List<String> offerIds = logisticsOfferBasisInfoDao.getByNameAndShipId(channelName, shipMentId, ele.getOfferTypeCd());
        for (String offerId : offerIds) {
            List<String> destIds = logisticsOfferDestinationDao.getDestIdByOfferId(offerId);
            if (destIds == null) {
                continue;
            }
            if (list.size() == destIds.size() && list.containsAll(destIds)) {
                throw new MyException(390, "重复", null);
            }
        }
        logisticsOfferBasisInfoDao.updatelittle(ele);

    }


    /*public Map getLabelName(int labelCd){
        Result result = labelAttrService.getLabelName(labelCd);
        if(result.getCode()==200){
            Map map = (Map) result.getData();
            System.out.println(map);
            return  map;
        }
        return null;
    }*/

    @Override
    public List<LogisticsOfferBasisInfoEntity> getShipmentPrice(String offerId) throws Exception {
        String[] ids = offerId.split(",");
        String str = "";
        String endstr = "";
        for (int i = 0; i < ids.length; i++) {
            str = "'" + ids[i] + "'";
            if (i < ids.length - 1) {
                str += ",";
            }
            endstr += str;
        }
        List<LogisticsOfferBasisInfoEntity> basisList = logisticsOfferBasisInfoDao.getShipmentPrice(endstr);
        List<String> shipmentIdList = new ArrayList<>();
        Map shipmentNameMap = new HashMap();
        for (LogisticsOfferBasisInfoEntity ele : basisList) {
            shipmentIdList.add(ele.getShipmentId());
        }
        shipmentNameMap = shipmentListService.getShipmentListService(shipmentIdList);
        for (LogisticsOfferBasisInfoEntity ele : basisList) {
            ele.setEffectiveList(logisticsOfferEffectiveDao.get(ele.getOfferId(), ele.getDeleteStatus()));
            ele.setHistoryList(logisticsOfferHistoryInfoDao.get(ele.getOfferId(), ele.getDeleteStatus()));
            ele.setLabelList(logisticsAllowLabelRelDao.get(ele.getOfferId(), ele.getDeleteStatus()));
            ele.setShipMentName((String) shipmentNameMap.get(ele.getShipmentId()));
        }
        return basisList;
    }

    @Override
    public List<LogisticsOfferVo> findByShipmentId(String shipMentId, int offerTypeCd) {
        if (offerTypeCd != 0) {
            List<LogisticsOfferVo> list = logisticsOfferBasisInfoDao.findBOfferByshipMentId(shipMentId, offerTypeCd);
            for (LogisticsOfferVo logisticsOfferVo : list) {
                List<DestinationEnityVo> destinationEnityVoList = logisticsOfferDestinationDao.getDest(logisticsOfferVo.getOfferId());
                List<LogisticsOfferDestinationEnity> logisticsOfferDestinationEnityList = new ArrayList<>();
                for (DestinationEnityVo destinationEnityVo : destinationEnityVoList) {
                    LogisticsOfferDestinationEnity logisticsOfferDestinationEnity = new LogisticsOfferDestinationEnity();
                    logisticsOfferDestinationEnity.setDestinationCd(destinationEnityVo.getDestinationCd());
                    logisticsOfferDestinationEnity.setOfferId(destinationEnityVo.getOfferId());
                    logisticsOfferDestinationEnity.setShipmentId(shipMentId);
                    if (offerTypeCd == 1) {
                        logisticsOfferDestinationEnity.setDestinationName(destinationEnityVo.getDestinationName());
                    } else {
                        logisticsOfferDestinationEnity.setDestinationName(destinationEnityVo.getCountryName());
                    }
                    logisticsOfferDestinationEnityList.add(logisticsOfferDestinationEnity);
                }
                logisticsOfferVo.setLogisticsOfferDestinationEnityList(logisticsOfferDestinationEnityList);
            }
            return list;
        }
        return new ArrayList<>();
    }

    @Override
    public List<LogisticsOfferBasisInfoEntity> getOfferList(String[] offerIds) {
        StringBuilder builder = new StringBuilder();
        for (String offerId : offerIds) {
            builder.append("\"" + offerId + "\"" + ",");

        }
        String ids = builder.toString();
        if (ids.endsWith(",")) {
            int index = ids.lastIndexOf(",");
            ids = ids.substring(0, index);
        }
        List<LogisticsOfferBasisInfoEntity> list = logisticsOfferBasisInfoDao.getShipmentPrice(ids);
        return list;
    }

    @Override
    public List<Map<String, String>> getShipmentIdByOfferIDs(String[] offerIds) {
        StringBuilder builder = new StringBuilder();
        for (String offerId : offerIds) {
            builder.append("\"" + offerId + "\"" + ",");

        }
        String ids = builder.toString();
        if (ids.endsWith(",")) {
            int index = ids.lastIndexOf(",");
            ids = ids.substring(0, index);
        }
        return logisticsOfferBasisInfoDao.getShipMentIDs(ids);
    }

    @Override
    public List<SimpleOfferBasisInfoEnity> getAllOffers(int offerTypeCd) throws Exception {
        List<SimpleOfferBasisInfoEnity> simpleOfferBasisInfoEnities = logisticsOfferBasisInfoDao.getAllOffers(offerTypeCd);
        List<String> shipmentIdList = new ArrayList<>();
        Map shipmentNameMap = new HashMap();
        for (SimpleOfferBasisInfoEnity ele : simpleOfferBasisInfoEnities) {
            if (ele != null) {
                if (!shipmentIdList.contains(ele.getShipmentId())) {
                    shipmentIdList.add(ele.getShipmentId());
                }
            }
        }
        shipmentNameMap = shipmentListService.getShipmentListService(shipmentIdList);
        for (SimpleOfferBasisInfoEnity enity : simpleOfferBasisInfoEnities) {
            enity.setShipMentName((String) shipmentNameMap.get(enity.getShipmentId()));
        }
        return simpleOfferBasisInfoEnities;
    }

    @Override
    public Integer getStatusByOfferId(String offerId) {
        return logisticsOfferBasisInfoDao.getStatusByOfferId(offerId);
    }

    @Override
    public List<CarrierEnity> getAllCarriers(int offerTypeCd) {
        return logisticsOfferBasisInfoDao.getAllCarriers(offerTypeCd, 0);
    }

    @Override
    public List<ChannelNameEnity> getAllChannleName() {
        List<ChannelNameEnity> channelNameEnityList = logisticsOfferBasisInfoDao.getAllChannleName();
        return channelNameEnityList;
    }

    @Override
    public LinkedHashMap findLogisticByFba(String pageNum, String pageSize, int offerTypeCd, String[] fbaReplenishmentId, int channleTypeCd, String labelcds, String keyword) {
        Result repResult = replenishmentService.getFbaInfoBatch(fbaReplenishmentId);
        Set<Integer> labelSet = new HashSet<>();
        if (repResult.getCode() != 200) {
            throw new RuntimeException("外部接口异常");
        }
        List<Map> mapList = (List<Map>) repResult.getData();
        String zipCode = null;
        if (mapList != null && mapList.size() > 0) {
            zipCode = (String) mapList.get(0).get("zipCode");
        }
        for (Map map : mapList) {
            List<Map> mskuInfoList = (List<Map>) map.get("fbaMskuInfoList");
            if (mskuInfoList != null) {
                for (Map mskuMap : mskuInfoList) {
                    Map mskuInfoVOMap = (Map) mskuMap.get("mskuInfoVO");
                    List<Map> storeLabelMap = (List<Map>) mskuInfoVOMap.get("storeLabel");
                    for (Map storeMap : storeLabelMap) {
                        Integer labelCd = (Integer) storeMap.get("labelCd");
                        if (labelCd != null) {
                            labelSet.add(labelCd);
                        }
                    }
                }
            }
        }
        String destName = null;
        if (zipCode != null) {
            destName = zipCode.substring(0, 1);
        }
        if (destName != null) {
            if (!destName.matches("[0-9]")) {
                destName = null;
            }
        }
        String shipments = "";
        if (StringUtils.isNotEmpty(keyword)) {
            Result shipResult = shipMentService.getByName(keyword);
            if (shipResult.getCode() != 200) {
                throw new RuntimeException("物流商接口调用异常");
            }
            Map map = (Map) shipResult.getData();
            List<Map> shipmentEnterpriseEntityList = (List) map.get("shipmentEnterpriseEntityList");
            if (shipments == null || shipmentEnterpriseEntityList.size() <= 0) {
                shipments = null;
            } else {
                for (Map shipMap : shipmentEnterpriseEntityList) {
                    String shipmentId = (String) shipMap.get("shipmentId");
                    shipments += "'" + shipmentId + "'" + ",";
                }
                if (shipments.endsWith(",")) {
                    int index = shipments.lastIndexOf(",");
                    shipments = shipments.substring(0, index);
                }
            }
        }
        List<LogisticsOfferBasisInfoEntity> basisList = logisticsOfferBasisInfoDao.findByCond(channleTypeCd, 0, keyword, offerTypeCd, labelcds, shipments);
        Set<String> shipmentIds = new HashSet<>();
        if (basisList != null) {
            for (LogisticsOfferBasisInfoEntity basis : basisList) {
                List<LogisticsOfferEffectiveEntity> effeList = logisticsOfferEffectiveDao.get(basis.getOfferId(), basis.getDeleteStatus());
                basis.setEffectiveList(effeList);
                if (basis.getOfferTypeCd() == 1) {
                    List<LogisticsOfferHistoryInfoEntity> historyList = logisticsOfferHistoryInfoDao.get(basis.getOfferId(), basis.getDeleteStatus());
                    String chargeName = logisticsChargeTypeAttrDao.findChargeNameById(basis.getChargeTypeCd());
                    basis.setChargeTypeName(chargeName);
                    basis.setHistoryList(historyList);
                }
                List<LogisticsOfferDestinationEnity> destList = logisticsOfferDestinationDao.findByCond(basis.getOfferId(), basis.getDeleteStatus());
                for (LogisticsOfferDestinationEnity dest : destList) {
                    if (offerTypeCd == 1) {
                        String destionName = logisticsOfferDestinationDao.findDestNameById(dest.getDestinationCd());
                        dest.setDestinationName(destionName);
                    }
                }
                List<LogisticsAllowLabelRelEntity> labelList = logisticsAllowLabelRelDao.findByCond(0, basis.getOfferId(), basis.getDeleteStatus());
                basis.setDestinationList(destList);
                basis.setLabelList(labelList);
                if (!shipmentIds.contains(basis.getShipmentId())) {
                    shipmentIds.add(basis.getShipmentId());
                }
            }
        }
        StringBuilder ids = new StringBuilder();
        for (String shipmentId : shipmentIds) {
            ids.append(shipmentId + ",");
        }
//      通过上诉拼接的ids批量查询对应的物流商名称
        Result shipResult = shipMentService.getProducts(ids.toString());
        if (shipResult.getCode() == 200) {
            List objects = (List) shipResult.getData();
            Map<String, String> shipMentMap = new HashMap();
            for (Object shipMent : objects) {
                Map shipMap = (Map) shipMent;
                String shipMentId = (String) shipMap.get("shipmentId");
                String shipmentName = (String) shipMap.get("shipmentName");
                shipMentMap.put(shipMentId, shipmentName);
            }
            for (LogisticsOfferBasisInfoEntity basis : basisList) {
                basis.setShipMentName(shipMentMap.get(basis.getShipmentId()));
            }
        }
        List<LogisticsOfferBasisInfoEntity> result = fileter(basisList, destName, labelSet, channleTypeCd);
        int startIndex = 0;
        int endIndex = result.size();
        int size = Integer.valueOf(pageSize);
        int num = Integer.valueOf(pageNum);
        if (num > 0 && size > 0) {
            // 开始分页
            startIndex = (num - 1) * size;
            if (startIndex >= result.size()) {
                startIndex = 0;
            }
            endIndex = startIndex + size;
            if (endIndex > result.size()) {
                endIndex = result.size();
            }
        }

        int pages = 0;
        if (result.size() > 0 && size > 0) {
            pages = result.size() / size;
            if (result.size() % size > 0) {
                pages += 1;
            }
        }
        return PageData.pack(result.size(), pages, "logisticsOfferBasisInfoList", result.subList(startIndex, endIndex));
    }

    private List<LogisticsOfferBasisInfoEntity> fileter(List<LogisticsOfferBasisInfoEntity> basisList, String destName, Set<Integer> labelSet, int channleTypeCd) {
        List<LogisticsOfferBasisInfoEntity> logisticsOfferBasisInfoEntityList = new ArrayList<>();
        for (LogisticsOfferBasisInfoEntity logisticsOfferBasisInfoEntity : basisList) {
            if (destName != null) {
                List<LogisticsOfferDestinationEnity> destList = logisticsOfferBasisInfoEntity.getDestinationList();
                boolean haveName = false;
                for (LogisticsOfferDestinationEnity logisticsOfferDestinationEnity : destList) {
                    String destionname = logisticsOfferDestinationEnity.getDestinationName();
                    if (destName.equals(destionname)) {
                        haveName = true;
                        break;
                    }
                }
                if (!haveName) {
                    continue;
                }
            }
            if (labelSet != null && labelSet.size() > 0) {
                List<Integer> lableCdList = new ArrayList<>();
                List<LogisticsAllowLabelRelEntity> labelList = logisticsOfferBasisInfoEntity.getLabelList();
                for (LogisticsAllowLabelRelEntity logisticsAllowLabelRelEntity : labelList) {
                    if (logisticsAllowLabelRelEntity.getLabelCd() != null) {
                        lableCdList.add(logisticsAllowLabelRelEntity.getLabelCd());
                    }
                }
                if (!lableCdList.containsAll(labelSet)) {
                    continue;
                }
            }
            logisticsOfferBasisInfoEntityList.add(logisticsOfferBasisInfoEntity);
        }
        return logisticsOfferBasisInfoEntityList;
    }

}
