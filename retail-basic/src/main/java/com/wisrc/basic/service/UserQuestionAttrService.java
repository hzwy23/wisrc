package com.wisrc.basic.service;

import com.wisrc.basic.entity.UserQuestionAttrEntity;

import java.util.List;

public interface UserQuestionAttrService {

    void addImages(UserQuestionAttrEntity userQuestionAttrEntity);

    byte[] downloadImages(String obsName, String imagesUrl);

    List<UserQuestionAttrEntity> getImages(String questionId);
}
