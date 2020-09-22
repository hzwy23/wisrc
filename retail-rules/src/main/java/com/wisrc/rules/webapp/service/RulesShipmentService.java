package com.wisrc.rules.webapp.service;

import java.util.List;
import java.util.Map;

public interface RulesShipmentService {
    Map getChannelName(List offerIds) throws Exception;

    Map getShipment(List offerIds) throws Exception;

    List channelSelector() throws Exception;
}
