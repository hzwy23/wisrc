package com.wisrc.basic.service.impl;

import com.wisrc.basic.dao.UserQuestionNoteDao;
import com.wisrc.basic.entity.UserQuestionNoteEntity;
import com.wisrc.basic.service.UserQuestionNoteService;
import com.wisrc.basic.vo.UserQuestionNoteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserQuestionNoteServiceImpl implements UserQuestionNoteService {

    @Autowired
    private UserQuestionNoteDao questionNoteDao;

    @Override
    public List<UserQuestionNoteVO> findAllNote(String questionId) {

        return questionNoteDao.findAllNote(questionId);
    }

    @Override
    public void addQuestinNote(UserQuestionNoteEntity userQuestionNoteEntity) {
        questionNoteDao.addQuestinNote(userQuestionNoteEntity);
    }
}
