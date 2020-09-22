package com.wisrc.merchandise.dto.msku;

import lombok.Data;

import java.util.List;

@Data
public class MskuPageDTO {
    private List<GetMskuPageDTO> mskuList;
    private long totalNum;
    private List salesStatus;
    private List mskuStatus;
    private List teamList;
    private List dispatchStatus;
}
