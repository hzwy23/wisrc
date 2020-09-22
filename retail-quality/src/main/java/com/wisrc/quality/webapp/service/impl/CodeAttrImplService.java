package com.wisrc.quality.webapp.service.impl;

import com.wisrc.quality.webapp.service.CodeAttrService;
import com.wisrc.quality.webapp.dao.CodeAttrDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CodeAttrImplService implements CodeAttrService {

    @Autowired
    private CodeAttrDao codeAttrDao;

    @Override
    public List<LinkedHashMap> getISA() {
        return codeAttrDao.getISA();
    }

    @Override
    public List<LinkedHashMap> getIMA() {
        return codeAttrDao.getIMA();
    }

    @Override
    public List<LinkedHashMap> getITA() {
        return codeAttrDao.getITA();
    }

    @Override
    public List<LinkedHashMap> getILA() {
        return codeAttrDao.getILA();
    }

    @Override
    public List<LinkedHashMap> getISPA() {
        return codeAttrDao.getISPA();
    }

    @Override
    public List<LinkedHashMap> getICRA() {
        return codeAttrDao.getICRA();
    }

    @Override
    public List<LinkedHashMap> getICA() {
        return codeAttrDao.getICA();
    }

    @Override
    public List<LinkedHashMap> getIFDA() {
        return codeAttrDao.getIFDA();
    }

    @Override
    public List<LinkedHashMap> getISTA() {
        return codeAttrDao.getISTA();
    }

    @Override
    public List<LinkedHashMap> getIIA() {
        return codeAttrDao.getIIA();
    }

    @Override
    public List<LinkedHashMap> getIIRA() {
        return codeAttrDao.getIIRA();
    }

    @Override
    public List<LinkedHashMap> getIJA() {
        return codeAttrDao.getIJA();
    }

}
