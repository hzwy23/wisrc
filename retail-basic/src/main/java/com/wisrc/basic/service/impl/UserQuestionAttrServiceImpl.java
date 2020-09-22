package com.wisrc.basic.service.impl;

import com.wisrc.basic.service.proxy.FileUploadService;
import com.wisrc.basic.dao.UserQuestionAttrDao;
import com.wisrc.basic.entity.UserQuestionAttrEntity;
import com.wisrc.basic.service.UserQuestionAttrService;
import com.wisrc.basic.utils.Toolbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserQuestionAttrServiceImpl implements UserQuestionAttrService {
    @Autowired
    private UserQuestionAttrDao userQuestionAttrDao;
    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public void addImages(UserQuestionAttrEntity userQuestionAttrEntity) {
        userQuestionAttrEntity.setUuid(Toolbox.UUIDrandom());
        userQuestionAttrDao.addImages(userQuestionAttrEntity);
    }

    @Override
    public byte[] downloadImages(String obsName, String imagesUrl) {
        return fileUploadService.downloadFile(obsName, imagesUrl);
    }

    @Override
    public List<UserQuestionAttrEntity> getImages(String questionId) {
        return userQuestionAttrDao.getImages(questionId);
    }


}
