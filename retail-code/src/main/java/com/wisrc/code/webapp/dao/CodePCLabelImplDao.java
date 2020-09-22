package com.wisrc.code.webapp.dao;

import com.wisrc.code.webapp.entity.CodeProductCharacteristicLabelEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CodePCLabelImplDao {
    @Select(" select product_label_cd,product_label_desc from code_product_characteristic_label ")
    List<CodeProductCharacteristicLabelEntity> findAll();

    @Update(" update code_product_characteristic_label set product_label_desc = #{productLabelDesc} where product_label_cd = #{productLabelCd} ")
    void update(CodeProductCharacteristicLabelEntity entity);

    @Insert(" insert into code_product_characteristic_label (product_label_cd,product_label_desc)values(#{productLabelCd},#{productLabelDesc}) ")
    void insert(CodeProductCharacteristicLabelEntity entity);

    @Delete(" delete from code_product_characteristic_label where product_label_cd = #{productLabelCd} ")
    void delete(Integer productLabelCd);
}
