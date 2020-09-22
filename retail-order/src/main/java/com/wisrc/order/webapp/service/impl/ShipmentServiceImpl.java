package com.wisrc.order.webapp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisrc.order.webapp.dto.shipment.LogisticsOfferBasisInfoEntity;
import com.wisrc.order.webapp.dto.shipment.SimpleOfferBasisInfoEnity;
import com.wisrc.order.webapp.service.BasicService;
import com.wisrc.order.webapp.service.ShipmentOutsideService;
import com.wisrc.order.webapp.service.ShipmentService;
import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    @Autowired
    private ShipmentOutsideService shipmentOutsideService;
    @Autowired
    private BasicService basicService;

    @Override
    public Map getShipment(List offerIds) throws Exception {
        Map result = new HashMap();

        Result returnResult = shipmentOutsideService.getShipment(offerIds);

        if (returnResult.getCode() != 200) {
            throw new Exception(returnResult.getData().toString());
        }

        ObjectMapper mapper = new ObjectMapper();
        List<LogisticsOfferBasisInfoEntity> logisticsOffers = mapper.convertValue(returnResult.getData(), new TypeReference<List<LogisticsOfferBasisInfoEntity>>() {
        });

        for (LogisticsOfferBasisInfoEntity logisticsOffer : logisticsOffers) {
            Map relationship = new HashMap();
            relationship.put("channelName", logisticsOffer.getChannelName());
            relationship.put("shipmentName", logisticsOffer.getShipMentName());
            result.put(logisticsOffer.getOfferId(), relationship);
        }

        return result;
    }

    @Override
    public Map getChannelName(List offerIds) throws Exception {
        Map result = new HashMap();

        Result returnResult = shipmentOutsideService.getShipment(offerIds);

        if (returnResult.getCode() != 200) {
            throw new Exception(returnResult.getMsg());
        }

        ObjectMapper mapper = new ObjectMapper();
        List<LogisticsOfferBasisInfoEntity> logisticsOffers = mapper.convertValue(returnResult.getData(), new TypeReference<List<LogisticsOfferBasisInfoEntity>>() {
        });
        for (LogisticsOfferBasisInfoEntity logisticsOffer : logisticsOffers) {
            result.put(logisticsOffer.getOfferId(), logisticsOffer.getChannelName());
        }

        return result;
    }

    @Override
    public List channelSelector() throws Exception {
        List result = new ArrayList();

        Result channelResult = shipmentOutsideService.channelSelector(2);
        if (channelResult.getCode() != 200) {
            throw new Exception(channelResult.getMsg());
        }

        ObjectMapper mapper = new ObjectMapper();
        List<SimpleOfferBasisInfoEnity> channelList = mapper.convertValue(channelResult.getData(), new TypeReference<List<SimpleOfferBasisInfoEnity>>() {
        });

        for (SimpleOfferBasisInfoEnity getChannel : channelList) {
            result.add(ServiceUtils.idAndName(getChannel.getOfferId(), getChannel.getChannelName() + "【" + getChannel.getShipMentName() + "】"));
        }

        return result;
    }
}
