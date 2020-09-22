package com.wisrc.quality.webapp.entity;

import lombok.Data;

@Data
public class InspectionItemCheckDetailsEntity {
    private String uuid;    //  uuid
    private String inspectionItemId;    //抽检项目ID
    private String inspectionContent;    //  测试内容
    private Integer inspectionQuantity;    //   测试数量
    private Integer cri;    // cri	cri
    private Integer maj;    //  maj	maj
    private Integer min;    //  min	min
    private Integer judgmentCd;    //  判定编码

    private String problemDescription;
    private String inspectionResults;

}
