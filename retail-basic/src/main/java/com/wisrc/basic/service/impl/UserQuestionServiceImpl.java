package com.wisrc.basic.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.basic.service.proxy.ExternalEmployeeService;
import com.wisrc.basic.service.proxy.FileUploadService;
import com.wisrc.basic.dao.UserQuestionDao;
import com.wisrc.basic.dao.UserQuestionStarDao;
import com.wisrc.basic.entity.UserQuestionAttrEntity;
import com.wisrc.basic.entity.UserQuestionEntity;
import com.wisrc.basic.service.UserQuestionAttrService;
import com.wisrc.basic.service.UserQuestionNoteService;
import com.wisrc.basic.service.UserQuestionService;
import com.wisrc.basic.service.UserQuestionStarService;
import com.wisrc.basic.utils.PageData;
import com.wisrc.basic.utils.Result;
import com.wisrc.basic.utils.Time;
import com.wisrc.basic.utils.Toolbox;
import com.wisrc.basic.vo.AddUserQuestionVO;
import com.wisrc.basic.vo.UserQuestionNoteVO;
import com.wisrc.basic.vo.UserQuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class UserQuestionServiceImpl implements UserQuestionService {
    @Autowired
    private UserQuestionDao userQuestionDao;
    @Autowired
    private ExternalEmployeeService externalUserService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private UserQuestionStarService userQuestionStarService;
    @Autowired
    private UserQuestionNoteService userQuestionNoteService;
    @Autowired
    private UserQuestionAttrService userQuestionAttrService;
    @Autowired
    private UserQuestionStarDao userQuestionStarDao;


    @Override
    public LinkedHashMap findAllQuestion(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserQuestionVO> userQuestionVOList = userQuestionDao.findAllQuestion();
        for (UserQuestionVO userQuestionVO : userQuestionVOList) {
            String questionId = userQuestionVO.getQuestionId();
            String userId = userQuestionVO.getUserId();
            List<String> useridList = new ArrayList<>();
            useridList.add(userId);
            try {
                Result result = externalUserService.getUserBatch(useridList);
                List list = (List<String>) result.getData();
                for (int i = 0; i < list.size(); i++) {
                    Map map = (Map) list.get(i);
                    userQuestionVO.setUserName((String) map.get("userName"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<UserQuestionAttrEntity> userQuestionAttrEntityList = userQuestionAttrService.getImages(questionId);
            List<UserQuestionNoteVO> userQuestionNoteVOList = userQuestionNoteService.findAllNote(questionId);
            for (UserQuestionNoteVO userQuestionNoteVO : userQuestionNoteVOList) {
                String userIds = userQuestionNoteVO.getUserId();
                List<String> userIdsList = new ArrayList<>();
                userIdsList.add(userIds);
                try {
                    Result result = externalUserService.getUserBatch(userIdsList);
                    List list = (List<String>) result.getData();
                    for (int i = 0; i < list.size(); i++) {
                        Map map = (Map) list.get(i);
                        userQuestionNoteVO.setUserName((String) map.get("userName"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            userQuestionVO.setQuestionAttrEntityList(userQuestionAttrEntityList);
            userQuestionVO.setUserQuestionNoteVOList(userQuestionNoteVOList);
            UserQuestionEntity userQuestionEntity = findById(questionId);
            String obsName = userQuestionEntity.getObsName();
            if (obsName == null) {
                obsName = "matrix-replenishment";
            }
            String uuid = userQuestionEntity.getQuestionUrl();
            byte[] file = downloadFile(obsName, uuid);
            try {
                userQuestionVO.setFile(new String(file, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //判断当前问题创建者和点赞人id一样时 是否点过赞 0: 当前用户没有点赞 1：当前用户已经点赞
            int isStar = userQuestionStarDao.iflike(userId, questionId);
            userQuestionVO.setIsStar(isStar);
        }
        PageInfo pageInfo = new PageInfo(userQuestionVOList);
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "userQuestionEntityList", userQuestionVOList);
    }

    @Override
    public void addQuestion(AddUserQuestionVO addUserQuestionVO, String userId) {
        Result result = fileUploadService.uploadFile(addUserQuestionVO.getFile(), "txt", addUserQuestionVO.getObsName());
        if (result.getCode() != 200) {
            throw new RuntimeException("获取返回地址失败");
        }
        Map map = new HashMap();
        map = (Map) result.getData();
        //获取上传储存桶的名称和文件地址
        String uuid = (String) map.get("uuid");
        UserQuestionEntity userQuestionEntity = new UserQuestionEntity();
        userQuestionEntity.setUserId(userId);
        userQuestionEntity.setTitle(addUserQuestionVO.getTitle());
        userQuestionEntity.setQuestionUrl(uuid);
        userQuestionEntity.setObsName(addUserQuestionVO.getObsName());
        userQuestionEntity.setCreateTime(Time.getCurrentDateTime());
        userQuestionEntity.setStatusCd(2);
        userQuestionEntity.setStarCnt(0);
        String questionId = Toolbox.UUIDrandom();
        userQuestionEntity.setQuestionId(questionId);
        userQuestionDao.addQuestion(userQuestionEntity);
        //上传图片
        List<Map> imagesList = addUserQuestionVO.getImages();
        for (Map images : imagesList) {
            UserQuestionAttrEntity userQuestionAttrEntity = new UserQuestionAttrEntity();
            userQuestionAttrEntity.setUuid(Toolbox.UUIDrandom());
            userQuestionAttrEntity.setQuestionId(questionId);
            userQuestionAttrEntity.setObsName((String) images.get("obsName"));
            userQuestionAttrEntity.setImagesUrl((String) images.get("uuid"));
            userQuestionAttrService.addImages(userQuestionAttrEntity);
        }

    }

    @Override
    public void changeStatusCd(String questionId, int statusCd) {
        userQuestionDao.changeStatusCd(questionId, statusCd);
    }

    @Override
    public void changeStarCnt(String questionId, int starCnt) {
        userQuestionDao.changeStarCnt(questionId, starCnt);
    }

    /**
     * 获取存储桶当中的内容
     *
     * @param obsName 存储桶名称
     * @param uuid    返回地址
     * @return
     */
    @Override
    public byte[] downloadFile(String obsName, String uuid) {
        return fileUploadService.downloadFile(obsName, uuid);

    }

    @Override
    public UserQuestionEntity findById(String questionId) {
        return userQuestionDao.findById(questionId);
    }

}
