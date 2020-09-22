package com.wisrc.basic.dao;

import com.wisrc.basic.entity.UserQuestionAttrEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserQuestionAttrDao {
    /**
     * 上传文件反馈图片
     *
     * @param userQuestionAttrEntity
     */
    @Insert("insert into user_question_attachment(uuid,question_id,obs_name,images_url) values(#{uuid},#{questionId},#{obsName},#{imagesUrl})")
    void addImages(UserQuestionAttrEntity userQuestionAttrEntity);

    /**
     * 通过问题id获取当前下的所有图片存储地址
     *
     * @param questionId
     * @return
     */
    @Select("select obs_name,images_url from user_question_attachment where question_id=#{questionId}")
    List<UserQuestionAttrEntity> getImages(@Param("questionId") String questionId);

}
