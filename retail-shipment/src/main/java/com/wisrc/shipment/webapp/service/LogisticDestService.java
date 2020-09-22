package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.entity.FbaDestinationEnity;
import com.wisrc.shipment.webapp.entity.LittlePacketDestEnity;

import java.util.List;

public interface LogisticDestService {

    List<FbaDestinationEnity> getAllDest(String countryCd);

    List<LittlePacketDestEnity> getAllLittleDest();
}
